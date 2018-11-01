package com.yinhetianze.back.mall.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.pojo.back.NoticePojo;
import com.yinhetianze.systemservice.mall.model.NoticeModel;
import com.yinhetianze.systemservice.mall.service.info.NoticeInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询商城公告
 */

@Component
public class GetNoticeForManageExecutor extends AbstractRestBusiExecutor<Object>  {

    @Autowired
    private NoticeInfoService noticeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticeModel noticeModel = (NoticeModel)model;
        PageHelper.startPage(noticeModel.getCurrentPage(),noticeModel.getPageSize());
        NoticePojo noticePojo = new NoticePojo();
        noticeModel.setDelFlag((short)0);
        BeanUtils.copyProperties(noticeModel,noticePojo);
        PageInfo pageInfo = new PageInfo(noticeInfoServiceImpl.selectList(noticePojo));
        return pageInfo;
    }
}
