package com.sss.controller;

import com.sss.classModel.FacultyResponse;
import com.sss.classModel.OverallResponse;
import com.sss.classModel.UpdateProfileResponse;
import com.sss.services.FetchFacultyCourseData;
import com.sss.services.UpdateFacultyData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class UpdateProfileController {

    @RequestMapping("/updateProfile")
    public ResponseEntity<UpdateProfileResponse> updateProfileData(@RequestBody FacultyResponse facultyResponse){
        UpdateFacultyData updateFacultyData = new UpdateFacultyData();
        return new ResponseEntity<>(updateFacultyData.updateFacultyData(facultyResponse), HttpStatus.OK);
    }
}
