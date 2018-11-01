package com.yinhetianze.business.expressCode.service;

import com.yinhetianze.pojo.order.ExpressCodePojo;

import java.util.List;

public interface ExpressCodeInfoService
{
    List<ExpressCodePojo> findAll();

    ExpressCodePojo selectOne(ExpressCodePojo expressCodePojo);
}