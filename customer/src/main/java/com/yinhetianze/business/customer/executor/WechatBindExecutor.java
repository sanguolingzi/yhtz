package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiWechatModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.common.business.wx.service.AppWxService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 微信绑定登陆用户
 */

@Component
public class WechatBindExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private WxService wxService;

    @Autowired
    private AppWxService appWxService;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiWechatModel busiWechatModel =(BusiWechatModel)model;
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("result","failed");
        //拿到code 获取用户微信信息
        String code = busiWechatModel.getCode();
        WxMpUser wxMpUser = null;
        try{
            if(busiWechatModel.getIdType() == 1){
                wxMpUser =  wxService.getWxUserInfoByCode(code);
            }else if(busiWechatModel.getIdType() == 2){
                wxMpUser = appWxService.getAppWxMpUser(code);
            }
            if(wxMpUser == null){
                param.put("code","3");//微信授权失败
                return param;
            }
        }catch(Exception e){
            LoggerUtil.error(WechatBindExecutor.class,"微信授权异常! :"+e.getMessage());
            //throw new BusinessException("微信授权异常!");
            param.put("code","3");
            return param;
        }


        //拿到token 获取当前登录用户信息
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiWechatModel.getToken());

        //校验 openId 和 customerId 是否已有绑定信息
        BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByCustomerId(tokenUser.getId(),busiWechatModel.getIdType());
        if(busiCustomerWechatPojo != null){
            param.put("code","2");//用户已绑定
        }else{
            busiCustomerWechatPojo =  customerWechatInfoServiceImpl.selectByOpenId(wxMpUser.getOpenId());
            if(busiCustomerWechatPojo != null){
                //openId 已存在 并且已经绑定用户
                if(CommonUtil.isNotEmpty(busiCustomerWechatPojo.getCustomerId())){
                    param.put("code","1");
                    return param;
                }

                BusiCustomerWechatPojo temp = new BusiCustomerWechatPojo();
                temp.setId(busiCustomerWechatPojo.getId());
                temp.setCustomerId(tokenUser.getId());
                temp.setCustomerCode(MD5CoderUtil.encode(code));
                temp.setIsRegeister((short)1);
                customerWechatBusiServiceImpl.updateByPrimaryKeySelective(temp);
                param.put("customerCode",temp.getCustomerCode());

            }else{
                busiCustomerWechatPojo = new BusiCustomerWechatPojo();
                busiCustomerWechatPojo.setCustomerId(tokenUser.getId());
                busiCustomerWechatPojo.setOpenId(wxMpUser.getOpenId());
                busiCustomerWechatPojo.setIdType(busiWechatModel.getIdType());
                busiCustomerWechatPojo.setCustomerCode(MD5CoderUtil.encode(code));
                busiCustomerWechatPojo.setIsRegeister((short)1);

                    try{
                        busiCustomerWechatPojo.setSex(wxMpUser.getSexId()==null?(short)0:wxMpUser.getSexId().shortValue());
                        busiCustomerWechatPojo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                        busiCustomerWechatPojo.setNickName(wxMpUser.getNickname()!=null? URLEncoder.encode(wxMpUser.getNickname(),"utf-8"):null);
                    }catch (Exception e){

                        LoggerUtil.error(WechatBindExecutor.class,e.getMessage());
                        LoggerUtil.debug(WechatBindExecutor.class,"wxMpUser:"+wxMpUser.toString());
                    }

                try{
                    customerWechatBusiServiceImpl.insertSelective(busiCustomerWechatPojo);
                    param.put("customerCode",busiCustomerWechatPojo.getCustomerCode());
                }catch (Exception e){
                    if(e instanceof DuplicateKeyException){
                        LoggerUtil.error(WechatBindExecutor.class,"微信绑定openId重复异常! :"+e.getMessage()+" customerId:"+tokenUser.getId()+".....openId:"+wxMpUser.getOpenId()+"......idType:"+busiWechatModel.getIdType());
                        param.put("code","1");//微信已绑定
                        return param;
                    }
                    LoggerUtil.error(WechatBindExecutor.class,"微信绑定异常! :"+e.getMessage()+" customerId:"+tokenUser.getId()+".....openId:"+wxMpUser.getOpenId());
                    param.put("code","4");//绑定失败
                    return param;
                }
            }

            param.put("code","0");//绑定成功
            param.put("result","success");


            //处理用户头像 性别 昵称信息
            BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(busiCustomerWechatPojo.getCustomerId());
            customerBusiServiceImpl.handleUserInfo(busiCustomerPojo,busiCustomerWechatPojo);


        }
        return param;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        BusiWechatModel busiWechatModel =(BusiWechatModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        /*
        if(CommonUtil.isEmpty(busiWechatModel.getCode())){
            errorMessage.rejectNull("code",null,"code");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiWechatModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiWechatModel.getIdType())){
            errorMessage.rejectNull("idType",null,"idType");
            return errorMessage;
        }else if(busiWechatModel.getIdType() != 1 && busiWechatModel.getIdType() != 2){
            errorMessage.rejectErrorMessage("idType","idType数据异常","idType数据异常");
            return errorMessage;
        }
        */
        return null;
    }

}
