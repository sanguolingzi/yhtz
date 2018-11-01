package com.yinhetianze.business.logistics.excutor;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yinhetianze.business.logistics.service.busi.LogisticsInformationBusiService;
import com.yinhetianze.business.logistics.service.info.LogisticsInformationInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.TemplateBusinessExecutor;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.order.LogisticsInformationPojo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 快递鸟推送信息接收接口

 */
@Service
public  class PushReceiveExchange extends TemplateBusinessExecutor<String>{

    @Autowired
    private LogisticsInformationInfoService logisticsInformationInfoServiceImpl;

    @Autowired
    private LogisticsInformationBusiService logisticsInformationBusiServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    /*//快递鸟appId
    private String EBusinessID="1362415";*/
    @Override
    protected String handleBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
            String returnMessage;
        try {
            String RequestData = request.getParameter("RequestData");
            parseJson(RequestData);
            returnMessage=returnMessageToKuaiDiNiao(response,true);
        } catch (Exception e) {
            returnMessage=returnMessageToKuaiDiNiao(response,false);//保证快递鸟公司能够接收到信息
            e.printStackTrace();
        }
        return  returnMessage;
    }


    /**
     * @param returnJson
     * 解析json数据
     * @throws JSONException
     */
    private void parseJson(String returnJson) throws JSONException, ParseException{
        org.json.JSONObject obj = new org.json.JSONObject(returnJson);
        JSONArray array = obj.getJSONArray("Data");// 存放着订阅的多个订单信息
        //推送时间转换
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < array.length(); i++) {
            org.json.JSONObject json = array.getJSONObject(i);
            String logisticCode = json.getString("LogisticCode");// 订单号
            String shipperCode = json.getString("ShipperCode");// 快递公司编号
            JSONArray traces = json.getJSONArray("Traces");
            //将接收到的信息按照公司的业务进行处理.....
            LogisticsInformationPojo LogisticsInformationPojo = new LogisticsInformationPojo();
            LogisticsInformationPojo.setLogisticeCode(logisticCode);
            LogisticsInformationPojo.setExpressCode(shipperCode);
            LogisticsInformationPojo pojo= logisticsInformationInfoServiceImpl.getLogisticsInformation(LogisticsInformationPojo);
            if(CommonUtil.isNotEmpty(pojo)){
                pojo.setPushLogistics(traces.toString());
                pojo.setPushTime(sdf.parse(obj.getString("PushTime")));
                //保存物流信息 修改物流表
                logisticsInformationBusiServiceImpl.updateLogisticsInformation(pojo);
            }


        }
    }
    /**
     * @param response
     * 返回信息给快递鸟公司
     */
    private String returnMessageToKuaiDiNiao(HttpServletResponse response,Boolean judge) {
            JSONObject obj = new JSONObject();
        try {
            String EBusinessID = sysPropertiesUtils.getStringValue("kdn_EBusinessID");
            //PrintWriter out = response.getWriter();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            obj.put("EBusinessID", EBusinessID);
            obj.put("UpdateTime", sdf.format(new Date()));
            obj.put("Success", judge);
            obj.put("Reason", "");
            /*out.write(obj.toString());
            out.flush();
            out.close();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    @Override
    protected String handleException(HttpServletRequest request, HttpServletResponse response, Exception e){
        return null;
    }

    @Override
    protected String handleErrorMessage(HttpServletRequest request, HttpServletResponse response, ErrorMessage errorMessage){
        return null;
    }
}
