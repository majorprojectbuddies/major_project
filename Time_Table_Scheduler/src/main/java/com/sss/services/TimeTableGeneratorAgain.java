package com.sss.services;

import com.sss.classModel.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeTableGeneratorAgain {

    public static ArrayList<FacultyResponse> teachersData;
    public static HashMap<String, Course> courseDataHM;
    public static ArrayList<FirstYearGroup> firstYearData;
    public static ArrayList<PhdGroup> phdData;
    public static ArrayList<Section> sections;
    public static ArrayList<FacultyResponse> unfreezedTeachersData;
    public static ArrayList<FacultyResponse> freezedTeachersData;
    public static ArrayList<FirstYearGroup> freezedFirstYearData;
    public static ArrayList<FirstYearGroup> unfreezedFirstYearData;
    public static Map<String, Pair<Integer, Integer>> decLabSlotToBeAssigned;
    public Room[] rooms;
    public static ArrayList<String> decRemainingLabList;

    public TimeTableGeneratorAgain(ArrayList<FacultyResponse> teachersData,
                              ArrayList<Course> courseDataList,
                              ArrayList<FirstYearGroup> firstYearData,
                              ArrayList<PhdGroup> phdData,
                              ArrayList<Section> sections,ArrayList<FacultyResponse> unfreezedTeachersData,
                                   ArrayList<FacultyResponse> freezedTeachersData, ArrayList<FirstYearGroup> freezedFirstYearData,
                                   ArrayList<FirstYearGroup> unfreezedFirstYearData ,
                                   Map<String,Pair<Integer,Integer>> decLabSlotToBeAssigned, Room[] rooms,
                                   ArrayList<String> decRemainingLabList) {
        this.teachersData = teachersData;
        this.courseDataHM = new HashMap<>();
        for (Course course : courseDataList) {
            this.courseDataHM.put(course.courseId, course);
        }
        this.firstYearData = firstYearData;
        this.phdData = phdData;
        this.sections = sections;
        this.unfreezedFirstYearData = unfreezedFirstYearData;
        this.freezedFirstYearData = freezedFirstYearData;
        this.unfreezedTeachersData = unfreezedTeachersData;
        this.freezedTeachersData = freezedTeachersData;
        this.decLabSlotToBeAssigned = decLabSlotToBeAssigned;
        this.rooms = rooms;
        this.decRemainingLabList = decRemainingLabList;
    }

    //FORMAT FOR TEACHER
    //format -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)

    //FORMAT FOR SECTIONS
    //format -  (MC312(SLOT-A)LAB:Lab:COMPUTATION LAB)
    //format -  (MC324:Lecture:1:PAYAL)
    //format -  (MC318(SLOT-A):Lecture:2:PAYAL)
    //format -  (MC318:Lab:COMPUTATION LAB)


    //FORMAT FOR ROOMS
    //format -  (MC312(SLOT-A)LAB:t2:Lab)
    //format -  (MC324:t1:Lecture:PAYAL)
    //format -  (MC318(SLOT-A):t2:Lecture:PAYAL)
    //format -  (MC318:t2:Lab)

    //ASSIGN ELECTIVES
    static void assignElectives(Section[] sections, Teacher[] unfreezedTeachers, Room[] rooms) {

        for (int section = 0; section < sections.length; section += 2) {
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!sections[section].timeTable.timetable[d][h].equals("null")) {
                        String sub = sections[section].timeTable.timetable[d][h];
                        for (Teacher t : unfreezedTeachers) {
                            if (t.facultyResponse.subject1.equals(sub)) {
                                t.assign(d, h, sub + ":" + sections[section].secId + ":" +
                                        "Lecture:" + (section/2));
                                rooms[section / 2].assign(d, h, sub + ":" + sections[section].secId + ":Lecture:" + t.facultyResponse.facultyid);

                            }
                            if (t.facultyResponse.subject2.equals(sub)) {
                                t.assign(d, h, sub + ":" + sections[section].secId + ":" +
                                        "Lecture:" + (section/2));
                                rooms[section / 2].assign(d, h, sub + ":" + sections[section].secId + ":Lecture:" + t.facultyResponse.facultyid);
                            }
                            if (t.facultyResponse.subject3.equals(sub)) {
                                t.assign(d, h, sub + ":" + sections[section].secId + ":" +
                                        "Lecture:" + (section/2));
                                rooms[section / 2].assign(d, h, sub + ":" + sections[section].secId + ":Lecture:" + t.facultyResponse.facultyid);
                            }

                        }
                    }
                }
            }
        }
    }


    //ASSIGN PHD LECTURES
    //format -- (AM501:PHD:Lecture)
    static void assignPhdLectures(Teacher[] unfreezedTeachers, Room[] rooms, Section[] sections) {
        for (PhdGroup phdGroup : phdData) {
            String[][] timetable = phdGroup.timeTable.timetable;

            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!timetable[d][h].equals("null")) {
                        String sub = timetable[d][h];
                        for (Teacher t : unfreezedTeachers) {
                            if (t.facultyResponse.subject1.equals(sub)) {
                                t.assign(d, h, sub + ":PHD:Lecture");
                            }
                            if (t.facultyResponse.subject2.equals(sub)) {
                                t.assign(d, h, sub + ":PHD:Lecture");
                            }
                            if (t.facultyResponse.subject3.equals(sub)) {
                                t.assign(d, h, sub + ":PHD:Lecture");
                            }
                        }
                    }
                }
            }
        }
    }

    //ASSIGN FIRST YEAR
    static void assignTeachersToFirstYear(Teacher[] unfreezedTeachers,Room[] rooms,Section[] sectionsArray){

        Collections.shuffle(unfreezedFirstYearData);

        int numOfFirstYearGroup = unfreezedFirstYearData.size();
        int indexOfFirstYear = 0;

        while(!helperFirstYear(unfreezedTeachers,indexOfFirstYear,numOfFirstYearGroup,rooms,sectionsArray)){
            Collections.shuffle(unfreezedFirstYearData);
        }
    }

    static boolean helperFirstYear(Teacher[] teachers,int indexOfFirstYear, int numOfFirstYearGroup, Room[] rooms,Section[] sectionsArray){
        if (indexOfFirstYear == numOfFirstYearGroup){

// IMP IMP WORK NEED TO BE DONE HERE
//            if(assignDecLabs(teachers, rooms[3], sectionsArray,rooms,sectionsArray)){
//                return true;
//            }
//            return false;
            return true;
        }
        FirstYearGroup firstYearGroup = unfreezedFirstYearData.get(indexOfFirstYear);
        System.out.println(firstYearGroup.groupId);

        String[][] timetable = firstYearGroup.timeTable.timetable;
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

        int j =0;
        for(;j<teachers.length;++j){

            Teacher t = teachers[j];

            if ( (t.isFree(days[0], hours[0]) && t.isFree(days[1], hours[1])) &&
                    (t.isFree(days[2], hours[2]) && t.isFree(days[3], hours[3]))
                    && t.current1Batches < t.total1Batches) {

                boolean isNotAvailable = false;

                for(int p=0;p<4;++p){

                    int continuousPrev = 0;
                    int continuousNext = 0;
                    int continuousPrevNext = 0;

                    if(hours[p]>1 && !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-1].equals("X"))
                            && !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-2].equals("X"))){
                        continuousPrev=2;
                    }

                    if(hours[p]<8 && !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+1].equals("X"))
                            && !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+2].equals("X"))){
                        continuousNext=2;
                    }

                    if(hours[p]>=1 && hours[p]<=8 &&  !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]-1].equals("X"))
                            && !(teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[days[p]][hours[p]+1].equals("X"))){
                        continuousPrevNext=2;
                    }

                    if(continuousNext>=2 || continuousPrev>=2 || continuousPrevNext>=2 ){
                        isNotAvailable = true;
                        break;
                    }

                }

                if(isNotAvailable){
                    continue;
                }

                t.current1Batches++;
                t.assign(days[0], hours[0], "MA102:" + firstYearGroup.groupId + ":Lecture" + ":SPS");
                t.assign(days[1], hours[1], "MA102:" + firstYearGroup.groupId + ":Lecture" + ":SPS");
                t.assign(days[2], hours[2], "MA102:" + firstYearGroup.groupId + ":Lecture" + ":SPS");
                t.assign(days[3], hours[3], "MA102:" + firstYearGroup.groupId + ":Lecture" + ":SPS");
                if(helperFirstYear(teachers,indexOfFirstYear+1,numOfFirstYearGroup,rooms,sectionsArray)) {
                    return true;
                }

                t.current1Batches--;

                t.assign(days[0], hours[0],"null");
                t.assign(days[1], hours[1], "null");
                t.assign(days[2], hours[2], "null");
                t.assign(days[3], hours[3], "null");

            }
        }
        if(j==teachers.length){
            return false;
        }
        return false;
    }


    public OverallTT generateTimeTable() {

        System.out.println("inside generateTimeTable() function");

        Teacher[] freezedTeachers = new Teacher[freezedTeachersData.size()];
        for (int i = 0; i < freezedTeachersData.size(); i++) {
            freezedTeachers[i] = new Teacher(freezedTeachersData.get(i));
        }

        Teacher[] unfreezedTeachers = new Teacher[unfreezedTeachersData.size()];
        for (int i = 0; i < unfreezedTeachersData.size(); i++) {
            unfreezedTeachers[i] = new Teacher(unfreezedTeachersData.get(i));
        }

        Section[] sectionsArray = new Section[sections.size()];
        for (int i = 0; i < sections.size(); i++) {
            sectionsArray[i] = sections.get(i);
        }


        //Functions callings will be done here

        assignElectives(sectionsArray, unfreezedTeachers, rooms);

        assignPhdLectures(unfreezedTeachers, rooms, sectionsArray);

        assignTeachersToFirstYear(unfreezedTeachers,rooms,sectionsArray);

        //returning the overallTT work done here

        OverallTT overallTT = new OverallTT();

        //adding the freezed teachers
        for (Teacher t : freezedTeachers) {
            overallTT.facultyResponses.add(t.facultyResponse);
        }
        //adding the unfreezed teachers
        for (Teacher t : unfreezedTeachers) {
            overallTT.facultyResponses.add(t.facultyResponse);
        }
        //adding the rooms
        for (Room room : rooms) {
            overallTT.rooms.add(room);
        }
        //adding the sections
        overallTT.sections = sections;

        //Assigning '-' to 'null' and 'X' to '0'
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

        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.sections.get(i).timeTable.timetable[j][k].equals("null")) {
                        overallTT.sections.get(i).timeTable.timetable[j][k] = "-";
                    }
                }
            }
        }

        //printing the timetable in console
        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    System.out.print(overallTT.sections.get(i).timeTable.timetable[j][k] + "  ");
                }
                System.out.println("");
            }
        }

        return overallTT;
    }
}
