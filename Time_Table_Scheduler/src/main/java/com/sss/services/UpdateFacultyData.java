package com.sss.services;

import com.sss.classModel.FacultyResponse;
import com.sss.dao.UpdateDao;
import org.springframework.stereotype.Service;

@Service
public class UpdateFacultyData {

    public int updateFacultyData(FacultyResponse facultyResponse){
        UpdateDao updateDao = new UpdateDao();
        int affected = updateDao.UpdateFacultyInfo(facultyResponse);
        return affected;
    }
}
