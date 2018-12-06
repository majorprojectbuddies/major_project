package com.sss.dao;


import com.sss.classModel.Course;
import com.sss.classModel.CourseResponse;
import com.sss.classModel.FacultyResponse;

import java.sql.*;
public class SignupDao {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ttsdb";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "YourRootPassword";

    public boolean CheckOrSignUp(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * from logindetails where loginid=\""+username+"\"";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);

            if(rs.next()){
                return true;
            }
            else{
                sql = "insert into logindetails values(\""+username+"\",\""+password+"\")";
                stmt.executeUpdate(sql);

                //initialize faculty data
                sql = "insert into facultydetails values(\""+username+"\",\"null\", \"null\",0,\"null\",\"null\",\"null\")";
                System.out.println(sql);
                stmt.executeUpdate(sql);

                String sql1,sql2,sql3,sql4,sql5;
                String username1,username2,username3,username4,username5;
                username1 = username + "-mon";
                username2 = username + "-tue";
                username3 = username + "-wed";
                username4 = username + "-thur";
                username5 = username + "-fri";
;               sql1 = "insert into facultytimetable values(\""+username1+"\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\")";
                stmt.executeUpdate(sql1);
                sql2 = "insert into facultytimetable values(\""+username2+"\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\")";
                stmt.executeUpdate(sql2);
                sql3 = "insert into facultytimetable values(\""+username3+"\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\")";
                stmt.executeUpdate(sql3);
                sql4 = "insert into facultytimetable values(\""+username4+"\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\")";
                stmt.executeUpdate(sql4);
                sql5 = "insert into facultytimetable values(\""+username5+"\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\",\"null\")";
                stmt.executeUpdate(sql5);

                return false;
            }


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

        return false;
    }


    //fetch faculty data function
    public FacultyResponse FetchFacultyData(String username) {
        Connection conn = null;
        Statement stmt = null;
        FacultyResponse facultyResponse = new FacultyResponse();
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * from facultydetails where facultyid=\""+username+"\"";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);

            if(rs.next()){

                facultyResponse.facultyid = rs.getString(1);
                facultyResponse.designation = rs.getString(2);
                facultyResponse.name = rs.getString(3);
                facultyResponse.noOfHours = rs.getInt(4);
                facultyResponse.subject1 = rs.getString(5);
                facultyResponse.subject2 = rs.getString(6);
                facultyResponse.subject3 = rs.getString(7);
                return facultyResponse;


            }
            else{
                return facultyResponse;
            }


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

        return facultyResponse;
    }

    //fetch course data from db function
    public CourseResponse FetchCourseData() {
        Connection conn = null;
        Statement stmt = null;
        CourseResponse courseResponse = new CourseResponse();
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * from courselist";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs);

            while(rs.next()){
                Course course = new Course();
                course.courseId =rs.getString(1);
                course.courseName =rs.getString(2);
                course.credits =rs.getInt(3);
                course.tutHours =rs.getInt(4);
                course.containsLab =rs.getBoolean(5);
                courseResponse.courseList.add(course);
            }
            return courseResponse;


        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

        return courseResponse;
    }
}
