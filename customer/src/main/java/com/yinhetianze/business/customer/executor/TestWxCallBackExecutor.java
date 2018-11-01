package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerWechatBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.common.business.wx.service.WxService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 获取微信授权以及平台绑定用户结果
 */

@Component
public class TestWxCallBackExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private WxService wxService;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;

    @Autowired
    private CustomerWechatBusiService customerWechatBusiServiceImpl;


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;
    /*
       state  1 已成功绑定 允许登陆   2 首次微信授权   3 微信授权失败 4 系统有记录但未绑定用户
     */
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object> param = new HashMap<String,Object>();
        String code = params[0].toString();
        //String openId = "oCmxG0n573B6v3nUOrVFytYyQgsQ";
        String openId = "oCmxG0n573B6v3nUOrVFytYyQgsS";
        try{
            WxMpUser wxMpUser = new WxMpUser();
            wxMpUser.setOpenId(openId);
            //WxMpUser wxMpUser =  wxService.getWxUserInfoByCode(code);
            //if(wxMpUser == null){
            //    param.put("state","3");//微信授权失败
            //    return param;
            //}
            BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByOpenId(wxMpUser.getOpenId());
            if(busiCustomerWechatPojo == null){//平台未绑定 返回需要注册状态
                busiCustomerWechatPojo = new BusiCustomerWechatPojo();
                busiCustomerWechatPojo.setOpenId(wxMpUser.getOpenId());
                String customerCode = UUID.randomUUID().toString();
                busiCustomerWechatPojo.setCustomerCode(customerCode);
                customerWechatBusiServiceImpl.insertSelective(busiCustomerWechatPojo);


                //注册业务
                BusiRegeisterModel busiRegeisterModel = new BusiRegeisterModel();
                busiRegeisterModel.setPhone("18670732021");
                busiRegeisterModel.setCustomerCode(customerCode);
                int reuslt = customerWechatBusiServiceImpl.wxRegeister(busiRegeisterModel);

                param.put("customerCode",customerCode);
                param.put("state","2");

            }else if(busiCustomerWechatPojo.getIsRegeister() == 0 ){
                //绑定业务
                param.put("customerCode", busiCustomerWechatPojo.getCustomerCode());
                param.put("state","4");


                BusiRegeisterModel busiRegeisterModel = new BusiRegeisterModel();
                busiRegeisterModel.setPhone("18670732021");
                busiRegeisterModel.setCustomerCode(busiCustomerWechatPojo.getCustomerCode());

                BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectByPhone(busiRegeisterModel.getPhone());
                if(busiCustomerPojo == null)
                    throw new BusinessException("BC007", ResponseConstant.RESULT_DESCRIPTION_FAILED);


                BusiCustomerWechatPojo dbPojo = customerWechatInfoServiceImpl.selectByCustomerCode(busiRegeisterModel.getCustomerCode());
                if(busiCustomerWechatPojo == null)
                    throw new BusinessException("",ResponseConstant.RESULT_DESCRIPTION_FAILED);

                dbPojo.setCustomerId(busiCustomerPojo.getId());
                int result = customerWechatBusiServiceImpl.updateByPrimaryKeySelective(dbPojo);


            }else{//已在平台绑定 返回登录成功状态!
                //登录
                param.put("customerCode", busiCustomerWechatPojo.getCustomerCode());
                param.put("state","1");

                BusiCustomerModel busiCustomerModel = new BusiCustomerModel();
                busiCustomerModel.setId(busiCustomerWechatPojo.getCustomerId());
                param.put("userInfo",customerInfoServiceImpl.login(busiCustomerModel));
            }
            return param;
        }catch(Exception e){
            LoggerUtil.error(WechatLoginExecutor.class,"微信登录异常! :"+e.getMessage());
            throw new BusinessException();
        }
    }
    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        return null;
    }
}
