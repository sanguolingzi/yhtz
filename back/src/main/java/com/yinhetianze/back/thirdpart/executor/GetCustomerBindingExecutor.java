package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.impl.CustomerInfoServiceImpl;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.security.custom.userdetails.UserDetails;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.busi.RewardGameRecordBusiService;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import com.yinhetianze.systemservice.thirdpart.service.info.impl.GameRecordInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商城提供给游戏用户注册接口
 */

@Component
public class GetCustomerBindingExecutor extends AbstractRestBusiExecutor<Object>{

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationBusiService customerRecommendRelationBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BusiCustomerPojo customerIdPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        BusiCustomerPojo  customer = new BusiCustomerPojo();
        try{
            //查询该gameId在平台是否绑定
            BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
            if(CommonUtil.isNotEmpty(customerPojo)){
                throw new BusinessException("您已绑定游戏账号");
            }
            //查询customerId是否绑定gameId 已绑定则无法建立新的绑定关系
            customerIdPojo.setId(Integer.parseInt(gameBusinessModel.getCustomerId()));
            customer=customerInfoServiceImpl.selectOne(customerIdPojo);
            if(CommonUtil.isEmpty(customer)){
                throw new BusinessException("游戏账号已绑定商城账号");
            }
            if(CommonUtil.isNotEmpty(customer.getGameId())){
                throw new BusinessException("您已绑定游戏账号");
            }
            //满足条件建立绑定关系
            customerIdPojo.setGameId(gameBusinessModel.getGameId());

            //如果pGameId不为空 则存储customerBankrollBusiServiceImpl
            if(CommonUtil.isNotEmpty(gameBusinessModel.getpGameId())&& gameBusinessModel.getpGameId()>0){
                BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
                busiCustomerBankrollPojo.setGameId(gameBusinessModel.getpGameId());
                //根据pGameId查询记录是否存在
                BusiCustomerBankrollPojo bankrollPojo=customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);
                if(CommonUtil.isEmpty(bankrollPojo)){
                    //如果信息不存在则插入记录
                    try{
                        customerBankrollBusiServiceImpl.insertSelective(busiCustomerBankrollPojo);
                    }catch(DuplicateKeyException e){
                        LoggerUtil.error(GetCustomerBindingExecutor.class,"bankroll表gameId已存在:"+gameBusinessModel.getGameId()+e.toString());
                    }
                }
                BusiCustomerRecommendRelationPojo  relationPojo = new BusiCustomerRecommendRelationPojo();
                relationPojo.setRecomedGameId(gameBusinessModel.getGameId());
                relationPojo.setRecomGameId(gameBusinessModel.getpGameId());
                customerRecommendRelationBusiServiceImpl.bindRelation(relationPojo);
            }

            //获取配置的赠送房卡数量
            int gameNumber = sysPropertiesUtils.getIntValue("gameNumber");
            int productType = sysPropertiesUtils.getIntValue("productType");
            SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
            //绑定成功调用通知游戏端接口
            gameBusinessModel.setTradeNo(CommonUtil.getSerialnumber());
            gameBusinessModel.setAmount(new BigDecimal(0));
            gameBusinessModel.setGameId(gameBusinessModel.getGameId());
            gameBusinessModel.setTradeDesc("商城绑定游戏Id赠送旗豆:"+gameNumber);
            gameBusinessModel.setPaymentTime(sf.format(new Date()));
            gameBusinessModel.setNum(gameNumber);
            gameBusinessModel.setTradeType(productType);
        }catch (Exception e){
            e.printStackTrace();
            LoggerUtil.error(GetCustomerBindingExecutor.class,e.toString());
            return "gameIdBound";
        }
        //所有代码执行没问题则进行绑定关系确定
        int back=customerBusiServiceImpl.bindCustomerByGameId(customerIdPojo);
        if(back !=1){
            return "failure";
        }
        //绑定关系确定后调游戏端接口
        gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);

        TokenUser  tokenUser= (TokenUser) redisUserDetails.getUserDetails(gameBusinessModel.getToken());
        //默认设置 角色为 USER 拥有访问所有链接的权限
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("USER");
        Map<String,Object> info=null;
        try{
            info = CommonUtil.readFromString(CommonUtil.objectToJsonString(customer),Map.class);
        }catch (Exception e){
            LoggerUtil.error(CustomerInfoServiceImpl.class,e.getMessage());
        }
        TokenUser newTokenUser = new TokenUser(
                tokenUser.getId(),
                gameBusinessModel.getGameId(),
                customer.getPhone(),
                customer.getLoginPassword(),
                gameBusinessModel.getToken(),
                roleSet,
                info
        );
        redisUserDetails.saveUserDetails(newTokenUser);

        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        ErrorMessage errorMessage = new ErrorMessage();

        if (CommonUtil.isEmpty(gameBusinessModel.getGameId())) {
            errorMessage.rejectNull("gameId", null, "游戏用户ID");
            return errorMessage;
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getCustomerId())) {
            errorMessage.rejectNull("customerId", null, "商城用户ID");
            return errorMessage;
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getChannelCode())) {
            errorMessage.rejectNull("channelCode", null, "渠道编码");
            return errorMessage;
        }
        if (CommonUtil.isNull(gameBusinessModel.getSign())) {
            errorMessage.rejectNull("sign", null, "签名参数");
            return errorMessage;
        } else {
            String channelCode = gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String,Map<String,Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String,Map<String,Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret + "&customerId=" + gameBusinessModel.getCustomerId()+"&gameId=" + gameBusinessModel.getGameId()+"&pGameId="+gameBusinessModel.getpGameId();
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }

        return errorMessage;
    }
}
