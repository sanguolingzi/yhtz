package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.activity.service.busi.ActivityProductBusiService;
import com.yinhetianze.business.activity.service.info.ActivityProductInfoService;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ActivityProductPojo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;
import java.util.List;

public class ActivityProductStateSchedule extends InterruptableJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ActivityProductInfoService activityProductInfoService= (ActivityProductInfoService) ApplicationContextFactory.getBean("activityProductInfoServiceImpl");
        ActivityProductBusiService activityProductBusiService=(ActivityProductBusiService)ApplicationContextFactory.getBean("activityProductBusiServiceImpl");
        ActivityProductPojo saleActivityProductPojo=new ActivityProductPojo();
        //定时上架 商品必须是待上架状态 时间小于等于当前时间的商品可以上架
        saleActivityProductPojo.setIsSale((short)3);
        saleActivityProductPojo.setAutoSaleTime(new Date());
        List<ActivityProductPojo>activityProductPojoList=activityProductInfoService.putawayActivityProduct(saleActivityProductPojo);
        if(CommonUtil.isNotEmpty(activityProductPojoList)){
            for (ActivityProductPojo activityProductPojo:activityProductPojoList){
                activityProductPojo.setIsSale((short)0);
                activityProductBusiService.updateByPrimaryKeySelective(activityProductPojo);
            }
        }
        //定时下架 商品必须是上架状态 时间小于等于当前时间的商品可以下架
        ActivityProductPojo offActivityProductPojo= new ActivityProductPojo();
        offActivityProductPojo.setIsSale((short)0);
        offActivityProductPojo.setAutoOffTime(new Date());
        List<ActivityProductPojo>activityProductPojos=activityProductInfoService.putawayActivityProduct(offActivityProductPojo);
        if(CommonUtil.isNotEmpty(activityProductPojos)){
            for (ActivityProductPojo activityProductPojo:activityProductPojos){
                activityProductPojo.setIsSale((short)1);
                activityProductBusiService.updateByPrimaryKeySelective(activityProductPojo);
            }
        }
    }
}
