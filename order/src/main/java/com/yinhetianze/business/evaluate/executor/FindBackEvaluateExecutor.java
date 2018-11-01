package com.yinhetianze.business.evaluate.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateInfoService;
import com.yinhetianze.business.util.Util;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.order.EvaluatePojo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindBackEvaluateExecutor extends AbstractRestBusiExecutor {
    @Autowired
    private EvaluateInfoService evaluateInfoServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;
    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateModel evaluateModel=(EvaluateModel)model;
        Map<String, Object> result=new HashMap<String, Object>();

        List<Map<String,Object>> evaluList=new ArrayList<>();

        //查询评论
        if(CommonUtil.isEmpty(evaluateModel.getCurrentPage())){
            evaluateModel.setCurrentPage(1);
        }
        if(CommonUtil.isEmpty(evaluateModel.getPageSize())){
            evaluateModel.setPageSize(10);
        }
        Map<String,Object> parameter=new HashMap<>();
        if(CommonUtil.isNotEmpty(evaluateModel.getProductId())){
            parameter.put("productId",evaluateModel.getProductId());
        }
        if(CommonUtil.isNotEmpty(evaluateModel.getEvaluateId())){
            parameter.put("evaluateId",evaluateModel.getEvaluateId());
        }
        PageHelper.startPage(evaluateModel.getCurrentPage(),evaluateModel.getPageSize());
        List<EvaluateModel> list=evaluateInfoServiceImpl.findEvaluate(parameter);
        PageInfo<EvaluateModel> pageList = new PageInfo<>(list);
        try {
            if(CommonUtil.isNotEmpty(pageList.getList())){
                for(EvaluateModel evaluatePojo:pageList.getList()){
                    //用户信息
                    BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
                    busiCustomerPojo.setId(evaluatePojo.getEvaluateUser());
                    busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
                    if (CommonUtil.isEmpty(busiCustomerPojo)) {
                        throw  new BusinessException("评价人信息异常");
                    }
                    Map<String,Object> map= Util.transBean2Map(evaluatePojo);
                    map.put("userName",busiCustomerPojo.getNickName());
                    map.put("userImg",busiCustomerPojo.getPhotoUrl());
                    map.put("phone",busiCustomerPojo.getPhone());
                    evaluList.add(map);
                }
            }
            result.put("total",pageList.getTotal());
            result.put("evaluateList",evaluList);
        }catch (Exception e){
            LoggerUtil.error(FindBackEvaluateExecutor.class,e.getMessage());
            throw new BusinessException("后台查询评论失败");
        }
        return result;
    }


}
