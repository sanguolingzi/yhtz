package com.yinhetianze.systemservice.system.service.busi.impl;

import com.yinhetianze.systemservice.system.mapper.busi.TopSearchBusiMapper;
import com.yinhetianze.systemservice.system.service.busi.TopSearchBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.TopSearchPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = {Exception.class})
public class TopSearchBusiServiceImpl implements TopSearchBusiService
{
    @Autowired
    private TopSearchBusiMapper mapper;

    @Override
    public int insertSelective(TopSearchPojo topSearchPojo){
        return mapper.insertSelective(topSearchPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(TopSearchPojo topSearchPojo){
        topSearchPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(topSearchPojo);
    }

    @Override
    public int batchDelete(String ids) throws BusinessException {
        String[] idsArr = ids.split(",");
        if(idsArr==null || idsArr.length == 0){
            return 0;
        }
        for(String id:idsArr){
            TopSearchPojo topSearchPojo = new TopSearchPojo();
            topSearchPojo.setDelFlag((short)1);
            topSearchPojo.setId(Integer.parseInt(id));
            int result = updateByPrimaryKeySelective(topSearchPojo);
            if(result == 0){
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            }
        }
        return 1;
    }
}