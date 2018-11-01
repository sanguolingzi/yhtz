package com.yinhetianze.business.task.schedule;

import com.yinhetianze.business.customer.mapper.info.CustomerBankrollInfoMapper;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.impl.CustomerBankrollInfoServiceImpl;
import com.yinhetianze.business.order.mapper.OrderInfoMapper;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.back.BusiSysSyspropertiesPojo;
import com.yinhetianze.systemservice.system.service.info.impl.SysSyspropertiesInfoServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsSchedule extends InterruptableJob{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        /**
         * 迁移1.0版本消费券自动领取消费券定时器,迁移1.0 shop_customer_bankroll表的 expenseSubsidy消费总补贴字段
         * 已领劵字段 yokaAmount 未领劵字段 notYokaAmount 可用消费券字段 coinAmount
         * expenseSubsidy = yokaAmount +notYokaAmount 或者 expenseSubsidy=notYokaAmount+coinAmount+消费者消费数量，
         * 迁移领劵流水记录表 fanli_yoka
         */
        //获取系统参数最低可返消费券金额
        /*SysSyspropertiesInfoServiceImpl sysSyspropertiesInfoServiceImpl=(SysSyspropertiesInfoServiceImpl) ApplicationContextFactory.getBean("sysSyspropertiesInfoServiceImpl");
        BusiSysSyspropertiesPojo busiSysSyspropertiesPojo = new BusiSysSyspropertiesPojo();
        busiSysSyspropertiesPojo.setpName("rewardsAmount");
        BusiSysSyspropertiesPojo sysSysproperties=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);
        //获取返利比率
        busiSysSyspropertiesPojo.setpName("rewardsRatio");
        BusiSysSyspropertiesPojo rewardsRatio=sysSyspropertiesInfoServiceImpl.selectProperties(busiSysSyspropertiesPojo);
        if(CommonUtil.isNotNull(sysSysproperties) && CommonUtil.isNotNull(rewardsRatio)){
            //获取用户资金表信息
            CustomerBankrollInfoMapper customerBankrollInfoMapper=(CustomerBankrollInfoMapper)ApplicationContextFactory.getBean("customerBankrollInfoMapper");
            //查询所有用户的领劵积分
            List<Map> listBankroll = customerBankrollInfoMapper.findAllCustomerBankroll(new BigDecimal(sysSysproperties.getpValue()));
            if(CommonUtil.isNotNull(listBankroll)&& listBankroll.size()>0){
                CustomerBankrollBusiService customerBankrollBusiServiceImpl=(CustomerBankrollBusiService)ApplicationContextFactory.getBean("customerBankrollBusiServiceImpl");
                try {
                    customerBankrollBusiServiceImpl.updateRewards(listBankroll,rewardsRatio.getpValue());
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }
}
