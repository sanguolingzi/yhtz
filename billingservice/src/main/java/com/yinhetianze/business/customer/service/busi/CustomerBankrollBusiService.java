package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.business.customer.model.BusiCustomerStarCoinInfoModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;

import java.util.List;
import java.util.Map;

public interface CustomerBankrollBusiService
{

    int insert(BusiCustomerBankrollPojo record);

    int insertSelective(BusiCustomerBankrollPojo record);

    int updateByPrimaryKeySelective(BusiCustomerBankrollPojo record);

    int updateByPrimaryKey(BusiCustomerBankrollPojo record);

    int add(BusiCustomerBankrollPojo busiCustomerBankrollPojo);

    int deleteInfo(BusiCustomerBankrollPojo busiCustomerBankrollPojo);
    /**
     * 修改用户账户  同时增加账户流水
     * 账户流水信息由调用者准备 因为有一些业务相关字段
     * @param busiUpdateBankrollModel  详见model内字段描述
     * @return 返回 账户流水id 字符串
     * @throws BusinessException
     */
    String updateBankrollForOrder(BusiUpdateBankrollModel busiUpdateBankrollModel) throws BusinessException;

    /**
     * 摘星业务处理逻辑
     */
    int addStarCoin(BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel) throws BusinessException;

    void updateRewards(List<Map> listBankroll, String rewardsRatio) throws BusinessException;

    /**
     * 根据gameid 更新 customerId
     * @param busiCustomerBankrollPojo
     * @return
     */
    int bindCustomerByGameId(BusiCustomerBankrollPojo busiCustomerBankrollPojo);

    /**
     * 清除单独为游戏用户生成账户gameId
     * @param id
     * @return
     */
    int clearGameId(Integer id);

    /**
     * 设置gameId
     * @param busiCustomerBankrollPojo
     * @return
     */
    int setGameId(BusiCustomerBankrollPojo busiCustomerBankrollPojo);

    /**
     * 把游戏用户生成的账户数据迁移  gameId  rewardAmount 迁移到指定的customerId账户
     * @param busiCustomerBankrollPojo
     * @return
     */
    int bindGameIdByCustomerId(BusiCustomerBankrollPojo busiCustomerBankrollPojo);

    /**
     * 同步游戏过来的U币 只做累加
     * @param busiUpdateBankrollModel
     * @return
     */
    String addBankrollRewardAmount(BusiUpdateBankrollModel busiUpdateBankrollModel);
}