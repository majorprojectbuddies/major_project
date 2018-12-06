package com.sss.dao;

import com.sss.classModel.FacultyResponse;
import com.sss.classModel.FirstYearGroup;

import java.sql.*;
import java.util.ArrayList;

public class UpdateDao {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ttsdb";

    static final String USER = "root";
    static final String PASS = "YourRootPassword";

    public int UpdateFacultyInfo(FacultyResponse facultyResponse) {
        Connection conn = null;
        Statement stmt = null;
        int affected = 0;
        try {

            Class.forName("com.mysql.jdbc.Driver");


            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "update facultydetails set name=\""+ facultyResponse.name +"\", designation=\""+facultyResponse.designation+"\",noOfHours=" + facultyResponse.noOfHours + ", subject1=\""+facultyResponse.subject1+"\",subject2=\""+facultyResponse.subject2+"\",subject3=\""+facultyResponse.subject3+"\" where facultyid=\""+facultyResponse.facultyid+"\"";
            System.out.println(sql);
            affected = stmt.executeUpdate(sql);










            String facultyid = facultyResponse.facultyid;
            String facultyid1 = facultyid+"-mon";
            String facultyid2 = facultyid+"-tue";
            String facultyid3 = facultyid+"-wed";
            String facultyid4 = facultyid+"-thu";
            String facultyid5 = facultyid+"-fri";


            String sql1 = "update facultytimetable set p89=\""+facultyResponse.timeTable.timetable[0][0]+"\",p910=\""+facultyResponse.timeTable.timetable[0][1]+"\",p1011=\""+facultyResponse.timeTable.timetable[0][2]+"\",p1112=\""+facultyResponse.timeTable.timetable[0][3]+"\",p121=\""+facultyResponse.timeTable.timetable[0][4]+"\",p12=\""+facultyResponse.timeTable.timetable[0][5]+"\",p23=\""+facultyResponse.timeTable.timetable[0][6]+"\",p34=\""+facultyResponse.timeTable.timetable[0][7]+"\",p45=\""+facultyResponse.timeTable.timetable[0][8]+"\",p56=\""+facultyResponse.timeTable.timetable[0][9]+"\" where facultyttid=\""+facultyid1+"\"";
            String sql2 = "update facultytimetable set p89=\""+facultyResponse.timeTable.timetable[1][0]+"\",p910=\""+facultyResponse.timeTable.timetable[1][1]+"\",p1011=\""+facultyResponse.timeTable.timetable[1][2]+"\",p1112=\""+facultyResponse.timeTable.timetable[1][3]+"\",p121=\""+facultyResponse.timeTable.timetable[1][4]+"\",p12=\""+facultyResponse.timeTable.timetable[1][5]+"\",p23=\""+facultyResponse.timeTable.timetable[1][6]+"\",p34=\""+facultyResponse.timeTable.timetable[1][7]+"\",p45=\""+facultyResponse.timeTable.timetable[1][8]+"\",p56=\""+facultyResponse.timeTable.timetable[1][9]+"\" where facultyttid=\""+facultyid2+"\"";
            String sql3 = "update facultytimetable set p89=\""+facultyResponse.timeTable.timetable[2][0]+"\",p910=\""+facultyResponse.timeTable.timetable[2][1]+"\",p1011=\""+facultyResponse.timeTable.timetable[2][2]+"\",p1112=\""+facultyResponse.timeTable.timetable[2][3]+"\",p121=\""+facultyResponse.timeTable.timetable[2][4]+"\",p12=\""+facultyResponse.timeTable.timetable[2][5]+"\",p23=\""+facultyResponse.timeTable.timetable[2][6]+"\",p34=\""+facultyResponse.timeTable.timetable[2][7]+"\",p45=\""+facultyResponse.timeTable.timetable[2][8]+"\",p56=\""+facultyResponse.timeTable.timetable[2][9]+"\" where facultyttid=\""+facultyid3+"\"";
            String sql4 = "update facultytimetable set p89=\""+facultyResponse.timeTable.timetable[3][0]+"\",p910=\""+facultyResponse.timeTable.timetable[3][1]+"\",p1011=\""+facultyResponse.timeTable.timetable[3][2]+"\",p1112=\""+facultyResponse.timeTable.timetable[3][3]+"\",p121=\""+facultyResponse.timeTable.timetable[3][4]+"\",p12=\""+facultyResponse.timeTable.timetable[3][5]+"\",p23=\""+facultyResponse.timeTable.timetable[3][6]+"\",p34=\""+facultyResponse.timeTable.timetable[3][7]+"\",p45=\""+facultyResponse.timeTable.timetable[3][8]+"\",p56=\""+facultyResponse.timeTable.timetable[3][9]+"\" where facultyttid=\""+facultyid4+"\"";
            String sql5 = "update facultytimetable set p89=\""+facultyResponse.timeTable.timetable[4][0]+"\",p910=\""+facultyResponse.timeTable.timetable[4][1]+"\",p1011=\""+facultyResponse.timeTable.timetable[4][2]+"\",p1112=\""+facultyResponse.timeTable.timetable[4][3]+"\",p121=\""+facultyResponse.timeTable.timetable[4][4]+"\",p12=\""+facultyResponse.timeTable.timetable[4][5]+"\",p23=\""+facultyResponse.timeTable.timetable[4][6]+"\",p34=\""+facultyResponse.timeTable.timetable[4][7]+"\",p45=\""+facultyResponse.timeTable.timetable[4][8]+"\",p56=\""+facultyResponse.timeTable.timetable[4][9]+"\" where facultyttid=\""+facultyid5+"\"";

            System.out.println(sql1);

            affected+=stmt.executeUpdate(sql1);
            affected+=stmt.executeUpdate(sql2);
            affected+=stmt.executeUpdate(sql3);
            affected+=stmt.executeUpdate(sql4);
            affected+=stmt.executeUpdate(sql5);



            return affected;

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return affected;
    }






    public int UpdateFirstYearInfo(ArrayList<FirstYearGroup> firstYearGroupList) {
        Connection conn = null;
        Statement stmt = null;
        int affected = 0;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            for(int i = 0;i<firstYearGroupList.size();++i){

                String groupid = firstYearGroupList.get(i).groupId;
                String groupid1 = groupid+"-mon";
                String groupid2 = groupid+"-tue";
                String groupid3 = groupid+"-wed";
                String groupid4 = groupid+"-thu";
                String groupid5 = groupid+"-fri";


                String sql1 = "update firstyeartimetable set p89=\""+firstYearGroupList.get(i).timeTable.timetable[0][0]+"\",p910=\""+firstYearGroupList.get(i).timeTable.timetable[0][1]+"\",p1011=\""+firstYearGroupList.get(i).timeTable.timetable[0][2]+"\",p1112=\""+firstYearGroupList.get(i).timeTable.timetable[0][3]+"\",p121=\""+firstYearGroupList.get(i).timeTable.timetable[0][4]+"\",p12=\""+firstYearGroupList.get(i).timeTable.timetable[0][5]+"\",p23=\""+firstYearGroupList.get(i).timeTable.timetable[0][6]+"\",p34=\""+firstYearGroupList.get(i).timeTable.timetable[0][7]+"\",p45=\""+firstYearGroupList.get(i).timeTable.timetable[0][8]+"\",p56=\""+firstYearGroupList.get(i).timeTable.timetable[0][9]+"\" where groupid=\""+groupid1+"\"";
                String sql2 = "update firstyeartimetable set p89=\""+firstYearGroupList.get(i).timeTable.timetable[1][0]+"\",p910=\""+firstYearGroupList.get(i).timeTable.timetable[1][1]+"\",p1011=\""+firstYearGroupList.get(i).timeTable.timetable[1][2]+"\",p1112=\""+firstYearGroupList.get(i).timeTable.timetable[1][3]+"\",p121=\""+firstYearGroupList.get(i).timeTable.timetable[1][4]+"\",p12=\""+firstYearGroupList.get(i).timeTable.timetable[1][5]+"\",p23=\""+firstYearGroupList.get(i).timeTable.timetable[1][6]+"\",p34=\""+firstYearGroupList.get(i).timeTable.timetable[1][7]+"\",p45=\""+firstYearGroupList.get(i).timeTable.timetable[1][8]+"\",p56=\""+firstYearGroupList.get(i).timeTable.timetable[1][9]+"\" where groupid=\""+groupid2+"\"";
                String sql3 = "update firstyeartimetable set p89=\""+firstYearGroupList.get(i).timeTable.timetable[2][0]+"\",p910=\""+firstYearGroupList.get(i).timeTable.timetable[2][1]+"\",p1011=\""+firstYearGroupList.get(i).timeTable.timetable[2][2]+"\",p1112=\""+firstYearGroupList.get(i).timeTable.timetable[2][3]+"\",p121=\""+firstYearGroupList.get(i).timeTable.timetable[2][4]+"\",p12=\""+firstYearGroupList.get(i).timeTable.timetable[2][5]+"\",p23=\""+firstYearGroupList.get(i).timeTable.timetable[2][6]+"\",p34=\""+firstYearGroupList.get(i).timeTable.timetable[2][7]+"\",p45=\""+firstYearGroupList.get(i).timeTable.timetable[2][8]+"\",p56=\""+firstYearGroupList.get(i).timeTable.timetable[2][9]+"\" where groupid=\""+groupid3+"\"";
                String sql4 = "update firstyeartimetable set p89=\""+firstYearGroupList.get(i).timeTable.timetable[3][0]+"\",p910=\""+firstYearGroupList.get(i).timeTable.timetable[3][1]+"\",p1011=\""+firstYearGroupList.get(i).timeTable.timetable[3][2]+"\",p1112=\""+firstYearGroupList.get(i).timeTable.timetable[3][3]+"\",p121=\""+firstYearGroupList.get(i).timeTable.timetable[3][4]+"\",p12=\""+firstYearGroupList.get(i).timeTable.timetable[3][5]+"\",p23=\""+firstYearGroupList.get(i).timeTable.timetable[3][6]+"\",p34=\""+firstYearGroupList.get(i).timeTable.timetable[3][7]+"\",p45=\""+firstYearGroupList.get(i).timeTable.timetable[3][8]+"\",p56=\""+firstYearGroupList.get(i).timeTable.timetable[3][9]+"\" where groupid=\""+groupid4+"\"";
                String sql5 = "update firstyeartimetable set p89=\""+firstYearGroupList.get(i).timeTable.timetable[4][0]+"\",p910=\""+firstYearGroupList.get(i).timeTable.timetable[4][1]+"\",p1011=\""+firstYearGroupList.get(i).timeTable.timetable[4][2]+"\",p1112=\""+firstYearGroupList.get(i).timeTable.timetable[4][3]+"\",p121=\""+firstYearGroupList.get(i).timeTable.timetable[4][4]+"\",p12=\""+firstYearGroupList.get(i).timeTable.timetable[4][5]+"\",p23=\""+firstYearGroupList.get(i).timeTable.timetable[4][6]+"\",p34=\""+firstYearGroupList.get(i).timeTable.timetable[4][7]+"\",p45=\""+firstYearGroupList.get(i).timeTable.timetable[4][8]+"\",p56=\""+firstYearGroupList.get(i).timeTable.timetable[4][9]+"\" where groupid=\""+groupid5+"\"";


                affected+=stmt.executeUpdate(sql1);
                affected+=stmt.executeUpdate(sql2);
                affected+=stmt.executeUpdate(sql3);
                affected+=stmt.executeUpdate(sql4);
                affected+=stmt.executeUpdate(sql5);

            }


            return affected;

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return affected;
    }



}