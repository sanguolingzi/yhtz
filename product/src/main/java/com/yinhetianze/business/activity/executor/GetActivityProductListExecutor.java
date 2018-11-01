package com.yinhetianze.business.activity.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.business.product.executor.GetBackstageOneAreaListExecutor;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.SysDictionaryPojo;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import com.yinhetianze.systemservice.system.service.info.SysDictionaryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
@Component
public class GetActivityProductListExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ActivityProductInfoService activityProductInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        BeanUtils.copyProperties(activityProductModel,activityProductPojo);
        PageHelper.startPage(activityProductModel.getCurrentPage(), activityProductModel.getPageSize());
        PageInfo pageInfo=new PageInfo(activityProductInfoServiceImpl.selectActivityProductList(activityProductPojo));
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return  pageData;
    }

}
