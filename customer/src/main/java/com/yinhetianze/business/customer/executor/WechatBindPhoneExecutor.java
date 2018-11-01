package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 微信首次登录绑定手机号
 */

@Component
public class WechatBindPhoneExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel) model;
        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByPhone(busiRegeisterModel.getPhone());

        Map<String,Object> param = new HashMap<String,Object>();

        //账号不存在 且 推荐码不为空  则 注册新账号
        if(busiCustomerPojo == null && CommonUtil.isNotEmpty(busiRegeisterModel.getRecommendCode())){
            int customerId = customerBusiServiceImpl.addRegeisterCustomer(busiRegeisterModel);
            busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setId(customerId);
        //账号存在 并且  recommendCode 为 null 认为是绑定已有账号的操作
        }else if(busiCustomerPojo != null && CommonUtil.isEmpty(busiRegeisterModel.getRecommendCode())){
            //已存在号码 查看是否已绑定微信用户
            BusiCustomerWechatPojo busiCustomerWechatPojo = new BusiCustomerWechatPojo();
            busiCustomerWechatPojo.setCustomerId(busiCustomerPojo.getId());
            busiCustomerWechatPojo.setIdType(busiRegeisterModel.getIdType());
            busiCustomerWechatPojo = customerWechatInfoServiceImpl.select(busiCustomerWechatPojo);
            if(busiCustomerWechatPojo != null){
                param.put("msg","failed");
                param.put("code","0");//该号码已绑定其它微信!
                return param;
                //throw new BusinessException("该号码已绑定其它微信!");
            }
        }else{
            if(busiCustomerPojo != null){
                param.put("msg","failed");
                param.put("code","5");//用户执行注册逻辑 提示号码已存在!
                return param;
            }

            param.put("msg","failed");
            param.put("code","4");//参数组合不对导致 业务无法正确执行
            return param;
        }
        BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByCustomerCode(busiRegeisterModel.getCustomerCode());
        if(busiCustomerWechatPojo == null){
            param.put("msg","failed");
            param.put("code","1");//customerCode不存在
            return param;
            //throw new BusinessException("信息异常",ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        busiCustomerWechatPojo.setCustomerId(busiCustomerPojo.getId());
        busiCustomerWechatPojo.setIsRegeister((short)1);
        int result = customerWechatBusiServiceImpl.updateByPrimaryKeySelective(busiCustomerWechatPojo);
        if (result <= 0){
            param.put("msg","failed");
            param.put("code","2");//业务操作失败
            return param;
        }
        BusiCustomerModel busiCustomerModel = new BusiCustomerModel();
        busiCustomerModel.setId(busiCustomerWechatPojo.getCustomerId());
        busiCustomerModel.setCheckPassword(false);
        param.put("userInfo",customerInfoServiceImpl.login(busiCustomerModel));
        param.put("msg","success");
        param.put("code","3");

        redisManager.deleteValue(busiRegeisterModel.getPhone()+ CustomerConstant.wechatBindSufixKey);

        //处理用户头像 性别 昵称信息
        busiCustomerPojo = customerInfoServiceImpl.selectById(busiCustomerWechatPojo.getCustomerId());
        customerBusiServiceImpl.handleUserInfo(busiCustomerPojo,busiCustomerWechatPojo);

        return param;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel) model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(busiRegeisterModel.getPhone())){
            errorMessage.rejectNull("phone",null,"手机号码");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiRegeisterModel.getIdType())){
            errorMessage.rejectNull("idType",null,"请求类型idType");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiRegeisterModel.getCustomerCode())){
            errorMessage.rejectNull("customerCode",null,"customerCode");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiRegeisterModel.getSmsCode())){
            errorMessage.rejectNull("smsCode",null,"验证码");
            return errorMessage;
        }

        if(busiRegeisterModel.getSmsCode().equals(CustomerConstant.commonSmsCode)){
            return null;
        }

        Object code = redisManager.getValue(busiRegeisterModel.getPhone()+ CustomerConstant.wechatBindSufixKey);
        if(code == null){
            errorMessage.rejectError("smsCode","BC0029");
            return errorMessage;
        }else if(!code.toString().equals(busiRegeisterModel.getSmsCode())){
            errorMessage.rejectError("smsCode","BC0053");
            return errorMessage;
        }


        if(CommonUtil.isNotEmpty(busiRegeisterModel.getRecommendCode())){

            BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByRecommendCode(busiRegeisterModel.getRecommendCode());
            if(busiCustomerPojo == null){
                errorMessage.rejectErrorMessage("recommendCode","推荐码不存在","推荐码不存在");
                return errorMessage;
            }
            //TODO 用户身份是否为 会员 或者 合伙人
            if(busiCustomerPojo.getIsMember() == 1){
                errorMessage.rejectError("isMember","","推荐人不是会员");
                return errorMessage;
            }
        }

        return null;
    }

}
