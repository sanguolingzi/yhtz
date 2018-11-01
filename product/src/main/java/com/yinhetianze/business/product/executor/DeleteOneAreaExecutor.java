package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.OneAreaModel;
import com.yinhetianze.business.product.service.OneAreaBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteOneAreaExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OneAreaBusiService oneAreaBusiServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        OneAreaPojo oneAreaPojo=new OneAreaPojo();
        oneAreaPojo.setId(oneAreaModel.getId());
        oneAreaPojo.setDelFlag((short)1);
        int result=oneAreaBusiServiceImpl.updateByPrimaryKeySelective(oneAreaPojo);
        if (result<=0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(oneAreaModel.getId())){
            errorMessage.rejectNull("id",null, "id");
        }
        return errorMessage;
    }
}
