package com.sss.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sss.classModel.*;
import com.sss.services.GetPhdTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class FetchPhdGroupDataController {

    @PostMapping("/phdBatch")
    public ResponseEntity<PhdGroup> getSingleGroupData(@RequestBody String groupid) throws IOException {

        OnlyStringClass onlyStringClass = new ObjectMapper().readValue(groupid,OnlyStringClass.class);


        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        FullPhdGroup fullPhdGroup = getPhdTimeTable.getPhdTimeTableData();

        PhdGroup phdGroup = new PhdGroup();

        for(int i=0;i<fullPhdGroup.fullPhdGroupList.size();++i){
            phdGroup = fullPhdGroup.fullPhdGroupList.get(i);
            if(phdGroup.groupId.equals(onlyStringClass.loginid)){
                return new ResponseEntity<>(phdGroup, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(phdGroup, HttpStatus.OK);

    }
}
