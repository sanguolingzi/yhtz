package com.yinhetianze.business.product.service;

import com.yinhetianze.pojo.product.ProductDetailPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ProductDetailBusiService{

    @Transactional
    int addProductDetail(List<ProductDetailPojo> pojoList, ProductPojo prodPojo);

    /**
     * 更新商品详情商品单品信息
     *
     * @param detailList 新增的商品单品信息
     * @param prodPojo   商品基本信息
     * @param delPojo    删除原有详情参数参数
     * @return
     */
    @Transactional
    int modifyProductDetail(List<ProductDetailPojo> detailList, ProductPojo prodPojo, ProductDetailPojo delPojo);

    /**
     * 更新商品库存
     *
     * @param map
     * @return
     */
    @Transactional
    int updatePDStorage(Map<String,Object> map);

    int updateSkuPojo(ProductDetailPojo detailPojo);

    int deleteProductSpec(ProductDetailPojo paramProductDetailPojo);
  /**
     * 审核中修改商品规格价格
     * @param productDetailPojo
     * @return
     */
    int updateProductDetailAuditing(ProductDetailPojo productDetailPojo);


    /**
     * 店铺增加商品规格
     * @param detailPojoList
     * @param prodPojo
     * @return
     */
    int addShopProductDetail(List<ProductDetailPojo> detailPojoList, ProductPojo prodPojo);
}