package com.yinhetianze.business.product.executor;

import com.yinhetianze.business.product.model.OneAreaModel;
import com.yinhetianze.business.product.service.OneAreaImgInfoService;
import com.yinhetianze.business.product.service.OneAreaInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.product.OneAreaImgPojo;
import com.yinhetianze.pojo.product.OneAreaPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetOneAreaDetailsExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private OneAreaImgInfoService oneAreaImgInfoServiceImpl;

    @Autowired
    private OneAreaInfoService oneAreaInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        Map<String,Object>oneAreaMap=new HashMap<String, Object>();
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        OneAreaPojo oneAreaPojo=new OneAreaPojo();
        oneAreaPojo.setId(oneAreaModel.getId());
        List<Map> oneAreaList=oneAreaInfoServiceImpl.selectOneAreaDetails(oneAreaPojo);
        oneAreaMap.put("oneArea",oneAreaList);
        if(oneAreaList.size()>0){
            OneAreaImgPojo oneAreaImgPojo=new OneAreaImgPojo();
            oneAreaImgPojo.setOneAreaId(oneAreaModel.getId());
            oneAreaImgPojo.setDelFlag((short)0);
            List<OneAreaImgPojo>oneAreaImgList=oneAreaImgInfoServiceImpl.selectOneAreaImgList(oneAreaImgPojo);
            oneAreaMap.put("oneAreaImgList",oneAreaImgList);
        }else{
            throw new BusinessException("该商品不存在");
        }
        return oneAreaMap;
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        OneAreaModel oneAreaModel=(OneAreaModel)model;
        ErrorMessage errorMessage=new ErrorMessage();
        if(CommonUtil.isEmpty(oneAreaModel.getId())){
            errorMessage.rejectNull("id","null","一元专区商品ID");
        }
        return errorMessage;
    }
}
