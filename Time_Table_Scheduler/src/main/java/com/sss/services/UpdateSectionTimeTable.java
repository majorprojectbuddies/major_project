package com.sss.services;


import com.sss.classModel.FullSectionGroup;
import com.sss.classModel.UpdateSectionResponse;
import com.sss.dao.UpdateDao;
import org.springframework.stereotype.Service;

@Service
public class UpdateSectionTimeTable {

    public UpdateSectionResponse updateSectionTimeTableData(FullSectionGroup fullSectionGroup){
        UpdateDao updateDao = new UpdateDao();
        UpdateSectionResponse updateSectionResponse = new UpdateSectionResponse();
        updateSectionResponse.affected = Integer.toString(updateDao.UpdateSectionInfo(fullSectionGroup.fullSectionGroupList));

        return updateSectionResponse;
    }

}
