package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.impl.CustomerBusiServiceImpl;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.product.service.ProductFresherRewardBusiService;
import com.yinhetianze.business.product.service.ProductFresherRewardInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.product.ProductFresherRewardPojo;
import com.yinhetianze.pojo.thirdpart.RewardGameRecordPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.busi.RewardGameRecordBusiService;
import com.yinhetianze.systemservice.thirdpart.service.info.RewardGameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.dao.DuplicateKeyException;

/**
 * 游戏币奖励
 */

@Component
public class GetCustomerGameAmountExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private RewardGameRecordBusiService rewardGameRecordBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private RewardGameRecordInfoService rewardGameRecordInfoServiceImpl;

    @Autowired
    private ProductFresherRewardInfoService productFresherRewardInfoServiceImpl;

    @Autowired
    private ProductFresherRewardBusiService productFresherRewardBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        //gameId 查不倒 对应用户
        BusiCustomerBankrollPojo record =new BusiCustomerBankrollPojo();
        record.setGameId(gameBusinessModel.getGameId());
        //查询该gameId在平台是否绑定
        BusiCustomerBankrollPojo customerBankrollPojo = customerBankrollInfoServiceImpl.selectOne(record);
        if (CommonUtil.isEmpty(customerBankrollPojo)) {
            // 生成一个 账户信息
            try{
                customerBankrollBusiServiceImpl.insertSelective(record);
                customerBankrollPojo=record;
            }catch(DuplicateKeyException e){
                //说明已存在直接 select
                customerBankrollPojo = customerBankrollInfoServiceImpl.selectOne(record);
                LoggerUtil.error(GetCustomerGameAmountExecutor.class,"bankroll表gameId已存在:"+gameBusinessModel.getGameId());
            }
        }
        String id=null;
        // 0成功 1 失败
        int judge=1;
        //已绑定则修改该gameId对应的customerId账号的gameAmount金额
        if (gameBusinessModel.getGameAmount().compareTo(BigDecimal.ZERO) == 1) {
            //更新推荐用户资金表 流水记录
            try{
                //不存在 insterGameAmount   捕获异常
                //生成流水记录表 记录当前成功的gameId customerId gameAmount
                RewardGameRecordPojo rewardGameRecordPojo = new RewardGameRecordPojo();
                rewardGameRecordPojo.setGameId(gameBusinessModel.getGameId());
                if(CommonUtil.isNotEmpty(customerBankrollPojo.getCustomerId())){
                    rewardGameRecordPojo.setCustomerId(customerBankrollPojo.getCustomerId());
                }
                rewardGameRecordPojo.setGameAmount(gameBusinessModel.getGameAmount());
                if(CommonUtil.isNotEmpty(id)){
                    rewardGameRecordPojo.setBankrollSerial(Integer.parseInt(id));
                }
                rewardGameRecordPojo.setJudge(judge);
                rewardGameRecordPojo.setGameTaskId(gameBusinessModel.getRewardId());
                rewardGameRecordBusiServiceImpl.insterGameAmount(rewardGameRecordPojo);

                BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
                BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
                List<BusiCustomerBankrollFlowModel> list = new ArrayList<>();
                busiUpdateBankrollModel.setRewardAmount(gameBusinessModel.getGameAmount());
                if(CommonUtil.isNotEmpty(customerBankrollPojo.getCustomerId())){
                    busiUpdateBankrollModel.setCustomerId(customerBankrollPojo.getCustomerId());
                }
                busiUpdateBankrollModel.setGameId(gameBusinessModel.getGameId());
                busiUpdateBankrollModel.setRewardAmountAddOrMinus((short) 0);
                busiCustomerBankrollFlowModel.setFlowType((short) 0);
                busiCustomerBankrollFlowModel.setFlowCategory((short) 6);
                busiCustomerBankrollFlowModel.setFlowDescription((short) 19);
                busiCustomerBankrollFlowModel.setAmount(gameBusinessModel.getGameAmount());
                busiCustomerBankrollFlowModel.setRelationId(gameBusinessModel.getGameId());
                list.add(busiCustomerBankrollFlowModel);
                busiUpdateBankrollModel.setList(list);
                id = customerBankrollBusiServiceImpl.addBankrollRewardAmount(busiUpdateBankrollModel);
                 if (CommonUtil.isEmpty(id)) {
                    throw new BusinessException("添加账户流水失败");
                }
                //修改记录流水表
                judge=0;
                rewardGameRecordPojo.setJudge(judge);
                rewardGameRecordBusiServiceImpl.updateRecord(rewardGameRecordPojo);
            }catch (Exception e){
                if (e instanceof DuplicateKeyException){
                    throw new BusinessException("奖励编号id已存在!");
                }
                LoggerUtil.error(GetCustomerGameAmountExecutor.class,"gameId:"+gameBusinessModel.getGameId()+",rewardId"+gameBusinessModel.getRewardId()+",gameAmount"+gameBusinessModel.getGameAmount());
                throw new BusinessException("添加失败!");
            }
        }
        //查询 busi_product_fresher_reward 是否存在记录
        ProductFresherRewardPojo productFresherRewardPojo = new ProductFresherRewardPojo();
        productFresherRewardPojo.setGameId(gameBusinessModel.getGameId());
        productFresherRewardPojo=productFresherRewardInfoServiceImpl.selectFresherReward(productFresherRewardPojo);
        if(CommonUtil.isEmpty(productFresherRewardPojo)){
            //格式化时间参数
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(gameBusinessModel.getRegisterTime(), dtf);
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            Date registerTime = Date.from(zdt.toInstant());
            ProductFresherRewardPojo fresherRewardPojo = new ProductFresherRewardPojo();
            fresherRewardPojo.setGameId(gameBusinessModel.getGameId());
            fresherRewardPojo.setRegeisterTime(registerTime);
            try{
                productFresherRewardBusiServiceImpl.insertSelective(fresherRewardPojo);
            }catch(DuplicateKeyException e){
                LoggerUtil.error(GetCustomerGameAmountExecutor.class,"busi_product_fresher_reward表gameId已存在:"+gameBusinessModel.getGameId()+e.toString());
            }
        }
        Map dataCheck =new HashMap();
        //查询gameId充值u币后的u币金额
        BusiCustomerBankrollPojo bankroll = customerBankrollInfoServiceImpl.selectOne(record);
        dataCheck.put("dataCheck",judge);
        dataCheck.put("rewardAmount",bankroll.getRewardAmount().divide(new BigDecimal(100)));
        return dataCheck;

    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(gameBusinessModel.getRewardId())){
            errorMessage.rejectNull("rewardId",null,"奖励编号Id");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"游戏Id");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getGameAmount())){
            errorMessage.rejectNull("gameAmount",null,"游戏币");
            return errorMessage;
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getChannelCode())) {
            errorMessage.rejectNull("channelCode", null, "渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getRegisterTime())){
            errorMessage.rejectNull("registerTime", null, "注册时间");
            return errorMessage;
        }else{
            //注册时间格式校验
            try{
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate.parse(gameBusinessModel.getRegisterTime(), dtf);
            }catch (Exception e){
                errorMessage.rejectError("registerTime", null, "时间格式错误");
                return errorMessage;
            }
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getSign())) {
            errorMessage.rejectNull("sign", null, "签名参数");
            return errorMessage;
        } else {
            String channelCode = gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String,Map<String,Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String,Map<String,Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret +"&rewardId=" + gameBusinessModel.getRewardId()+ "&gameId=" + gameBusinessModel.getGameId()+"&gameAmount=" + gameBusinessModel.getGameAmount()+"&registerTime="+gameBusinessModel.getRegisterTime();
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }
        RewardGameRecordPojo rewardGameRecordPojo =new RewardGameRecordPojo();
        rewardGameRecordPojo.setGameTaskId(gameBusinessModel.getRewardId());
        //校验rewardId是否存在 如果存在则提示错误 不存在才进行操作
        RewardGameRecordPojo pojo= rewardGameRecordInfoServiceImpl.getOneRewardId(rewardGameRecordPojo);
        if(CommonUtil.isNotEmpty(pojo)){
            errorMessage.rejectError("rewardId","奖励编号id已存在");
            return errorMessage;
        }
        return errorMessage;
    }
}
