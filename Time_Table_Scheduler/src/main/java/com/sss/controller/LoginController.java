package com.sss.controller;


import com.sss.classModel.FacultyResponse;
import com.sss.classModel.OverallResponse;
import com.sss.dao.LoginDao;
import com.sss.dao.SignupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @PostMapping("/Login")

    public ResponseEntity<OverallResponse> LoginMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){
        SignupDao signupDao = new SignupDao();
        OverallResponse overallResponse = new OverallResponse();
        LoginDao loginDao = new LoginDao();
        if(loginDao.ValidateUser(loginid,password)){
            overallResponse.facultyResponse = signupDao.FetchFacultyData(loginid);
            overallResponse.courseResponse = signupDao.FetchCourseData();
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }

        else{
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }

    }
    @PostMapping("/Signup")

    public ResponseEntity<OverallResponse> SignupMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){
        SignupDao signupDao = new SignupDao();
        //if user not exist
        OverallResponse overallResponse = new OverallResponse();
        if(!signupDao.CheckOrSignUp(loginid,password)){

            overallResponse.facultyResponse = signupDao.FetchFacultyData(loginid);
            overallResponse.courseResponse = signupDao.FetchCourseData();
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }
    }
    
}

