package com.yinhetianze.back.index.pc.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.pojo.back.NoticePojo;
import com.yinhetianze.systemservice.mall.service.info.NoticeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component
public class PcIndexNoticeExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private NoticeInfoService noticeInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        PageHelper.startPage(1,5);
        NoticePojo noticePojo = new NoticePojo();
        noticePojo.setDelFlag((short)0);
        List<Map> list  = noticeInfoServiceImpl.selectForMobileIdex(noticePojo);
        PageInfo<Map> pageInfo = new PageInfo(list);
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}
