package com.sss.controller;

import com.sss.classModel.*;
import com.sss.services.GetPhdTimeTable;
import com.sss.services.GetSectionTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class FetchSectionDataController {


    @PostMapping("/sectionBatch")
    public ResponseEntity<Section> getSingleGroupData(@RequestBody OnlyStringClass2 onlyStringClass2) throws IOException {

        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        FullSectionGroup fullSectionGroup = getSectionTimeTable.getSectionTimeTableData();

        Section section = new Section();

        System.out.println("printing "+onlyStringClass2.groupid);

        for(int i=0;i<fullSectionGroup.fullSectionGroupList.size();++i){
            section = fullSectionGroup.fullSectionGroupList.get(i);
            if(section.secId.equals(onlyStringClass2.groupid)){
                return new ResponseEntity<>(section, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(section, HttpStatus.OK);

    }

}
