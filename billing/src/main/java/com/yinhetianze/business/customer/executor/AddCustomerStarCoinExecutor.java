package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerStarCoinInfoModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 消费者/会员 添加 摘星记录
 */

@Component
public class AddCustomerStarCoinExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel =(BusiCustomerStarCoinInfoModel)model;
        customerBankrollBusiServiceImpl.addStarCoin(busiCustomerStarCoinInfoModel);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel =(BusiCustomerStarCoinInfoModel)model;
        if(CommonUtil.isEmpty(busiCustomerStarCoinInfoModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(busiCustomerStarCoinInfoModel.getToken());
        busiCustomerStarCoinInfoModel.setCustomerId(tokenUser.getId());

        BusiCustomerBankrollPojo busiCustomerBankrollPojo =
                customerBankrollInfoServiceImpl.selectByCustomerId(busiCustomerStarCoinInfoModel.getCustomerId());
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();

        //查询当天是否有 摘星消耗的积分支出流水
        busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.EXPENSE.getValue());
        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.INTEGRALTOSTARCOIN.getValue());
        busiCustomerBankrollFlowModel.setStartTime(LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        busiCustomerBankrollFlowModel.setEndTime(LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        busiCustomerBankrollFlowModel.setBankrollId(busiCustomerBankrollPojo.getId());
        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.INTEGRAL.getValue());
        List<BusiCustomerBankrollFlowModel> busiCustomerBankrollFlowModelList =
                customerBankrollFlowInfoServiceImpl.selectList(busiCustomerBankrollFlowModel);

        if(!busiCustomerBankrollFlowModelList.isEmpty()){
            errorMessage.rejectErrorMessage("info","今日已摘星","今日已摘星");
            return errorMessage;
        }
        return errorMessage;
    }

}
