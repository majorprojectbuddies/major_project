package com.sss.model;

import javax.persistence.*;

@Entity(name = "logindetails")
public class LoginDetails {
    @Id
    @Column(name = "loginid",unique=true,columnDefinition="VARCHAR(64)")
    private String loginid;
    private String password;

    public String getUsername() {
        return loginid;
    }

    public void setUsername(String id) {
        this.loginid = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
