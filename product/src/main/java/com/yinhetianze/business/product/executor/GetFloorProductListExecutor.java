package com.yinhetianze.business.product.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.service.FloorProductInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询楼层商品
 */
//TODO 业务未拆分
@Service
public class GetFloorProductListExecutor extends AbstractRestBusiExecutor
{
    @Autowired
    private FloorProductInfoService floorProductInfoServiceImpl;

    @Override
    protected Map<String, Object> executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException
    {
        Map<String,Object>map=new HashMap<String,Object>();
        ProductModel productModel = (ProductModel)model;
        PageHelper.startPage(productModel.getCurrentPage(), productModel.getPageSize());
        List<Map> list = floorProductInfoServiceImpl.selectProductFromFloor(Integer.parseInt(productModel.getFloorId()));
        PageInfo<Map> pageInfo = new PageInfo<>(list);
        PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
        map.put("pageData",pageData);
        List<Map> floorImg=floorProductInfoServiceImpl.selectFloorImg(Integer.parseInt(productModel.getFloorId()));
        map.put("floorImg",floorImg);
        return map;
    }
}
