package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员 修改账户合伙人状态
 */

@Component
public class UpdateCustomerPartnerExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        BeanUtils.copyProperties(model,busiCustomerPojo);

        BusiCustomerPojo dbPojo = customerInfoServiceImpl.selectById(busiCustomerPojo.getId());

        if(dbPojo == null){
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }

        if(CommonUtil.isEmpty(dbPojo.getGameId())){
            throw new BusinessException("未绑定游戏账号!");
        }

        int result = 0;
        if(busiCustomerPojo.getIsPartner() == 0){
            result = customerBusiServiceImpl.updatePartner(dbPojo.getId(),dbPojo.getGameId());
        }else if(busiCustomerPojo.getIsPartner() == 1){
            result = customerBusiServiceImpl.cancelPartner(dbPojo.getId(),dbPojo.getGameId());
        }

        if(result <= 0)
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        /**
         * 校验 token
         */
        if(CommonUtil.isEmpty(busiCustomerModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }


        //这里后台操作用户修改 需要传递指定参数才设置id
        if(CommonUtil.isEmpty(busiCustomerModel.getModelName())
            || !"backSendRequest".equals(busiCustomerModel.getModelName())){
            errorMessage.rejectErrorMessage("info","modelName不正确","modelName不正确");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getId())){
            errorMessage.rejectNull("id",null,"用户id");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerModel.getIsPartner())){
            errorMessage.rejectNull("isPartner",null,"isPartner");
            return errorMessage;
        }
        return errorMessage;
    }
}
