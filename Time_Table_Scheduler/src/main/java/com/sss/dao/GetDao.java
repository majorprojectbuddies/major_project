package com.sss.dao;

import com.sss.classModel.FacultyResponse;
import com.sss.classModel.FirstYearGroup;
import com.sss.classModel.FirstYearGroupList;

import java.sql.*;

public class GetDao {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/ttsdb";

    static final String USER = "root";
    static final String PASS = "YourRootPassword";



    public FirstYearGroupList getFirstYearTimeTableInfo() {
        Connection conn = null;
        Statement stmt = null;
        FirstYearGroupList firstYearGroupList = new FirstYearGroupList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            int numOfAGroups = 15;
            int numOfBGroups = 15;

            for(int i=1;i<=numOfAGroups;++i){
                FirstYearGroup firstYearGroup = new FirstYearGroup();
                firstYearGroup.groupId = "a"+Integer.toString(i);
                firstYearGroupList.firstYearGroupList.add(firstYearGroup);
            }

            for(int i=1;i<=numOfBGroups;++i){
                FirstYearGroup firstYearGroup = new FirstYearGroup();
                firstYearGroup.groupId = "b"+Integer.toString(i);
                firstYearGroupList.firstYearGroupList.add(firstYearGroup);
            }

            for(int i=0;i<firstYearGroupList.firstYearGroupList.size();++i){
                String groupId = firstYearGroupList.firstYearGroupList.get(i).groupId;

                for(int j=0;i<5;++i){
                    String sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-mon\"";
                    ResultSet rs = stmt.executeQuery(sql);
                    if(j==0){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-mon\"";
                        rs = stmt.executeQuery(sql);

                    }
                    if(j==1){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-tue\"";
                        rs = stmt.executeQuery(sql);
                    }
                    if(j==2){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-wed\"";
                        rs = stmt.executeQuery(sql);
                    }
                    if(j==3){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-thu\"";
                        rs = stmt.executeQuery(sql);
                    }
                    if(j==4) {
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-fri\"";
                        rs = stmt.executeQuery(sql);
                    }

                    if(rs.next()){
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(9);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(10);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(1);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(2);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(4);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(3);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(5);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(6);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(7);
                        firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(8);
                    }
                }
            }

            return firstYearGroupList;

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
        return firstYearGroupList;
    }



}
