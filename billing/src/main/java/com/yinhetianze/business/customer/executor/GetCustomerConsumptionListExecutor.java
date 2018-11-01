package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollModel;
import com.yinhetianze.business.customer.model.BusiCustomerConsumptionModel;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费者/会员 查询消费情况
 */

@Component
public class GetCustomerConsumptionListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;


    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {

        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiCustomerBankrollPojo == null){
            return null;
        }
        Map<String,Object> paraMap = new HashMap<String,Object>();
        paraMap.put("bankrollId",busiCustomerBankrollPojo.getId());
        paraMap.put("flowType",1);

        BusiCustomerConsumptionModel busiCustomerConsumptionModel = (BusiCustomerConsumptionModel)model;
        PageHelper.startPage(busiCustomerConsumptionModel.getCurrentPage(),busiCustomerConsumptionModel.getPageSize());
        List<Map<String,Object>> list  = customerBankrollFlowInfoServiceImpl.selectConsumption(paraMap);
        PageInfo pageInfo = new PageInfo(list);
        if(list==null||list.isEmpty()){
            return null;
        }
        List<BusiCustomerConsumptionModel> returnList = new ArrayList<>();
        for(Map<String,Object> m : list){

            busiCustomerConsumptionModel = (BusiCustomerConsumptionModel)model;
            busiCustomerConsumptionModel.setCreateTime(m.getOrDefault("createTime","").toString());
            busiCustomerConsumptionModel.setFlowNumber(m.getOrDefault("flowNumber","").toString());
            busiCustomerConsumptionModel.setFlowType((short)1);
            String content = m.getOrDefault("content","").toString();
            if(CommonUtil.isNotEmpty(content)){

                String[] arr = content.split(",");

                JSONArray jsonArray = new JSONArray();
                for(String temp :arr){
                    String[] tempArr = temp.split("@");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("amount",new BigDecimal(tempArr[0]).movePointLeft(2));
                    jsonObject.put("flowCategory",tempArr[1]);
                    jsonObject.put("flowDescription",tempArr[2]);

                    jsonArray.add(jsonObject);
                }
                busiCustomerConsumptionModel.setContent(jsonArray.toString());
            }
            returnList.add(busiCustomerConsumptionModel);
        }
        PageData<BusiCustomerConsumptionModel> pageData = new PageData(returnList,pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        BusiCustomerConsumptionModel busiCustomerConsumptionModel = (BusiCustomerConsumptionModel)model;
        ErrorMessage errorMessage  = new ErrorMessage();

       if(CommonUtil.isEmpty(busiCustomerConsumptionModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }

        return errorMessage;
    }

}
