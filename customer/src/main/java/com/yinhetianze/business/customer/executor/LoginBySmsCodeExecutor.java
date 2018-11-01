package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 短信验证码登录/注册
 */

@Component
public class LoginBySmsCodeExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;

        Map<String,Object> paraMap = new HashMap<>();
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByPhone(busiRegeisterModel.getPhone());
        if(busiCustomerPojo == null) {
            //paraMap.put("code","1");
            //redisManager.setValue(busiRegeisterModel.getPhone()+CustomerConstant.currentUserOperator,busiRegeisterModel.getPhone(),new Long(1000*180));

            if(CommonUtil.isEmpty(busiRegeisterModel.getGameId())){
                paraMap.put("code","3");//号码不存在 并且 没有游戏信息  拒绝注册
                return paraMap;
            }

            int customerId = customerBusiServiceImpl.addRegeisterCustomer(busiRegeisterModel);
            BusiCustomerModel busiCustomerModel  = new BusiCustomerModel();
            busiCustomerModel.setCheckPassword(false);
            busiCustomerModel.setId(customerId);
            Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);
            paraMap.put("code","2");
            paraMap.put("data",userInfo);
            return paraMap;
        }
        BusiCustomerModel busiCustomerModel  = new BusiCustomerModel();
        busiCustomerModel.setCheckPassword(false);
        busiCustomerModel.setId(busiCustomerPojo.getId());
        Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);
        redisManager.deleteValue(busiRegeisterModel.getPhone()+CustomerConstant.userLoginSufixKey);

        paraMap.put("code","2");
        paraMap.put("data",userInfo);

        redisManager.setValue(busiRegeisterModel.getPhone()+CustomerConstant.currentUserOperator,busiRegeisterModel.getPhone(),new Long(1000*180));
        return paraMap;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;

        ErrorMessage errorMessage  = new ErrorMessage();
        if(CommonUtil.isEmpty(busiRegeisterModel.getPhone())){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }

        if(!busiRegeisterModel.getPhone().matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("recommendPhone","BC0017","手机号");
            return errorMessage;
        }



        if(CommonUtil.isEmpty(busiRegeisterModel.getSmsCode())){
            errorMessage.rejectNull("smsCode","BC0017","短信验证码");
            return errorMessage;
        }

        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------

        if(busiRegeisterModel.getSmsCode().equals(CustomerConstant.commonSmsCode)){
            return null;
        }

        Object code = redisManager.getValue(busiRegeisterModel.getPhone()+CustomerConstant.userLoginSufixKey);
        if(CommonUtil.isEmpty(code)){
            errorMessage.rejectError("smsCode","BC0029","");
            return errorMessage;
        }

        if(!code.toString().equals(busiRegeisterModel.getSmsCode())){
            errorMessage.rejectError("smsCode","BC0053","");
            return errorMessage;
        }


        //TODO 获取gameId
        if(CommonUtil.isNotEmpty(busiRegeisterModel.getGameKey())){
            Map<String,Object> dataMap = (Map<String,Object>)redisManager.getValue(busiRegeisterModel.getGameKey());
            boolean gameInfoOk = true;
            if(dataMap != null){
                String gameId = dataMap.getOrDefault("gameId","").toString();
                if(CommonUtil.isNotEmpty(gameId)){
                    BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByGameId(Integer.parseInt(gameId));
                    if(busiCustomerPojo != null){
                        errorMessage.rejectErrorMessage("gameId","游戏信息已绑定!","游戏信息已绑定!");
                        return errorMessage;
                    }
                    busiRegeisterModel.setGameId(gameId.isEmpty()?null:Integer.parseInt(gameId));
                }else{
                    gameInfoOk = false;
                }

                String pGameId = dataMap.getOrDefault("pGameId","").toString();
                if(CommonUtil.isNotEmpty(pGameId))
                    busiRegeisterModel.setpGameId(Integer.parseInt(pGameId));
            }else{
                gameInfoOk = false;
            }

            if(!gameInfoOk){
                errorMessage.rejectErrorMessage("gameId","游戏信息异常!","游戏信息异常!");
                return errorMessage;
            }
        }

        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------
        return null;
    }
}
