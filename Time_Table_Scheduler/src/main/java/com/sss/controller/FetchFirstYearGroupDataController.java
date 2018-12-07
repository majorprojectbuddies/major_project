package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.*;
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
    public ResponseEntity<FirstYearGroup> getSingleGroupData(@RequestBody OnlyStringClass2 onlyStringClass2)  {

        //OnlyStringClass onlyStringClass = new ObjectMapper().readValue(groupid,OnlyStringClass.class);
        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        FirstYearGroupList firstYearGroupList = getFirstYearTimeTable.getFirstYearTimeTableData();


        FirstYearGroup firstYearGroup = new FirstYearGroup();
        for(int i=0;i<firstYearGroupList.firstYearGroupList.size();++i){
            firstYearGroup = firstYearGroupList.firstYearGroupList.get(i);
            if(firstYearGroup.groupId.equals(onlyStringClass2.groupid)){
                return new ResponseEntity<>(firstYearGroup, HttpStatus.OK);
            }
        }

        System.out.println("sagar "+ onlyStringClass2.groupid);
        return new ResponseEntity<>(firstYearGroup, HttpStatus.OK);

    }

}
