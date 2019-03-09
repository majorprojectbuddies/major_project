package com.sss.services;

import com.sss.classModel.*;
import com.sss.dao.SignupDao;
import com.sss.model.Faculty;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Service
public class GenerateAllTimeTablesAgain {

    public OverallTT overallTT;
    public ArrayList<FacultyResponse> teachersData;
    public ArrayList<Course> courseDataList;
    public ArrayList<FirstYearGroup> firstYearData;
    public ArrayList<PhdGroup> phdData;
    public ArrayList<Section> sections;
    public ArrayList<FacultyResponse> unfreezedTeachersData;
    public ArrayList<FacultyResponse> freezedTeachersData;
    public ArrayList<FirstYearGroup> freezedFirstYearData;
    public ArrayList<FirstYearGroup> unfreezedFirstYearData;
    // map of (course name, pair of( day<0-mon,1-tue...>, start time(0-9am,1-10am...)))
    public Map<String, Pair<Integer, Integer>> decLabSlotToBeAssigned;
    public ArrayList<String> decRemainingLabList;
    public Room[] rooms;
    //map that stores no of teachers assigned to every dec lab along with day and time vals
    // --initialize it using sections with 0
    public Map<String,Pair<Integer,Pair<Integer,Integer>>> decLabsFixedMap;


    public ArrayList<String> dccLabsAssignToBothSections;
    public ArrayList<Pair<String,Integer>> dccLabsAssignToSingleSection;
    public ArrayList<Pair<Pair<String,Integer>,Pair<Integer,Integer>>> dccLabsAssignToSingleTeacher;
    public HashMap<String, Course> courseDataHM;

    public OverallTT getAllTimeTableAgainData(){
        System.out.println("overallTT IS faculty response size is : " + overallTT.facultyResponses.size());

        //Get data of all teachers from db
        GetAllTeacherTimeTable getAllTeacherTimeTable = new GetAllTeacherTimeTable();
        teachersData = getAllTeacherTimeTable.getAllFacultyData().fullFacultyResponseList;

        System.out.println("Teacher Data command run");

        //Get course data from db
        SignupDao signupDao = new SignupDao();
        courseDataList = signupDao.FetchCourseData().courseList;

        System.out.println("Course List command run");

        //Get 1st year data
        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        firstYearData = getFirstYearTimeTable.getFirstYearTimeTableData().firstYearGroupList;

        System.out.println("first year data command run");

        //Get phd timetable
        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        phdData = getPhdTimeTable.getPhdTimeTableData().fullPhdGroupList;

        System.out.println("PHD data command run");

        //Get all sections data
        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        sections = getSectionTimeTable.getSectionTimeTableData().fullSectionGroupList;

        System.out.println("Sections data command run");

        //find unfreezed and freezed teachers
        unfreezedTeachersData = new ArrayList<>();
        freezedTeachersData = new ArrayList<>();
        Iterator itr = overallTT.facultyResponses.iterator();
        while (itr.hasNext()){
            FacultyResponse facultyResponseobj = (FacultyResponse) itr.next() ;
            System.out.println(facultyResponseobj.isFreezed);
            if(facultyResponseobj.isFreezed.equals(false)){
                System.out.println("its unfreezed");
                //we need to send empty data of teacher (which was in db) not the filled one
                Iterator fItr = teachersData.iterator();
                while(fItr.hasNext()){
                    FacultyResponse facultyResponseobjT = (FacultyResponse) fItr.next();
                    if(facultyResponseobj.name.equals(facultyResponseobjT.name)){
                        //we found the initial data of the teacher..so add this to the list of unfreezed teachers
                        unfreezedTeachersData.add(facultyResponseobjT);
                        break;
                    }
                }
            }else{
                System.out.println("its freezed");
                freezedTeachersData.add(facultyResponseobj);
            }
        }

        //find freezed and unfreezed first year data
        freezedFirstYearData = new ArrayList<>();
        unfreezedFirstYearData = new ArrayList<>();
        Set<String> hashSet = new HashSet<>();

        Iterator itrFirst = freezedTeachersData.iterator();
        while(itrFirst.hasNext()){
            FacultyResponse facultyResponseObj = (FacultyResponse) itrFirst.next();
            for(int i = 0;i<5;i++){
                for(int j=0;j<10;j++){
                    if(facultyResponseObj.timeTable.timetable[i][j].equals("-")
                            || facultyResponseObj.timeTable.timetable[i][j].equals("X")
                            ||facultyResponseObj.timeTable.timetable[i][j].equals("null")){
                        continue;
                    }else{
                        //break the string -  (MA102:B6:LECTURE:SPS)
                        String s = facultyResponseObj.timeTable.timetable[i][j];
                        String[] breaks = s.split(":");
                        if(breaks[0].equals("MA102")){
                            hashSet.add(breaks[1]);
                        }
                    }
                }
            }
        }

        //Now finding first year freezed and unfreezed using group id
        Iterator it = firstYearData.iterator();
        while(it.hasNext()){
            FirstYearGroup firstYearGroup = (FirstYearGroup) it.next();
            if(hashSet.contains(firstYearGroup.groupId)){
                //its freezed
                System.out.println("freezed group id is: " + firstYearGroup.groupId);
                freezedFirstYearData.add(firstYearGroup);
            }else{
                //not freezed
                System.out.println("unfreezed group id is: " + firstYearGroup.groupId);
                unfreezedFirstYearData.add(firstYearGroup);
            }
        }

        System.out.println("init declabsfixed");
        //Initialize this map with values from sections array
        decLabsFixedMap = new HashMap<>();
        Iterator sectionItr = sections.iterator();
        while(sectionItr.hasNext()){
            Section sec = (Section) sectionItr.next();
            for(int d=0;d<5;d++){
                for(int h=0;h<10;h++){
                    String cellValue = sec.timeTable.timetable[d][h];
                    System.out.println(cellValue);
                    if(cellValue.equals("-") || cellValue.equals("null") || cellValue.equals("X")){
                        continue;
                    }else{
                        System.out.println("gonna separate");

                        if(cellValue.contains("lab")){
                            //its an elective lab
                            System.out.println("found elective lab");
                            if(decLabsFixedMap.containsKey(cellValue)){
                                continue;
                            }else{
                                //pair for day time
                                Pair p = new Pair(0,0);
                                //pair for val and p
                                Pair q = new Pair(0,p);
                                System.out.println("putting val");
                                decLabsFixedMap.put(cellValue,q);
                            }
                        }else{
                            //dcc course or lab
                            continue;
                        }
                    }
                }
            }
        }


        // find lab slot that needs to be definitely filled
        //format -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
        //format - (MC312:t1:Lab:COMPUTATION LAB)
        System.out.println("put values in dec lab fixed");
        //put values in decLabFixedMap
        Iterator itrFreezedTeachersData = freezedTeachersData.iterator();
        while(itrFreezedTeachersData.hasNext()){
            FacultyResponse facultyResponse = (FacultyResponse) itrFreezedTeachersData.next();
            for(int i=0;i<5;i++){
                for(int j=0;j<10;j++){
                    String s = facultyResponse.timeTable.timetable[i][j];
                    if(s.equals("-") || s.equals("null") || s.equals("X")){
                        continue;
                    }else{
                        //separate the string to check whether its a lab or not
                        String[] breaks = s.split(":");
                        if(breaks[2].equals("Lab")){
                            //check if its a elective lab or normal lab
                            if (breaks[0].contains("lab")){
                                //its an electives lab
                                Pair<Integer,Pair<Integer,Integer>> currPair = decLabsFixedMap.get(breaks[0]);
                                Integer currVal = currPair.getKey();
                                currVal++;
                                Pair p = currPair.getValue();
                                Pair q = new Pair(currVal,p);
                                decLabsFixedMap.put(breaks[0],q);
                            }else {
                                //dcc lab
                                continue;
                            }
                        }else{
                            continue;
                        }
                    }
                }
            }
        }

        //FILL REMAINING LAB LIST AND LAB SLOTS
        decRemainingLabList = new ArrayList<>();
        decLabSlotToBeAssigned = new HashMap<>();
        System.out.println("doing final work dec lab");
        for (Map.Entry<String, Pair<Integer,Pair<Integer,Integer>>> entry : decLabsFixedMap.entrySet()) {
            String courseId = entry.getKey();
            Pair<Integer,Pair<Integer,Integer>> valPair = entry.getValue();
            //case 1 : no teacher : 0 val
            if(valPair.getKey().equals(0)){
                decRemainingLabList.add(courseId);
            }else if(valPair.getKey().equals(2)){
                //case 2: 1 teacher val : 2
                decLabSlotToBeAssigned.put(courseId,valPair.getValue());
            }else if(valPair.getKey().equals(4)){
                //case 3: 2 teacher val : 4
                //ignore
                continue;
            }
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

        System.out.println("filling sec");
        //Fill Sections
        Iterator itrFreezedTeachersData2 = freezedTeachersData.iterator();
        while(itrFreezedTeachersData2.hasNext()){
            FacultyResponse facultyResponse = (FacultyResponse) itrFreezedTeachersData2.next();
            for(int i=0;i<5;i++){
                for(int j=0;j<10;j++){
                    String s = facultyResponse.timeTable.timetable[i][j];
                    if(s.equals("-") || s.equals("null") || s.equals("X")){
                        continue;
                    }else{
                        //separate the string to check whether its a lab or lecture
                        String[] breaks = s.split(":");
                        //format for teacher -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
                        if(breaks[2].equals("Lab")){
                            //its a lab
                            String[] breaksLab = breaks[1].split("-");
                            for(int l=0;l<breaksLab.length;l++){
                                String tempSec = breaksLab[l];
                                Iterator secItr = sections.iterator();
                                while(secItr.hasNext()) {
                                    Section sec = (Section) secItr.next();
                                    if (sec.secId.equals(tempSec)) {
                                        //check if its a elective lab or normal lab
                                        if (breaks[0].contains("lab")) {
                                            //its an elective lab
                                            //format of sec for elective lab-  (MC312(SLOT-A)lab:Lab:COMPUTATION LAB)
                                            sec.timeTable.timetable[i][j] = breaks[0] + ":Lab" + ":3";
                                        } else {
                                            //its a normal lab
                                            //format of sec for normal lab -  (MC318:Lab:COMPUTATION LAB)
                                            sec.timeTable.timetable[i][j] = breaks[0] + ":Lab" + ":3";
                                        }
                                        break;
                                    }
                                }
                            }

                        }else{
                            //its a lecture
                            Iterator secItr = sections.iterator();
                            while(secItr.hasNext()) {
                                Section sec = (Section) secItr.next();
                                if(sec.secId.equals(breaks[1])){
                                    //format for section -  (MC324:Lecture:1:PAYAL)
                                    //format for teacher -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
                                    sec.timeTable.timetable[i][j] = breaks[0] + ":Lecture:" + breaks[3] + ":" + facultyResponse.name;
                                    sec.subjects.put(breaks[0],1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("filling sec compp");


        //FILL ROOMS
        System.out.println("filling rooms");
        rooms = new Room[4];
        for (int i = 0; i < 4; i++) {
            rooms[i] = new Room(i);
        }
        Iterator itrFreezedTeachersData3 = freezedTeachersData.iterator();
        while(itrFreezedTeachersData3.hasNext()){
            FacultyResponse facultyResponse = (FacultyResponse) itrFreezedTeachersData3.next();
            for(int i=0;i<5;i++){
                for(int j=0;j<10;j++){
                    String s = facultyResponse.timeTable.timetable[i][j];
                    if(s.equals("-") || s.equals("null") || s.equals("X")){
                        continue;
                    }else{
                        //separate the string to check whether its a lab or lecture
                        String[] breaks = s.split(":");
                        //format for teacher -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
                        if(breaks[2].equals("Lab")){
                            //its a lab so block the lab
                            //format for lab room-  (MC312(SLOT-A)LAB:t2:Lab)
                            String t_id = breaks[0] + ":" + breaks[1] + ":" + "Lab";
                            rooms[3].assign(i,j,t_id);
                        }else{

                            //check if its a first year lecture--if so no need to do anything--skip it!
                            if(breaks[0].equals("MA102") || breaks[0].equals("AM501")){
                                continue;
                            }
                            //its a lecture so block the room accordingly
                            //format for room with lecture -  (MC324:t1:Lecture:PAYAL)
                            String t_id = breaks[0] + ":" + breaks[1] + ":Lecture:" + facultyResponse.name;
                            rooms[Integer.parseInt(breaks[3])].assign(i,j,t_id);
                        }
                    }
                }
            }
        }
        System.out.println("filling rooms comp");

        // Main Function Calling
        System.out.println("main func prep");






        //temp work
        System.out.println("lab slot to be assigned are");
        for (Map.Entry<String, Pair<Integer,Integer>> entry : decLabSlotToBeAssigned.entrySet()) {
            String courseId = entry.getKey();
            System.out.println(courseId);
        }
        System.out.println("remaining labs are");
        for(String s: decRemainingLabList){
            System.out.println(s);
        }



        // Filling the DCC lab lists

        System.out.println("gizmo finding error 1");
        //Filling courses details
        this.courseDataHM = new HashMap<>();
        for (Course course : courseDataList) {
            this.courseDataHM.put(course.courseId, course);
        }
        //Finding courses with labs
        System.out.println("gizmo finding error 2");

        HashSet<String> coursesWithLab = new HashSet<>();
        for (FacultyResponse t : teachersData) {

            String sub = t.subject1;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }
            sub = t.subject2;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }
            sub = t.subject3;
            if (sub.length() == 5 && courseDataHM.get(sub).containsLab) {
                coursesWithLab.add(sub);
            }
        }

        System.out.println("gizmo finding error 3");
        /*ArrayList<String> coursesWithLabList = new ArrayList<>();

        for (String sub : coursesWithLab) {
            coursesWithLabList.add(sub);
        }
        Collections.shuffle(coursesWithLabList);*/

        HashMap<String,Integer> labsSectionsDetails = new HashMap<>();
        HashMap<String,Pair<Integer,Integer>> previousTeacherLabDetails = new HashMap<>();

        System.out.println("gizmo finding error 4");
        //Fill the labSectionDetails with 0
        for (String sub : coursesWithLab) {
            labsSectionsDetails.put(sub,0);
        }

        System.out.println("gizmo finding error 5");
        for(int i=0;i<freezedTeachersData.size();++i){
            for(int j=0;j<5;++j){
                for(int k=0;k<10;++k){
                    String sub = freezedTeachersData.get(i).timeTable.timetable[j][k];
                    System.out.println("sub value is "+ sub);
                    if(sub.equals("null") || sub.equals("X") || sub.equals("-")){
                        System.out.println("gizmo finding error 5.1");
                        continue;
                    }
                    else{
                        //separate the string to check whether its a lab or lecture
                        String[] breaks = sub.split(":");
                        //format for teacher -  (MC312(SLOT-A)lab:t1:Lab:COMPUTATION LAB)
                        if(breaks[2].equals("Lab")){
                            if(!breaks[0].contains("lab")){
                                if(breaks[1].contains("1")){
                                    if(labsSectionsDetails.get(breaks[0])==0 || labsSectionsDetails.get(breaks[0])==8 ||
                                            labsSectionsDetails.get(breaks[0])==16 || labsSectionsDetails.get(breaks[0])==24 ||
                                            labsSectionsDetails.get(breaks[0])==32){
                                        previousTeacherLabDetails.put(breaks[0]+":0",new Pair(j,k));
                                    }
                                    labsSectionsDetails.put(breaks[0],labsSectionsDetails.get(breaks[0])+1);
                                    System.out.println("gizmo finding error 5.2");
                                }
                                else if(breaks[1].contains("2")){
                                    if(labsSectionsDetails.get(breaks[0])==0 || labsSectionsDetails.get(breaks[0])==1 ||
                                            labsSectionsDetails.get(breaks[0])==2 || labsSectionsDetails.get(breaks[0])==3 ||
                                            labsSectionsDetails.get(breaks[0])==4){
                                        previousTeacherLabDetails.put(breaks[0]+":1",new Pair(j,k));
                                    }
                                    labsSectionsDetails.put(breaks[0],labsSectionsDetails.get(breaks[0])+8);
                                    System.out.println("gizmo finding error 5.3");
                                }
                            }

                        }
                    }
                }
            }
        }

        //Populating these arraylists
        dccLabsAssignToBothSections = new ArrayList<>();
        dccLabsAssignToSingleSection = new ArrayList<>();
        dccLabsAssignToSingleTeacher = new ArrayList<>();
        System.out.println("gizmo finding error 6");

        Iterator<Map.Entry<String, Integer>> itr2 = labsSectionsDetails.entrySet().iterator();
        while(itr2.hasNext()){
            System.out.println("gizmo finding error 6.1");
            Map.Entry<String, Integer> entry = itr2.next();
            System.out.println("gizmo finding error 6.2");
            if(entry.getValue()==0){
                dccLabsAssignToBothSections.add(entry.getKey());
            }
            else if(entry.getValue()==2){
                dccLabsAssignToSingleSection.add(new Pair(entry.getKey(),1));
                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),0),previousTeacherLabDetails.get(entry.getKey()+":0")));
            }
            else if(entry.getValue()==16){
                dccLabsAssignToSingleSection.add(new Pair(entry.getKey(),0));
                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),1),previousTeacherLabDetails.get(entry.getKey()+":1")));
            }
            else if(entry.getValue()==18){
                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),0),previousTeacherLabDetails.get(entry.getKey()+":0")));

                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),1),previousTeacherLabDetails.get(entry.getKey()+":1")));
            }
            else if(entry.getValue()==32){
                dccLabsAssignToSingleSection.add(new Pair(entry.getKey(),0));
            }
            else if(entry.getValue()==4){
                dccLabsAssignToSingleSection.add(new Pair(entry.getKey(),1));
            }
            else if(entry.getValue()==34){
                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),0),previousTeacherLabDetails.get(entry.getKey()+":0")));
            }
            else if(entry.getValue()==20){
                dccLabsAssignToSingleTeacher.add(new Pair(new Pair(entry.getKey(),1),previousTeacherLabDetails.get(entry.getKey()+":1")));
            }
            else{

            }
        }


        System.out.println("gizmo finding error 7");


        TimeTableGeneratorAgain timeTableGeneratorAgain = new TimeTableGeneratorAgain(teachersData,courseDataList,
                firstYearData,phdData,sections,unfreezedTeachersData,
                freezedTeachersData, freezedFirstYearData,
                unfreezedFirstYearData,decLabSlotToBeAssigned,rooms,decRemainingLabList,dccLabsAssignToBothSections,
                dccLabsAssignToSingleSection,dccLabsAssignToSingleTeacher);
        System.out.println("main func called");
        OverallTT dataToBeReturned = timeTableGeneratorAgain.generateTimeTable();
        return dataToBeReturned;
    }
}
