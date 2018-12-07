package com.sss.classModel;


public class Room {
    public int id;

    public TimeTable timeTable;

    public Room(int id) {
        this.id = id;
        this.timeTable = new TimeTable();
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
