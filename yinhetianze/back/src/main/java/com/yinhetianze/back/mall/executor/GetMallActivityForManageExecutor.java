package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.MallActivityPojo;
import com.yinhetianze.systemservice.mall.model.MallActivityModel;
import com.yinhetianze.systemservice.mall.service.info.MallActivityInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询商城活动
 */

@Component
public class GetMallActivityForManageExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private MallActivityInfoService mallActivityInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        MallActivityModel mallActivityModel = (MallActivityModel)model;
        PageHelper.startPage(mallActivityModel.getCurrentPage(),mallActivityModel.getPageSize());
        MallActivityPojo mallActivityPojo = new MallActivityPojo();
        mallActivityPojo.setDelFlag((short)0);
        BeanUtils.copyProperties(mallActivityModel,mallActivityPojo);
        PageInfo pageInfo = new PageInfo(mallActivityInfoServiceImpl.selectList(mallActivityPojo));
        return pageInfo;
    }
}
