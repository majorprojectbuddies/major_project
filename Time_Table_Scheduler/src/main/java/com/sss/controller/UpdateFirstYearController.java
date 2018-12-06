package com.sss.controller;


import com.sss.classModel.FacultyResponse;
import com.sss.classModel.FirstYearGroupList;
import com.sss.classModel.UpdateFirstYearResponse;
import com.sss.services.UpdateFirstYearTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class UpdateFirstYearController {
    @RequestMapping("/updateFirstYear")
    public ResponseEntity<UpdateFirstYearResponse> updateFirstYearData(@RequestBody FirstYearGroupList firstYearGroupList){
        UpdateFirstYearTimeTable updateFirstYearTimeTable = new UpdateFirstYearTimeTable();
        return new ResponseEntity<>(updateFirstYearTimeTable.updateFirstYearTimeTable(firstYearGroupList), HttpStatus.OK);
    }
}
