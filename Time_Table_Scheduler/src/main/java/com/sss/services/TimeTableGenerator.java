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

        System.out.println("gleba in first function 2 call at 0");

    }


    static void assignElectives(Section[] sections, Teacher[] teachers, Room[] rooms) {

        for (int section = 0; section < sections.length; section += 2) {
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!sections[section].timeTable.timetable[d][h].equals("null")) {
                        String sub = sections[section].timeTable.timetable[d][h];
                        for (Teacher t : teachers) {
                            if (t.facultyResponse.subject1.equals(sub)) {
                                t.assign(d, h, (section / 2) + "-" + sub);
                                rooms[section / 2].assign(d, h, sub + "-" + t.facultyResponse.facultyid);
                            }
                            if (t.facultyResponse.subject2.equals(sub)) {
                                t.assign(d, h, (section / 2) + "-" + sub);
                                rooms[section / 2].assign(d, h, sub + "-" + t.facultyResponse.facultyid);
                            }
                            if (t.facultyResponse.subject3.equals(sub)) {
                                t.assign(d, h, (section / 2) + "-" + sub);
                                rooms[section / 2].assign(d, h, sub + "-" + t.facultyResponse.facultyid);
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
                    if (!timetable[d][h].equals("null")) {
                        days[i] = d;
                        hours[i] = h;
                        i++;
                    }
                }
            }
            DaysHours daysHours = new DaysHours(days, hours);
            if (i != 0) {
                firstYearHelper(pq, daysHours, firstYear.groupId.charAt(0),
                        Integer.parseInt(firstYear.groupId.substring(1)));
            }
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
        t.assign(days[0], hours[0], "MA102-" + group + (i));
        t.assign(days[1], hours[1], "MA102-" + group + (i));
        t.assign(days[2], hours[2], "MA102-" + group + (i));
        t.assign(days[3], hours[3], "MA102-" + group + (i));
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
                            && sections[section].timeTable.timetable[d][h].substring(9).equals("lab")) {
                        while (true) {
                            int n = rand.nextInt(N);

                            if (teachers[n].isFree(d, h) && teachers[n].isFree(d, h + 1) &&
                                    sections[section].isFree(d, h) && sections[section].isFree(d, h + 1)
                                    && teachers[n].labHours >= 2) {
                                teachers[n].assign(d, h, sections[section].timeTable.timetable[d][h]);
                                teachers[n].assign(d, h + 1, sections[section].timeTable.timetable[d][h]);
                                lab.assign(d, h, sections[section].timeTable.timetable[d][h] + "-" + teachers[n].facultyResponse.facultyid);
                                lab.assign(d, h + 1, sections[section].timeTable.timetable[d][h] + "-" + teachers[n].facultyResponse.facultyid);
                                teachers[n].labHours -= 2;
                                //h++;
                                break;
                            }
                        }
                        while (true) {
                            int n = rand.nextInt(N);

                            if (teachers[n].facultyResponse.noOfHours == 6 &&
                                    teachers[n].isFree(d, h) && teachers[n].isFree(d, h + 1)
                                    && teachers[n].labHours >= 2) {
                                teachers[n].assign(d, h, sections[section].timeTable.timetable[d][h]);
                                teachers[n].assign(d, h + 1, sections[section].timeTable.timetable[d][h]);
                                lab.assignSecondTeacher(d, h, teachers[n].facultyResponse.facultyid);
                                lab.assignSecondTeacher(d, h + 1, teachers[n].facultyResponse.facultyid);
                                h++;
                                teachers[n].labHours -= 2;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    static void assignLabs(Teacher[] teachers, Room lab, Section[] sections) {

        HashSet<String> coursesWithLab = new HashSet<>();

        for (Teacher t : teachers) {

            String sub = t.facultyResponse.subject1;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }
            sub = t.facultyResponse.subject2;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }
            sub = t.facultyResponse.subject3;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }

        }
        Random rand = new Random();
        int N = teachers.length;
        for (String sub : coursesWithLab) {

            int year = Integer.parseInt(sub.charAt(2) + "");
            int section;
            if (year == 2) {
                section = 0;
            } else if (year == 3) {
                section = 2;
            } else if (year == 4) {
                section = 4;
            } else {
                section = -1;
            }


            for (int batch = 1; batch <= 2; batch++) {
                int prev_d = -1, prev_n = -1, prev_m = -1;
                int d=-1, h=-1, n=-1, m=-1;

                for (int r = 1; r <= 2; r++) {


                    int counter1 = 0;
                    while (true) {
                        counter1++;
                        if (counter1 == 200)
                            break;
                        d = rand.nextInt(5);
                        h = rand.nextInt(9);
                        n = rand.nextInt(N);

                        int freeHours = teachers[n].hoursToAssign;

                        if (d != prev_d && n != prev_n && teachers[n].isFree(d, h) && teachers[n].isFree(d, h + 1) &&
                                lab.isFree(d, h) && lab.isFree(d, h + 1) &&
                                sections[section].isFree(d, h) && sections[section].isFree(d, h + 1)
                                && teachers[n].labHours >= 2 && teachers[n].facultyResponse.noOfHours != 6
                                && teachers[n].hoursToAssign >= 1) {

                            System.out.println(" Lab being assigned to teacher #1 ");
                            sections[section].subjects.put(sub + "lab", 1);
                            teachers[n].assign(d, h, "lab-" + matchSection(section) + "-" + sub + " lab");
                            if (freeHours > 1)
                                teachers[n].assign(d, h + 1, "lab-" + matchSection(section) + "-" + sub + " lab");
                            lab.assign(d, h, section + "-" + sub + "-" + teachers[n].facultyResponse.facultyid);
                            if (freeHours > 1)
                                lab.assign(d, h + 1, section + "-" + sub + "-" + teachers[n].facultyResponse.facultyid);
                            sections[section].assign(d, h,
                                    "lab-" + section + "-" + sub);
                            sections[section].assign(d, h + 1,
                                    "lab-" + section + "-" + sub);
                            teachers[n].labHours -= Math.min(freeHours, 2);
                            break;
                        }
                    }
                    counter1 = 0;
                    while (true) {
                        counter1++;
                        if (counter1 == 200)
                            break;
                        m = rand.nextInt(N);
                        if (m != prev_m && m != n && teachers[m].facultyResponse.noOfHours == 6 &&
                                teachers[m].facultyResponse.designation.equals("Research Scholar") &&
                                teachers[m].isFree(d, h) && teachers[m].isFree(d, h + 1)
                                && teachers[m].labHours >= 2 && teachers[m].hoursToAssign >= 1) {
                            System.out.println(" Lab being assigned to teacher #2 ");

                            int freeHours = teachers[m].hoursToAssign;

                            teachers[m].assign(d, h, "lab-" + section + "-" + sub);
                            if (freeHours > 1)
                                teachers[m].assign(d, h + 1, "lab-" + section + "-" + sub);
                            lab.assignSecondTeacher(d, h, teachers[m].facultyResponse.facultyid);
                            if (freeHours > 1)
                                lab.assignSecondTeacher(d, h + 1, teachers[m].facultyResponse.facultyid);
                            teachers[m].labHours -= Math.min(2, freeHours);
                            break;
                        }
                    }
                    prev_d = d;
                    prev_n = n;
                    prev_m = m;

                }
                section++;
            }

        }

    }


    static void assignPhdLectures(Teacher[] teachers, Room[] rooms, Section[] sections) {
        for (PhdGroup phdGroup : phdData) {
            String[][] timetable = phdGroup.timeTable.timetable;

            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!timetable[d][h].equals("null")) {
                        String sub = timetable[d][h];
                        for (Teacher t : teachers) {
                            if (t.facultyResponse.subject1.equals(sub)) {
                                t.assign(d, h, sub);
                            }
                            if (t.facultyResponse.subject2.equals(sub)) {
                                t.assign(d, h, sub);
                            }
                            if (t.facultyResponse.subject3.equals(sub)) {
                                t.assign(d, h, sub);
                            }
                        }
                    }
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

                int year = Integer.parseInt(sub.charAt(2) + "");
                if (year < 2 || year > 4 || sub.length() != 5) {
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
                                t.assign(i, j, room + "-" + matchSection(section) + "-" + sub);
                                rooms[room].assign(i, j, matchSection(section) + "-" + sub + "-" + t.facultyResponse.facultyid);
                                sections[section].assign(i, j, room + "-" + sub + "-" + t.facultyResponse.facultyid);
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
                                    t.assign(i, j, room + "-" + matchSection(section) + "-" + sub);
                                    rooms[room].assign(i, j, matchSection(section) + "-" + sub + "-" + t.facultyResponse.facultyid);
                                    sections[section].assign(i, j, room + "-" + sub + "-" + t.facultyResponse.facultyid);
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
                                    t.assign(i, j, room + "-" + matchSection(section) + "-" + sub);
                                    rooms[room].assign(i, j, matchSection(section) + "-" + sub + "-" + t.facultyResponse.facultyid);
                                    sections[section].assign(i, j, room + "-" + sub + "-" + t.facultyResponse.facultyid);
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
                                t.assign(i, j, room + "-" + matchSection(section) + "-" + sub);
                                rooms[room].assign(i, j, matchSection(section) + "-" + sub + "-" + t.facultyResponse.facultyid);
                                sections[section].assign(i, j, room + "-" + sub + "-" + t.facultyResponse.facultyid);
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


    static public String matchSection(int i) {
        if (i == 0) {
            return "2A";
        } else if (i == 1) {
            return "2B";
        } else if (i == 2) {
            return "3A";
        } else if (i == 3) {
            return "3B";
        } else if (i == 4) {
            return "4A";
        } else if (i == 5) {
            return "4B";
        }
        return "2A";
    }

    static int getSection(String sub, Section[] sections) {
        int year = Integer.parseInt(sub.charAt(2) + "");
        int section;
        if (year == 2) {
            section = 0;
        } else if (year == 3) {
            section = 2;
        } else {
            section = 4;
        }
        if (sections[section].subjects.containsKey(sub)) {
            section++;
        } else {
            sections[section].subjects.put(sub, 1);
        }
        return section;
    }


    public void assignTrainingSeminar(Section[] sections, Teacher[] teachers) {
        int N = teachers.length;
        Random random = new Random();
        HashSet<Integer> hashSet = new HashSet<>();
        for (int s = 4; s <= 5; s++) {
            Boolean assigned = false;
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 9; h++) {
                    int n = random.nextInt(N);
                    int m = random.nextInt(N);
                    while (teachers[n].facultyResponse.noOfHours == 6 || teachers[m].facultyResponse.noOfHours == 6
                            || hashSet.contains(n) || hashSet.contains(m)) {
                        if (teachers[n].facultyResponse.noOfHours == 6 || hashSet.contains(n)) {
                            n = random.nextInt(N);
                        }
                        if (teachers[m].facultyResponse.noOfHours == 6 || hashSet.contains(m)) {
                            m = random.nextInt(N);
                        }

                    }
                    if (n != m && teachers[n].hoursToAssign >= 2 && teachers[m].hoursToAssign >= 2 && teachers[n].isFree(d, h) && teachers[m].isFree(d, h) &&
                            teachers[n].isFree(d, h + 1) && teachers[m].isFree(d, h + 1) &&
                            sections[s].isFree(d, h)
                            && sections[s].isFree(d, h + 1)) {
                        teachers[n].assign(d, h, matchSection(s) + "-Training Seminar");
                        teachers[m].assign(d, h, matchSection(s) + "-Training Seminar");
                        teachers[n].assign(d, h + 1, matchSection(s) + "-Training Seminar");
                        teachers[m].assign(d, h + 1, matchSection(s) + "-Training Seminar");
                        sections[s].assign(d, h, " Training Seminar " + teachers[n].facultyResponse.facultyid
                                + "-" + teachers[m].facultyResponse.facultyid);
                        sections[s].assign(d, h + 1, " Training Seminar " + teachers[n].facultyResponse.facultyid
                                + "-" + teachers[m].facultyResponse.facultyid);
                        assigned = true;
                        hashSet.add(n);
                        hashSet.add(m);
                        break;
                    }
                }
                if (assigned)
                    break;
            }
        }
    }


    public void assignMajorProj(Teacher[] teachers, Section[] sections) {
        // divide list of teachers into two
        // assigning first half to section a, second to b
        // call this function first


        ArrayList<Teacher> teachersWithMajorProj = new ArrayList<>();
        for (Teacher t : teachers) {
            if (!t.facultyResponse.designation.equals("Research Scholar")
                    && !t.facultyResponse.designation.equals("Guest Faculty")
                    && !t.facultyResponse.designation.equals("UGC Prof")
                    && t.labHours   >=3) {
                teachersWithMajorProj.add(t);
            }
        }
        int len = teachersWithMajorProj.size();

        boolean assigned = false;
        for (int d = 0; d < 5; d++) {
            for (int h = 0; h < 8; h++) {
                if (sections[4].isFree(d, h) && sections[4].isFree(d, h + 1)
                        && sections[4].isFree(d, h + 2)) {
                    boolean allFree = true;
                    for (int t = 0; t < len / 2; t++) {
                        if (!teachersWithMajorProj.get(t).isFree(d, h) ||
                                !teachersWithMajorProj.get(t).isFree(d, h + 1) ||
                                !teachersWithMajorProj.get(t).isFree(d, h + 2)) {
                            allFree = false;
                            break;
                        }
                    }
                    if (allFree) {
                        assigned = true;
                        for (int t = 0; t < len / 2; t++) {
                            teachersWithMajorProj.get(t).assign(d, h, "Major Project - 4A");
                            teachersWithMajorProj.get(t).assign(d, h + 1, "Major Project - 4A");
                            teachersWithMajorProj.get(t).assign(d, h + 2, "Major Project - 4A");
                        }
                        sections[4].assign(d, h, "Major Project");
                        sections[4].assign(d, h + 1, "Major Project");
                        sections[4].assign(d, h + 2, "Major Project");
                    }

                }
                if (assigned)
                    break;
            }
            if (assigned)
                break;
        }

        assigned = false;
        for (int d = 0; d < 5; d++) {
            for (int h = 0; h < 8; h++) {
                if (sections[5].isFree(d, h) && sections[5].isFree(d, h + 1)
                        && sections[5].isFree(d, h + 2)) {
                    boolean allFree = true;
                    for (int t = len / 2; t < len; t++) {
                        if (!teachersWithMajorProj.get(t).isFree(d, h) ||
                                !teachersWithMajorProj.get(t).isFree(d, h + 1) ||
                                !teachersWithMajorProj.get(t).isFree(d, h + 2)) {
                            allFree = false;
                            break;
                        }
                    }
                    if (allFree) {
                        assigned = true;
                        for (int t = len / 2; t < len; t++) {
                            teachersWithMajorProj.get(t).assign(d, h, "Major Project - 4B");
                            teachersWithMajorProj.get(t).assign(d, h + 1, "Major Project - 4B");
                            teachersWithMajorProj.get(t).assign(d, h + 2, "Major Project - 4B");
                        }
                        sections[5].assign(d, h, "Major Project");
                        sections[5].assign(d, h + 1, "Major Project");
                        sections[5].assign(d, h + 2, "Major Project");
                    }

                }
                if (assigned)
                    break;
            }
            if (assigned)
                break;
        }

    }


    public OverallTT generateTimeTable() {

        System.out.println("gleba in first function 2 call at 1");

        Room[] rooms = new Room[4];
        for (int i = 0; i < 4; i++) {
            rooms[i] = new Room(i);
        }
        Teacher[] teachers = new Teacher[teachersData.size()];
        for (int i = 0; i < teachersData.size(); i++) {
            teachers[i] = new Teacher(teachersData.get(i));
        }

        System.out.println("gleba in first function 2 call at 2");

        Section[] sectionsArray = new Section[sections.size()];
        for (int i = 0; i < sections.size(); i++) {
            sectionsArray[i] = sections.get(i);
        }

        System.out.println("gleba in first function 2 call at 3");

        assignElectives(sectionsArray, teachers, rooms);

        System.out.println("gleba in first function 2 call at 4");


        assignPhdLectures(teachers, rooms, sectionsArray);

        System.out.println("gleba in first function 2 call at 5");

        assignDecLabs(teachers, rooms[3], sectionsArray);

        System.out.println("gleba in first function 2 call at 6");

        assignTeachersToFirstYear(teachers);

        System.out.println("gleba in first function 2 call at 7");

        assignMajorProj(teachers, sectionsArray);

        System.out.println("gleba in first function 2 call at 8");
        assignDccCourses(teachers, rooms, sectionsArray);


        System.out.println("gleba in first function 2 call at 8.1");


        System.out.println("gleba in first function 2 call at 9");


        // assign training seminar after major project
        assignTrainingSeminar(sectionsArray, teachers);

        System.out.println("gleba in first function 2 call at 9.2");

        // assign labs at last
        assignLabs(teachers, rooms[3], sectionsArray);


        OverallTT overallTT = new OverallTT();

        for (Teacher t : teachers) {
            overallTT.facultyResponses.add(t.facultyResponse);
        }

        System.out.println("gleba in first function 2 call at 10");
        overallTT.sections = sections;


        for (Room room : rooms) {
            overallTT.rooms.add(room);
        }

        System.out.println("gleba in first function 2 call at 11");


        for (int i = 0; i < overallTT.facultyResponses.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("null")) {
                        overallTT.facultyResponses.get(i).timeTable.timetable[j][k] = "-";
                    }

                    if (overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("0")) {
                        overallTT.facultyResponses.get(i).timeTable.timetable[j][k] = "X";
                    }
                }
            }
        }


        int count = 0;
        int pid = 0;
        for (int i = 0; i < overallTT.facultyResponses.size(); ++i) {

            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.facultyResponses.get(i).facultyid.equals("payal") && !overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("-") && !overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("X")) {
                        pid = i;
                        count++;
                    }
                }
            }
            if (count > 0) {
                break;
            }
        }
        System.out.println("payal count = " + count);
        if (count == 4) {
            boolean find = false;
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 9; ++k) {
                    if (overallTT.facultyResponses.get(pid).timeTable.timetable[j][k].equals("-") && overallTT.facultyResponses.get(pid).timeTable.timetable[j][k + 1].equals("-")) {
                        overallTT.facultyResponses.get(pid).timeTable.timetable[j][k] = "lab-3B-MC302 lab";
                        overallTT.facultyResponses.get(pid).timeTable.timetable[j][k + 1] = "lab-3B-MC302 lab";
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
        }

        System.out.println("Size of First Year = " + firstYearData.size());
        return overallTT;
    }
}