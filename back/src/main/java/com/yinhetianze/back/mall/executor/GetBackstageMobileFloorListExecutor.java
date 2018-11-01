package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.MobileFloorPojo;
import com.yinhetianze.systemservice.mall.model.MobileFloorModel;
import com.yinhetianze.systemservice.mall.service.info.MobileFloorInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageMobileFloorListExecutor extends AbstractRestBusiExecutor{

    @Autowired
    private MobileFloorInfoService mobileFloorInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MobileFloorModel mobileFloorModel=(MobileFloorModel)model;
        MobileFloorPojo mobileFloorPojo=new MobileFloorPojo();
        if(CommonUtil.isNotEmpty(mobileFloorModel.getMobileFloorName())){
            try{
                String mobileFloorName = URLDecoder.decode(mobileFloorModel.getMobileFloorName(),"UTF-8");
                mobileFloorModel.setMobileFloorName(mobileFloorName);
            }catch (Exception e){
                LoggerUtil.error(GetPcFloorProductListExecutor.class, e);
            }
        }
        BeanUtils.copyProperties(mobileFloorModel,mobileFloorPojo);
        PageHelper.startPage(mobileFloorModel.getCurrentPage(),mobileFloorModel.getPageSize());
        PageInfo pageInfo=new PageInfo(mobileFloorInfoServiceImpl.selectMobileFloorList(mobileFloorPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
