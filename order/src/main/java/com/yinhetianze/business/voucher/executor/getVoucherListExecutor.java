package com.yinhetianze.business.voucher.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.settlement.model.SettlementModel;
import com.yinhetianze.business.voucher.model.VoucherlModel;
import com.yinhetianze.business.voucher.service.info.VoucherInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.VoucherlPojo;
import com.yinhetianze.pojo.unit.UnitPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 根据登录用户查询兑换券列表
 */
@Service
public class getVoucherListExecutor extends AbstractRestBusiExecutor<Object>{
    @Autowired
    private VoucherInfoService voucherInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException{
        VoucherlModel voucherlModel=(VoucherlModel)model;
        VoucherlPojo voucherlPojo = new VoucherlPojo();
        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(model.getToken());
        if (CommonUtil.isEmpty(tokenUser)) {
            throw new BusinessException("用户没有登录");
        }
        //校验用户信息
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(tokenUser.getId());
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if (CommonUtil.isEmpty(busiCustomerPojo)) {
            throw new BusinessException("没有获取到用户信息");
        }
        voucherlPojo.setCustId(tokenUser.getId());
        voucherlPojo.setDelFlag((short)0);
        PageHelper.startPage(voucherlModel.getCurrentPage(),voucherlModel.getPageSize());
        PageInfo pageInfo=new PageInfo(voucherInfoServiceImpl.getVoucherList(voucherlPojo));
        PageData pageData=new PageData(pageInfo.getList(),pageInfo.getTotal());
        return pageData;
    }
}