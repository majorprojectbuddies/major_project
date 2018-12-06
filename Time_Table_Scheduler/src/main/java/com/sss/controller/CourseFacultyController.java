package com.sss.controller;

import com.sss.classModel.OverallResponse;
import com.sss.services.FetchFacultyCourseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CourseFacultyController {

    @RequestMapping("/profile")
    public ResponseEntity<OverallResponse> getCourseFacultyData(@RequestBody String loginid){
        FetchFacultyCourseData fetchFacultyCourseData = new FetchFacultyCourseData();
        return new ResponseEntity<>(fetchFacultyCourseData.fetchFacultyCourseInfo(loginid), HttpStatus.OK);
    }
}
