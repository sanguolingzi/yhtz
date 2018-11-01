package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城提供给游戏用户注册接口
 */

@Component
public class GetCustomerCheckExecutor extends AbstractRestBusiExecutor<Object>{

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setPhone(gameBusinessModel.getCustomerPhone());
        //查询该CustomerPhone在平台是否注册
        BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(CommonUtil.isEmpty(customerPojo)){
            throw new BusinessException("账号不存在");
        }
        //密码校验
        if(CommonUtil.isEmpty(customerPojo.getLoginPassword())){
            throw new BusinessException("用户未设置支付密码");
        }
        if(MD5CoderUtil.encode(MD5CoderUtil.encode(gameBusinessModel.getPassword())).equals(customerPojo.getLoginPassword())){
            //密码校验通过则返回改用户信息
            Map customer = new HashMap();
            if(CommonUtil.isEmpty(customerPojo.getGameId())){
                //封装返回信息给游戏端
                customer.put("customerId",customerPojo.getId());
                customer.put("nickName",customerPojo.getNickName());
                customer.put("sex",customerPojo.getSex());
                customer.put("headUrl","https://yhtz-20180425-private.oss-cn-hangzhou.aliyuncs.com/"+customerPojo.getPhotoUrl());
                return customer;
            }
            customer.put("gameId",customerPojo.getGameId());
            customer.put("customerId",customerPojo.getId());
            return customer;
        }else{
            throw new BusinessException("密码错误");
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        GameBusinessModel gameBusinessModel = (GameBusinessModel) model;
        ErrorMessage errorMessage = new ErrorMessage();

        if (CommonUtil.isEmpty(gameBusinessModel.getCustomerPhone())) {
            errorMessage.rejectNull("customerPhone", null, "登录账号");
            return errorMessage;
        }
        if (CommonUtil.isEmpty(gameBusinessModel.getPassword())) {
            errorMessage.rejectNull("password", null, "登录密码");
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
            //密码加密
            String pass= MD5CoderUtil.encode(gameBusinessModel.getPassword()+"yhtz");
            String checkSignString = "channelCode=" + channelCode + "&channelSecret=" + channelSecret + "&customerPhone=" + gameBusinessModel.getCustomerPhone()+ "&customerPhone=" + gameBusinessModel.getCustomerPhone()+ "&password=" + gameBusinessModel.getPassword();
            String checkSign = MD5CoderUtil.encode(checkSignString);
            if (!checkSign.equals(gameBusinessModel.getSign())) {
                errorMessage.rejectError("checkSign", "BC0057", "签名错误");
                return errorMessage;
            }
        }
        return errorMessage;
    }
}
