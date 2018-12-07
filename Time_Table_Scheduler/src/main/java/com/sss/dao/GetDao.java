package com.sss.dao;

import com.sss.classModel.*;

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

                for(int j=0;j<5;++j){
                    String sql;
                    ResultSet rs;
                    if(j==0){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-mon\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }

                    }
                    if(j==1){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-tue\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==2){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-wed\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==3){
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-thu\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==4) {
                        sql = "SELECT * from firstyeartimetable where groupid=\""+groupId+"-fri\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            firstYearGroupList.firstYearGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
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













    public FullFacultyResponse getFullFacultyResponse() {
        Connection conn = null;
        Statement stmt = null;
        FullFacultyResponse fullFacultyResponse = new FullFacultyResponse();
        SignupDao signupDao = new SignupDao();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * from facultydetails";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                FacultyResponse facultyResponse = new FacultyResponse();
                facultyResponse.facultyid = rs.getString(1);
                facultyResponse.designation = rs.getString(2);
                facultyResponse.name = rs.getString(3);
                facultyResponse.noOfHours = rs.getInt(4);
                facultyResponse.subject1 = rs.getString(5);
                facultyResponse.subject2 = rs.getString(6);
                facultyResponse.subject3 = rs.getString(7);
                facultyResponse.timeTable = signupDao.getTimeTableOfTeacher(facultyResponse.facultyid);
                fullFacultyResponse.fullFacultyResponseList.add(facultyResponse);
            }

            return fullFacultyResponse;

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
        return fullFacultyResponse;
    }













    public FullPhdGroup getPhdTimeTableInfo() {
        Connection conn = null;
        Statement stmt = null;
        FullPhdGroup fullPhdGroup = new FullPhdGroup();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            int numOfPhdGroups = 1;


            for(int i=1;i<=numOfPhdGroups;++i){
                PhdGroup phdGroup = new PhdGroup();
                phdGroup.groupId = "phd"+Integer.toString(i);
                fullPhdGroup.fullPhdGroupList.add(phdGroup);
            }


            for(int i=0;i<fullPhdGroup.fullPhdGroupList.size();++i){
                String groupId = fullPhdGroup.fullPhdGroupList.get(i).groupId;

                for(int j=0;i<5;++i){
                    String sql;
                    ResultSet rs;
                    if(j==0){
                        sql = "SELECT * from phdtimetable where groupid=\""+groupId+"-mon\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }

                    }
                    if(j==1){
                        sql = "SELECT * from phdtimetable where groupid=\""+groupId+"-tue\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==2){
                        sql = "SELECT * from phdtimetable where groupid=\""+groupId+"-wed\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==3){
                        sql = "SELECT * from phdtimetable where groupid=\""+groupId+"-thu\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==4) {
                        sql = "SELECT * from phdtimetable where groupid=\""+groupId+"-fri\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullPhdGroup.fullPhdGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }


                }
            }

            return fullPhdGroup;

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
        return fullPhdGroup;
    }








    public FullSectionGroup getSectionTimeTableInfo() {
        Connection conn = null;
        Statement stmt = null;
        FullSectionGroup fullSectionGroup = new FullSectionGroup();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            int numOfSectionsSecondYear = 2;
            int numOfSectionsThirdYear = 2;
            int numOfSectionsFourthYear = 2;



            //initialize year and section constructor

            for(int i=1;i<=numOfSectionsSecondYear;++i){
                Section section = new Section();
                section.year = "2";
                section.secId = "s"+Integer.toString(i);
                fullSectionGroup.fullSectionGroupList.add(section);
            }

            for(int i=1;i<=numOfSectionsThirdYear;++i){
                Section section = new Section();
                section.year = "3";
                section.secId = "t"+Integer.toString(i);
                fullSectionGroup.fullSectionGroupList.add(section);
            }

            for(int i=1;i<=numOfSectionsFourthYear;++i) {
                Section section = new Section();
                section.year = "4";
                section.secId = "f" + Integer.toString(i);
                fullSectionGroup.fullSectionGroupList.add(section);
            }




            for(int i=0;i<fullSectionGroup.fullSectionGroupList.size();++i){
                String groupId = fullSectionGroup.fullSectionGroupList.get(i).secId;

                for(int j=0;i<5;++i){
                    String sql;
                    ResultSet rs;
                    if(j==0){
                        sql = "SELECT * from sectionstimetable where sectionid=\""+groupId+"-mon\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }

                    }
                    if(j==1){
                        sql = "SELECT * from sectionstimetable where groupid=\""+groupId+"-tue\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==2){
                        sql = "SELECT * from sectionstimetable where groupid=\""+groupId+"-wed\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==3){
                        sql = "SELECT * from sectionstimetable where groupid=\""+groupId+"-thu\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }
                    if(j==4) {
                        sql = "SELECT * from sectionstimetable where groupid=\""+groupId+"-fri\"";
                        rs = stmt.executeQuery(sql);
                        if(rs.next()){
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][0]=rs.getString(10);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][1]=rs.getString(11);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][2]=rs.getString(2);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][3]=rs.getString(3);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][4]=rs.getString(5);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][5]=rs.getString(4);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][6]=rs.getString(6);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][7]=rs.getString(7);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][8]=rs.getString(8);
                            fullSectionGroup.fullSectionGroupList.get(i).timeTable.timetable[j][9]=rs.getString(9);
                        }
                    }


                }
            }

            return fullSectionGroup;

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
        return fullSectionGroup;
    }




}
