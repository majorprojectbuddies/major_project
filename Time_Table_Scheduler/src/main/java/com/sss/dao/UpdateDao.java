package com.sss.dao;

import com.sss.classModel.FacultyResponse;

import java.sql.*;

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
}