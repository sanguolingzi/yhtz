package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.AdvertisementDetailPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.AdvertisementDetailBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteAdvertisementDetailExecutor extends AbstractRestBusiExecutor {


    @Autowired
    private AdvertisementDetailBusiService advertisementDetailBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        AdvertisementDetailModel advertisementDetailModel=(AdvertisementDetailModel)model;
        AdvertisementDetailPojo advertisementDetailPojo=new AdvertisementDetailPojo();
        advertisementDetailPojo.setId(advertisementDetailModel.getId());
        advertisementDetailPojo.setDelFlag((short)1);
        int result = advertisementDetailBusiServiceImpl.updateByPrimaryKeySelective(advertisementDetailPojo);
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        AdvertisementDetailModel advertisementDetailModel=(AdvertisementDetailModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(advertisementDetailModel.getId())){
            errorMessage.rejectNull("id","null","id");
        }
        return errorMessage;
    }
}
