package com.yinhetianze.business.product.controller;


import com.yinhetianze.business.product.model.*;
import com.yinhetianze.core.business.BusinessExecutor;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.utils.ApplicationContextFactory;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.springmvc.controller.RestfulController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("product")
public class ProductController extends RestfulController{
    /**
     * 查询商品详情
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "details", method = RequestMethod.GET)
    public ResponseData getProduct(HttpServletResponse response, HttpServletRequest request, ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询商品详情
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "detailsBackstage", method = RequestMethod.GET)
    public ResponseData getProductBackstage(HttpServletResponse response, HttpServletRequest request, ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductDetailsBackstageExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询商品详情
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "frontProductDetails", method = RequestMethod.GET)
    public ResponseData getFrontProductDetails(HttpServletResponse response, HttpServletRequest request, ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getFrontProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品列表
     *·
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseData getProductDetails(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "frontProductList", method = RequestMethod.GET)
    public ResponseData getFrontProductList(HttpServletRequest request, HttpServletResponse response, ProductPageModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getFrontProductListExecuto");
        return executor.execute(request, response, model);
    }


    /**
     * 查询楼层商品列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "floorProductList", method = RequestMethod.GET)
    public ResponseData getFloorProductList(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getFloorProductListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 刷新猜你喜欢缓存
     *
     * @param request
     * @param response
     * @param
     * @return
     */
    @RequestMapping(value = "refreshGuess", method = RequestMethod.GET)
    public ResponseData refreshGuessCacheExecutor(HttpServletRequest request, HttpServletResponse response, String refresh){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("refreshGuessCacheExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, null,refresh);
    }

    /**
     * 猜你喜欢
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "guess", method = RequestMethod.GET)
    public ResponseData guessProductList(HttpServletRequest request, HttpServletResponse response, ProductGuessModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("guessProductListExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 添加商品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData addProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 添加商品的单品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "info/add", method = RequestMethod.POST)
    public ResponseData addProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductInfoExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 添加商品详情
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "details/add", method = RequestMethod.POST)
    public ResponseData addProductDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品的单品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "info/modify", method = RequestMethod.POST)
    public ResponseData modifyProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyProductInfoExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品详情第一步
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "details/modify", method = RequestMethod.POST)
    public ResponseData modifyProductDetails(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public ResponseData modifyProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyProductExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除商品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseData deleteProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 更新商品拓展信息
     * 拓展信息包括：商品参数，商品介绍详情
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "extra/update", method = RequestMethod.POST)
    public ResponseData addProductExtra(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateProductExtraExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 更新商品库存
     *
     * @return
     */
    @RequestMapping(value = "info/updateStorage", method = RequestMethod.POST)
    public ResponseData updateStorage(HttpServletRequest request, HttpServletResponse response, ProductSkuModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateStorageExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 添加猜你喜欢的商品
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/insertProductGuess")
    public Object addProductGuess(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductGuessModel model){
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("addProductGuessExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }
    /**
     * 修改猜你喜欢的商品
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping(value = "/updateProductGuess")
    public Object updateProductGuess(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductGuessModel model){
        BusinessExecutor<String> executor = (BusinessExecutor<String>) ApplicationContextFactory.getBean("updateProductGuessExecutor");
        return CommonUtil.isNull(executor) ? null : executor.execute(request, response, model);
    }

    /**
     * 商品轮播图
     *
     * @return
     */
    @RequestMapping(value = "slideshowImage/add", method = RequestMethod.POST)
    public ResponseData addSlideshowImage(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addSlideshowImageExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品轮播图
     *
     * @return
     */
    @RequestMapping(value = "slideshowImage", method = RequestMethod.GET)
    public ResponseData getSlideshowImage(HttpServletRequest request, HttpServletResponse response, ProductImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getSlideshowImageExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 增加商品参数
     *
     * @return
     */
    @RequestMapping(value = "detailsParameter/add", method = RequestMethod.POST)
    public ResponseData addDetailsParameter(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addDetailsParameterExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品参数和商品信息
     *
     * @return
     */
    @RequestMapping(value = "updateProductDetails", method = RequestMethod.POST)
    public ResponseData updateProductDetails(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductDetailInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询猜你喜欢
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productGuessList", method = RequestMethod.GET)
    public ResponseData getProductGuessList(HttpServletRequest request, HttpServletResponse response, ProductGuessModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductGuessListExecuto");
        return executor.execute(request, response, model);
    }


    /**
     * 删除猜你喜欢
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteProductGuess", method = RequestMethod.POST)
    public ResponseData deleteProductGuess(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductGuessModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductGuessExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询猜你喜欢是否存在
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productGuess", method = RequestMethod.POST)
    public ResponseData getProductGuess(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductGuessModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductGuessExecuto");
        return executor.execute(request, response, model);
    }

    /**
     * 根据计量单位模糊查询商品计量
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "unitRelationList", method = RequestMethod.GET)
    public ResponseData getUnitRelationList(HttpServletRequest request, HttpServletResponse response, ProductUnitModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductUnitRelationListExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 根据计量单位查询商品计量
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "unitRelationName", method = RequestMethod.GET)
    public ResponseData getUnitRelationName(HttpServletRequest request, HttpServletResponse response, ProductUnitRelationPageModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductUnitNameExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 修改商品规格(单条修改)
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "info/modifyOne", method = RequestMethod.POST)
    public ResponseData modifyOneProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("modifyOneProductInfoExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 添加商品计量单位
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addProductUnitRelation", method = RequestMethod.POST)
    public ResponseData addProductUnitRelation(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductUnitRelationPageModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductUnitRelationExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 修改商品计量单位
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateProductUnitRelation", method = RequestMethod.POST)
    public ResponseData updateProductUnitRelation(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductUnitRelationPageModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateProductUnitRelationExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除商品计量单位
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteProductUnitRelation", method = RequestMethod.POST)
    public ResponseData deleteProductUnitRelation(HttpServletRequest request, HttpServletResponse response, @RequestBody DeleteModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductUnitRelationExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 根据ID查询商品计量单位
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productUnitRelation", method = RequestMethod.GET)
    public ResponseData getProductUnitRelation(HttpServletRequest request, HttpServletResponse response,ProductUnitRelationModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductUnitRelationExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品种类详情
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productUnitcategory", method = RequestMethod.POST)
    public ResponseData getProductUnitcategory(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductUnitRelationModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductUnitcategoryExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 删除商品规格
     */
    @RequestMapping(value = "info/delete", method = RequestMethod.POST)
    public ResponseData deleteProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductInfoExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 根据商品分类Id查询查询计量单位
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "categoryUnit", method = RequestMethod.GET)
    public ResponseData getcategoryUnit(HttpServletRequest request, HttpServletResponse response, ProductUnitRelationModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getCategoryUnitExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商品模糊搜索查询
     */

    @RequestMapping(value = "searchGoods", method = RequestMethod.GET)
    public ResponseData getSearchGoods(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getSearchGoodsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * PC端查询商品信息
     */
    @RequestMapping(value = "PcProductList", method = RequestMethod.GET)
    public ResponseData getPcProductListExecutor(HttpServletRequest request, HttpServletResponse response, ProductPageModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getPcProductListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询自营商品列表
     */
    @RequestMapping(value = "selfMadeProduct", method = RequestMethod.GET)
    public ResponseData getSelfMadeProduct(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getSelfMadeProductExecuto");
        return executor.execute(request, response, model);
    }

    /**
     * 商品审核
     */
    @RequestMapping(value = "addSysProdaudit", method = RequestMethod.POST)
    public ResponseData addSysProdaudit(HttpServletRequest request, HttpServletResponse response, @RequestBody SysProdauditModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addSysProdauditExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商品状态查询（PC端 如：待审核 ，待上架，已上架，已下架）
     */
    @RequestMapping(value = "state", method = RequestMethod.GET)
    public ResponseData getProductState(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductStateExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * pc端添加商品信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "add/shop", method = RequestMethod.POST)
    public ResponseData addShopProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addShopProductExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询店铺内商品详情
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "shopDetails", method = RequestMethod.GET)
    public ResponseData getShopProductdetails(HttpServletResponse response, HttpServletRequest request, ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getShopProductDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 查询商品审核记录
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "ShopProdaudit", method = RequestMethod.GET)
    public ResponseData getShopProdaudit(HttpServletResponse response, HttpServletRequest request, SysProdauditModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getShopProdauditExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 商品下架接口
     *
     * @param response
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "soldOut", method = RequestMethod.POST)
    public ResponseData updateSoldOut(HttpServletResponse response, HttpServletRequest request, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateSoldOutExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * pc店铺内商品状态查询列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "shopList", method = RequestMethod.GET)
    public ResponseData getProductShopList(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductShopListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * pc商品轮播图
     */
    @RequestMapping(value = "slideshowImagePc/add", method = RequestMethod.POST)
    public ResponseData addSlideshowImagePc(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addSlideshowImagePcExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * Pc添加商品详情(富文本)
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "detailsPc/add", method = RequestMethod.POST)
    public ResponseData addProductDetailsPc(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductDetailsPcExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * Pc增加商品参数
     *
     * @return
     */
    @RequestMapping(value = "detailsParameterPc/add", method = RequestMethod.POST)
    public ResponseData addDetailsParameterPc(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addDetailsParameterPcExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * Pc添加店铺内规格(单规格)
     */
    @RequestMapping(value = "spec/add", method = RequestMethod.POST)
    public ResponseData addProductSpec(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductSpecExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * Pc修改店铺内规格(单规格)
     */
    @RequestMapping(value = "spec/update", method = RequestMethod.POST)
    public ResponseData updateProductSpec(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductInfoModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateProductSpecExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询1元商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageOneAreaList", method = RequestMethod.GET)
    public ResponseData getBackstageOneAreaListExecutor(HttpServletRequest request, HttpServletResponse response, OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageOneAreaListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台增加1元商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addOneArea", method = RequestMethod.POST)
    public ResponseData addOneAreaExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addOneAreaExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台删除1元商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteOneArea", method = RequestMethod.POST)
    public ResponseData deleteOneAreaExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteOneAreaExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台修改1元商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateOneArea", method = RequestMethod.POST)
    public ResponseData updateOneAreaExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateOneAreaExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * Front查询1元商品列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "oneAreaList", method = RequestMethod.GET)
    public ResponseData getOneAreaListExecutor(HttpServletRequest request, HttpServletResponse response, OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getOneAreaListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询会员礼包列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageMemberParcelList", method = RequestMethod.GET)
    public ResponseData getBackstageMemberParcelListExecutor(HttpServletRequest request, HttpServletResponse response, MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageMemberParcelListExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台增加会员礼包列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addMemberParcel", method = RequestMethod.POST)
    public ResponseData addMemberParcelExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addMemberParcelExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台修改会员礼包列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMemberParcel", method = RequestMethod.POST)
    public ResponseData updateMemberParcelExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateMemberParcelExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台删除会员礼包列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deteleMemberParcel", method = RequestMethod.POST)
    public ResponseData deteleMemberParcelExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deteleMemberParcelExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询会员礼包列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "memberParcelList", method = RequestMethod.GET)
    public ResponseData getMemberParcelListExecutor(HttpServletRequest request, HttpServletResponse response, MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getMemberParcelListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询会员礼包滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageMemberParcelImgList", method = RequestMethod.GET)
    public ResponseData getBackstageMemberParcelImgListExecutor(HttpServletRequest request, HttpServletResponse response, MemberParcelImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageMemberParcelImgListExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台增加、修改、删除 会员礼包滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addMemberParcelImg", method = RequestMethod.POST)
    public ResponseData addMemberParcelImgExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody MemberParcelImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addMemberParcelImgExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询会员礼包详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "memberParcelDetails", method = RequestMethod.GET)
    public ResponseData getMemberParcelDetailsExecutor(HttpServletRequest request, HttpServletResponse response, MemberParcelModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getMemberParcelDetailsExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台查询一元专区滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageOneAreaImgList", method = RequestMethod.GET)
    public ResponseData getBackstageOneAreaImgListExecutor(HttpServletRequest request, HttpServletResponse response, OneAreaImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageOneAreaImgListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台增加、修改、删除 一元专区滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addOneAreaImg", method = RequestMethod.POST)
    public ResponseData addOneAreaImgExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody OneAreaImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addOneAreaImgExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询一元专区商品详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "oneAreaDetails", method = RequestMethod.GET)
    public ResponseData getOneAreaDetailsExecutor(HttpServletRequest request, HttpServletResponse response, OneAreaModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getOneAreaDetailsExecutor");
        return executor.execute(request, response, model);
    }


    //  U币 兑换
    /**
     * 后台增加U币兑换商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addProductFresher", method = RequestMethod.POST)
    public ResponseData addProductFresherExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductFresherExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台删除U币兑换商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deleteProductFresher", method = RequestMethod.POST)
    public ResponseData deleteProductFresherExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("deleteProductFresherExecutor");
        return executor.execute(request, response, model);
    }
    /**
     * 后台修改U币兑换商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "updateProductFresher", method = RequestMethod.POST)
    public ResponseData updateProductFresherExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("updateProductFresherExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * Front查询U币兑换商品列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productFresherList", method = RequestMethod.GET)
    public ResponseData getProductFresherListExecutor(HttpServletRequest request, HttpServletResponse response, ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductFresherListExecutor");
        return executor.execute(request, response, model);
    }



    /**
     * 后台查询U币兑换专区滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageProductFresherImgList", method = RequestMethod.GET)
    public ResponseData getBackstageProductFresherImgListExecutor(HttpServletRequest request, HttpServletResponse response, ProductFresherImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageProductFresherImgListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 后台增加、修改、删除 U币兑换专区滚动图片
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addProductFresherImg", method = RequestMethod.POST)
    public ResponseData addProductFresherImgExecutor(HttpServletRequest request, HttpServletResponse response,@RequestBody ProductFresherImgModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("addProductFresherImgExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 前端查询U币兑换专区商品详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productFresherDetails", method = RequestMethod.GET)
    public ResponseData getProductFresherDetailsExecutor(HttpServletRequest request, HttpServletResponse response, ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getProductFresherDetailsExecutor");
        return executor.execute(request, response, model);
    }


    /**
     * 后台查询1元商品
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "backstageProductFresherList", method = RequestMethod.GET)
    public ResponseData getBackstageProductFresheraListExecutor(HttpServletRequest request, HttpServletResponse response, ProductFresherModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getBackstageProductFresherListExecutor");
        return executor.execute(request, response, model);
    }

    /**
     * 根据价格排序查询商品列表
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sellPriceProductList", method = RequestMethod.GET)
    public ResponseData getSellPriceProductListExecutor(HttpServletRequest request, HttpServletResponse response, ProductModel model){
        BusinessExecutor<ResponseData> executor = (BusinessExecutor<ResponseData>) ApplicationContextFactory.getBean("getSellPriceProductListExecutor");
        return executor.execute(request, response, model);
    }

}