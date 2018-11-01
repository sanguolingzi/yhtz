package com.yinhetianze.business.voucher.mapper.busi;

import com.yinhetianze.mybatis.mapper.BusiMapper;
import com.yinhetianze.pojo.product.VoucherlPojo;

import java.util.Map;

public interface VoucherBusiMapper extends BusiMapper<VoucherlPojo> {

    int updateStatus(Map<String,Object> map);
}