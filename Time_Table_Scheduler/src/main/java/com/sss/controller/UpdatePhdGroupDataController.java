package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.*;
import com.sss.services.GetFirstYearTimeTable;
import com.sss.services.GetPhdTimeTable;
import com.sss.services.UpdateFirstYearTimeTable;
import com.sss.services.UpdatePhdTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class UpdatePhdGroupDataController {

    @PostMapping("/updatePhdBatch")
    public ResponseEntity<UpdatePhdResponse> updateSingleGroupData(@RequestBody PhdGroup phdGroup) throws IOException {

        //OnlyStringClass onlyStringClass = new ObjectMapper().readValue(phdGroup.groupId,OnlyStringClass.class);


        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        FullPhdGroup fullPhdGroup = getPhdTimeTable.getPhdTimeTableData();
        FullPhdGroup fullPhdGroup1 = new FullPhdGroup();

        for(int i=0;i<fullPhdGroup.fullPhdGroupList.size();++i){
            PhdGroup phdGroup1 = new PhdGroup();
            phdGroup1 = fullPhdGroup.fullPhdGroupList.get(i);
            if(phdGroup1.groupId.equals(phdGroup.groupId)){
                phdGroup1.timeTable = phdGroup.timeTable;
            }
            fullPhdGroup1.fullPhdGroupList.add(phdGroup1);
        }


        UpdatePhdTimeTable updatePhdTimeTable = new UpdatePhdTimeTable();

        System.out.println("sagar "+ phdGroup.groupId);
        return new ResponseEntity<>(updatePhdTimeTable.updatePhdTimeTableData(fullPhdGroup1), HttpStatus.OK);

    }
}
