package com.yinhetianze.business.expressCode.mapper;

import com.yinhetianze.mybatis.mapper.InfoMapper;
import com.yinhetianze.pojo.order.ExpressCodePojo;

import java.util.List;

public interface ExpressCodeInfoMapper extends InfoMapper<ExpressCodePojo> {

    List<ExpressCodePojo> findAll();
}