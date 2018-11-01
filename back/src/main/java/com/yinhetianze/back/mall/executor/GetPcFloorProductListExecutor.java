package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.systemservice.mall.model.FloorDetailProductPageModel;
import com.yinhetianze.systemservice.mall.model.SysFloorDetailModel;
import com.yinhetianze.systemservice.mall.service.info.SysFloorDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Component
public class GetPcFloorProductListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private SysFloorDetailInfoService sysFloorDetailInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        FloorDetailProductPageModel floorDetailProductPageModel=(FloorDetailProductPageModel)model;
        if(CommonUtil.isNotEmpty(floorDetailProductPageModel.getProdName())){
            try{
                String prodName = URLDecoder.decode(floorDetailProductPageModel.getProdName(),"UTF-8");
                floorDetailProductPageModel.setProdName(prodName);
            }catch (Exception e){
                LoggerUtil.error(GetPcFloorProductListExecutor.class, e);
            }
        }
        PageHelper.startPage(floorDetailProductPageModel.getCurrentPage(), floorDetailProductPageModel.getPageSize());
        PageInfo pageInfo=new PageInfo(sysFloorDetailInfoServiceImpl.selectPcFloorProductList((FloorDetailProductPageModel) model));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        FloorDetailProductPageModel floorDetailProductPageModel =(FloorDetailProductPageModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(floorDetailProductPageModel.getFloorId())){
            errorMessage.rejectNull("floorId",null,"楼层ID");
        }
        return errorMessage;
    }
}
