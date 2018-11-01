package com.yinhetianze.back.mall.executor;

import com.yinhetianze.systemservice.mall.model.NoticeModel;
import com.yinhetianze.systemservice.mall.service.busi.NoticeBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.NoticePojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增商城活动
 */
@Component
public class AddNoticeExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private NoticeBusiService noticeBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticePojo noticePojo = new NoticePojo();
        BeanUtils.copyProperties(model,noticePojo);
        int result = noticeBusiServiceImpl.insertSelective(noticePojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        NoticeModel noticeModel = (NoticeModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

        if(noticeModel.getTitle() == null){
            errorMessage.rejectError("title",null,"公告标题");
        }

        if(noticeModel.getContent() == null){
            errorMessage.rejectNull("content",null,"公告内容");
            return errorMessage;
        }
        return errorMessage;
    }
}
