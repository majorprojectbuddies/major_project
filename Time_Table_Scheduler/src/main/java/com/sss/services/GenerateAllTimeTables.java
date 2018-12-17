package com.sss.services;

import com.sss.classModel.*;
import com.sss.dao.SignupDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GenerateAllTimeTables {

    public ArrayList<FacultyResponse> teachersData;
    public ArrayList<Course> courseDataList;
    public ArrayList<FirstYearGroup> firstYearData;
    public ArrayList<PhdGroup> phdData;
    public ArrayList<Section> sections;

    public OverallTT getAllTimeTableData(){


        System.out.println("gleba in first function call at 0");

        GetAllTeacherTimeTable getAllTeacherTimeTable = new GetAllTeacherTimeTable();
        teachersData = getAllTeacherTimeTable.getAllFacultyData().fullFacultyResponseList;

        System.out.println("gleba in first function call at 1");

        SignupDao signupDao = new SignupDao();
        courseDataList = signupDao.FetchCourseData().courseList;

        System.out.println("gleba in first function call at 2");

        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        firstYearData = getFirstYearTimeTable.getFirstYearTimeTableData().firstYearGroupList;

        System.out.println("gleba in first function call at 3");

        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        phdData = getPhdTimeTable.getPhdTimeTableData().fullPhdGroupList;

        System.out.println("gleba in first function call at 4");

        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        sections = getSectionTimeTable.getSectionTimeTableData().fullSectionGroupList;

        System.out.println("gleba in first function call at 5");

        System.out.println("teacherdata " + teachersData);

        System.out.println("gleba in first function call at 5.1");
        System.out.println("teacherdata " + teachersData.size());
        System.out.println("gleba in first function call at 5.2");
        TimeTableGenerator timeTableGenerator = new TimeTableGenerator(teachersData,courseDataList,firstYearData,phdData,sections);
        OverallTT overallTT = new OverallTT();
        overallTT = timeTableGenerator.generateTimeTable();

        System.out.println("gleba in first function call at 6");

        for(int i=0;i<overallTT.facultyResponses.size();++i){
            FacultyResponse facultyResponse = overallTT.facultyResponses.get(i);
            for(int j=0;j<5;++j){
                for(int k=0;k<10;++k){
                    System.out.print(facultyResponse.timeTable.timetable[j][k]+"   ");
                }
                System.out.println("");
            }
        }
        return overallTT;
    }

}
