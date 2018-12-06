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
            sql = "insert into facultydetails values(\"" + facultyResponse.facultyid + "\",\"" + facultyResponse.designation + "\",\"" + facultyResponse.name + "\",\"" + facultyResponse.noOfHours + "\",\"" + facultyResponse.subject1 + "\",\"" + facultyResponse.subject2 + "\",\"" + facultyResponse.subject3 + "\")";
            System.out.println(sql);
            affected = stmt.executeUpdate(sql);
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
                String sql1 = "insert into firstyeartimetable values(\""+groupid1+"\",firstYearGroupList.get(i).timeTable.timetable[0][2],firstYearGroupList.get(i).timeTable.timetable[0][3],firstYearGroupList.get(i).timeTable.timetable[0][5],firstYearGroupList.get(i).timeTable.timetable[0][4],firstYearGroupList.get(i).timeTable.timetable[0][6],firstYearGroupList.get(i).timeTable.timetable[0][7],firstYearGroupList.get(i).timeTable.timetable[0][8],firstYearGroupList.get(i).timeTable.timetable[0][9],firstYearGroupList.get(i).timeTable.timetable[0][0],firstYearGroupList.get(i).timeTable.timetable[0][1])";
                String sql2 = "insert into firstyeartimetable values(\""+groupid2+"\",firstYearGroupList.get(i).timeTable.timetable[1][2],firstYearGroupList.get(i).timeTable.timetable[1][3],firstYearGroupList.get(i).timeTable.timetable[1][5],firstYearGroupList.get(i).timeTable.timetable[1][4],firstYearGroupList.get(i).timeTable.timetable[1][6],firstYearGroupList.get(i).timeTable.timetable[1][7],firstYearGroupList.get(i).timeTable.timetable[1][8],firstYearGroupList.get(i).timeTable.timetable[1][9],firstYearGroupList.get(i).timeTable.timetable[1][0],firstYearGroupList.get(i).timeTable.timetable[1][1])";
                String sql3 = "insert into firstyeartimetable values(\""+groupid3+"\",firstYearGroupList.get(i).timeTable.timetable[2][2],firstYearGroupList.get(i).timeTable.timetable[2][3],firstYearGroupList.get(i).timeTable.timetable[2][5],firstYearGroupList.get(i).timeTable.timetable[2][4],firstYearGroupList.get(i).timeTable.timetable[2][6],firstYearGroupList.get(i).timeTable.timetable[2][7],firstYearGroupList.get(i).timeTable.timetable[2][8],firstYearGroupList.get(i).timeTable.timetable[2][9],firstYearGroupList.get(i).timeTable.timetable[2][0],firstYearGroupList.get(i).timeTable.timetable[2][1])";
                String sql4 = "insert into firstyeartimetable values(\""+groupid4+"\",firstYearGroupList.get(i).timeTable.timetable[3][2],firstYearGroupList.get(i).timeTable.timetable[3][3],firstYearGroupList.get(i).timeTable.timetable[3][5],firstYearGroupList.get(i).timeTable.timetable[3][4],firstYearGroupList.get(i).timeTable.timetable[3][6],firstYearGroupList.get(i).timeTable.timetable[3][7],firstYearGroupList.get(i).timeTable.timetable[3][8],firstYearGroupList.get(i).timeTable.timetable[3][9],firstYearGroupList.get(i).timeTable.timetable[3][0],firstYearGroupList.get(i).timeTable.timetable[3][1])";
                String sql5 = "insert into firstyeartimetable values(\""+groupid5+"\",firstYearGroupList.get(i).timeTable.timetable[4][2],firstYearGroupList.get(i).timeTable.timetable[4][3],firstYearGroupList.get(i).timeTable.timetable[4][5],firstYearGroupList.get(i).timeTable.timetable[4][4],firstYearGroupList.get(i).timeTable.timetable[4][6],firstYearGroupList.get(i).timeTable.timetable[4][7],firstYearGroupList.get(i).timeTable.timetable[4][8],firstYearGroupList.get(i).timeTable.timetable[4][9],firstYearGroupList.get(i).timeTable.timetable[4][0],firstYearGroupList.get(i).timeTable.timetable[4][1])";

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