package com.sss.services;

import com.sss.classModel.*;
import com.sss.dao.SignupDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
                unfreezedTeachersData.add(facultyResponseobj);
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





        return overallTT;
    }
}
