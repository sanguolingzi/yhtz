package com.yinhetianze.back.system.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.system.model.DeleteModel;
import com.yinhetianze.systemservice.system.service.busi.SysMemberBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class DeleteParentSystemMemberExecutor extends AbstractRestBusiExecutor<String> {

    @Autowired
    private SysMemberBusiService sysMemberBusiServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        DeleteModel deleteModel=(DeleteModel)model;
        int result = sysMemberBusiServiceImpl.deleteParentSysMember(deleteModel.getIds());
        if(result <= 0){
            throw new BusinessException("删除失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage=new ErrorMessage();
        DeleteModel deleteModel=(DeleteModel)model;
        if(CommonUtil.isEmpty(deleteModel.getIds())){
            errorMessage.rejectNull("id",null,"id");
        }
        return errorMessage;
    }
}
