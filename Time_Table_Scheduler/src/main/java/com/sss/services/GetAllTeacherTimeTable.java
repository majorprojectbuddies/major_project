package com.sss.services;


import com.sss.classModel.FullFacultyResponse;
import com.sss.dao.GetDao;
import org.springframework.stereotype.Service;

@Service
public class GetAllTeacherTimeTable {

    public FullFacultyResponse getAllFacultyData(){
        FullFacultyResponse fullFacultyResponse = new FullFacultyResponse();
        GetDao getDao = new GetDao();
        return getDao.getFullFacultyResponse();
    }



}
