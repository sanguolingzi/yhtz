package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.systemservice.mall.mapper.busi.MallFlashreportBusiMapper;
import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.systemservice.mall.service.busi.MallFlashreportBusiService;
import com.yinhetianze.systemservice.mall.service.info.MallFlashreportInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
@Service
@Transactional(rollbackFor = {Exception.class})
public class MallFlashreportBusiServiceImpl implements MallFlashreportBusiService {

    @Autowired
    private MallFlashreportBusiMapper mapper;

    @Autowired
    private MallFlashreportInfoService mallFlashreportInfoServiceImpl;

    @Override
    public  int insertSelective(BusiMallFlashreportPojo busiMallFlashreportPojo){
        return  mapper.insertSelective(busiMallFlashreportPojo);
    }
    @Override
    public  int deleteBatch(String ids) throws BusinessException {
        String[] idsArr = ids.split("\\,");
        if(idsArr == null || idsArr.length == 0){
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        for(String id:idsArr){
            BusiMallFlashreportPojo busiMallFlashreportPojo=new BusiMallFlashreportPojo();
            busiMallFlashreportPojo.setId(Integer.parseInt(id));
            busiMallFlashreportPojo.setDelFlag((short)1);
            int result = updateByPrimaryKeySelective(busiMallFlashreportPojo);
            if(result <= 0)
                throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return  1;
    }

    @Override
    public int updateByPrimaryKeySelective(BusiMallFlashreportPojo busiMallFlashreportPojo){
        busiMallFlashreportPojo.setUpdateTime(new Date());
        return mapper.updateByPrimaryKeySelective(busiMallFlashreportPojo);
    }


    @Override
    public  int updateInfo(MallFlashreportModel mallFlashreportModel)throws  BusinessException{
        try {
            BusiMallFlashreportPojo busiMallFlashreportPojo=new BusiMallFlashreportPojo();
            BeanUtils.copyProperties(busiMallFlashreportPojo, mallFlashreportModel);
            int result= this.updateByPrimaryKeySelective(busiMallFlashreportPojo);
            if(result<=0)
                throw  new BusinessException("BC0049",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }catch (Exception e){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }

}
