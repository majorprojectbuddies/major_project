package com.sss.services;

import com.sss.classModel.OverallResponse;
import com.sss.dao.SignupDao;
import org.springframework.stereotype.Service;

@Service
public class FetchFacultyCourseData {

    public OverallResponse fetchFacultyCourseInfo(String username){
        OverallResponse overallResponse = new OverallResponse();
        SignupDao signupDao = new SignupDao();
        overallResponse.courseResponse = signupDao.FetchCourseData();
        overallResponse.facultyResponse = signupDao.FetchFacultyData(username);
        return overallResponse;
    }
}
