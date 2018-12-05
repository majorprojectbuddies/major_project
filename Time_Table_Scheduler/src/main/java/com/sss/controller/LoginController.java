package com.sss.controller;


import com.sss.classModel.FacultyResponse;
import com.sss.dao.LoginDao;
import com.sss.dao.SignupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @PostMapping("/Login")

    public ResponseEntity<FacultyResponse> LoginMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){
        SignupDao signupDao = new SignupDao();
        FacultyResponse facultyResponse = new FacultyResponse();
        LoginDao loginDao = new LoginDao();
        if(loginDao.ValidateUser(loginid,password)){
            facultyResponse = signupDao.FetchFacultyData(loginid);
            return new ResponseEntity<>(facultyResponse, HttpStatus.OK);
        }

        else{
            return new ResponseEntity<>(facultyResponse, HttpStatus.OK);
        }

    }
    @PostMapping("/Signup")

    public ResponseEntity<FacultyResponse> SignupMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){
        SignupDao signupDao = new SignupDao();
        //if user not exist
        FacultyResponse facultyResponse = new FacultyResponse();
        if(!signupDao.CheckOrSignUp(loginid,password)){

            facultyResponse = signupDao.FetchFacultyData(loginid);
            return new ResponseEntity<>(facultyResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(facultyResponse, HttpStatus.OK);
        }
    }
    
}

