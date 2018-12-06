package com.sss.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity (name = "firstyeartimetable")
public class FirstYearTimeTable {
    @Id
    @Column(name = "groupid",unique=true,columnDefinition="VARCHAR(64)")
    private String groupid;
    private String p89;
    private String p910;
    private String p1011;
    private String p1112;
    private String p121;
    private String p12;
    private String p23;
    private String p34;
    private String p45;
    private String p56;
}
