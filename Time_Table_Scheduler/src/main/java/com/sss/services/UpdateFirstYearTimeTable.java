package com.sss.services;


import com.sss.classModel.*;
import com.sss.dao.UpdateDao;

import java.util.ArrayList;

public class UpdateFirstYearTimeTable {
    public  UpdateFirstYearResponse updateFirstYearTimeTable(FirstYearGroupList firstYearGroupList){
        UpdateDao updateDao = new UpdateDao();
        UpdateFirstYearResponse updateFirstYearResponse = new UpdateFirstYearResponse();
        updateFirstYearResponse.affected = Integer.toString(updateDao.UpdateFirstYearInfo(firstYearGroupList.firstYearGroupList));

        return updateFirstYearResponse;
    }
}
