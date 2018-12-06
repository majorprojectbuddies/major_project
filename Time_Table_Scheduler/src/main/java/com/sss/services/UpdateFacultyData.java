package com.sss.services;

import com.sss.classModel.FacultyResponse;
import com.sss.classModel.UpdateProfileResponse;
import com.sss.dao.UpdateDao;
import org.springframework.stereotype.Service;

@Service
public class UpdateFacultyData {

    public UpdateProfileResponse updateFacultyData(FacultyResponse facultyResponse){
        UpdateDao updateDao = new UpdateDao();
        int affected = updateDao.UpdateFacultyInfo(facultyResponse);
        UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();
        updateProfileResponse.affected =Integer.toString(affected);
        return updateProfileResponse;
    }
}
