package com.sss.dao;


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
                rs = stmt.executeQuery(sql);
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
}
