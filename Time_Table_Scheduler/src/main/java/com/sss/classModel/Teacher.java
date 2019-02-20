package com.sss.classModel;


import com.sss.services.TimeTableGenerator;

public class Teacher {

    public FacultyResponse facultyResponse;
    public boolean[] days;
    public boolean[] slots;
    public int hoursToAssign;
    public int current1Batches; // first year batches already given
    public int total1Batches;   // total first year batches to be given
    public int labHours;

    public Teacher(FacultyResponse facultyResponse) {

        this.facultyResponse = facultyResponse;
        this.hoursToAssign = facultyResponse.noOfHours;
        days = new boolean[5];
        slots = new boolean[10];
        this.current1Batches = 0;
        this.total1Batches = 0;
        if (this.facultyResponse.subject1.equals("MA102")) {
            this.total1Batches++;
        }
        if (this.facultyResponse.subject2.equals("MA102")) {
            this.total1Batches++;
        }
        if (this.facultyResponse.subject3.equals("MA102")) {
            this.total1Batches++;
        }

        //
        this.labHours = this.hoursToAssign;
        if (!this.facultyResponse.subject1.equals("null")) {
            this.labHours -= TimeTableGenerator.courseDataHM.get(this.facultyResponse.subject1).tutHours;
        }
        if (!this.facultyResponse.subject2.equals("null")) {
            this.labHours -= TimeTableGenerator.courseDataHM.get(this.facultyResponse.subject2).tutHours;
        }
        if (!this.facultyResponse.subject3.equals("null")) {
            this.labHours -= TimeTableGenerator.courseDataHM.get(this.facultyResponse.subject3).tutHours;
        }
    }


    public void assign(int d, int t, String val) {
        if (!this.facultyResponse.timeTable.timetable[d][t].equals( "null" )) {
            System.out.println("Error for teacher " + this.facultyResponse.facultyid
                    + " for slot " + d + " " + t);
        }
        this.facultyResponse.timeTable.timetable[d][t] = val;
        this.hoursToAssign--;
        days[d] = true;
        slots[t] = true;
    }

    public boolean isFree(int d, int t) {
        return this.facultyResponse.timeTable.timetable[d][t].equals("null");
    }

    public void makeFree(int d,int t){
        this.facultyResponse.timeTable.timetable[d][t] = "null";
    }

    public void printTeacherTT() {
        System.out.println("Time Table for teacher " + this.facultyResponse.timeTable);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (this.facultyResponse.timeTable.timetable[i][j].equals("null")) {
                    System.out.print("----" + "\t");
                } else {
                    System.out.print(this.facultyResponse.timeTable.timetable[i][j] + "\t");
                }
            }
            System.out.println();

        }
    }

}
