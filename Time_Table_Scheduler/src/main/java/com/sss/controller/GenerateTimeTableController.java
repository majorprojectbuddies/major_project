package com.sss.controller;

import com.sss.classModel.*;
import com.sss.services.GenerateAllTimeTables;
import com.sss.services.GetAllTeacherTimeTable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class GenerateTimeTableController {

    @GetMapping("/generateTimeTable")
    public ResponseEntity<OverallTT> getGeneratedTimeTableData() {



        GenerateAllTimeTables generateAllTimeTables = new GenerateAllTimeTables();
        System.out.println("reachn the controller gleba");
        return new ResponseEntity<>(generateAllTimeTables.getAllTimeTableData(), HttpStatus.OK);
    }

}
