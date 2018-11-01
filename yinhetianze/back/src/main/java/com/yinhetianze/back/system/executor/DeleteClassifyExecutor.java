package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.ClassifyModel;
import com.yinhetianze.systemservice.system.service.busi.ClassifyBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.ClassifyPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteClassifyExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private ClassifyBusiService classifyBusiServiceImpl;
    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        ClassifyPojo classifyPojo=new ClassifyPojo();
        BeanUtils.copyProperties(model,classifyPojo);
        int result=classifyBusiServiceImpl.delete(classifyPojo);
        if(result!=1){
            throw new BusinessException("删除失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        ClassifyModel classifyModel=(ClassifyModel)model;
        if(CommonUtil.isEmpty(classifyModel.getId())){
            errorMessage.rejectNull("id",null,"分类ID");
        }
        return errorMessage;
    }
}
