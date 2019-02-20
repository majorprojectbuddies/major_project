package com.sss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.OnlyStringClass;
import com.sss.classModel.OverallResponse;
import com.sss.dao.SignupDao;
import com.sss.services.FetchFacultyCourseData;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
public class CourseFacultyController {

    @PostMapping("/profile")
    public ResponseEntity<OverallResponse> getCourseFacultyData(@RequestBody String loginid) throws IOException {
        OnlyStringClass onlyStringClass = new ObjectMapper().readValue(loginid,OnlyStringClass.class);
        FetchFacultyCourseData fetchFacultyCourseData = new FetchFacultyCourseData();
        System.out.println("sagar "+ loginid);
        return new ResponseEntity<>(fetchFacultyCourseData.fetchFacultyCourseInfo(onlyStringClass.loginid), HttpStatus.OK);
    }
}
