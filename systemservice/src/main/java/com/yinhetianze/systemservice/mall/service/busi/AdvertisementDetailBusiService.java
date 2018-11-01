package com.yinhetianze.systemservice.mall.service.busi;

import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementDetailModel;

public interface AdvertisementDetailBusiService
{
    int addAdvertisementDetail(AdvertisementDetailPojo advertisementDetailPojo);

    int updateByPrimaryKeySelective(AdvertisementDetailPojo advertisementDetailPojo);

    int addInfo(AdvertisementDetailModel advertisementDetailModel) throws BusinessException;

    int updateInfo(AdvertisementDetailModel advertisementDetailModel) throws BusinessException;
}