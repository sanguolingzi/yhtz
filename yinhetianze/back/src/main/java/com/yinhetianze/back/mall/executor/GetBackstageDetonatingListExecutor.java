package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.DetonatingPojo;
import com.yinhetianze.systemservice.mall.model.DetonatingModel;
import com.yinhetianze.systemservice.mall.service.info.DetonatingInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetBackstageDetonatingListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private DetonatingInfoService detonatingInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DetonatingModel detonatingModel=(DetonatingModel)model;
        DetonatingPojo detonatingPojo=new DetonatingPojo();
        if(CommonUtil.isNotEmpty(detonatingModel.getDetonatingName())){
            try{
                String detonatingName = URLDecoder.decode(detonatingModel.getDetonatingName(),"UTF-8");
                detonatingModel.setDetonatingName(detonatingName);
            }catch (Exception e){
                LoggerUtil.error(GetPcFloorProductListExecutor.class, e);
            }
        }
        BeanUtils.copyProperties(detonatingModel,detonatingPojo);
        PageHelper.startPage(detonatingModel.getCurrentPage(),detonatingModel.getPageSize());
        PageInfo pageInfo=new PageInfo(detonatingInfoServiceImpl.selectBackstageDetonatingList(detonatingPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
