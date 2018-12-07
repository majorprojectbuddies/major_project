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

    public void PrintTimeTable(){
        GetAllTeacherTimeTable getAllTeacherTimeTable = new GetAllTeacherTimeTable();
        teachersData = getAllTeacherTimeTable.getAllFacultyData().fullFacultyResponseList;

        SignupDao signupDao = new SignupDao();
        courseDataList = signupDao.FetchCourseData().courseList;

        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        firstYearData = getFirstYearTimeTable.getFirstYearTimeTableData().firstYearGroupList;

        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        phdData = getPhdTimeTable.getPhdTimeTableData().fullPhdGroupList;

        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        sections = getSectionTimeTable.getSectionTimeTableData().fullSectionGroupList;

        TimeTableGenerator timeTableGenerator = new TimeTableGenerator(teachersData,courseDataList,firstYearData,phdData,sections);
        OverallTT overallTT = new OverallTT();
        overallTT = timeTableGenerator.generateTimeTable();

        for(int i=0;i<overallTT.facultyResponses.size();++i){
            FacultyResponse facultyResponse = overallTT.facultyResponses.get(i);
            for(int j=0;j<5;++j){
                for(int k=0;k<10;++k){
                    System.out.print(facultyResponse.timeTable.timetable[j][k]+"   ");
                }
                System.out.println("");
            }
        }
    }

}
