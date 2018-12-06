package com.sss.controller;


import com.sss.classModel.FacultyResponse;
import com.sss.classModel.LoginDetails;
import com.sss.classModel.OverallResponse;
import com.sss.dao.LoginDao;
import com.sss.dao.SignupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class LoginController {

    @PostMapping("/Login")
    public ResponseEntity<OverallResponse> LoginMethod(@RequestBody LoginDetails loginDetails){
        System.out.println("hit aayi");
        SignupDao signupDao = new SignupDao();
        OverallResponse overallResponse = new OverallResponse();
        LoginDao loginDao = new LoginDao();
        if(loginDao.ValidateUser(loginDetails.loginid,loginDetails.password)){
            if(loginDetails.loginid.equals("admin")){
                overallResponse.facultyResponse.facultyid="admin";
                return new ResponseEntity<>(overallResponse, HttpStatus.OK);
            }
            else{
                overallResponse.facultyResponse = signupDao.FetchFacultyData(loginDetails.loginid);
                overallResponse.courseResponse = signupDao.FetchCourseData();
                return new ResponseEntity<>(overallResponse, HttpStatus.OK);
            }

        }

        else{
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }

    }
    @PostMapping("/Signup")

    public ResponseEntity<OverallResponse> SignupMethod(@RequestBody LoginDetails loginDetails){
        SignupDao signupDao = new SignupDao();
        //if user not exist
        OverallResponse overallResponse = new OverallResponse();
        if(!signupDao.CheckOrSignUp(loginDetails.loginid,loginDetails.password)){

            overallResponse.facultyResponse = signupDao.FetchFacultyData(loginDetails.loginid);
            overallResponse.courseResponse = signupDao.FetchCourseData();
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(overallResponse, HttpStatus.OK);
        }
    }
    
}

