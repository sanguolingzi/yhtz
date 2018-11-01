package com.yinhetianze.back.thirdpart.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.cachedata.CacheData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.security.custom.CustomChannelCheckFilter;
import com.yinhetianze.systemservice.system.model.SysBannerModel;
import com.yinhetianze.systemservice.system.model.SysMenuModel;
import com.yinhetianze.systemservice.system.service.info.SysMenuInfoService;
import com.yinhetianze.systemservice.thirdpart.model.GameRecordModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查询游戏用户充值记录接口（反向接口）
 */

@Component
public class GetRechargeExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;



    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        GameRecordModel gameRecordModel = (GameRecordModel)model;
        PageHelper.startPage(gameRecordModel.getCurrentPage(),gameRecordModel.getPageSize());
        PageInfo pageInfo = new PageInfo(gameRecordInfoServiceImpl.selectGameRecord(gameRecordModel));
        Map dataCheck =new HashMap();
        dataCheck.put("rechargeHis",pageInfo);
        if(pageInfo.getList().size()>0){
            dataCheck.put("dataCheck",0);
        }else{
            dataCheck.put("dataCheck",1);
        }
        return dataCheck;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        GameRecordModel gameRecordModel = (GameRecordModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(CommonUtil.isEmpty(gameRecordModel.getPageSize())){
            errorMessage.rejectNull("pageSize",null,"分页大小");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameRecordModel.getCurrentPage())){
            errorMessage.rejectNull("currentPage",null,"分页页数");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameRecordModel.getStartDate())){
            errorMessage.rejectNull("startDate",null,"开始时间");
            return errorMessage;
        }
        if(CommonUtil.isNull(gameRecordModel.getEndDate())){
            errorMessage.rejectNull("endDate",null,"结束时间");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameRecordModel.getSign())){
            errorMessage.rejectNull("sign",null,"签名");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameRecordModel.getChannelCode())){
            errorMessage.rejectNull("channelCode",null,"渠道编码");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(gameRecordModel.getStartDate()) && CommonUtil.isEmpty( gameRecordModel.getEndDate())){
            //结束时间，含当天，与开始时间间隔不大于30天
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = format.parse(gameRecordModel.getStartDate());
                Date endDate = format.parse(gameRecordModel.getEndDate());
                int day= (int) ((endDate.getTime()-startDate.getTime())/(1000*3600*24));
                if(day>30){
                    errorMessage.rejectError("date","BC0056","30");
                    return errorMessage;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                errorMessage.rejectError("date","BC0018","日期");
                return errorMessage;
            }
        }
        //签名参数校验
        CacheData<HashMap<String, Map<String, Object>>> sysChannelCacheData = (CacheData) ApplicationContextFactory.getBean("sysChannelCacheData");
        HashMap<String, Map<String, Object>> channelInfo = sysChannelCacheData.getCacheData();
        Map map = channelInfo.get(gameRecordModel.getChannelCode());
        String channelSecret = map.get("channelSecret").toString();
        if(CommonUtil.isNotNull(gameRecordModel.getGameId())){
            //拼接需要校验的参数
            String checkSignString="channelCode="+gameRecordModel.getChannelCode()+"&channelSecret="+channelSecret+
                    "&pageSize="+gameRecordModel.getPageSize()+"&currentPage="+gameRecordModel.getCurrentPage()+"&startDate="+gameRecordModel.getStartDate()+
                    "&endDate="+gameRecordModel.getEndDate()+"&gameId="+gameRecordModel.getGameId();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(gameRecordModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名失败");
                return errorMessage;
            }
        }else{
            //签名参数校验校验
            String checkSignString="channelCode="+gameRecordModel.getChannelCode()+"&channelSecret="+channelSecret+
                   "&pageSize="+gameRecordModel.getPageSize()+"&currentPage="+gameRecordModel.getCurrentPage()+"&startDate="+gameRecordModel.getStartDate()+
                    "&endDate="+gameRecordModel.getEndDate();
            String checkSign=MD5CoderUtil.encode(checkSignString);
            if(!checkSign.equals(gameRecordModel.getSign())){
                errorMessage.rejectError("checkSign","BC0057","签名失败");
                return errorMessage;
            }
        }
        return errorMessage;
    }
}
