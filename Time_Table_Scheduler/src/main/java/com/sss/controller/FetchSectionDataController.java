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

        //OnlyStringClass onlyStringClass = new ObjectMapper().readValue(groupid,OnlyStringClass.class);


        GetPhdTimeTable getPhdTimeTable = new GetPhdTimeTable();
        FullPhdGroup fullPhdGroup = getPhdTimeTable.getPhdTimeTableData();

        PhdGroup phdGroup = new PhdGroup();

        GetSectionTimeTable getSectionTimeTable = new GetSectionTimeTable();
        FullSectionGroup fullSectionGroup = new FullSectionGroup();

        Section section = new Section();


        for(int i=0;i<fullSectionGroup.fullSectionGroupList.size();++i){
            section = fullSectionGroup.fullSectionGroupList.get(i);
            if(section.secId.equals(onlyStringClass2.groupid)){
                return new ResponseEntity<>(section, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(section, HttpStatus.OK);

    }

}
