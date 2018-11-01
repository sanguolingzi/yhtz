package com.yinhetianze.business.voucher.service.info;

import com.yinhetianze.business.voucher.model.VoucherlModel;
import com.yinhetianze.pojo.product.VoucherlPojo;

import java.util.List;

public interface VoucherInfoService
{

    VoucherlPojo selectOne(VoucherlPojo voucherlPojo);

    List<VoucherlPojo> getVoucherList(VoucherlPojo voucherlPojo);
}