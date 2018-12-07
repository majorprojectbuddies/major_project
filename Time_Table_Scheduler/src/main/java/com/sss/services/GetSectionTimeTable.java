package com.sss.services;


import com.sss.classModel.FullSectionGroup;
import com.sss.dao.GetDao;
import org.springframework.stereotype.Service;

@Service
public class GetSectionTimeTable {

    public FullSectionGroup getSectionTimeTableData(){
        GetDao getDao = new GetDao();
        return getDao.getSectionTimeTableInfo();
    }

}
