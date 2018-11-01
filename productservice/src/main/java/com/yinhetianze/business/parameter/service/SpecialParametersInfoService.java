package com.yinhetianze.business.parameter.service;

import com.yinhetianze.pojo.product.SpecialParametersPojo;

import java.util.List;

public interface SpecialParametersInfoService
{
    Object getProductParameterList(SpecialParametersPojo pojo);

    SpecialParametersPojo findProductParameter(SpecialParametersPojo paramPojo);

    List<SpecialParametersPojo> findListProductParameter(SpecialParametersPojo paramPojo);
}