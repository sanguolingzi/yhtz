package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiWechatModel;
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
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取微信授权以及平台绑定用户结果
 */

@Component
public class GetWechatAuthResultExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private WxService wxService;

    @Autowired
    private AppWxService appWxService;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;
    /*
       state  1 已成功绑定 允许登陆   2 需要绑定   3 微信授权失败
     */
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> param = new HashMap<String,Object>();
        BusiWechatModel busiWechatModel = (BusiWechatModel)model;
        String code = busiWechatModel.getCode();
        Short idType = busiWechatModel.getIdType();
        WxMpUser wxMpUser = null;
        try{
            if(idType == 1){
                wxMpUser =  wxService.getWxUserInfoByCode(code);
            }else if(idType == 2){
                wxMpUser = appWxService.getAppWxMpUser(code);
            }

            if(wxMpUser == null){
                param.put("state","3");//微信授权失败
                return param;
            }
        }catch(Exception e){
            LoggerUtil.error(GetWechatAuthResultExecutor.class,"微信授权异常! :"+e.getMessage());
            if(e.getMessage().indexOf("code been used") > 0){
                param.put("state","4");//code重复使用
            }else{
                param.put("state","3");//微信授权失败
            }
            return param;
            //throw new BusinessException("微信授权异常!");
        }
        BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByOpenId(wxMpUser.getOpenId());
        if(busiCustomerWechatPojo == null){//平台未绑定 返回需要注册状态
            busiCustomerWechatPojo = new BusiCustomerWechatPojo();
            busiCustomerWechatPojo.setOpenId(wxMpUser.getOpenId());
            String customerCode = MD5CoderUtil.encode(code);
            busiCustomerWechatPojo.setCustomerCode(customerCode);
            busiCustomerWechatPojo.setIdType(idType);
            try{
                busiCustomerWechatPojo.setSex(wxMpUser.getSexId()==null?(short)0:wxMpUser.getSexId().shortValue());
                busiCustomerWechatPojo.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                busiCustomerWechatPojo.setNickName(wxMpUser.getNickname()!=null?URLEncoder.encode(wxMpUser.getNickname(),"utf-8"):null);
            }catch (Exception e){
                LoggerUtil.error(GetWechatAuthResultExecutor.class,e.getMessage());
                LoggerUtil.debug(GetWechatAuthResultExecutor.class,"wxMpUser:"+wxMpUser.toString());
            }

            customerWechatBusiServiceImpl.insertSelective(busiCustomerWechatPojo);

            param.put("customerCode",customerCode);
            param.put("state","2");
            return param;
        }else if(busiCustomerWechatPojo.getIsRegeister() == 0 ){

            try{
                BusiCustomerWechatPojo temp = new BusiCustomerWechatPojo();
                temp.setId(busiCustomerWechatPojo.getId());
                boolean doUpdate = false;
                if(wxMpUser.getSexId()!=null){
                    if(busiCustomerWechatPojo.getSex() == null
                            ||(wxMpUser.getSexId().shortValue()!=busiCustomerWechatPojo.getSex().shortValue())){
                        temp.setSex(wxMpUser.getSexId().shortValue());
                        doUpdate = true;
                    }
                }

                if(wxMpUser.getHeadImgUrl()!=null){
                    if(busiCustomerWechatPojo.getHeadImgUrl() == null
                            || (!wxMpUser.getHeadImgUrl().equals(busiCustomerWechatPojo.getHeadImgUrl()))){
                        temp.setHeadImgUrl(wxMpUser.getHeadImgUrl());
                        doUpdate = true;
                    }
                }

                if(wxMpUser.getNickname()!=null){
                    if(busiCustomerWechatPojo.getNickName() == null
                            ||(!wxMpUser.getNickname().equals(busiCustomerWechatPojo.getNickName()))){
                        temp.setNickName(URLEncoder.encode(wxMpUser.getNickname(),"utf-8"));
                        doUpdate = true;
                    }
                }
                if(doUpdate){
                    customerWechatBusiServiceImpl.updateByPrimaryKeySelective(temp);
                }
            }catch (Exception e){
                LoggerUtil.error(GetWechatAuthResultExecutor.class,e.getMessage()+"  wxMpUser:"+wxMpUser.toString());
            }
            param.put("customerCode", busiCustomerWechatPojo.getCustomerCode());
            param.put("state","2");

            return param;
        }else{//已在平台绑定 返回登录成功状态!

            BusiCustomerModel busiCustomerModel = new BusiCustomerModel();
            busiCustomerModel.setId(busiCustomerWechatPojo.getCustomerId());
            busiCustomerModel.setCheckPassword(false);
            param.put("userInfo",customerInfoServiceImpl.login(busiCustomerModel));

            param.put("customerCode", busiCustomerWechatPojo.getCustomerCode());
            param.put("state","1");
        }
        return param;

    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {

        BusiWechatModel busiWechatModel = (BusiWechatModel)model;
        ErrorMessage errorMessage = new ErrorMessage();
        if(CommonUtil.isEmpty(busiWechatModel.getCode())){
            errorMessage.rejectNull("code",null,"code");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiWechatModel.getIdType())){
            errorMessage.rejectNull("idType",null,"idType");
            return errorMessage;
        }else if(busiWechatModel.getIdType() != 1 && busiWechatModel.getIdType() != 2){
            errorMessage.rejectErrorMessage("idType","idType数据异常","idType数据异常");
            return errorMessage;
        }
        return null;
    }
}