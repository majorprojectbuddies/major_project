package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.FullFacultyResponse;
import com.sss.classModel.OnlyStringClass;
import com.sss.classModel.OverallResponse;
import com.sss.services.FetchFacultyCourseData;
import com.sss.services.GetAllTeacherTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
public class FetchAllFacultyDataController {

    @GetMapping("/fetchAllFacultyData")
    public ResponseEntity<FullFacultyResponse> getAllFacultyData() {
        //OnlyStringClass onlyStringClass = new ObjectMapper().readValue(loginid,OnlyStringClass.class);
        GetAllTeacherTimeTable getAllTeacherTimeTable = new GetAllTeacherTimeTable();
        //System.out.println("sagar "+ loginid);
        FullFacultyResponse fullFacultyResponse = getAllTeacherTimeTable.getAllFacultyData();

        for(int i=0;i<fullFacultyResponse.fullFacultyResponseList.size();++i){
            for(int j=0;j<5;++j){
                for(int k=0;k<10;++k){
                    if (fullFacultyResponse.fullFacultyResponseList.get(i).timeTable.timetable[j][k].equals("null")) {
                        fullFacultyResponse.fullFacultyResponseList.get(i).timeTable.timetable[j][k] = "-";
                    }

                    if (fullFacultyResponse.fullFacultyResponseList.get(i).timeTable.timetable[j][k].equals("0")) {
                        fullFacultyResponse.fullFacultyResponseList.get(i).timeTable.timetable[j][k] = "X";
                    }
                }
            }
        }




        return new ResponseEntity<>(fullFacultyResponse, HttpStatus.OK);

    }
}
