package com.yinhetianze.business.activity.executor;

import com.yinhetianze.business.activity.model.ActivityProductModel;
import com.yinhetianze.business.activity.service.info.ActivityProductImgInfoService;
import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.product.ActivityProductImgPojo;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetActivityProductDetailsExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private ActivityProductInfoService activityProductInfoServiceImpl;

    @Autowired
    private ActivityProductImgInfoService activityProductImgInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map map=new HashMap();
        ActivityProductModel activityProductModel=(ActivityProductModel)model;
        ActivityProductPojo activityProductPojo=new ActivityProductPojo();
        BeanUtils.copyProperties(activityProductModel,activityProductPojo);
        List<Map> activityProductlist=activityProductInfoServiceImpl.selectActivityProduct(activityProductPojo);
        map.put("activityProduct",activityProductlist);
        if(activityProductlist.size()>0){
            ActivityProductImgPojo activityProductImgPojo=new ActivityProductImgPojo();
            activityProductImgPojo.setActProdId(activityProductModel.getId());
            activityProductImgPojo.setDelFlag((short)0);
            List<ActivityProductImgPojo> list=activityProductImgInfoServiceImpl.selectOneAreaImgList(activityProductImgPojo);
            map.put("activityProductImg",list);
        }else{
            throw  new BusinessException("该活动商品不存在");
        }
        return map;
    }
}
