package com.yinhetianze.back.mall.executor;

import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.systemservice.mall.model.AdvertisementDetailModel;
import com.yinhetianze.systemservice.mall.service.busi.AdvertisementDetailBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AddAdvertisementDetailExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private AdvertisementDetailBusiService advertisementDetailBusiServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        int result = advertisementDetailBusiServiceImpl.addInfo( (AdvertisementDetailModel) model);
        if(result <= 0)
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        AdvertisementDetailModel advertisementDetailModel=(AdvertisementDetailModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(advertisementDetailModel.getAdvertisementDetailImg())){
            errorMessage.rejectNull("advertisementDetailImg","null","图片路径");
        }
        if(CommonUtil.isEmpty(advertisementDetailModel.getSort())){
            errorMessage.rejectNull("sort","null","排序");
        }
        if(CommonUtil.isEmpty(advertisementDetailModel.getIsShow())){
            errorMessage.rejectNull("isShow","null","是否显示");
        }
        if(CommonUtil.isEmpty(advertisementDetailModel.getAdvertisementId())){
            errorMessage.rejectNull("advertisementId","null","广告ID");
        }
        if(CommonUtil.isEmpty(advertisementDetailModel.getLinkMarkup())){
            errorMessage.rejectNull("linkMarkup","null","链接标记");
        }
        return errorMessage;
    }
}
