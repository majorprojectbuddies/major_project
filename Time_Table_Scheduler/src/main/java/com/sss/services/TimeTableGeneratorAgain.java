package com.sss.services;

import com.sss.classModel.*;
import javafx.util.Pair;

import java.util.*;

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

    public ArrayList<String> dccLabsAssignToBothSections;
    public ArrayList<Pair<String,Integer>> dccLabsAssignToSingleSection;
    public ArrayList<Pair<Pair<String,Integer>,Pair<Integer,Integer>>> dccLabsAssignToSingleTeacher;

    public TimeTableGeneratorAgain(ArrayList<FacultyResponse> teachersData,
                              ArrayList<Course> courseDataList,
                              ArrayList<FirstYearGroup> firstYearData,
                              ArrayList<PhdGroup> phdData,
                              ArrayList<Section> sections,ArrayList<FacultyResponse> unfreezedTeachersData,
                                   ArrayList<FacultyResponse> freezedTeachersData, ArrayList<FirstYearGroup> freezedFirstYearData,
                                   ArrayList<FirstYearGroup> unfreezedFirstYearData ,
                                   Map<String,Pair<Integer,Integer>> decLabSlotToBeAssigned, Room[] rooms,
                                   ArrayList<String> decRemainingLabList,ArrayList<String> dccLabsAssignToBothSections,
                                   ArrayList<Pair<String,Integer>> dccLabsAssignToSingleSection,
                                   ArrayList<Pair<Pair<String,Integer>,Pair<Integer,Integer>>> dccLabsAssignToSingleTeacher) {
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
        this.dccLabsAssignToBothSections = dccLabsAssignToBothSections;
        this.dccLabsAssignToSingleSection = dccLabsAssignToSingleSection;
        this.dccLabsAssignToSingleTeacher = dccLabsAssignToSingleTeacher;
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





    static boolean assignDecLabs(Teacher[] teachers,Room lab, Section[] sections,Room[] rooms,Section[] sectionArray,
                                 Map<String, Pair<Integer, Integer>> decLabSlotToBeAssigned,
                                 ArrayList<String> decRemainingLabList){

        int sectionIndex = 0;
        ArrayList<Integer> sectionIndexArray=new ArrayList<Integer>();
        sectionIndexArray.add(0);
        sectionIndexArray.add(2);
        sectionIndexArray.add(4);
        Collections.shuffle(sectionIndexArray);
        if(helperDecLabs(teachers,lab,sections,0,0,sectionIndex,sectionIndexArray,rooms,sectionArray,
                decLabSlotToBeAssigned,decRemainingLabList)){
            return true;
        }
        return false;
    }



    static boolean helperDecLabs(Teacher[] teachers, Room lab,Section[] sections,int day,int hour, int sectionIndex,ArrayList<Integer> sectionIndexArray,Room[] rooms,Section[] sectionsArray,
                                 Map<String, Pair<Integer, Integer>> decLabSlotToBeAssigned,
                                 ArrayList<String> decRemainingLabList) {

        if (sectionIndex >= sectionIndexArray.size()) {


            for (int i = 0; i < teachers.length; ++i) {
                if (teachers[i].facultyResponse.name.equals("DK")) {
                    System.out.println("Printing timetable in dec labs ");
                    for (int j = 0; j < 5; ++j) {
                        for (int k = 0; k < 10; ++k) {
                            System.out.print(teachers[i].facultyResponse.timeTable.timetable[j][k] + "$$$");
                        }
                        System.out.println("");
                    }
                    break;
                }
            }

            //**IMP IMP REMOVE FROM COMMENTS
            /*if(assignDccCourses(teachers, rooms, sectionsArray)){
                return true;
            }
            return false;*/
            return true;
        }

        int d = 0, h = 0;
        String sub = "";
        boolean isFound = false;

        for (h = hour; h < 10; ++h) {
            if (!sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].equals("null") && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].length() > 14 && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].contains("lab")) {
                System.out.println("gleba dec lab funciton " + sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].substring(13));
                sub = sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h];
                if(sub.length()>15){
                    String[] breaks = sub.split(":");
                    sub = breaks[0];
                }
                System.out.println("gleba sub found" + sub);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            for (d = day + 1; d < 5; d++) {
                for (h = 0; h < 10; h++) {
                    System.out.println("new gleba " + d + " " + h + " " + sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h]);
                    if (!sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].equals("null") && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].length() > 14 && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].contains("lab")) {
                        System.out.println("gleba dec lab funciton " + sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].substring(13));
                        sub = sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h];
                        if(sub.length()>15){
                            String[] breaks = sub.split(":");
                            sub = breaks[0];
                        }
                        System.out.println("gleba sub found" + sub);
                        isFound = true;
                        break;
                    }

                }
                if (isFound) {
                    break;
                }
            }
        }

        if (d == 5) {

            return helperDecLabs(teachers, lab, sections, 0, 0, sectionIndex + 1, sectionIndexArray, rooms, sectionsArray,
                    decLabSlotToBeAssigned, decRemainingLabList);
        }
        System.out.println("saags " + sectionIndexArray.get(sectionIndex) + " " + d + " " + h);


        //check how many teachers to be assigned

        if (decRemainingLabList.contains(sub)) {
            //2 teachers will be assigned
            System.out.println("2 teachers will be assigned");
            int j = 0;
            int k = 0;
            for (; j < teachers.length; ++j) {

                System.out.println("saagsSS " + j);

                int continuousPrev = 0;
                int continuousNext = 0;
                int continuousPrevNext = 0;

                if (h > 1 && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 2].equals("X"))) {
                    continuousPrev = 2;
                }

                if (h < 8 && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 2].equals("X"))) {
                    continuousNext = 2;
                }

                if (h >= 1 && h <= 8 && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))) {
                    continuousPrevNext = 2;
                }

                if (continuousNext >= 2 || continuousPrev >= 2 || continuousPrevNext >= 2) {
                    continue;
                }


                if (teachers[j].isFree(d, h) && teachers[j].isFree(d, h + 1)
                        && teachers[j].labHours >= 2) {

                    for (k = j + 1; k < teachers.length; ++k) {


                        int continuousPrevForK = 0;
                        int continuousNextForK = 0;
                        int continuousPrevNextForK = 0;

                        if (h > 1 && !(teachers[k].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                                && !(teachers[k].facultyResponse.timeTable.timetable[d][h - 2].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h - 2].equals("X"))) {
                            continuousPrevForK = 2;
                        }

                        if (h < 8 && !(teachers[k].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))
                                && !(teachers[k].facultyResponse.timeTable.timetable[d][h + 2].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h + 2].equals("X"))) {
                            continuousNextForK = 2;
                        }

                        if (h >= 1 && h <= 8 && !(teachers[k].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                                && !(teachers[k].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))) {
                            continuousPrevNextForK = 2;
                        }

                        if (continuousNextForK >= 2 || continuousPrevForK >= 2 || continuousPrevNextForK >= 2) {
                            continue;
                        }

                        if (teachers[k].isFree(d, h) && teachers[k].isFree(d, h + 1)
                                && teachers[k].labHours >= 2) {

                            teachers[j].assign(d, h, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");
                            teachers[j].assign(d, h + 1, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");
                            teachers[k].assign(d, h, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");
                            teachers[k].assign(d, h + 1, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");

                            lab.assign(d, h, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab");
                            lab.assign(d, h + 1, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                                    sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab");

                            sections[sectionIndexArray.get(sectionIndex)].assign(d, h, sub + ":Lab:3");
                            sections[sectionIndexArray.get(sectionIndex)].assign(d, h + 1, sub + ":Lab:3");

                            sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h, sub + ":Lab:3");
                            sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h + 1, sub + ":Lab:3");

                            teachers[j].labHours -= 2;
                            teachers[k].labHours -= 2;

                            if (helperDecLabs(teachers, lab, sections, d, h + 2, sectionIndex, sectionIndexArray, rooms, sectionsArray,
                                    decLabSlotToBeAssigned, decRemainingLabList)) {
                                return true;
                            }


                            teachers[j].assign(d, h, "null");
                            teachers[j].assign(d, h + 1, "null");
                            teachers[k].assign(d, h, "null");
                            teachers[k].assign(d, h + 1, "null");

                            lab.assign(d, h, "null");
                            lab.assign(d, h + 1, "null");

                            sections[sectionIndexArray.get(sectionIndex)].assign(d, h, sub);
                            sections[sectionIndexArray.get(sectionIndex)].assign(d, h + 1, sub);

                            sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h, sub);
                            sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h + 1, sub);

                            teachers[j].labHours += 2;
                            teachers[k].labHours += 2;

                        }
                    }
                }
            }
            if (j == teachers.length) {
                return false;
            }

            if (k == teachers.length) {
                return false;
            }
            return true;
        }
        if (decLabSlotToBeAssigned.containsKey(sub)) {
            //1 teacher will be assigned
            System.out.println("1 teacher will be assigned and subject is " + sub);
            int j = 0;

            for (; j < teachers.length; ++j) {

                System.out.println("saagsSS " + j);

                int continuousPrev = 0;
                int continuousNext = 0;
                int continuousPrevNext = 0;

                if (h > 1 && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 2].equals("X"))) {
                    continuousPrev = 2;
                }

                if (h < 8 && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 2].equals("X"))) {
                    continuousNext = 2;
                }

                if (h >= 1 && h <= 8 && !(teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h - 1].equals("X"))
                        && !(teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h + 1].equals("X"))) {
                    continuousPrevNext = 2;
                }

                if (continuousNext >= 2 || continuousPrev >= 2 || continuousPrevNext >= 2) {
                    continue;
                }


                if (teachers[j].isFree(d, h) && teachers[j].isFree(d, h + 1)
                        && teachers[j].labHours >= 2) {

                    teachers[j].assign(d, h, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                            sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");
                    teachers[j].assign(d, h + 1, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                            sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab:" + "3");


                    lab.assign(d, h, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                            sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab");
                    lab.assign(d, h + 1, sub + ":" + sections[sectionIndexArray.get(sectionIndex)].secId + "-" +
                            sections[sectionIndexArray.get(sectionIndex) + 1].secId + ":Lab");

                    sections[sectionIndexArray.get(sectionIndex)].assign(d, h, sub + ":Lab:3");
                    sections[sectionIndexArray.get(sectionIndex)].assign(d, h + 1, sub + ":Lab:3");

                    sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h, sub + ":Lab:3");
                    sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h + 1, sub + ":Lab:3");

                    teachers[j].labHours -= 2;


                    if (helperDecLabs(teachers, lab, sections, d, h + 2, sectionIndex, sectionIndexArray, rooms, sectionsArray,
                            decLabSlotToBeAssigned, decRemainingLabList)) {
                        return true;
                    }

                    teachers[j].assign(d, h, "null");
                    teachers[j].assign(d, h + 1, "null");

                    lab.assign(d, h, "null");
                    lab.assign(d, h + 1, "null");

                    sections[sectionIndexArray.get(sectionIndex)].assign(d, h, sub);
                    sections[sectionIndexArray.get(sectionIndex)].assign(d, h + 1, sub);

                    sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h, sub);
                    sections[sectionIndexArray.get(sectionIndex) + 1].assign(d, h + 1, sub);

                    teachers[j].labHours += 2;

                }
            }
            if (j == teachers.length) {
                return false;
            }

            return true;

        }
        //else 0 teacher should be assigned so simply return true
        return true;

    }










































    static boolean assignDccCourses(Teacher[] unfreezedTeachers, Room[] rooms,Section[] sections){
        ArrayList<Teacher> unfreezedTeacherArrayList = new ArrayList<>();
        for(int i=0;i<unfreezedTeachers.length;++i){
            unfreezedTeacherArrayList.add(unfreezedTeachers[i]);
            unfreezedTeacherArrayList.get(i).facultyResponse.timeTable.timetable=unfreezedTeachers[i].facultyResponse.timeTable.timetable;
        }
        Collections.shuffle(unfreezedTeacherArrayList);

        //printing the teachers
        for(int i=0;i<unfreezedTeacherArrayList.size();++i){
            System.out.println(unfreezedTeacherArrayList.get(i).facultyResponse.name);
        }

        int teacherIndex = 0;

        ArrayList<ArrayList<String>> subjectListOfAllUnfreezedTeachers = new ArrayList<ArrayList<String>>(unfreezedTeacherArrayList.size());
        int subjectListIndex = 0;
        for(int i=0;i<unfreezedTeacherArrayList.size();++i){

            int year;
            ArrayList<String> a1 = new ArrayList<>();
            if(!unfreezedTeacherArrayList.get(i).facultyResponse.subject1.equals("null")){
                year = Integer.parseInt(unfreezedTeacherArrayList.get(i).facultyResponse.subject1.charAt(2) + "");
                if (!(year < 2 || year > 4 || unfreezedTeacherArrayList.get(i).facultyResponse.subject1.length() != 5)) {
                    a1.add(unfreezedTeacherArrayList.get(i).facultyResponse.subject1);
                }
            }
            if(!unfreezedTeacherArrayList.get(i).facultyResponse.subject2.equals("null")){
                year = Integer.parseInt(unfreezedTeacherArrayList.get(i).facultyResponse.subject2.charAt(2) + "");
                if (!(year < 2 || year > 4 || unfreezedTeacherArrayList.get(i).facultyResponse.subject2.length() != 5)) {
                    a1.add(unfreezedTeacherArrayList.get(i).facultyResponse.subject2);
                }
            }
            if(!unfreezedTeacherArrayList.get(i).facultyResponse.subject3.equals("null")){
                year = Integer.parseInt(unfreezedTeacherArrayList.get(i).facultyResponse.subject3.charAt(2) + "");
                if (!(year < 2 || year > 4 || unfreezedTeacherArrayList.get(i).facultyResponse.subject3.length() != 5)) {
                    a1.add(unfreezedTeacherArrayList.get(i).facultyResponse.subject3);
                }
            }
            subjectListOfAllUnfreezedTeachers.add(a1);
            Collections.shuffle(subjectListOfAllUnfreezedTeachers.get(i));

            for(int l=0;l<subjectListOfAllUnfreezedTeachers.get(i).size();++l){
                System.out.println(subjectListOfAllUnfreezedTeachers.get(i).get(l));
            }
        }

        // initial section passing as 0
        // it can either be 0 or 1
        // 0 means A section and 1 meand B section
        // check for getinitialsection + section passed
        System.out.println("inside the dcc function after the teachers name");
        if(helperDccCourses(unfreezedTeacherArrayList,teacherIndex,rooms,sections,subjectListOfAllUnfreezedTeachers,subjectListIndex,0,0)||
                helperDccCourses(unfreezedTeacherArrayList,teacherIndex,rooms,sections,subjectListOfAllUnfreezedTeachers,subjectListIndex,1,0)){

            return true;
        }
        return false;
    }

    static boolean helperDccCourses(ArrayList<Teacher> unfreezedTeacherArrayList,int unfreezedTeacherIndex, Room[] rooms,Section[] sections,
                                    ArrayList<ArrayList<String>> subjectListOfAllUnfreezedTeachers, int unfreezedSubjectListIndex, int sectionPassed,int currentHour){

        if(unfreezedTeacherIndex>=unfreezedTeacherArrayList.size()){
            System.out.println("helper dcc in if 1");

            Teacher[] teachers = new Teacher[unfreezedTeacherArrayList.size()];
            for(int i=0;i<unfreezedTeacherArrayList.size();++i){
                teachers[i]=unfreezedTeacherArrayList.get(i);
            }
            ArrayList<Teacher> newTeacherArrayList = new ArrayList<>();
            for(int i=0;i<unfreezedTeacherArrayList.size();++i){
                newTeacherArrayList.add(unfreezedTeacherArrayList.get(i));
            }



            for(int i=0;i<teachers.length;++i){
                if(teachers[i].facultyResponse.name.equals("DK")){
                    System.out.println("Printing timetable in dcc courses ");
                    for(int j=0;j<5;++j){
                        for(int k=0;k<10;++k){
                            System.out.print(teachers[i].facultyResponse.timeTable.timetable[j][k] + "$$$");
                        }
                        System.out.println("");
                    }
                    break;
                }
            }


            //******** NEXT BACKTRACK FUNCTION HERE
            /*if(assignLabs(teachers, rooms[3], sections,rooms,newTeacherArrayList)){
                return true;
            }
            return false;*/
            return true;

        }
        else if(unfreezedSubjectListIndex>=subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).size()) {

            System.out.println("helper dcc in if 2");
            return (helperDccCourses(unfreezedTeacherArrayList, unfreezedTeacherIndex+1, rooms, sections, subjectListOfAllUnfreezedTeachers, 0, 0,0)||
                    helperDccCourses(unfreezedTeacherArrayList, unfreezedTeacherIndex+1, rooms, sections, subjectListOfAllUnfreezedTeachers, 0, 1,0));
        }
        else if(currentHour>=courseDataHM.get(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)).tutHours){
            System.out.println("helper dcc in if 3");
            sections[getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed].subjects.put(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex),1);
            if(helperDccCourses(unfreezedTeacherArrayList, unfreezedTeacherIndex, rooms, sections, subjectListOfAllUnfreezedTeachers, unfreezedSubjectListIndex+1, 0,0) || helperDccCourses(unfreezedTeacherArrayList, unfreezedTeacherIndex, rooms, sections, subjectListOfAllUnfreezedTeachers, unfreezedSubjectListIndex+1, 1,0)){
                return true;
            }
            sections[getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed].subjects.remove(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex));
            return false;
        }
        else{

            if(sections[getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed].subjects.containsKey(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex))){
                return false;
            }

            System.out.println("helper dcc in if 4");
            int [] D = new int[5];
            int [] H = new int[10];
            for(int d=0;d<5;++d){
                int classes = 10;
                for(int h=0;h<10;++h){
                    if(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[d][h].equals("null") || unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[d][h].equals("X")){
                        classes--;
                    }
                }
                D[d]=classes;
            }

            for(int h =0 ;h<10;++h){
                int classes = 5;
                for(int d=0;d<5;++d){
                    if(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[d][h].equals("null") || unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[d][h].equals("X")){
                        classes--;
                    }
                }
                H[h]=classes;
            }


            List<Pair<Integer, Integer>> DP = new ArrayList<Pair<Integer, Integer>>();
            List<Pair<Integer, Integer>> HP = new ArrayList<Pair<Integer, Integer>>();
            for(int j=0;j<5;++j){
                DP.add(new Pair<Integer, Integer>(D[j],j));
            }

            Collections.sort(DP, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {
                    return (o2.getKey()-o1.getKey());
                }
            });


            ArrayList<Integer> newDP = new ArrayList<>();
            newDP.add(0);
            newDP.add(1);
            newDP.add(2);
            newDP.add(3);
            newDP.add(4);

            Collections.shuffle(newDP);

            for(int j=0;j<5;++j){
                /*for(int k=0;k<10;++k){
                    HP.add(new Pair<Integer, Integer>(H[k],k));
                }
                Collections.sort(HP, new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {
                        return (o2.getKey()-o1.getKey());
                    }
                });*/

                int lecureCountPerDay = 0;

                for(int k=0;k<10;++k){
                    String tempArr[]= unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDP.get(j)][k].split(":");
                    for (String temp: tempArr){
                        //System.out.println(temp);
                        if(temp.equals(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex))){
                            lecureCountPerDay++;
                        }
                    }
                }
                if(lecureCountPerDay>=2){
                    continue;
                }

                for(int k=0;k<10;++k){

                    int newDay =newDP.get(j);
                    int newHour = k;



                    int continuousLecturesPrev = 0;
                    int continuousLecturesNext = 0;
                    int continuousLecturesPrevNext = 0;

                    if(k>1){
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1].equals("X"))){
                            continuousLecturesPrev++;

                        }
                        System.out.println("CP "+continuousLecturesPrev+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1] );
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-2].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-2].equals("X"))){
                            continuousLecturesPrev++;
                        }
                        System.out.println("CP "+continuousLecturesPrev+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-2]);
                    }

                    if(k<8){
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1].equals("X"))){
                            continuousLecturesNext++;
                        }
                        System.out.println("Cn "+continuousLecturesNext+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1]);
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+2].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+2].equals("X"))){
                            continuousLecturesNext++;
                        }
                        System.out.println("Cn "+continuousLecturesNext+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+2]);
                    }

                    if(k>0){
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1].equals("X"))){
                            continuousLecturesPrevNext++;
                        }
                        System.out.println("CPn "+continuousLecturesPrevNext+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k-1]);

                    }
                    if(k<9){
                        if(!(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1].equals("null")||
                                unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1].equals("X"))){
                            continuousLecturesPrevNext++;
                        }
                        System.out.println("CPn "+continuousLecturesPrevNext+" "+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.timeTable.timetable[newDay][k+1]);

                    }

                    if(continuousLecturesPrev>=2 || continuousLecturesNext>=2 || continuousLecturesPrevNext>=2){
                        continue;
                    }



                    // D[newDay]<5 to make every day load of teacher to be max of 4
                    //**** also add the constraint for the break between 12-2
                    if(unfreezedTeacherArrayList.get(unfreezedTeacherIndex).isFree(newDay,newHour) && sections[getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed].isFree(newDay,newHour) && rooms[(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2].isFree(newDay,newHour) && D[newDay]<6){
                        //assign classes to everything

                        unfreezedTeacherArrayList.get(unfreezedTeacherIndex).assign(newDay,newHour,subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)+":"+((((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2)+2)==2?"s":((((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2)+2)==3)?"t":"f")+ ((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)%2==0?"1:":"2:") +"Lecture:" + (getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2);
                        sections[(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)].assign(newDay,newHour,subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)+":Lecture:"+(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2+":"+unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.name);
                        rooms[(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2].assign(newDay, newHour, subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)+":"+((((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2)+2)==2?"s":((((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2)+2)==3)?"t":"f")+ ((getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)%2==0?"1:":"2:") +"Lecture:"+"t.facultyResponse.name");

                        System.out.println("assigned to teacher " + unfreezedTeacherArrayList.get(unfreezedTeacherIndex).facultyResponse.name + " " + subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex) + " in section" + (getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed) + " at "+newDay+" "+newHour+" with "+continuousLecturesPrev+ " "+continuousLecturesNext+" "+continuousLecturesPrevNext);

                        if(helperDccCourses(unfreezedTeacherArrayList, unfreezedTeacherIndex, rooms, sections, subjectListOfAllUnfreezedTeachers, unfreezedSubjectListIndex, sectionPassed,currentHour+1)){
                            return true;
                        }

                        unfreezedTeacherArrayList.get(unfreezedTeacherIndex).assign(newDay,newHour,"null");
                        sections[(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)].assign(newDay,newHour,"null");
                        rooms[(getInitialSectionSaag(subjectListOfAllUnfreezedTeachers.get(unfreezedTeacherIndex).get(unfreezedSubjectListIndex)) + sectionPassed)/2].assign(newDay, newHour, "null");


                    }
                }
            }
            return false;

        }
    }







    static boolean assignLabs(ArrayList<Teacher> unfreezedTeacherArrayList,ArrayList<String> dccLabsAssignToBothSections,
                              ArrayList<Pair<String,Integer>> dccLabsAssignToSingleSection,
                              ArrayList<Pair<Pair<String,Integer>,Pair<Integer,Integer>>> dccLabsAssignToSingleTeacher,
                              Room lab, Section[] sections,Room[] rooms){

        /*if(helperLabs(coursesWithLabList,0,0,0,new Teacher(),new Teacher(),
                teachersArrayList,rooms,sections,-1,-1,lab)){
            return true;
        }
        return false;*/

        System.out.println("sub in both section");
        for(int i=0;i<dccLabsAssignToBothSections.size();++i){
            System.out.println(dccLabsAssignToBothSections.get(i));
        }

        System.out.println("sub in single section " + dccLabsAssignToSingleSection.size());
        /*for(int i=0;i<dccLabsAssignToBothSections.size();++i){
            System.out.println(dccLabsAssignToSingleSection.get(i).getKey());
        }*/

        System.out.println("sub in single teacher" + dccLabsAssignToSingleTeacher.size());
        /*for(int i=0;i<dccLabsAssignToBothSections.size();++i){
            System.out.println(dccLabsAssignToSingleTeacher.get(i).getKey().getKey());
        }*/

        if(assignDccLabsAssignToBothSections(dccLabsAssignToBothSections,
                dccLabsAssignToSingleSection,
                dccLabsAssignToSingleTeacher,
                0,0,0,
                new Teacher(),new Teacher(),unfreezedTeacherArrayList,
                lab,sections,rooms,-1,-1)){
            return true;
        }
        return false;
    }

    static boolean assignDccLabsAssignToBothSections(ArrayList<String> dccLabsAssignToBothSections,
                                                     ArrayList<Pair<String,Integer>> dccLabsAssignToSingleSection,
                                                     ArrayList<Pair<Pair<String,Integer>,Pair<Integer,Integer>>> dccLabsAssignToSingleTeacher,
                                                     int courseWithLabsListIndex,int sectionIndex,
                                                     int selectedTeacherIndex,Teacher teacher1,Teacher teacher2,ArrayList<Teacher> unfreezedTeacherArrayList,
                                                     Room lab, Section[] sections,Room[] rooms,int currD, int currH){

        if(courseWithLabsListIndex>=dccLabsAssignToBothSections.size()){
            System.out.println("inside lab helper 1");


            for(int i=0;i<unfreezedTeacherArrayList.size();++i){
                if(unfreezedTeacherArrayList.get(i).facultyResponse.name.equals("DK")){
                    System.out.println("Printing timetable in normal labs ");
                    for(int j=0;j<5;++j){
                        for(int k=0;k<10;++k){
                            System.out.print(unfreezedTeacherArrayList.get(i).facultyResponse.timeTable.timetable[j][k] + "$$$");
                        }
                        System.out.println("");
                    }
                    break;
                }
            }

            //MAKE THE RECURSIVE CALL FOR SINGLE SECTION HERE
            return true;
        }
        else if(sectionIndex>1){
            System.out.println("inside lab helper 2");

            return assignDccLabsAssignToBothSections(dccLabsAssignToBothSections,dccLabsAssignToSingleSection,
                    dccLabsAssignToSingleTeacher,courseWithLabsListIndex+1,0,0,new Teacher(),
                    new Teacher(),unfreezedTeacherArrayList,lab,sections,rooms,-1,-1);
        }
        else if(selectedTeacherIndex>1){
            System.out.println("inside lab helper 3");

            return assignDccLabsAssignToBothSections(dccLabsAssignToBothSections,dccLabsAssignToSingleSection,
                    dccLabsAssignToSingleTeacher,courseWithLabsListIndex,sectionIndex+1,0,new Teacher(),
                    new Teacher(),unfreezedTeacherArrayList,lab,sections,rooms,-1,-1);
        }
        else if(selectedTeacherIndex==1){
            System.out.println("inside lab helper 4");

            int initialSection = getInitialSectionSaag(dccLabsAssignToBothSections.get(courseWithLabsListIndex));
            int sectionToAssign = initialSection + sectionIndex;



            int j;
            System.out.println(unfreezedTeacherArrayList.size());
            for(j = 0;j<unfreezedTeacherArrayList.size();++j){
                Teacher teacher = unfreezedTeacherArrayList.get(j);
                System.out.println("inside lab helper 4.1");
                System.out.println(unfreezedTeacherArrayList.get(j).labHours);
//                if(unfreezedTeacherArrayList.get(j).isFree(currD,currH)){
//                    System.out.println("free hai");
//                }
//                else{
//                    System.out.println("not free hai");
//                }
                if(unfreezedTeacherArrayList.get(j)!= teacher1){
                    System.out.println("same nahi hai");
                }
                else{
                    System.out.println("same hai");
                }
                if(unfreezedTeacherArrayList.get(j)!= teacher1 && unfreezedTeacherArrayList.get(j).isFree(currD,currH) && unfreezedTeacherArrayList.get(j).labHours>=2){
                    System.out.println("inside lab helper 4.2");
                    int numOfClasses = 0;
                    for(int p =0;p<10;++p){
                        if(!(teacher.facultyResponse.timeTable.timetable[currD][p].equals("null") ||
                                teacher.facultyResponse.timeTable.timetable[currD][p].equals("X"))){
                            numOfClasses++;
                        }
                    }
                    System.out.println("inside lab helper 4.3");
                    if(numOfClasses>3){
                        continue;
                    }

                    System.out.println("inside lab helper 4.4");
                    if(currH>2 && !teacher.isFree(currD,currH-1) && !teacher.isFree(currD,currH-2) && !teacher.isFree(currD,currH-3)){
                        continue;
                    }
                    if(currH<6 && !teacher.isFree(currD,currH+2) && !teacher.isFree(currD,currH+3) && !teacher.isFree(currD,currH+4)){
                        continue;
                    }
                    if(currH>1 && currH<8 && !teacher.isFree(currD,currH-1) && !teacher.isFree(currD,currH-2) && !teacher.isFree(currD,currH+2)){
                        continue;
                    }
                    if(currH>0 && currH<7 && !teacher.isFree(currD,currH-1) && !teacher.isFree(currD,currH+2) && !teacher.isFree(currD,currH+3)){
                        continue;
                    }
                    System.out.println("inside lab helper 4.5");

                    teacher2 = unfreezedTeacherArrayList.get(j);
                    //assign lab here
                    unfreezedTeacherArrayList.get(j).labHours-=2;


                    System.out.println("inside lab helper 4.6");
                    unfreezedTeacherArrayList.get(j).assign(currD,currH, dccLabsAssignToBothSections.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                    sections[sectionToAssign].assign(currD,currH,dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                    rooms[3].assign(currD, currH, dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");

                    System.out.println("inside lab helper 4.7");
                    unfreezedTeacherArrayList.get(j).assign(currD,currH+1, dccLabsAssignToBothSections.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                    sections[sectionToAssign].assign(currD,currH+1,dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                    rooms[3].assign(currD, currH+1, dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");


                    System.out.println("inside lab helper 4.8");
                    if(assignDccLabsAssignToBothSections(dccLabsAssignToBothSections,dccLabsAssignToSingleSection,
                            dccLabsAssignToSingleTeacher,courseWithLabsListIndex,sectionIndex,selectedTeacherIndex+1,
                            teacher1,teacher2,unfreezedTeacherArrayList,lab,sections,rooms,currD,currH)){
                        System.out.println("inside lab helper 4.9");
                        return true;
                    }
                    System.out.println("inside lab helper 4.10");
                    unfreezedTeacherArrayList.get(j).labHours+=2;
                    teacher2= new Teacher();

                    //unassign everything here
                    unfreezedTeacherArrayList.get(j).assign(currD,currH, "null");
                    sections[sectionToAssign].assign(currD,currH,"null");
                    rooms[3].assign(currD, currH, "null");

                    unfreezedTeacherArrayList.get(j).assign(currD,currH+1, "null");
                    sections[sectionToAssign].assign(currD,currH+1,"null");
                    rooms[3].assign(currD, currH+1, "null");

                }
            }

        }
        else if(selectedTeacherIndex==0){
            System.out.println("inside lab helper 5");

            int initialSection = getInitialSectionSaag(dccLabsAssignToBothSections.get(courseWithLabsListIndex));
            int sectionToAssign = initialSection + sectionIndex;
            for(int d = 0;d<5;++d){
                for(int h= 0;h<9;++h){
                    if(sections[sectionToAssign].isFree(d,h) && lab.isFree(d,h) &&
                            sections[sectionToAssign].isFree(d,h+1) && lab.isFree(d,h+1)){
                        int j;
                        for(j = 0;j<unfreezedTeacherArrayList.size();++j){
                            Teacher teacher = unfreezedTeacherArrayList.get(j);


                            if(unfreezedTeacherArrayList.get(j).isFree(d,h) && unfreezedTeacherArrayList.get(j).labHours>=2){

                                int numOfClasses = 0;
                                for(int p =0;p<10;++p){
                                    if(!(teacher.facultyResponse.timeTable.timetable[d][p].equals("null") ||
                                            teacher.facultyResponse.timeTable.timetable[d][p].equals("X"))){
                                        numOfClasses++;
                                    }
                                }
                                if(numOfClasses>3){
                                    continue;
                                }

                                boolean prev3 = false;
                                boolean next3= false;
                                boolean prev2next1 = false;
                                boolean prev1next2= false;

                                if(h>2 && !teacher.isFree(d,h-1) && !teacher.isFree(d,h-2) && !teacher.isFree(d,h-3)){
                                    continue;
                                }
                                if(h<6 && !teacher.isFree(d,h+2) && !teacher.isFree(d,h+3) && !teacher.isFree(d,h+4)){
                                    continue;
                                }
                                if(h>1 && h<8 && !teacher.isFree(d,h-1) && !teacher.isFree(d,h-2) && !teacher.isFree(d,h+2)){
                                    continue;
                                }
                                if(h>0 && h<7 && !teacher.isFree(d,h-1) && !teacher.isFree(d,h+2) && !teacher.isFree(d,h+3)){
                                    continue;
                                }



                                unfreezedTeacherArrayList.get(j).labHours-=2;
                                teacher1 = unfreezedTeacherArrayList.get(j);
                                //assign lab here



                                unfreezedTeacherArrayList.get(j).assign(d,h, dccLabsAssignToBothSections.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                                sections[sectionToAssign].assign(d,h,dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                                rooms[3].assign(d, h, dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");

                                unfreezedTeacherArrayList.get(j).assign(d,h+1, dccLabsAssignToBothSections.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                                sections[sectionToAssign].assign(d,h+1,dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                                rooms[3].assign(d, h+1, dccLabsAssignToBothSections.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");



                                if(assignDccLabsAssignToBothSections(dccLabsAssignToBothSections,dccLabsAssignToSingleSection,
                                        dccLabsAssignToSingleTeacher,courseWithLabsListIndex,sectionIndex,selectedTeacherIndex+1,
                                        teacher1,teacher2,unfreezedTeacherArrayList,lab,sections,rooms,d,h)){
                                    return true;
                                }

                                unfreezedTeacherArrayList.get(j).labHours+=2;
                                teacher1= new Teacher();
                                //unassign everything here
                                unfreezedTeacherArrayList.get(j).assign(d,h, "null");
                                sections[sectionToAssign].assign(d,h,"null");
                                rooms[3].assign(d, h, "null");

                                unfreezedTeacherArrayList.get(j).assign(d,h+1, "null");
                                sections[sectionToAssign].assign(d,h+1,"null");
                                rooms[3].assign(d, h+1, "null");

                            }
                        }

                    }
                }
            }
            return false;

        }
        return true;


    }





    static int getInitialSectionSaag(String subject){
        int year = Integer.parseInt(subject.charAt(2) + "");
        if(year==2){
            return 0;
        }
        else if(year==3){
            return 2;
        }
        else if(year==4){
            return 4;
        }
        return 0;
    }


    static String getTheYearAndSection(int year,int section){
        String toReturn="";
        if(year==0){
            toReturn+="s";
        }
        else if(year==2){
            toReturn+="t";
        }
        else if(year==4){
            toReturn+="f";
        }
        if(section==0){
            toReturn+="1";
        }
        else if(section==1){
            toReturn+="2";
        }
        return toReturn;
    }













    public OverallTT generateTimeTable() {

        System.out.println("inside generateTimeTable() function");

        Teacher[] freezedTeachers = new Teacher[freezedTeachersData.size()];
        for (int i = 0; i < freezedTeachersData.size(); i++) {
            freezedTeachers[i] = new Teacher(freezedTeachersData.get(i));
            for(int j=0;j<5;++j){
                for(int k=0;k<10;++k){
                    String s = freezedTeachersData.get(i).timeTable.timetable[j][k];
                    if(s.equals("-") || s.equals("null") || s.equals("X")){
                        continue;
                    }else{
                        //separate the string to check whether its a lab or lecture
                        String[] breaks = s.split(":");
                        //format for teacher -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
                        if(breaks[2].equals("Lab")){
                            //its a lab so block the lab
                            //format for lab room-  (MC312(SLOT-A)LAB:t2:Lab)
                            freezedTeachers[i].labHours--;
                        }
                    }
                }
            }
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

        assignDecLabs(unfreezedTeachers,rooms[3],sectionsArray,rooms,sectionsArray,decLabSlotToBeAssigned,decRemainingLabList);

        if(assignDccCourses(unfreezedTeachers,rooms,sectionsArray)){
            System.out.println("wuhoooo");
        }

        ArrayList<Teacher> unfreezedTeacherArrayList = new ArrayList<>();
        for(int i=0;i<unfreezedTeachers.length;++i){
            unfreezedTeacherArrayList.add(unfreezedTeachers[i]);
        }

        if(assignLabs(unfreezedTeacherArrayList,dccLabsAssignToBothSections,
                dccLabsAssignToSingleSection,
                dccLabsAssignToSingleTeacher,
                rooms[3],sectionsArray,rooms)){
            System.out.println("wuhooo2");
        }

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
