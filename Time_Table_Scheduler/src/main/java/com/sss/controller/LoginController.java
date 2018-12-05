package com.sss.controller;


import com.sss.dao.LoginDao;
import com.sss.dao.SignupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @PostMapping("/Login")
    @ResponseBody
    public String LoginMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){

        LoginDao loginDao = new LoginDao();
        if(loginDao.ValidateUser(loginid,password)){
            return loginid;
        }

        else{
            return "null";
        }

    }
    @PostMapping("/Signup")
    @ResponseBody
    public String SignupMethod(@RequestParam("loginid") String loginid, @RequestParam("password") String password){
        SignupDao signupDao = new SignupDao();
        //if user not exist
        if(!signupDao.CheckOrSignUp(loginid,password)){
            return loginid;
        }
        else{
            return "null";
        }
    }
    
}

