package com.yinhetianze.business.product.service.impl;

import com.yinhetianze.business.product.mapper.ProductBusiMapper;
import com.yinhetianze.business.product.mapper.ProductInfoMapper;
import com.yinhetianze.business.product.service.ProductDetailBusiService;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.yinhetianze.business.product.mapper.ProductDetailBusiMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductDetailBusiServiceImpl implements ProductDetailBusiService
{
    @Autowired
    private ProductDetailBusiMapper mapper;

    @Autowired
    private ProductBusiMapper productBusiMapper;

    @Override
    public int addProductDetail(List<ProductDetailPojo> pojoList, ProductPojo prodPojo)
    {
        // 更新商品库存总数，校正数量storage，更新计算值salt
        /*int result = productBusiMapper.updateByPrimaryKey(prodPojo);
        if (result > 0)
        {
            LoggerUtil.info(ProductDetailBusiServiceImpl.class, "更新商品[{}]库存总量成功", new Object[]{prodPojo.getId()});
        }*/

        return mapper.addProductDetail(pojoList);
    }

    @Override
    public int modifyProductDetail(List<ProductDetailPojo> detailList, ProductPojo prodPojo, ProductDetailPojo delPojo)
    {
        // 逻辑删除之前所有的规格
        int result = mapper.modifyProductDetailByProdId(delPojo);
        if (result > 0)
        {
            LoggerUtil.info(ProductDetailBusiServiceImpl.class, "更新商品ID {} 对应的所有规格：删除所有商品下的原规格信息，总共 {} 条", new Object[]{prodPojo.getId(), result});
        }

        // 更新新的商品总量库存
        result = productBusiMapper.updateByPrimaryKey(prodPojo);
        if (result > 0)
        {
            LoggerUtil.info(ProductDetailBusiServiceImpl.class, "更新商品[{}]库存总量成功。更新后{}", new Object[]{prodPojo.getId(), prodPojo.getpStorage()});
        }

        /*if (1 == 1)
        {
            throw new RuntimeException("测试回滚");
        }*/

        // 添加新增的商品详情规格
        return mapper.addProductDetail(detailList);
    }

    @Override
    public int updatePDStorage(Map<String, Object> map) {
        int result=productBusiMapper.updateStorage(map);
        result=result+mapper.updatePDStorage(map);
        return result;
    }

    @Override
    public int updateSkuPojo(ProductDetailPojo detailPojo){
        return mapper.updateByPrimaryKeySelective(detailPojo);
    }

    @Override
    public int deleteProductSpec(ProductDetailPojo paramProductDetailPojo){
        return mapper.updateByPrimaryKeySelective(paramProductDetailPojo);
    }

    @Override
    public int updateProductDetailAuditing(ProductDetailPojo productDetailPojo) {
        return mapper.updateProductDetailAuditing(productDetailPojo);
    }    
	
	@Override
    public int addShopProductDetail(List<ProductDetailPojo> detailPojoList, ProductPojo prodPojo){
        return mapper.addShopProductDetail(detailPojoList);
    }}