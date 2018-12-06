package com.sss.services;

import com.sss.classModel.FirstYearGroupList;
import com.sss.classModel.FullPhdGroup;
import com.sss.classModel.UpdateFirstYearResponse;
import com.sss.classModel.UpdatePhdResponse;
import com.sss.dao.UpdateDao;
import org.springframework.stereotype.Service;

@Service
public class UpdatePhdTimeTable {
    public UpdatePhdResponse updatePhdTimeTableData(FullPhdGroup fullPhdGroup){
        UpdateDao updateDao = new UpdateDao();
        UpdatePhdResponse updatePhdResponse = new UpdatePhdResponse();
        updatePhdResponse.affected = Integer.toString(updateDao.UpdatePhdInfo(fullPhdGroup.fullPhdGroupList));

        return updatePhdResponse;
    }
}
