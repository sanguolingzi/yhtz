package com.yinhetianze.business.voucher.service.busi;

import com.yinhetianze.pojo.product.VoucherlPojo;

import java.util.Map;

public interface VoucherBusiService
{
    int add(VoucherlPojo voucherlPojo);

    int updateStatus(Map<String,Object> map);
}