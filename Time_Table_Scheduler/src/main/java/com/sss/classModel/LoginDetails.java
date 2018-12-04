package com.sss.classModel;

public class LoginDetails {
    public String userid;
    public String password;

    public LoginDetails(){
        userid = "";
        password="";
    }
    public void setUserId(String userid){
        this.userid = userid;
    }
    public void setPassword(String password){
        this.password=password;
    }
}
