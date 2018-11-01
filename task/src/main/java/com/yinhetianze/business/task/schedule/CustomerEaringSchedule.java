package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.busi.CustomerEarningsBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerEarningsInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.CustomerEarningsPojo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 余额收益定时任务
 */
public class CustomerEaringSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{

        CustomerEarningsInfoService customerEarningsInfoServiceImpl = (CustomerEarningsInfoService) ApplicationContextFactory.getBean("customerEarningsInfoServiceImpl");

        //获取数据库当前时间
        Map<String,Object> dataMap = customerEarningsInfoServiceImpl.selectDbCurrentTime();
        String currentDate = dataMap.getOrDefault("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).toString();
        //String currentDate = "2018-08-25";

        Date currentDateObj = null;
        try{
            //查询日期为数据当前时间前一天
            currentDate = LocalDate.parse(currentDate).minusDays(1).toString();
            currentDateObj = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
        }catch (Exception e){
            LoggerUtil.error(CustomerEaringSchedule.class,"parseDate error    "+e.getMessage());
        }

        CustomerInfoService customerInfoServiceImpl = (CustomerInfoService) ApplicationContextFactory.getBean("customerInfoServiceImpl");
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();

        busiCustomerPojo.setDelFlag((short)0);
        busiCustomerPojo.setIsMember((short)0);

        //查询会员 暂时是进行全表查询
        //这里后续做优化 将会员信息单独处理成一张表
        List<BusiCustomerPojo> list = customerInfoServiceImpl.select(busiCustomerPojo);
        if(list!=null&&!list.isEmpty()){


            CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl =
                    (CustomerBankrollFlowInfoService) ApplicationContextFactory.getBean("customerBankrollFlowInfoServiceImpl");

            CustomerEarningsBusiService customerEarningsBusiServiceImpl =
                    (CustomerEarningsBusiService) ApplicationContextFactory.getBean("customerEarningsBusiServiceImpl");

            Map<String,Object> paraMap= new HashMap<>();
            paraMap.put("currentDate",currentDate);
            for(BusiCustomerPojo pojo:list){
                paraMap.put("customerId",pojo.getId());

                //查询收益情况
                List<BusiCustomerBankrollFlowModel> marketList = customerBankrollFlowInfoServiceImpl.selectMarket(paraMap);

                if(marketList!=null&&!marketList.isEmpty()){
                    int length = 0;
                    List<CustomerEarningsPojo> addList = new ArrayList<>();
                    for(BusiCustomerBankrollFlowModel model : marketList){
                        CustomerEarningsPojo customerEarningsPojo = new CustomerEarningsPojo();
                        customerEarningsPojo.setAmount(model.getAmount());
                        customerEarningsPojo.setCreateId(model.getCreateId());
                        customerEarningsPojo.setCustomerId(model.getCustomerId());
                        customerEarningsPojo.setCreateTime(currentDateObj);

                        addList.add(customerEarningsPojo);
                        length+=1;
                        if(length>0 && length%10==0){
                            customerEarningsBusiServiceImpl.addInfoBatch(addList);
                            addList.clear();
                        }
                    }

                    //处理不足10条的记录 也需要入库
                    if(!addList.isEmpty()){
                        customerEarningsBusiServiceImpl.addInfoBatch(addList);
                    }
                }
            }
        }
    }
}


