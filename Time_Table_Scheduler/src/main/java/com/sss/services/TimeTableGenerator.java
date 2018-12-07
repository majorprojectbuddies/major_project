package com.sss.services;


import com.sss.classModel.*;
import org.springframework.stereotype.Service;

import java.util.*;


public class TimeTableGenerator {


    public static ArrayList<FacultyResponse> teachersData;
    public static HashMap<String, Course> courseDataHM;
    public static ArrayList<FirstYearGroup> firstYearData;
    public static ArrayList<PhdGroup> phdData;
    public static ArrayList<Section> sections;


    public TimeTableGenerator(ArrayList<FacultyResponse> teachersData,
                              ArrayList<Course> courseDataList,
                              ArrayList<FirstYearGroup> firstYearData,
                              ArrayList<PhdGroup> phdData,
                              ArrayList<Section> sections) {
        this.teachersData = teachersData;
        this.courseDataHM = new HashMap<>();
        for (Course course : courseDataList) {
            this.courseDataHM.put(course.courseId, course);
        }
        this.firstYearData = firstYearData;
        this.phdData = phdData;

        this.sections = sections;


    }


    static void assignElectives(Section[] sections, Teacher[] teachers, Room[] rooms) {

        for (int section = 0; section < sections.length; section++) {
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (sections[section].timeTable.timetable[d][h] != "null") {
                        String sub = sections[section].timeTable.timetable[d][h];
                        for (Teacher t : teachers) {
                            if (t.facultyResponse.subject1.equals(sub)) {
                                t.assignRoom(d, h, (section / 2) + "");
                                rooms[section / 2].assignTeacher(d, h, t.facultyResponse.facultyid);
                            }
                        }
                    }
                }
            }
        }
    }


    static class sortTeachersByRemainingHours implements Comparator<Teacher> {
        public int compare(Teacher a, Teacher b) {
            return b.hoursToAssign - a.hoursToAssign;
        }
    }

    static void assignTeachersToFirstYear(Teacher[] teachers) {
        /*
         * 8 0 9 1 10 2 11 3 12 4 1 5 2 6 3 7 4 8 5 9
         */

        int n = teachers.length;

        PriorityQueue<Teacher> pq = new PriorityQueue<>(n, new sortTeachersByRemainingHours());
        for (Teacher t : teachers) {
            pq.add(t);
        }

        for (FirstYearGroup firstYear : firstYearData) {
            String[][] timetable = firstYear.timeTable.timetable;
            int i = 0;
            int[] days = new int[4];
            int[] hours = new int[4];

            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (timetable[d][h] != "null") {
                        days[i] = d;
                        hours[i] = h;
                    }
                }
            }
            DaysHours daysHours = new DaysHours(days, hours);
            if(i!=0)
            firstYearHelper(pq, daysHours, firstYear.groupId.charAt(0),
                    Integer.parseInt(firstYear.groupId.substring(1)));
        }


    }

    static void firstYearHelper(PriorityQueue<Teacher> pq,
                                DaysHours daysHours, char group, int i) {
        int[] days = daysHours.days;
        int[] hours = daysHours.hours;

        Teacher t = pq.poll();
        ArrayList<Teacher> al = new ArrayList<>();
        al.add(t);
        while (true) {
            if ((t.isFree(days[0], hours[0]) && t.isFree(days[1], hours[1])) &&
                    (t.isFree(days[2], hours[2]) && t.isFree(days[3], hours[3]))
                    && t.current1Batches < t.total1Batches) {
                break;
            }
            t = pq.poll();
            al.add(t);
        }
        t.assignRoom(days[0], hours[0], "" + group + (i + 1));
        t.assignRoom(days[1], hours[1], "" + group + (i + 1));
        t.assignRoom(days[2], hours[2], "" + group + (i + 1));
        t.assignRoom(days[3], hours[3], "" + group + (i + 1));
        t.current1Batches++;
        for (Teacher teacher : al) {
            pq.add(teacher);
        }
    }

    static class DaysHours {
        int[] days;
        int[] hours;

        DaysHours(int[] days, int[] hours) {
            this.days = days;
            this.hours = hours;
        }
    }


    static void assignDecLabs(Teacher[] teachers, Room lab, Section[] sections) {
        Random rand = new Random();
        int N = teachers.length;
        for (int section = 0; section < sections.length; section++) {
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!sections[section].timeTable.timetable[d][h].equals("null")
                            && sections[section].timeTable.timetable[d][h].equals("lab")) {
                        int n = rand.nextInt(N);
                        if (teachers[n].isFree(d, h) && teachers[n].isFree(d, h + 1) &&
                                sections[section].isFree(d, h) && sections[section].isFree(d, h + 1)) {
                            teachers[n].assignRoom(d, h, "lab3");
                            teachers[n].assignRoom(d, h + 1, "lab3");
                            h++;
                            break;
                        }
                    }
                }
            }
        }
    }


    static void assignLabs(Teacher[] teachers, Room lab, Section[] sections) {

        HashMap<String, Integer> coursesWithLab = new HashMap<>();

        for (Teacher t : teachers) {

            String sub = t.facultyResponse.subject1;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.put(sub, 1);
            }
            sub = t.facultyResponse.subject2;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.put(sub, 1);
            }
            sub = t.facultyResponse.subject3;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.put(sub, 1);
            }

        }
        Random rand = new Random();
        int N = teachers.length;
        for (String sub : coursesWithLab.keySet()) {

            while (true) {
                int d = rand.nextInt(5);
                int h = rand.nextInt(9);
                int n = rand.nextInt(N + 1);
                int section = getSection(sub + "lab", sections);
                if (teachers[n].isFree(d, h) && teachers[n].isFree(d, h + 1) &&
                        lab.isFree(d, h) && lab.isFree(d, h + 1) &&
                        sections[section].isFree(d, h) && sections[section].isFree(d, h + 1)) {
                    sections[section].subjects.put(sub + "lab", 1);
                    teachers[n].assignRoom(d, h, "lab3");
                    teachers[n].assignRoom(d, h + 1, "lab3");
                    lab.assignTeacher(d, h, teachers[n].facultyResponse.facultyid);
                    lab.assignTeacher(d, h + 1, teachers[n].facultyResponse.facultyid);
                    sections[section].assignTeacher(d, h + 1,
                            teachers[n].facultyResponse.facultyid);
                    sections[section].assignTeacher(d, h + 1,
                            teachers[n].facultyResponse.facultyid);
                    break;
                }
            }
        }

    }


    static void assignDccCourses(Teacher[] teachers, Room[] rooms, Section[] sections) {

        // TODO change for loop by priority queue
        for (Teacher t : teachers) {

            String sub;
            for (int sub_n = 1; sub_n <= 3; sub_n++) {
                if (sub_n == 1) {
                    sub = t.facultyResponse.subject1;
                } else if (sub_n == 2) {
                    sub = t.facultyResponse.subject2;
                } else {
                    sub = t.facultyResponse.subject3;
                }

                if (sub.equals("null")) {
                    continue;
                }

                int section = getSection(sub, sections);
                int room = section / 2;

                int subjectHours = courseDataHM.get(sub).tutHours;
                for (int hour = 1; hour <= subjectHours; hour++) {
                    boolean assigned = false;

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (!t.days[i] && !t.slots[j] && sections[section].isFree(i, j)
                                    && rooms[room].isFree(i, j)) {
                                t.assignRoom(i, j, room + "");
                                rooms[room].assignTeacher(i, j, t.facultyResponse.facultyid);
                                sections[section].assignTeacher(i, j, t.facultyResponse.facultyid);
                                assigned = true;
                                break;
                            }
                        }
                        if (assigned)
                            break;
                    }
                    if (assigned)
                        continue;

                    for (int i = 0; i < 5; i++) {
                        if (!t.days[i]) {
                            for (int j = 0; j < 10; j++) {
                                if (t.isFree(i, j) && sections[section].isFree(i, j)
                                        && rooms[room].isFree(i, j)) {
                                    t.assignRoom(i, j, room + "");
                                    rooms[room].assignTeacher(i, j, t.facultyResponse.facultyid);
                                    sections[section].assignTeacher(i, j, t.facultyResponse.facultyid);
                                    assigned = true;
                                    break;
                                }
                            }
                        }

                        if (assigned)
                            break;
                    }
                    if (assigned)
                        continue;

                    for (int j = 0; j < 10; j++) {
                        if (!t.slots[j]) {
                            for (int i = 0; i < 5; i++) {
                                if (t.isFree(i, j) && sections[section].isFree(i, j)
                                        && rooms[room].isFree(i, j)) {
                                    t.assignRoom(i, j, room + "");
                                    rooms[room].assignTeacher(i, j, t.facultyResponse.facultyid);
                                    sections[section].assignTeacher(i, j, t.facultyResponse.facultyid);
                                    assigned = true;
                                    break;
                                }
                            }
                        }

                        if (assigned)
                            break;
                    }
                    if (assigned)
                        continue;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (t.isFree(i, j) && sections[section].isFree(i, j)
                                    && rooms[room].isFree(i, j)) {
                                t.assignRoom(i, j, room + "");
                                rooms[room].assignTeacher(i, j, t.facultyResponse.facultyid);
                                sections[section].assignTeacher(i, j, t.facultyResponse.facultyid);
                                assigned = true;
                                break;
                            }
                        }
                        if (assigned)
                            break;
                    }

                }

            }
        }
    }

    static int getSection(String sub, Section[] sections) {
        int year = Integer.parseInt(sub.charAt(0) + "");
        int section;
        if (year == 2) {
            section = 0;
        } else if (year == 3) {
            section = 2;
        } else {
            section = 3;
        }
        if (sections[section].subjects.containsKey(sub)) {
            section++;
        }
        return section;
    }




    public OverallTT generateTimeTable(){
        Room[] rooms = new Room[4];
        for(int i=0;i<4;i++){
            rooms[i]=new Room(i);
        }
        Teacher[] teachers= new Teacher[teachersData.size()];
        for(int i=0;i<teachersData.size();i++){
            teachers[i]=new Teacher(teachersData.get(i));
        }
        Section[] sectionsArray = new Section[sections.size()];
        for(int i=0;i<sections.size();i++){
            sectionsArray[i]=sections.get(i);
        }
        assignElectives(sectionsArray, teachers, rooms);
        assignDecLabs(teachers, rooms[3], sectionsArray);
        assignTeachersToFirstYear(teachers);
        assignLabs(teachers, rooms[3], sectionsArray);
        assignDccCourses(teachers, rooms, sectionsArray);

        OverallTT overallTT = new OverallTT();

        for(Teacher t : teachers){
            overallTT.facultyResponses.add(t.facultyResponse);
        }

        overallTT.sections=sections;
        for(Room room : rooms){
            overallTT.rooms.add(room);
        }

        return overallTT;
    }
}