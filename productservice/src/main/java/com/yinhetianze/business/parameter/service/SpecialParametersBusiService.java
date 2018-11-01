package com.yinhetianze.business.parameter.service;

import com.yinhetianze.pojo.product.SpecialParametersPojo;

public interface SpecialParametersBusiService
{
    int addProductParameter(SpecialParametersPojo pojo);

    int modifyProductParameter(SpecialParametersPojo paramPojo);

    int deleteProductParameter(SpecialParametersPojo paramPojo);
}