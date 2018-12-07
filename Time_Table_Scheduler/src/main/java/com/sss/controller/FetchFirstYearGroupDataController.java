package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.FirstYearGroup;
import com.sss.classModel.FirstYearGroupList;
import com.sss.classModel.FullFacultyResponse;
import com.sss.classModel.OnlyStringClass;
import com.sss.services.GetAllTeacherTimeTable;
import com.sss.services.GetFirstYearTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class FetchFirstYearGroupDataController {

    @PostMapping("/batch")
    public ResponseEntity<FirstYearGroup> getSingleGroupData(@RequestBody String groupid) throws IOException {

        OnlyStringClass onlyStringClass = new ObjectMapper().readValue(groupid,OnlyStringClass.class);
        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        FirstYearGroupList firstYearGroupList = getFirstYearTimeTable.getFirstYearTimeTableData();


        FirstYearGroup firstYearGroup = new FirstYearGroup();
        for(int i=0;i<firstYearGroupList.firstYearGroupList.size();++i){
            firstYearGroup = firstYearGroupList.firstYearGroupList.get(i);
            if(firstYearGroup.groupId.equals(onlyStringClass.loginid)){
                return new ResponseEntity<>(firstYearGroup, HttpStatus.OK);
            }
        }

        System.out.println("sagar "+ groupid);
        return new ResponseEntity<>(firstYearGroup, HttpStatus.OK);

    }

}
