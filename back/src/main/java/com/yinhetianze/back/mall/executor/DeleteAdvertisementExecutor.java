package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.back.AdvertisementPojo;
import com.yinhetianze.systemservice.mall.model.AdvertisementModel;
import com.yinhetianze.systemservice.mall.service.busi.AdvertisementBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DeleteAdvertisementExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private AdvertisementBusiService advertisementBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        AdvertisementModel advertisementModel=(AdvertisementModel)model;
        AdvertisementPojo advertisementPojo=new AdvertisementPojo();
        advertisementPojo.setId(advertisementModel.getId());
        advertisementPojo.setDelFlag((short)1);
        int result = advertisementBusiServiceImpl.updateByPrimaryKeySelective(advertisementPojo);
        if(result <= 0)
            throw new BusinessException("BC0049", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        AdvertisementModel advertisementModel=(AdvertisementModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(advertisementModel.getId())){
            errorMessage.rejectNull("id","null","广告ID");
        }
        return errorMessage;
    }


}
