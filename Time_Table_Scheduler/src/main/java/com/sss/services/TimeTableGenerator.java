package com.sss.services;


import com.sss.classModel.*;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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



    static void assignPhdLectures(Teacher[] teachers, Room[] rooms, Section[] sections) {
        for (PhdGroup phdGroup : phdData) {
            String[][] timetable = phdGroup.timeTable.timetable;

            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!timetable[d][h].equals("null")) {
                        String sub = timetable[d][h];
                        for (Teacher t : teachers) {
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



    static void assignTeachersToFirstYear(Teacher[] teachers,Room[] rooms,Section[] sectionsArray){

        Collections.shuffle(firstYearData);

        int numOfFirstYearGroup = firstYearData.size();
        int indexOfFirstYear = 0;
        Boolean[] isAssignedGroup = new Boolean[teachers.length];
        for(int i=0;i<isAssignedGroup.length;++i){
            isAssignedGroup[i]=false;
        }
        while(!helperFirstYear(teachers,indexOfFirstYear,isAssignedGroup,numOfFirstYearGroup,rooms,sectionsArray)){
            Collections.shuffle(firstYearData);
        }

    }

    static boolean helperFirstYear(Teacher[] teachers,int indexOfFirstYear, Boolean[] isAssignedGroup, int numOfFirstYearGroup, Room[] rooms,Section[] sectionsArray){
        if (indexOfFirstYear == numOfFirstYearGroup){

            if(assignDecLabs(teachers, rooms[3], sectionsArray,rooms,sectionsArray)){
                return true;
            }
            return false;
        }
        FirstYearGroup firstYearGroup = firstYearData.get(indexOfFirstYear);
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
                if(helperFirstYear(teachers,indexOfFirstYear+1,isAssignedGroup,numOfFirstYearGroup,rooms,sectionsArray)) {
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


    static boolean assignDecLabs(Teacher[] teachers,Room lab, Section[] sections,Room[] rooms,Section[] sectionArray){

        int sectionIndex = 0;
        ArrayList<Integer> sectionIndexArray=new ArrayList<Integer>();
        sectionIndexArray.add(0);
        sectionIndexArray.add(2);
        sectionIndexArray.add(4);
        Collections.shuffle(sectionIndexArray);
        if(helperDecLabs(teachers,lab,sections,0,0,sectionIndex,sectionIndexArray,rooms,sectionArray)){
         return true;
        }
        return false;

    }

    static boolean helperDecLabs(Teacher[] teachers, Room lab,Section[] sections,int day,int hour, int sectionIndex,ArrayList<Integer> sectionIndexArray,Room[] rooms,Section[] sectionsArray){

        if(sectionIndex>=sectionIndexArray.size()){

            if(assignDccCourses(teachers, rooms, sectionsArray)){
                return true;
            }
            return false;
        }

        int d=0,h=0;
        String sub="";
        boolean isFound = false;
        for (d = day; d < 5; d++) {
            for (h = hour; h < 10; h++) {
                if (!sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].equals("null") && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].length() > 14 && sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].substring(13).equals("lab")) {
                    System.out.println("gleba dec lab funciton " + sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h].substring(13));
                    sub = sections[sectionIndexArray.get(sectionIndex)].timeTable.timetable[d][h];
                    System.out.println("gleba sub found" + sub);
                    isFound = true;
                    break;
                }

            }
            if(isFound){
                break;
            }
        }
        if(d==5){
            return helperDecLabs(teachers,lab,sections,0,0,sectionIndex+1,sectionIndexArray,rooms,sectionsArray);
        }
        System.out.println("saags " + sectionIndexArray.get(sectionIndex) + " " + d + " " + h);

        int j = 0;
        int k=0;
        for(;j<teachers.length;++j){

            System.out.println("saagsSS " + j);

            int continuousPrev = 0;
            int continuousNext = 0;
            int continuousPrevNext = 0;

            if(h>1 && !(teachers[j].facultyResponse.timeTable.timetable[d][h-1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h-1].equals("X"))
            && !(teachers[j].facultyResponse.timeTable.timetable[d][h-2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h-2].equals("X"))){
                continuousPrev=2;
            }

            if(h<8 && !(teachers[j].facultyResponse.timeTable.timetable[d][h+1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h+1].equals("X"))
                    && !(teachers[j].facultyResponse.timeTable.timetable[d][h+2].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h+2].equals("X"))){
                continuousNext=2;
            }

            if(h>=1 && h<=8 &&  !(teachers[j].facultyResponse.timeTable.timetable[d][h-1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h-1].equals("X"))
                    && !(teachers[j].facultyResponse.timeTable.timetable[d][h+1].equals("null") || teachers[j].facultyResponse.timeTable.timetable[d][h+1].equals("X"))){
                continuousPrevNext=2;
            }

            if(continuousNext>=2 || continuousPrev>=2 || continuousPrevNext>=2 ){
                continue;
            }


            if (teachers[j].isFree(d, h) && teachers[j].isFree(d, h + 1)
                    && teachers[j].labHours >= 2 ) {

                for(k=j+1;k<teachers.length;++k){


                    int continuousPrevForK = 0;
                    int continuousNextForK = 0;
                    int continuousPrevNextForK = 0;

                    if(h>1 && !(teachers[k].facultyResponse.timeTable.timetable[d][h-1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h-1].equals("X"))
                            && !(teachers[k].facultyResponse.timeTable.timetable[d][h-2].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h-2].equals("X"))){
                        continuousPrevForK=2;
                    }

                    if(h<8 && !(teachers[k].facultyResponse.timeTable.timetable[d][h+1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h+1].equals("X"))
                            && !(teachers[k].facultyResponse.timeTable.timetable[d][h+2].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h+2].equals("X"))){
                        continuousNextForK=2;
                    }

                    if(h>=1 && h<=8 &&  !(teachers[k].facultyResponse.timeTable.timetable[d][h-1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h-1].equals("X"))
                            && !(teachers[k].facultyResponse.timeTable.timetable[d][h+1].equals("null") || teachers[k].facultyResponse.timeTable.timetable[d][h+1].equals("X"))){
                        continuousPrevNextForK=2;
                    }

                    if(continuousNextForK>=2 || continuousPrevForK>=2 || continuousPrevNextForK>=2 ){
                        continue;
                    }

                    if (teachers[k].isFree(d, h) && teachers[k].isFree(d, h + 1)
                            && teachers[k].labHours >= 2 ) {

                        teachers[j].assign(d, h, sub + ":Lab:" + "3");
                        teachers[j].assign(d, h + 1, sub + ":Lab:" + "3");
                        teachers[k].assign(d, h, sub + ":Lab:" + "3");
                        teachers[k].assign(d, h + 1, sub + ":Lab:" + "3");

                        lab.assign(d, h, sub + ":"+ sections[sectionIndexArray.get(sectionIndex)].secId + "-"+
                                sections[sectionIndexArray.get(sectionIndex)+1].secId + ":Lab");
                        lab.assign(d, h+1, sub + ":"+ sections[sectionIndexArray.get(sectionIndex)].secId + "-"+
                                sections[sectionIndexArray.get(sectionIndex)+1].secId + ":Lab");


                        teachers[j].labHours -= 2;
                        teachers[k].labHours -= 2;

                        if(helperDecLabs(teachers,lab,sections,d,h+2,sectionIndex,sectionIndexArray,rooms,sectionsArray)){
                            return true;
                        }


                        teachers[j].assign(d, h, "null");
                        teachers[j].assign(d, h + 1, "null");
                        teachers[k].assign(d, h, "null");
                        teachers[k].assign(d, h + 1,"null");

                        lab.assign(d, h, "null");
                        lab.assign(d, h + 1, "null");

                        teachers[j].labHours += 2;
                        teachers[k].labHours += 2;

                    }
                }
            }
        }
        if(j==teachers.length){
            return false;
        }

        if(k==teachers.length){
            return false;
        }
        return true;
    }



    static boolean assignDccCourses(Teacher[] teachers, Room[] rooms,Section[] sections){
        ArrayList<Teacher> teacherArrayList = new ArrayList<>();
        for(int i=0;i<teachers.length;++i){
            teacherArrayList.add(teachers[i]);
        }
        Collections.shuffle(teacherArrayList);

        //printing the teachers
        for(int i=0;i<teacherArrayList.size();++i){
            System.out.println(teacherArrayList.get(i).facultyResponse.name);
        }

        int teacherIndex = 0;

        ArrayList<ArrayList<String>> subjectListOfAllTeachers = new ArrayList<ArrayList<String>>(teacherArrayList.size());
        int subjectListIndex = 0;
        for(int i=0;i<teacherArrayList.size();++i){

            int year;
            ArrayList<String> a1 = new ArrayList<>();
            if(!teacherArrayList.get(i).facultyResponse.subject1.equals("null")){
                year = Integer.parseInt(teacherArrayList.get(i).facultyResponse.subject1.charAt(2) + "");
                if (!(year < 2 || year > 4 || teacherArrayList.get(i).facultyResponse.subject1.length() != 5)) {
                    a1.add(teacherArrayList.get(i).facultyResponse.subject1);
                }
            }
            if(!teacherArrayList.get(i).facultyResponse.subject2.equals("null")){
                year = Integer.parseInt(teacherArrayList.get(i).facultyResponse.subject2.charAt(2) + "");
                if (!(year < 2 || year > 4 || teacherArrayList.get(i).facultyResponse.subject2.length() != 5)) {
                    a1.add(teacherArrayList.get(i).facultyResponse.subject2);
                }
            }
            if(!teacherArrayList.get(i).facultyResponse.subject3.equals("null")){
                year = Integer.parseInt(teacherArrayList.get(i).facultyResponse.subject3.charAt(2) + "");
                if (!(year < 2 || year > 4 || teacherArrayList.get(i).facultyResponse.subject3.length() != 5)) {
                    a1.add(teacherArrayList.get(i).facultyResponse.subject3);
                }
            }
            subjectListOfAllTeachers.add(a1);
            Collections.shuffle(subjectListOfAllTeachers.get(i));

            for(int l=0;l<subjectListOfAllTeachers.get(i).size();++l){
                System.out.println(subjectListOfAllTeachers.get(i).get(l));
            }
        }

        // initial section passing as 0
        // it can either be 0 or 1
        // 0 means A section and 1 meand B section
        // check for getinitialsection + section passed
        System.out.println("inside the dcc function after the teachers name");
        if(helperDccCourses(teacherArrayList,teacherIndex,rooms,sections,subjectListOfAllTeachers,subjectListIndex,0,0)||
                helperDccCourses(teacherArrayList,teacherIndex,rooms,sections,subjectListOfAllTeachers,subjectListIndex,1,0)){

            return true;
        }
        return false;
    }

    static boolean helperDccCourses(ArrayList<Teacher> teacherArrayList,int teacherIndex, Room[] rooms,Section[] sections,
                                    ArrayList<ArrayList<String>> subjectListOfAllTeachers, int subjectListIndex, int sectionPassed,int currentHour){

        if(teacherIndex>=teacherArrayList.size()){
            System.out.println("helper dcc in if 1");

            Teacher[] teachers = new Teacher[teacherArrayList.size()];
            for(int i=0;i<teacherArrayList.size();++i){
                teachers[i]=teacherArrayList.get(i);
            }
            ArrayList<Teacher> newTeacherArrayList = new ArrayList<>();
            for(int i=0;i<teacherArrayList.size();++i){
                newTeacherArrayList.add(teacherArrayList.get(i));
            }
            if(assignLabs(teachers, rooms[3], sections,rooms,newTeacherArrayList)){
                return true;
            }
            return false;

        }
        else if(subjectListIndex>=subjectListOfAllTeachers.get(teacherIndex).size()) {

            System.out.println("helper dcc in if 2");
            return (helperDccCourses(teacherArrayList, teacherIndex+1, rooms, sections, subjectListOfAllTeachers, 0, 0,0)||
                    helperDccCourses(teacherArrayList, teacherIndex+1, rooms, sections, subjectListOfAllTeachers, 0, 1,0));
        }
        else if(currentHour>=courseDataHM.get(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)).tutHours){
            System.out.println("helper dcc in if 3");
            sections[getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed].subjects.put(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex),1);
            if(helperDccCourses(teacherArrayList, teacherIndex, rooms, sections, subjectListOfAllTeachers, subjectListIndex+1, 0,0) || helperDccCourses(teacherArrayList, teacherIndex, rooms, sections, subjectListOfAllTeachers, subjectListIndex+1, 1,0)){
                return true;
            }
            sections[getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed].subjects.remove(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex));
            return false;
        }
        else{

            if(sections[getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed].subjects.containsKey(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex))){
                return false;
            }

            System.out.println("helper dcc in if 4");
            int [] D = new int[5];
            int [] H = new int[10];
            for(int d=0;d<5;++d){
                int classes = 10;
                for(int h=0;h<10;++h){
                    if(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[d][h].equals("null") || teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[d][h].equals("X")){
                        classes--;
                    }
                }
                D[d]=classes;
            }

            for(int h =0 ;h<10;++h){
                int classes = 5;
                for(int d=0;d<5;++d){
                    if(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[d][h].equals("null") || teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[d][h].equals("X")){
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
                    String tempArr[]= teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[newDP.get(j)][k].split(":");
                    for (String temp: tempArr){
                        //System.out.println(temp);
                        if(temp.equals(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex))){
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

                    if(k-1>=0){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-1].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-1].equals("X"))){
                            continuousLecturesPrev++;
                        }
                    }
                    if(k-2>=0){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-2].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-2].equals("X"))){
                            continuousLecturesPrev++;
                        }
                    }

                    if(k+1<10){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+1].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+1].equals("X"))){
                            continuousLecturesNext++;
                        }
                    }
                    if(k+2<10){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+2].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+2].equals("X"))){
                            continuousLecturesNext++;
                        }
                    }

                    if(k-1>=0){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-1].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k-1].equals("X"))){
                            continuousLecturesPrevNext++;
                        }
                    }
                    if(k+1<10){
                        if(!(teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+1].equals("null")||
                                teacherArrayList.get(teacherIndex).facultyResponse.timeTable.timetable[j][k+1].equals("X"))){
                            continuousLecturesPrevNext++;
                        }
                    }

                    if(continuousLecturesPrev>=2 || continuousLecturesNext>=2 || continuousLecturesPrevNext>=2){
                        continue;
                    }



                    // D[newDay]<5 to make every day load of teacher to be max of 4
                    //**** also add the constraint for the break between 12-2
                    if(teacherArrayList.get(teacherIndex).isFree(newDay,newHour) && sections[getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed].isFree(newDay,newHour) && rooms[(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2].isFree(newDay,newHour) && D[newDay]<6){
                        //assign classes to everything

                        teacherArrayList.get(teacherIndex).assign(newDay,newHour,subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)+":"+((((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2)+2)==2?"s":((((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2)+2)==3)?"t":"f")+ ((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)%2==0?"1:":"2:") +"Lecture:" + (getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2);
                        sections[(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)].assign(newDay,newHour,subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)+":Lecture:"+(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2+":"+teacherArrayList.get(teacherIndex).facultyResponse.name);
                        rooms[(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2].assign(newDay, newHour, subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)+":"+((((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2)+2)==2?"s":((((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2)+2)==3)?"t":"f")+ ((getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)%2==0?"1:":"2:") +"Lecture:"+"t.facultyResponse.name");

                        System.out.println("assigned to teacher " + teacherArrayList.get(teacherIndex).facultyResponse.name + " " + subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex) + " in section" + (getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed));

                        if(helperDccCourses(teacherArrayList, teacherIndex, rooms, sections, subjectListOfAllTeachers, subjectListIndex, sectionPassed,currentHour+1)){
                            return true;
                        }

                        teacherArrayList.get(teacherIndex).assign(newDay,newHour,"null");
                        sections[(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)].assign(newDay,newHour,"null");
                        rooms[(getInitialSectionSaag(subjectListOfAllTeachers.get(teacherIndex).get(subjectListIndex)) + sectionPassed)/2].assign(newDay, newHour, "null");


                    }
                }
            }
            return false;

        }
    }


    static boolean assignLabs(Teacher[] teachers, Room lab, Section[] sections,Room[] rooms,ArrayList<Teacher> teachersArrayList){
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

        ArrayList<String> coursesWithLabList = new ArrayList<>();

        for (String sub : coursesWithLab) {
            coursesWithLabList.add(sub);
        }
        Collections.shuffle(coursesWithLabList);
        System.out.println("Labs function not helper" + coursesWithLabList.size());
        if(helperLabs(coursesWithLabList,0,0,0,new Teacher(),new Teacher(),
                teachersArrayList,rooms,sections,-1,-1,lab)){
            return true;
        }
        return false;

    }

    static boolean helperLabs(ArrayList<String> coursesWithLabList,int courseWithLabsListIndex,int sectionIndex,
                              int selectedTeacherIndex,Teacher teacher1,Teacher teacher2,ArrayList<Teacher> teachers,
                              Room[] rooms,Section[] sections,int currD, int currH,Room lab){
        if(courseWithLabsListIndex>=coursesWithLabList.size()){
            System.out.println("inside lab helper 1");
            return true;
        }
        else if(sectionIndex>1){
            System.out.println("inside lab helper 2");

            return helperLabs(coursesWithLabList,courseWithLabsListIndex+1,0,0,new Teacher(),
                    new Teacher(),teachers,rooms,sections,-1,-1,lab);
        }
        else if(selectedTeacherIndex>1){
            System.out.println("inside lab helper 3");

            return helperLabs(coursesWithLabList,courseWithLabsListIndex,sectionIndex+1,0,new Teacher(),
                    new Teacher(),teachers,rooms,sections,-1,-1,lab);
        }
        else if(selectedTeacherIndex==1){
            System.out.println("inside lab helper 4");

            int initialSection = getInitialSectionSaag(coursesWithLabList.get(courseWithLabsListIndex));
            int sectionToAssign = initialSection + sectionIndex;



            int j;
            for(j = 0;j<teachers.size();++j){
                Teacher teacher = teachers.get(j);
                if(teachers.get(j)!= teacher1 && teachers.get(j).isFree(currD,currH) && teachers.get(j).labHours>=2){

                    int numOfClasses = 0;
                    for(int p =0;p<10;++p){
                        if(!(teacher.facultyResponse.timeTable.timetable[currD][p].equals("null") ||
                                teacher.facultyResponse.timeTable.timetable[currD][p].equals("X"))){
                            numOfClasses++;
                        }
                    }
                    if(numOfClasses>3){
                        continue;
                    }

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

                    teacher2 = teachers.get(j);
                    //assign lab here
                    teachers.get(j).labHours-=2;


                    teachers.get(j).assign(currD,currH, coursesWithLabList.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                    sections[sectionToAssign].assign(currD,currH,coursesWithLabList.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                    rooms[3].assign(currD, currH, coursesWithLabList.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");

                    teachers.get(j).assign(currD,currH+1, coursesWithLabList.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                    sections[sectionToAssign].assign(currD,currH+1,coursesWithLabList.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                    rooms[3].assign(currD, currH+1, coursesWithLabList.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");


                    if(helperLabs(coursesWithLabList,courseWithLabsListIndex,sectionIndex,selectedTeacherIndex+1,teacher1,
                            teacher2,teachers,rooms,sections,currD,currH,lab)){
                        return true;
                    }
                    teachers.get(j).labHours+=2;
                    teacher2= new Teacher();

                    //unassign everything here
                    teachers.get(j).assign(currD,currH, "null");
                    sections[sectionToAssign].assign(currD,currH,"null");
                    rooms[3].assign(currD, currH, "null");

                    teachers.get(j).assign(currD,currH+1, "null");
                    sections[sectionToAssign].assign(currD,currH+1,"null");
                    rooms[3].assign(currD, currH+1, "null");

                }
            }

        }
        else if(selectedTeacherIndex==0){
            System.out.println("inside lab helper 5");

            int initialSection = getInitialSectionSaag(coursesWithLabList.get(courseWithLabsListIndex));
            int sectionToAssign = initialSection + sectionIndex;
            for(int d = 0;d<5;++d){
                for(int h= 0;h<9;++h){
                    if(sections[sectionToAssign].isFree(d,h) && lab.isFree(d,h) &&
                            sections[sectionToAssign].isFree(d,h+1) && lab.isFree(d,h+1)){
                        int j;
                        for(j = 0;j<teachers.size();++j){
                            Teacher teacher = teachers.get(j);


                            if(teachers.get(j).isFree(d,h) && teachers.get(j).labHours>=2){

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



                                teachers.get(j).labHours-=2;
                                teacher1 = teachers.get(j);
                                //assign lab here



                                teachers.get(j).assign(d,h, coursesWithLabList.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                                sections[sectionToAssign].assign(d,h,coursesWithLabList.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                                rooms[3].assign(d, h, coursesWithLabList.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");

                                teachers.get(j).assign(d,h+1, coursesWithLabList.get(courseWithLabsListIndex) +":"+ getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab"+":"+"3");
                                sections[sectionToAssign].assign(d,h+1,coursesWithLabList.get(courseWithLabsListIndex)+":"+"Lab"+":"+"3");
                                rooms[3].assign(d, h+1, coursesWithLabList.get(courseWithLabsListIndex)+":"+getTheYearAndSection(initialSection,sectionIndex)+":"+"Lab");



                                if(helperLabs(coursesWithLabList,courseWithLabsListIndex,sectionIndex,selectedTeacherIndex+1,teacher1,
                                        teacher2,teachers,rooms,sections,d,h,lab)){
                                    return true;
                                }

                                teachers.get(j).labHours+=2;
                                teacher1= new Teacher();
                                //unassign everything here
                                teachers.get(j).assign(d,h, "null");
                                sections[sectionToAssign].assign(d,h,"null");
                                rooms[3].assign(d, h, "null");

                                teachers.get(j).assign(d,h+1, "null");
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


    public OverallTT generateTimeTable() {

        System.out.println("gleba in first function 2 call at 1");

        Room[] rooms = new Room[4];
        for (int i = 0; i < 4; i++) {
            rooms[i] = new Room(i);
        }
        Teacher[] teachers = new Teacher[teachersData.size()];
        for (int i = 0; i < teachersData.size(); i++) {
            System.out.println("inn teacher " + i);
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
        assignTeachersToFirstYear(teachers,rooms,sectionsArray);


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

        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.sections.get(i).timeTable.timetable[j][k].equals("null")) {
                        overallTT.sections.get(i).timeTable.timetable[j][k] = "-";
                    }
                }
            }
        }

        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    System.out.print(overallTT.sections.get(i).timeTable.timetable[j][k] + "  ");
                }
                System.out.println("");
            }
        }


        System.out.println("Size of First Year = " + firstYearData.size());

        for (Teacher t : teachers) {
            int count = 0;
            for (int d = 0; d < 5; d++) {
                for (int h = 0; h < 10; h++) {
                    if (!t.facultyResponse.timeTable.timetable[d][h].equals("-")) {
                        count++;
                    }
                }
            }
            System.out.println(t.facultyResponse.facultyid + " -- hours = " + count);
        }
        return overallTT;
    }
}