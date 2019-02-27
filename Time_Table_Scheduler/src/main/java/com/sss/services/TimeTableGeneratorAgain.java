package com.sss.services;

import com.sss.classModel.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeTableGeneratorAgain {

    public static ArrayList<FacultyResponse> teachersData;
    public static HashMap<String, Course> courseDataHM;
    public static ArrayList<FirstYearGroup> firstYearData;
    public static ArrayList<PhdGroup> phdData;
    public static ArrayList<Section> sections;
    public ArrayList<FacultyResponse> unfreezedTeachersData;
    public ArrayList<FacultyResponse> freezedTeachersData;
    public ArrayList<FirstYearGroup> freezedFirstYearData;
    public ArrayList<FirstYearGroup> unfreezedFirstYearData;
    public Map<String, Pair<Integer, Integer>> labSlotToBeAssigned;
    public Room[] rooms;

    public TimeTableGeneratorAgain(ArrayList<FacultyResponse> teachersData,
                              ArrayList<Course> courseDataList,
                              ArrayList<FirstYearGroup> firstYearData,
                              ArrayList<PhdGroup> phdData,
                              ArrayList<Section> sections,ArrayList<FacultyResponse> unfreezedTeachersData,
                                   ArrayList<FacultyResponse> freezedTeachersData, ArrayList<FirstYearGroup> freezedFirstYearData,
                                   ArrayList<FirstYearGroup> unfreezedFirstYearData ,
                                   Map<String,Pair<Integer,Integer>> labSlotToBeAssigned, Room[] rooms) {
        this.teachersData = teachersData;
        this.courseDataHM = new HashMap<>();
        for (Course course : courseDataList) {
            this.courseDataHM.put(course.courseId, course);
        }
        this.firstYearData = firstYearData;
        this.phdData = phdData;
        this.sections = sections;
        this.unfreezedFirstYearData = unfreezedFirstYearData;
        this.freezedFirstYearData = freezedFirstYearData;
        this.unfreezedTeachersData = unfreezedTeachersData;
        this.freezedTeachersData = freezedTeachersData;
        this.labSlotToBeAssigned = labSlotToBeAssigned;
        this.rooms = rooms;
    }
    public OverallTT generateTimeTable() {

        System.out.println("inside generateTimeTable() function");

        Teacher[] freezedTeachers = new Teacher[freezedTeachersData.size()];
        for (int i = 0; i < freezedTeachersData.size(); i++) {
            freezedTeachers[i] = new Teacher(freezedTeachersData.get(i));
        }

        Teacher[] unfreezedTeachers = new Teacher[unfreezedTeachersData.size()];
        for (int i = 0; i < unfreezedTeachersData.size(); i++) {
            unfreezedTeachers[i] = new Teacher(unfreezedTeachersData.get(i));
        }

        Section[] sectionsArray = new Section[sections.size()];
        for (int i = 0; i < sections.size(); i++) {
            sectionsArray[i] = sections.get(i);
        }

        //Functions callings will be done here





        //returning the overallTT work done here

        OverallTT overallTT = new OverallTT();

        //adding the freezed teachers
        for (Teacher t : freezedTeachers) {
            overallTT.facultyResponses.add(t.facultyResponse);
        }
        //adding the unfreezed teachers
        for (Teacher t : unfreezedTeachers) {
            overallTT.facultyResponses.add(t.facultyResponse);
        }
        //adding the rooms
        for (Room room : rooms) {
            overallTT.rooms.add(room);
        }
        //adding the sections
        overallTT.sections = sections;

        //Assigning '-' to 'null' and 'X' to '0'
        for (int i = 0; i < overallTT.facultyResponses.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("null")) {
                        overallTT.facultyResponses.get(i).timeTable.timetable[j][k] = "-";
                    }

                    if (overallTT.facultyResponses.get(i).timeTable.timetable[j][k].equals("0")) {
                        overallTT.facultyResponses.get(i).timeTable.timetable[j][k] = "X";
                    }
                }
            }
        }

        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    if (overallTT.sections.get(i).timeTable.timetable[j][k].equals("null")) {
                        overallTT.sections.get(i).timeTable.timetable[j][k] = "-";
                    }
                }
            }
        }

        //printing the timetable in console
        for (int i = 0; i < overallTT.sections.size(); ++i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 10; ++k) {
                    System.out.print(overallTT.sections.get(i).timeTable.timetable[j][k] + "  ");
                }
                System.out.println("");
            }
        }

        return overallTT;
    }
}
