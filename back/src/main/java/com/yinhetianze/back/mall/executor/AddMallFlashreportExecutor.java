package com.yinhetianze.back.mall.executor;


import com.yinhetianze.systemservice.mall.model.MallFlashreportModel;
import com.yinhetianze.systemservice.mall.service.busi.MallFlashreportBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.pojo.back.BusiMallFlashreportPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增商城快报
 */
@Component
public class AddMallFlashreportExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private MallFlashreportBusiService mallFlashreportBusiServiceImpl;


    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiMallFlashreportPojo busiMallFlashreportPojo =new BusiMallFlashreportPojo();
        BeanUtils.copyProperties(model,busiMallFlashreportPojo);
        int result= mallFlashreportBusiServiceImpl.insertSelective(busiMallFlashreportPojo);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        MallFlashreportModel mallFlashreportModel=(MallFlashreportModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(mallFlashreportModel.getTitle()==null){
            errorMessage.rejectNull("title",null,"标题");
            return errorMessage;
        }
        if(mallFlashreportModel.getContent()==null){
            errorMessage.rejectNull("content",null,"内容");
        }
        return errorMessage;
    }
}
