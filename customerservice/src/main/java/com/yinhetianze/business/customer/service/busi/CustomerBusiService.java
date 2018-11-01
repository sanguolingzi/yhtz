package com.yinhetianze.business.customer.service.busi;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;

public interface CustomerBusiService
{
    int insert(BusiCustomerPojo record);

    int insertSelective(BusiCustomerPojo record);

    int updateByPrimaryKeySelective(BusiCustomerPojo record);

    int updateByPrimaryKey(BusiCustomerPojo record);

    /**
     * 新增用户基本信息
     * @param busiCustomerPojo
     * @return
     * @throws BusinessException
     */
    int addCustomerInfo(BusiCustomerPojo busiCustomerPojo)   throws BusinessException ;

    /**
     * 注册 调用 新增用户信息
     * @param busiRegeisterModel
     * @return
     * @throws BusinessException
     */
    int addRegeisterCustomer(BusiRegeisterModel busiRegeisterModel) throws BusinessException;

    /**
     * 设置用户为合伙人
     * @param customerId
     * @return
     */
    int updatePartner(Integer customerId,Integer gameId)  throws BusinessException;

    /**
     * 取消用户合伙人身份
     * @param customerId  gameId
     * @return
     */
    int cancelPartner(Integer customerId,Integer gameId)  throws BusinessException;

    /**
     * 购买礼包成功后 设置购买人会员 以及 推荐二维码 推荐码 信息
     * @param customerId
     * @return
     * @throws BusinessException
     */
    int updateMember(Integer customerId) throws BusinessException;

    /**
     * 生成用户二维码
     * @param busiCustomerPojo
     * @return
     */
    void createQrcode(BusiCustomerPojo busiCustomerPojo);


    /**
     * 取消会员 同时清除会员推荐码和推荐二维码
     * @param customerId
     * @return
     */
    int cancelMember(Integer customerId);

    /**
     * 游戏用户绑定已有账号(个人设置接口调用/注册接口调用)
     * 1 更新customer的 gameId 字段
     * 2 判断该gameid 下是否有账户记录 若有 把U币账户余额 加到customner下 流水也修改到customer下  gameId也要绑定到账户信息
     * @param busiCustomerPojo
     * @return
     * @throws BusinessException
     */
    int bindCustomerByGameId(BusiCustomerPojo busiCustomerPojo) throws BusinessException;

    /**
     * 游戏账号绑定
     * @param customerIdPojo
     * @return
     */
    int updateGameId(BusiCustomerPojo customerIdPojo);


    /**
     * 微信与帐号建立绑定关系时 处理 商城用户的头像/昵称/性别 字段
     * 商城账户没有的信息 需要用微信的信息补全
     * @param busiCustomerPojo
     * @param busiCustomerWechatPojo
     */
    int handleUserInfo(BusiCustomerPojo busiCustomerPojo, BusiCustomerWechatPojo busiCustomerWechatPojo);
}