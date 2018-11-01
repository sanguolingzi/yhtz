package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费者/会员 刷新用户的推荐二维码
 */

@Component
public class RefreshUserQrcodeExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerBusiService customerBusiServiceImpl;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        Map<String,Object> paraMap = new HashMap<>();
        BusiCustomerModel busiCustomerModel = (BusiCustomerModel)model;

        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(busiCustomerModel.getId());
        if(busiCustomerPojo ==null){
            paraMap.put("status","2");//error 用户不存在
            return paraMap;
        }


        if(CommonUtil.isEmpty(busiCustomerPojo.getGameId())){
            paraMap.put("status","5");//用户未绑定gameId
            return paraMap;
        }

        /*
        if(busiCustomerPojo.getRecommendCode()!=null
                && busiCustomerPojo.getRecommendCode().equals(busiCustomerModel.getRecommendCode())){
            paraMap.put("status","3");//推荐码没有变化 给与提示
            return paraMap;
        }

        BusiCustomerPojo t = customerInfoServiceImpl.selectByRecommendCode(busiCustomerModel.getRecommendCode());
        if(t != null){
            paraMap.put("status","4");//推荐码已存在
            return paraMap;
        }
        */


        BusiCustomerPojo temp = new BusiCustomerPojo();

        //temp.setRecommendCode(busiCustomerModel.getRecommendCode());
        temp.setGameId(busiCustomerPojo.getGameId());
        customerBusiServiceImpl.createQrcode(temp);
        if(temp.getPersonQrcode() == null){
            paraMap.put("status","0");//二维码生成失败
            return paraMap;
        }

        temp.setId(busiCustomerPojo.getId());
        int result = customerBusiServiceImpl.updateByPrimaryKeySelective(temp);
        paraMap.put("status","1");//success
        return paraMap;
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

        if(CommonUtil.isEmpty(busiCustomerModel.getId())){
            errorMessage.rejectNull("id",null,"id");
            return errorMessage;
        }

        /*
        if(CommonUtil.isEmpty(busiCustomerModel.getRecommendCode())){
            errorMessage.rejectNull("recommendCode",null,"recommendCode");
            return errorMessage;
        }

        if(busiCustomerModel.getRecommendCode().length() != 6){
            errorMessage.rejectErrorMessage("recommendCode","推荐码长度不等于6位","推荐码长度不等于6位");
            return errorMessage;
        }

        if(!busiCustomerModel.getRecommendCode().matches(CommonConstant.DIGITAL_REGEX)){
            errorMessage.rejectErrorMessage("recommendCode",null,"推荐码只能是纯数字");
            return errorMessage;
        }
        */
        //这里后台操作用户修改 需要传递指定参数才设置id
        if(CommonUtil.isEmpty(busiCustomerModel.getModelName())
                || !"backSendRequest".equals(busiCustomerModel.getModelName())){
            errorMessage.rejectNull("info",null,"提交参数异常");
            return errorMessage;
        }

        return null;
    }
}
