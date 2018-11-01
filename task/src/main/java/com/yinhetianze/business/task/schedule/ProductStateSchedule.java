package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.impl.OrderInfoServiceImpl;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品定时上下架定时器
 */
public class ProductStateSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
       //获取所有商品的定时上架商品
        ProductInfoService productInfoService= (ProductInfoService)ApplicationContextFactory.getBean("productInfoServiceImpl");
        ProductBusiService productBusiService= (ProductBusiService)ApplicationContextFactory.getBean("productBusiServiceImpl");
        ProductPojo upProductPojo =new ProductPojo();
        //定时上架 商品必须是待上架状态 而且是审核通过的 时间小于等于当前时间的商品可以上架
        upProductPojo.setpStatus((short) 3);
        upProductPojo.setAuditState((short)3);
        upProductPojo.setUpTime(new Date());
        List<ProductPojo>  listProductPojo= productInfoService.putawayProduct(upProductPojo);
        if(CommonUtil.isNotEmpty(listProductPojo)){
            for(ProductPojo product : listProductPojo){
                product.setpStatus((short) 0);
                productBusiService.modifyProduct(product);
            }
        }
        //定时下架 商品必须是上架状态 而且是审核通过的 时间小于等于当前时间的商品可以下架
        ProductPojo downProductPojo =new ProductPojo();
        downProductPojo.setpStatus((short) 0);
        downProductPojo.setAuditState((short)3);
        downProductPojo.setDownTime(new Date());
        List<ProductPojo>  listDownProductPojo= productInfoService.putawayProduct(downProductPojo);
        if(CommonUtil.isNotEmpty(listDownProductPojo)){
            for(ProductPojo product : listDownProductPojo){
                product.setpStatus((short) 1);
                productBusiService.modifyProduct(product);
            }
        }
    }
}


