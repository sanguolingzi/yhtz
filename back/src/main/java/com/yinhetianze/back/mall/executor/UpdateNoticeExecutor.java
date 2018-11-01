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
 * 修改商城通知
 */

@Component
public class UpdateNoticeExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private NoticeBusiService noticeBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        NoticePojo noticePojo = new NoticePojo();
        BeanUtils.copyProperties(model,noticePojo);
        int result = noticeBusiServiceImpl.updateByPrimaryKeySelective(noticePojo);
        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


   @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
       NoticeModel noticeModel = (NoticeModel)model;
        ErrorMessage errorMessage = new ErrorMessage();

       if(noticeModel.getId() == null){
           errorMessage.rejectNull("id",null,"id");
           return errorMessage;
       }
       return errorMessage;
    }
}
