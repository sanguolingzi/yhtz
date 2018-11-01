package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.product.service.ProductFresherRewardBusiService;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FresherRewardStateSchedule extends InterruptableJob {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SysPropertiesUtils sysPropertiesUtils=(SysPropertiesUtils) ApplicationContextFactory.getBean("sysPropertiesUtils");
        ProductFresherRewardInfoService productFresherRewardInfoService=(ProductFresherRewardInfoService) ApplicationContextFactory.getBean("productFresherRewardInfoServiceImpl");
        ProductFresherRewardBusiService productFresherRewardBusiService=(ProductFresherRewardBusiService) ApplicationContextFactory.getBean("productFresherRewardBusiServiceImpl");
        Integer uReward=(sysPropertiesUtils.getIntValue("uReward")*100);
        String daysReward=sysPropertiesUtils.getStringValue("daysReward");
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("uReward",uReward);
        map.put("daysReward",daysReward);
        List<ProductFresherRewardPojo>list=productFresherRewardInfoService.selectProductFresherRewardId(map);
        ProductFresherRewardPojo productFresherRewardPojo=new ProductFresherRewardPojo();
        if(CommonUtil.isNotEmpty(list)){
            for(ProductFresherRewardPojo pfrpojp:list){
                productFresherRewardPojo.setId(pfrpojp.getId());
                productFresherRewardPojo.setHandleStatus((short)0);
                productFresherRewardBusiService.updateByPrimaryKeySelective(productFresherRewardPojo);
            }
        }
    }
}
