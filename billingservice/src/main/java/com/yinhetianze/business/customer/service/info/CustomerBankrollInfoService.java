package com.yinhetianze.business.customer.service.info;

import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;

import java.util.List;
import java.util.Map;

public interface CustomerBankrollInfoService
{

    BusiCustomerBankrollPojo selectOne(BusiCustomerBankrollPojo record);


    List<BusiCustomerBankrollPojo> select(BusiCustomerBankrollPojo record);

    /**
     * 根据消费者Id 查询关联账户
     * @param customerId
     * @return
     */
    BusiCustomerBankrollPojo selectByCustomerId(Integer customerId);

    /**
     * 根据游戏用户Id 查询关联账户
     * @param gameId
     * @return
     */
    BusiCustomerBankrollPojo selectByGameId(Integer gameId);



    /**
     * 返回 拥有的 消费券 余额 积分账户信息 返回数据缩小100
     * @param customerId
     * @return
     */
    BusiCustomerBankrollPojo selectBankrollInfo(Integer customerId);

}