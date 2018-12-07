package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.FirstYearGroup;
import com.sss.classModel.FirstYearGroupList;
import com.sss.classModel.OnlyStringClass;
import com.sss.classModel.UpdateFirstYearResponse;
import com.sss.services.GetFirstYearTimeTable;
import com.sss.services.UpdateFirstYearTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class UpdateFirstYearGroupDataController {


    @PostMapping("/updateBatch")
    public ResponseEntity<UpdateFirstYearResponse> updateSingleGroupData(@RequestBody FirstYearGroup firstYearGroup) throws IOException {

        OnlyStringClass onlyStringClass = new ObjectMapper().readValue(firstYearGroup.groupId,OnlyStringClass.class);
        GetFirstYearTimeTable getFirstYearTimeTable = new GetFirstYearTimeTable();
        FirstYearGroupList firstYearGroupList = getFirstYearTimeTable.getFirstYearTimeTableData();
        FirstYearGroupList firstYearGroupList1 = new FirstYearGroupList();

        
        for(int i=0;i<firstYearGroupList.firstYearGroupList.size();++i){
            FirstYearGroup firstYearGroup1 = new FirstYearGroup();
            firstYearGroup1 = firstYearGroupList.firstYearGroupList.get(i);
            if(firstYearGroup1.groupId.equals(onlyStringClass.loginid)){
                firstYearGroup1.timeTable = firstYearGroup.timeTable;
            }
            firstYearGroupList1.firstYearGroupList.add(firstYearGroup1);
        }

        UpdateFirstYearTimeTable updateFirstYearTimeTable = new UpdateFirstYearTimeTable();

        System.out.println("sagar "+ firstYearGroup.groupId);
        return new ResponseEntity<>(updateFirstYearTimeTable.updateFirstYearTimeTable(firstYearGroupList1), HttpStatus.OK);

    }

}
