package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductImgModel;
import com.yinhetianze.business.activity.service.info.ActivityProductImgInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class GetBackstageActivityProductImgExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ActivityProductImgInfoService activityProductImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ActivityProductImgModel activityProductImgModel=(ActivityProductImgModel)model;
        ActivityProductImgPojo activityProductImgPojo=new ActivityProductImgPojo();
        BeanUtils.copyProperties(activityProductImgModel,activityProductImgPojo);
        activityProductImgPojo.setDelFlag((short)0);
        List<ActivityProductImgPojo> activityProductImgPojoList=activityProductImgInfoServiceImpl.selectOneAreaImgList(activityProductImgPojo);
        return activityProductImgPojoList;
    }
}
