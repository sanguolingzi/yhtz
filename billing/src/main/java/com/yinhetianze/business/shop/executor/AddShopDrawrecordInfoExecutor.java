package com.yinhetianze.business.shop.executor;

import com.yinhetianze.business.shop.model.BusiShopDrawrecordModel;
import com.yinhetianze.business.shop.service.busi.ShopDrawrecordBusiService;
import com.yinhetianze.business.shop.service.info.ShopBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import com.yinhetianze.pojo.shop.BusiShopDrawrecordPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 新增店铺提现信息
 */

@Component
public class AddShopDrawrecordInfoExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private ShopDrawrecordBusiService shopDrawrecordBusiServiceImpl;

    @Autowired
    private ShopBankrollInfoService shopBankrollInfoServiceImpl;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        BusiShopDrawrecordModel busiShopDrawrecordModel = (BusiShopDrawrecordModel)model;

        //查询店铺账户余额
        BusiShopBankrollPojo busiShopBankrollPojo = shopBankrollInfoServiceImpl.selectByShopId(busiShopDrawrecordModel.getShopId());
        /**
         * 判断账户余额 和 提现申请额度
         */
        if(busiShopBankrollPojo.getGoodsAmount().movePointLeft(2).compareTo(busiShopDrawrecordModel.getDrawAmount())>=0){
            BusiShopDrawrecordPojo busiShopDrawrecordPojo  = new BusiShopDrawrecordPojo();
            BeanUtils.copyProperties(model,busiShopDrawrecordPojo);
            shopDrawrecordBusiServiceImpl.AddShopDrawrecordInfo(busiShopBankrollPojo,busiShopDrawrecordPojo);
            return "success";
        }
        throw new BusinessException("BC0051", ResponseConstant.RESULT_DESCRIPTION_FAILED);
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiShopDrawrecordModel busiShopDrawrecordModel = (BusiShopDrawrecordModel)model;

        if(busiShopDrawrecordModel.getShopId() == null){
            errorMessage.rejectNull("shopId",null,"提现申请店铺信息");
            return errorMessage;
        }

        if(busiShopDrawrecordModel.getDrawAmount() == null){
            errorMessage.rejectNull("drawAmount",null,"提现金额不能为空");
            return errorMessage;
        }

        if(busiShopDrawrecordModel.getDrawAmount().compareTo(new BigDecimal(0)) < 0){
            errorMessage.rejectError("drawAmount","BC0032","提现金额","提现金额");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopDrawrecordModel.getBankNumber())){
            errorMessage.rejectNull("bankNumber",null,"银行账号");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiShopDrawrecordModel.getReceiveUser())){
            errorMessage.rejectNull("receiveUser",null,"收款人");
            return errorMessage;
        }
        return errorMessage;
    }
}
