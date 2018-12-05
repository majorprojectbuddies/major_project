package com.sss.classModel;

public class TimeTable {
    public TimeTable() {
        this.timetable = new String[5][10];
        for(int i=0;i<5;i++){
            for(int j=0;j<10;j++){
                this.timetable[i][j] = "null";
            }
        }
    }

    public String [][] timetable;

}
