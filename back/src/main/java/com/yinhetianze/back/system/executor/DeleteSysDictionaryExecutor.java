package com.yinhetianze.back.system.executor;

import com.yinhetianze.systemservice.system.model.DeleteModel;
import com.yinhetianze.systemservice.system.service.busi.SysDictionaryBusiService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 删除信息
 */

@Component
public class DeleteSysDictionaryExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private SysDictionaryBusiService sysDictionaryBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel = (DeleteModel)model;
        int result = sysDictionaryBusiServiceImpl.deleteInfo(deleteModel.getIds());
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        DeleteModel deleteModel = (DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("ids",null,"ids");
            return errorMessage;
        }

        return errorMessage;
    }

}
