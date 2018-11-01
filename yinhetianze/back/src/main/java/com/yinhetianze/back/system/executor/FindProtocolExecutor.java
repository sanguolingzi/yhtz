package com.yinhetianze.back.system.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.systemservice.system.model.ProtocolModel;
import com.yinhetianze.systemservice.system.service.info.ProtocolInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.ProtocolPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class FindProtocolExecutor extends AbstractRestBusiExecutor<Object> {
    @Autowired
    private ProtocolInfoService protocolInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ProtocolModel protocolModel=(ProtocolModel)model;
        PageHelper.startPage(protocolModel.getCurrentPage(),protocolModel.getPageSize());
        ProtocolPojo protocolPojo = new ProtocolPojo();
        protocolModel.setDelFlag((short)0);
        BeanUtils.copyProperties(protocolModel,protocolPojo);
        PageInfo pageInfo = new PageInfo(protocolInfoServiceImpl.selectList(protocolPojo));
        Map<String,Object> map=new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        return map;
    }
}
