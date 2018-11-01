package com.yinhetianze.back.thirdpart.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 查询系统父级菜单  parent = -1
 */

@Component
public class GetUserBindExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setGameId(gameBusinessModel.getGameId());
        //查询该gameId在平台是否是首次购买登录
        BusiCustomerPojo  customerPojo=customerInfoServiceImpl.selectOne(busiCustomerPojo);
        Map<String,Object> userInfo=null;
        //如果不为空直接登录
        if(CommonUtil.isNotEmpty(customerPojo)){
            BusiCustomerModel busiCustomerModel=new BusiCustomerModel();
            busiCustomerModel.setId(customerPojo.getId());
            busiCustomerModel.setCheckPassword(false);
            userInfo= customerInfoServiceImpl.login(busiCustomerModel);
        //如果为空则进行用户关联创建平台商城用户(凭gameId进行自动注册功能)
        }else{
            //获取注册model
            BusiRegeisterModel busiRegeisterModel =new BusiRegeisterModel();
            busiRegeisterModel.setGameId(gameBusinessModel.getGameId());
            Integer customerId = customerBusiServiceImpl.addRegeisterCustomer(busiRegeisterModel);
            //登录操作
            BusiCustomerModel busiCustomerModel=new BusiCustomerModel();
            busiCustomerModel.setId(customerId);
            userInfo= customerInfoServiceImpl.login(busiCustomerModel);
            //调第三方 返回商城customerId gameId
            //获取当当前时间戳
            String timestamp= String.valueOf(System.currentTimeMillis());
            //获取当前需请求接口的渠道编码
            String  channelCode=gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("gameId", gameBusinessModel.getGameId());
            userMap.put("customerId",customerId);
            userMap.put("timestamp", timestamp);
            userMap.put("channelCode", channelCode);
            userMap.put("channelSecret", channelSecret);
            List<String> keys=new ArrayList<String>(userMap.keySet());
            Collections.sort(keys);
            String stringSignTemp="";
            for(int i=0;i<keys.size();i++){
                if(i!=keys.size()-1){
                    stringSignTemp=stringSignTemp+keys.get(i)+"="+userMap.get(keys.get(i))+"&";
                }else{
                    stringSignTemp=stringSignTemp+keys.get(i)+"="+userMap.get(keys.get(i));
                }
                //stringSignTemp=keys.get(0)+"="+userMap.get(keys.get(0))+"&"+keys.get(1)+"="+userMap.get(keys.get(1))+"&"+keys.get(2)+"="+userMap.get(keys.get(2));
            }
            String sign=MD5CoderUtil.encode(stringSignTemp).toUpperCase();
            try {
                //封装请求第三方参数
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("sign", sign);
                paramsMap.put("gameId", gameBusinessModel.getGameId());
                paramsMap.put("customerId",customerId);
                paramsMap.put("timestamp", timestamp);
                String  gameString= HttpClientUtil.doPost("http://yqwx.gaoqi99.cn/api/user/bind", paramsMap, null);
                // 解析返回信息字符串，转成map
                Map<String, Object> resultMap = CommonUtil.readFromString(gameString, HashMap.class);
                if(CommonUtil.isNotEmpty(resultMap) && "0".equals(resultMap.get("code").toString())){
                    return userInfo;
                }
                System.err.println(resultMap.get("data"));
            } catch (Exception e) {
                e.printStackTrace();
                LoggerUtil.error(GetUserBindExecutor.class,e.toString());
            }
        }
        return userInfo;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameBusinessModel gameBusinessModel = (GameBusinessModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(gameBusinessModel.getGameId())){
            errorMessage.rejectNull("gameId",null,"用户ID");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getChannelCode())){
            errorMessage.rejectNull("channelCode",null,"渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameBusinessModel.getTimestamp())){
            errorMessage.rejectNull("timestamp",null,"时间戳");
            return errorMessage;
        }
        if(CommonUtil.isNull(gameBusinessModel.getSign())){
            errorMessage.rejectNull("sign",null,"签名参数");
            return errorMessage;
        }else{
            String  channelCode=gameBusinessModel.getChannelCode();
            //获取当前请求接口端的渠道秘钥
            CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
            HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
            Map map = channelInfo.get(channelCode);
            String channelSecret = map.get("channelSecret").toString();
            String checkSignString="channelCode="+channelCode+"&channelSecret="+channelSecret+"&gameId="+gameBusinessModel.getGameId()+"&timestamp="+gameBusinessModel.getTimestamp();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(gameBusinessModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名错误");
                return errorMessage;
            }
    }

        return errorMessage;
    }
}
