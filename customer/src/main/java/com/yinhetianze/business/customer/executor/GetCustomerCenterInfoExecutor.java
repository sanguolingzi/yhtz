package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerCenterModel;
import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerWechatInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消费者/会员  个人中心
 */

@Component
public class GetCustomerCenterInfoExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private CustomerWechatInfoService customerWechatInfoServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCenterModel busiCustomerCenterModel= (BusiCustomerCenterModel)model;
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(busiCustomerCenterModel.getCustomerId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(busiCustomerPojo != null){
            //busiCustomerCenterModel.setCustomerId(busiCustomerPojo.getId());

            //TODO 需要兼容 app/ios 端
            String photoUrl = busiCustomerPojo.getPhotoUrl();
            if(CommonUtil.isEmpty(photoUrl)){
                BusiCustomerWechatPojo busiCustomerWechatPojo = customerWechatInfoServiceImpl.selectByCustomerId(busiCustomerCenterModel.getCustomerId(),(short)1);
                if(busiCustomerWechatPojo!=null && busiCustomerWechatPojo.getHeadImgUrl() !=null){
                    photoUrl = busiCustomerWechatPojo.getHeadImgUrl();
                }
            }
            busiCustomerCenterModel.setPhotoUrl(photoUrl);
            busiCustomerCenterModel.setNickName(busiCustomerPojo.getNickName());
            busiCustomerCenterModel.setAccount(busiCustomerPojo.getAccount());
            busiCustomerCenterModel.setIsMember(busiCustomerPojo.getIsMember());

            BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl
                    .selectBankrollInfo(busiCustomerPojo.getId());
            busiCustomerCenterModel.setAmount(busiCustomerBankrollPojo.getAmount());
            busiCustomerCenterModel.setStarCoin(busiCustomerBankrollPojo.getStarCoin());
            busiCustomerCenterModel.setRewardAmount(busiCustomerBankrollPojo.getRewardAmount());

            BusiCustomerCollectorModel busiCustomerCollectorModel = new BusiCustomerCollectorModel();
            busiCustomerCollectorModel.setCustomerId(busiCustomerPojo.getId());
            busiCustomerCollectorModel.setcType((short)0);
            Integer prodCollectorNum = customerCollectorInfoServiceImpl.selectCount(busiCustomerCollectorModel);

            busiCustomerCollectorModel.setcType((short)1);
            Integer shopCollectorNum = customerCollectorInfoServiceImpl.selectCount(busiCustomerCollectorModel);

            busiCustomerCenterModel.setProdCollectNum(prodCollectorNum);
            busiCustomerCenterModel.setShopCollectNum(shopCollectorNum);
            busiCustomerCenterModel.setCollectNum(shopCollectorNum+prodCollectorNum);

            if(CommonUtil.isNotNull(busiCustomerPojo.getPhone())){
                busiCustomerCenterModel.setSetPhone((short)1);
            }else{
                busiCustomerCenterModel.setSetPhone((short)0);
            }
            busiCustomerCenterModel.setRecommendCode(busiCustomerPojo.getRecommendCode());

            busiCustomerCenterModel.setIsMember(busiCustomerPojo.getIsMember());
            busiCustomerCenterModel.setIsPartner(busiCustomerPojo.getIsPartner());

        }
        return busiCustomerCenterModel;
    }

    public ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();

        BusiCustomerCenterModel busiCustomerCenterModel= (BusiCustomerCenterModel)model;
        if(busiCustomerCenterModel.getToken() == null){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerCenterModel.getToken());
        busiCustomerCenterModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }

}
