package com.sss.controller;


import com.sss.classModel.*;
import com.sss.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class UpdateSectionDataController {

    @PostMapping("/updateSectionBatch")
    public ResponseEntity<UpdateSectionResponse> updateSingleSectionData(@RequestBody Section section) throws IOException {

        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        FullSectionGroup fullSectionGroup = getSectionTimeTable.getSectionTimeTableData();
        FullSectionGroup fullSectionGroup1 = new FullSectionGroup();

        for(int i=0;i<fullSectionGroup.fullSectionGroupList.size();++i){
            Section section1 = new Section();
            section1 = fullSectionGroup.fullSectionGroupList.get(i);
            if(section1.secId.equals(section.secId)){
                section1.timeTable = section.timeTable;
            }
            fullSectionGroup1.fullSectionGroupList.add(section1);
        }

        UpdateSectionTimeTable updateSectionTimeTable = new UpdateSectionTimeTable();

        return new ResponseEntity<>(updateSectionTimeTable.updateSectionTimeTableData(fullSectionGroup1), HttpStatus.OK);

    }

}
