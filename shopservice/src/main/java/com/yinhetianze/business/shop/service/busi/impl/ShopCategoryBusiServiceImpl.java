package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.business.shop.model.ShopCategoryModel;
import com.yinhetianze.business.shop.service.busi.ShopCategoryBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.shop.mapper.busi.ShopCategoryBusiMapper;

@Service
public class ShopCategoryBusiServiceImpl implements ShopCategoryBusiService
{
    @Autowired
    private ShopCategoryBusiMapper mapper;

    @Override
    public int addShopCategory(ShopCategoryPojo shopCategoryPojo){
        return mapper.insertSelective(shopCategoryPojo);
    }

    @Override
    public int UpdateShopCategory(ShopCategoryPojo shopCategoryPojo){
        return mapper.updateByPrimaryKeySelective(shopCategoryPojo);
    }

    @Override
    public int updateInfo(ShopCategoryModel shopCategoryModel) throws BusinessException {
        try{
           JSONArray jsonArray =  CommonUtil.readFromString(shopCategoryModel.getJsonString(), JSONArray.class);
           ShopCategoryPojo shopCategoryPojo = new ShopCategoryPojo();
            shopCategoryPojo.setShopId(shopCategoryModel.getShopId());
           for(Object object : jsonArray){
              JSONObject jsonObject =  CommonUtil.readFromString(object.toString(), JSONObject.class);
              if(jsonObject == null
                      || jsonObject.get("id") == null
                      || jsonObject.getString("cateName") == null){
                  throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
              }
              shopCategoryPojo.setId(Integer.parseInt(jsonObject.get("id").toString()));
              shopCategoryPojo.setCateName(jsonObject.getString("cateName"));
              mapper.updateByCondition(shopCategoryPojo);
           }
        }catch (Exception e){
            if(e instanceof BusinessException)
                throw (BusinessException)e;
            LoggerUtil.error(ShopCategoryBusiServiceImpl.class,e.getMessage());
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        return 1;
    }

    @Override
    public int deleteInfo(ShopCategoryPojo shopCategoryPojo) {
        shopCategoryPojo.setDelFlag((short)1);
        return UpdateShopCategory(shopCategoryPojo);
    }
}