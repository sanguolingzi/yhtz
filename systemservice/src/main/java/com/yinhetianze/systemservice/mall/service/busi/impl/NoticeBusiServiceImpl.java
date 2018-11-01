package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.systemservice.mall.mapper.busi.NoticeBusiMapper;
import com.yinhetianze.systemservice.mall.service.busi.NoticeBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.NoticePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoticeBusiServiceImpl implements NoticeBusiService
{
    @Autowired
    private NoticeBusiMapper mapper;

    @Override
    public int updateByPrimaryKeySelective(NoticePojo noticePojo){
        noticePojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(noticePojo);
    }

    @Override
    public int insertSelective(NoticePojo noticePojo){
        return mapper.insertSelective(noticePojo);
    }

    @Override
    public int deleteBatch(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            NoticePojo noticePojo = new NoticePojo();
            noticePojo.setId(Integer.parseInt(id));
            noticePojo.setDelFlag((short)1);
            noticePojo.setUpdateTime(new Date());
            int result = updateByPrimaryKeySelective(noticePojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }
}