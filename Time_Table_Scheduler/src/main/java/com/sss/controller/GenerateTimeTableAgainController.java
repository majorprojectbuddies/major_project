package com.sss.controller;

import com.sss.classModel.FacultyResponse;
import com.sss.classModel.OverallTT;
import com.sss.model.Faculty;
import com.sss.services.GenerateAllTimeTablesAgain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@CrossOrigin
public class GenerateTimeTableAgainController {
    @PostMapping("/generateTimeTableAgain")
    public ResponseEntity<OverallTT> GenerateTimeTableDataAgain(@RequestBody OverallTT overallTT){
        Iterator itr = overallTT.facultyResponses.iterator();
        while (itr.hasNext()){
            System.out.println("inside while loop");
            FacultyResponse facultyResponseobj = (FacultyResponse) itr.next() ;
            System.out.println(facultyResponseobj.isFreezed);
        }


        GenerateAllTimeTablesAgain generateAllTimeTablesAgain = new GenerateAllTimeTablesAgain();
        generateAllTimeTablesAgain.overallTT = overallTT;
        System.out.println("assigned overallTT To service");
        return new ResponseEntity<>(generateAllTimeTablesAgain.getAllTimeTableAgainData(), HttpStatus.OK);
    }
}
