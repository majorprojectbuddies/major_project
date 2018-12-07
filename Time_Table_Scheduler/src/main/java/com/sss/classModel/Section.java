package com.sss.classModel;


import java.util.HashMap;

public class Section {
    public String year;
    public String secId;
    public TimeTable timeTable;
    public HashMap<String, Integer> subjects;

    public Section(String year, String secId) {
        this.year = year;
        this.secId = secId;
        this.timeTable = new TimeTable();
        subjects = new HashMap<>();
    }
    public Section(){
        this.year="";
        this.secId="";
        this.timeTable = new TimeTable();
        subjects = new HashMap<>();
    }

    public boolean isFree(int d, int h) {
        return (timeTable.timetable[d][h] == null || timeTable.timetable[d][h].equals("null"));
    }

    public void assignTeacher(int d, int t, String t_id) {
        this.timeTable.timetable[d][t] = t_id;
    }

    public void assignSecondTeacher(int d, int t, String t_id) {
        this.timeTable.timetable[d][t] = this.timeTable.timetable[d][t] + "-" + t_id;
    }
}
