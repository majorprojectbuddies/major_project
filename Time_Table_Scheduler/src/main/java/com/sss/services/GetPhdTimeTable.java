package com.sss.services;


import com.sss.classModel.FirstYearGroupList;
import com.sss.classModel.FullPhdGroup;
import com.sss.dao.GetDao;
import org.springframework.stereotype.Service;

@Service
public class GetPhdTimeTable {

    public FullPhdGroup getPhdTimeTableData(){
        GetDao getDao = new GetDao();
        return getDao.getPhdTimeTableInfo();
    }

}
