package com.yinhetianze.systemservice.mall.service.busi.impl;

import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.MobileFloorDetailPojo;
import com.yinhetianze.systemservice.mall.mapper.busi.MobileFloorDetailBusiMapper;
import com.yinhetianze.systemservice.mall.model.MobileFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.MobileFloorDetailBusiService;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorDetailInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MobileFloorDetailBusiServiceImpl implements MobileFloorDetailBusiService
{
    @Autowired
    private MobileFloorDetailBusiMapper mapper;

    @Autowired
    private MobileFloorDetailInfoService mobileFloorDetailInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Override
    public int insertSelective(MobileFloorDetailPojo mobileFloorDetailPojo) {
        return mapper.insertSelective(mobileFloorDetailPojo);
    }

    @Override
    public int updateByPrimaryKeySelective(MobileFloorDetailPojo mobileFloorDetailPojo) {
        return mapper.updateByPrimaryKeySelective(mobileFloorDetailPojo);
    }

}