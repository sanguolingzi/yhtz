package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.SmsModel;
import com.yinhetianze.systemservice.system.service.info.SmsInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.SmsPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Service
public class FindAllSmsExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private SmsInfoService smsInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        SmsModel smsModel=(SmsModel)model;
        PageHelper.startPage(smsModel.getCurrentPage(),smsModel.getPageSize());
        SmsPojo smsPojo = new SmsPojo();
        BeanUtils.copyProperties(smsModel,smsPojo);
        PageInfo pageInfo = new PageInfo(smsInfoServiceImpl.findAllSms(smsPojo));
        Map<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        return map;
    }

}
