package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerEaringModel;
import com.yinhetianze.business.customer.service.info.CustomerEarningsInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 获取我的市场 查询分级收益列表
 */

@Component
public class GetCustomerEaringListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerEarningsInfoService customerEarningsInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerEaringModel busiCustomerEaringModel = (BusiCustomerEaringModel)model;
        List<BusiCustomerEaringModel> earingList = new ArrayList<>();
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerEaringModel.getToken());
        if(tokenUser == null){
            return new PageData<>(earingList,new Long(0));
        }

        BusiCustomerPojo busiCustomerPojo = customerInfoServiceImpl.selectById(tokenUser.getId());
        Map<String,Object> map = new HashMap<>();
        map.put("customerId",busiCustomerPojo.getId());
        map.put("gameId",busiCustomerPojo.getGameId());
        if("1".equals(busiCustomerEaringModel.getSortColumn())){
            map.put("sortColumn","1");
        }
        map.put("orderType",busiCustomerEaringModel.getOrderType());
        map.put("type",busiCustomerEaringModel.getType());
        PageHelper.startPage(busiCustomerEaringModel.getCurrentPage(),busiCustomerEaringModel.getPageSize());
        earingList = customerEarningsInfoServiceImpl.selectEaringList(map);
        PageInfo pageInfo = new PageInfo(earingList);
        PageData<BusiCustomerEaringModel> pageData = new PageData(earingList,pageInfo.getTotal());
        return pageData;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerEaringModel busiCustomerEaringModel = (BusiCustomerEaringModel)model;
        if(CommonUtil.isEmpty(busiCustomerEaringModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return errorMessage;
        }
        if(CommonUtil.isEmpty(busiCustomerEaringModel.getType())){
            errorMessage.rejectNull("type",null,"type");
            return errorMessage;
        }

        if(!java.util.Arrays.asList(new String[]{"1","2","3"}).contains(busiCustomerEaringModel.getType())){
            errorMessage.rejectErrorMessage("type","type参数值不正确","type参数值不正确");
            return errorMessage;
        }
        return errorMessage;
    }

}
