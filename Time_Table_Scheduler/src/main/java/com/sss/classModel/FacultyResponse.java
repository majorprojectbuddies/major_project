package com.sss.classModel;

public class FacultyResponse {
    public String facultyid;
    public String name;
    public String subject1;
    public String subject2;
    public String subject3;
    public Integer noOfHours;
    public String designation;
    public TimeTable timeTable;
    public Boolean isFreezed;

    public FacultyResponse(){
        this.facultyid = "null";
        this.name = "null";
        this.subject1 = "null";
        this.subject2 = "null";
        this.subject3 = "null";
        this.noOfHours = 0;
        this.designation = "null";
        this.timeTable = new TimeTable();
        this.isFreezed = false;
    }


}
