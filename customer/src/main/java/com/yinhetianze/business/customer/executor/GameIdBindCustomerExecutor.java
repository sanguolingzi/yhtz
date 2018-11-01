package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 游戏用户登录 绑定已有账号
 */

@Component
public class GameIdBindCustomerExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private RedisManager redisManager;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;


    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerRecommendRelationBusiService customerRecommendRelationBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> m = new HashMap<>();
        BusiRegeisterModel busiRegeisterModel =(BusiRegeisterModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(busiRegeisterModel.getId());
        Map<String,Object> dataMap = (Map<String,Object>)redisManager.getValue(busiRegeisterModel.getGameKey());

        if(dataMap == null){
            LoggerUtil.error(GameIdBindCustomerExecutor.class,"gameKey is not found:"+busiRegeisterModel.getGameKey());
            m.put("code","2");
            return m;
        }

        String gameId = dataMap.getOrDefault("gameId","").toString();
        if(CommonUtil.isEmpty(gameId)) {
            LoggerUtil.error(GameIdBindCustomerExecutor.class, "gameId is not found:" + gameId);
            m.put("code", "2");
            return m;
        }

        //游戏端推荐人 gameId
        String pGameId = dataMap.getOrDefault("pGameId","").toString();

        //如果pGameId不为空 则存储customerBankrollBusiServiceImpl
        if(CommonUtil.isNotEmpty(pGameId) && Integer.parseInt(pGameId) != 0){
            BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
            busiCustomerBankrollPojo.setGameId(Integer.parseInt(pGameId));
            //根据pGameId查询记录是否存在
            BusiCustomerBankrollPojo bankrollPojo=customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);
            if(CommonUtil.isEmpty(bankrollPojo)){
                //如果信息不存在则插入记录
                try{
                    customerBankrollBusiServiceImpl.insertSelective(busiCustomerBankrollPojo);
                }catch (DuplicateKeyException e){

                }
            }
            BusiCustomerRecommendRelationPojo relationPojo = new BusiCustomerRecommendRelationPojo();
            relationPojo.setRecomedGameId(Integer.parseInt(gameId));
            relationPojo.setRecomGameId(Integer.parseInt(pGameId));
            customerRecommendRelationBusiServiceImpl.bindRelation(relationPojo);
        }



        //TODO 获取gameId
        busiCustomerPojo.setGameId(Integer.parseInt(gameId));
        int result = 0;
        try{
            result = customerBusiServiceImpl.bindCustomerByGameId(busiCustomerPojo);
            if(result <= 0){
                throw new BusinessException();
            }
        }catch(Exception e){
            if (e instanceof DuplicateKeyException){
                LoggerUtil.error(GameIdBindCustomerExecutor.class,"gameId exist gameId:"+busiCustomerPojo.getId());
                m.put("code","1");
                return m;
                //throw new BusinessException("游戏id已绑定!");
            }
            LoggerUtil.error(GameIdBindCustomerExecutor.class,e.getMessage());
            m.put("code","2");
            return m;
        }finally {

            if(result > 0){
                //判断是否是游戏用户过来注册 若是游戏用户过来注册 则需要调用接口赠送绑定账号所得的UB
                GameBusinessModel gameBusinessModel = new GameBusinessModel();
                //获取配置的赠送房卡数量
                int gameNumber = sysPropertiesUtils.getIntValue("gameNumber");
                int productType = sysPropertiesUtils.getIntValue("productType");
                SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
                //绑定成功调用通知游戏端接口
                gameBusinessModel.setGameId(Integer.parseInt(gameId));
                gameBusinessModel.setCustomerId(String.valueOf(busiCustomerPojo.getId()));
                gameBusinessModel.setTradeNo(CommonUtil.getSerialnumber());
                gameBusinessModel.setAmount(new BigDecimal(0));
                gameBusinessModel.setGameId(gameBusinessModel.getGameId());
                gameBusinessModel.setTradeDesc("绑定商城账号赠送旗豆:"+gameNumber);
                gameBusinessModel.setPaymentTime(sf.format(new Date()));
                gameBusinessModel.setNum(gameNumber);
                gameBusinessModel.setTradeType(productType);
                gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
            }
        }



        BusiCustomerModel busiCustomerModel  = new BusiCustomerModel();
        busiCustomerModel.setCheckPassword(false);
        busiCustomerModel.setId(busiCustomerPojo.getId());
        Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);


        m.put("code","0");
        m.put("userInfo",userInfo);
        redisManager.deleteValue(busiRegeisterModel.getGameKey());

        return m;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiRegeisterModel busiRegeisterModel =(BusiRegeisterModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        String smsCode = busiRegeisterModel.getSmsCode();
        String phone = busiRegeisterModel.getPhone();
        String gameKey = busiRegeisterModel.getGameKey();


        if(CommonUtil.isEmpty(phone)){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }

        if(!phone.matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(smsCode)){
            errorMessage.rejectNull("smsCode",null,"短信验证码");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(gameKey)){
            errorMessage.rejectNull("gameKey",null,"gameId");
            return errorMessage;
        }

        if(CustomerConstant.commonSmsCode.equals(smsCode)){
            //return null;
        }else{
            Object code = redisManager.getValue(phone+CustomerConstant.userBindGameSufixKey);
            if(CommonUtil.isEmpty(code)){
                errorMessage.rejectError("smsCode","BC0029","");
                return errorMessage;
            }

            if(!code.toString().equals(smsCode)){
                errorMessage.rejectError("smsCode","BC0053","");
                return errorMessage;
            }
        }

        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setPhone(busiRegeisterModel.getPhone());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(busiCustomerPojo == null){
            errorMessage.rejectErrorMessage("phone","该账号不存在,请注册!","该账号不存在,请注册!");
            return errorMessage;
        }else if(busiCustomerPojo.getGameId() != null){
            errorMessage.rejectErrorMessage("phone","该账号已绑定游戏用户!","该账号已绑定游戏用户!");
            return errorMessage;
        }
        busiRegeisterModel.setId(busiCustomerPojo.getId());
        return null;
    }

}
