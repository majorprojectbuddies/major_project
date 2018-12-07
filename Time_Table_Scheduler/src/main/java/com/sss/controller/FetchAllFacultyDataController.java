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
        return new ResponseEntity<>(getAllTeacherTimeTable.getAllFacultyData(), HttpStatus.OK);

    }
}
