package com.sss.services;

import com.sss.classModel.FirstYearGroupList;
import com.sss.dao.GetDao;
import org.springframework.stereotype.Service;

@Service
public class GetFirstYearTimeTable {
    public FirstYearGroupList getFirstYearTimeTableData(){
        GetDao getDao = new GetDao();
        return getDao.getFirstYearTimeTableInfo();
    }
}
