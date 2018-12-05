package com.sss.controller;


import com.sss.dao.LoginDao;
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
        /*if(true){
            return "hey";
        }*/
        else{
            return "null";
        }

    }
}

