package com.yinhetianze.business.product.utils;

import com.yinhetianze.business.brand.service.BrandInfoService;
import com.yinhetianze.business.category.service.CategoryInfoService;
import com.yinhetianze.business.product.executor.AddProductExecutor;
import com.yinhetianze.business.product.executor.AddShopProductExecutor;
import com.yinhetianze.business.product.model.ProductModel;
import com.yinhetianze.business.product.model.ProductSpeciModel;
import com.yinhetianze.business.product.service.ProductBusiService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.service.info.ShopCategoryInfoService;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.business.shopbrand.model.BusiShopBrandModel;
import com.yinhetianze.business.shopbrand.service.info.ShopBrandInfoService;
import com.yinhetianze.business.unit.service.UnitInfoService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.brand.BrandPojo;
import com.yinhetianze.pojo.category.CategoryPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.pojo.shop.BusiShopBrandPojo;
import com.yinhetianze.pojo.shop.BusiShopPojo;
import com.yinhetianze.pojo.shop.ShopCategoryPojo;
import com.yinhetianze.pojo.unit.UnitPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用于新增商品基础信息和修改商品基础信息时的公共处理方法
 */
@Service
public class ProductUtil
{
    @Autowired
    private CategoryInfoService categoryInfoServiceImpl;

    @Autowired
    private BrandInfoService brandInfoServiceImpl;

    @Autowired
    private UnitInfoService unitInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private ShopCategoryInfoService shopCategoryInfoServiceImpl;

    @Autowired
    private ShopBrandInfoService shopBrandInfoServiceImpl;

    @Autowired
    private ProductBusiService productBusiServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;
    /**
     * 店铺接口调用地址
     */
//    @Value("${api.address.shop-server}")
//    private String apiGetShopInfo;
    
    /**
     * 商品基本固定信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setProdFixInfo(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        // 商品名称不能为空
        if (CommonUtil.isEmpty(prodModel.getProdName()))
        {
            throw new BusinessException("商品名称不能为空");
        }
        // 商品标题不能为空
        if (CommonUtil.isEmpty(prodModel.getProdTitle()))
        {
            throw new BusinessException("商品标题不能为空");
        }
        // 商品主图不能为空
        if (CommonUtil.isEmpty(prodModel.getProdImg()))
        {
            throw new BusinessException("商品主图不能为空");
        }
        prodPojo.setpTitle(prodModel.getProdTitle());
        prodPojo.setProdName(prodModel.getProdName());
        prodPojo.setProductImg(prodModel.getProdImg());
        //prodPojo.setpStatus((short)1); // 设置下架状态
        prodPojo.setpStatus(prodModel.getpStatus());

        // 设置子标题
        if (CommonUtil.isNotEmpty(prodModel.getProdSubTitle()))
        {
            prodPojo.setpSubtitle(prodModel.getProdSubTitle());
        }
        // 商品是否可以退换货，默认可以退换货
        if (CommonUtil.isEmpty(prodModel.getIsReplacement()))
        {
            prodPojo.setIsReplacement((short)1);
        }
        else
        {
            prodPojo.setIsReplacement(prodModel.getIsReplacement());
        }
    }

    /**
     * 商品分类信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setProdCate(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        if (CommonUtil.isEmpty(prodModel.getProdCateId()))
        {
            throw new BusinessException("商品分类ID不能为空");
        }
        else
        {
            CategoryPojo catePojo = new CategoryPojo();
            // 根据cateId查询对应的分类信息
            catePojo.setId(prodModel.getProdCateId());
            catePojo = categoryInfoServiceImpl.findCategory(catePojo);
            if (CommonUtil.isEmpty(catePojo))
            {
                throw new BusinessException("选择的分类不存在");
            }
            // 必须是3级分类
            if (catePojo.getCateLevel() != 3)
            {
                throw new BusinessException("选择的分类不符合要求");
            }

            // 设置商品分类相关信息
            prodPojo.setProdCateId(prodModel.getProdCateId());
            prodPojo.setProdCateName(catePojo.getCateName());
        }
    }

    /**
     * 商品品牌信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setProdBrand(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        if (CommonUtil.isEmpty(prodModel.getProdBrandId()))
        {
            throw new BusinessException("商品品牌ID不能为空");
        }
        else
        {
            BrandPojo brandPojo = new BrandPojo();
            brandPojo.setId(prodModel.getProdBrandId());
            brandPojo = brandInfoServiceImpl.findBrand(brandPojo);
            if (CommonUtil.isEmpty(brandPojo))
            {
                throw new BusinessException("品牌信息不存在");
            }

            prodPojo.setProdBrandId(prodModel.getProdBrandId());
            prodPojo.setProdBrandName(brandPojo.getBrandName());
        }
    }

    /**
     * 商品单位信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setProdUnit(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        if (CommonUtil.isEmpty(prodModel.getProdUnitId()))
        {
            throw new BusinessException("商品计量单位ID不能为空");
        }
        else
        {
            UnitPojo pojo = new UnitPojo();
            pojo.setId(prodModel.getProdUnitId()); // 商品计量单位ID
            pojo = unitInfoServiceImpl.findUnit(pojo);
            if (CommonUtil.isNull(pojo))
            {
                throw new BusinessException("商品计量单位不存在");
            }

            prodPojo.setProdUnitId(prodModel.getProdUnitId());
            prodPojo.setProdUnitName(pojo.getUnitName());
        }
    }

    /**
     * 商品运费信息
     * @param prodPojo
     * @param prodModel
     */
    public void setProdFreight(ProductPojo prodPojo, ProductModel prodModel)
    {
        if (CommonUtil.isEmpty(prodModel.getIsFreightFree()) || prodModel.getIsFreightFree() == 1)
        {
            // 免运费
            prodPojo.setIsFreightFree((short) 1);
            prodPojo.setFreight(new BigDecimal(0));
            prodPojo.setFreightFreePrice(new BigDecimal(0));
        }
        else
        {
            // 不免运费，设置运费和满XX元免运费信息
            // 运费与满XX元免运费优先级为：运费>满XX免运费，当运费为0时查看满XX元免运费
            prodPojo.setIsFreightFree((short) 0);
            if (CommonUtil.isEmpty(prodModel.getFreight()))
            {
                // 默认设置0元运费
                prodPojo.setFreight(new BigDecimal(0));
            }
            else
            {
                prodPojo.setFreight(prodModel.getFreight());
            }
            if (CommonUtil.isEmpty(prodModel.getFreightFreePrice()))
            {
                // 默认设置满0元免运费
                prodPojo.setFreightFreePrice(new BigDecimal(0));
            }
            else
            {
                prodPojo.setFreightFreePrice(prodModel.getFreightFreePrice());
            }
        }
    }

    /**
     * 设置商品展示库存与价格
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setProdPriceStorage(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        if (CommonUtil.isEmpty(prodModel.getSellPrice()))
        {
            throw new BusinessException("商品显示价格不能为空");
        }
        else
        {
            prodPojo.setSellPrice(prodModel.getSellPrice());
        }

        if (CommonUtil.isNotEmpty(prodModel.getMarketPrice()))
        {
            prodPojo.setMarketPrice(prodModel.getMarketPrice());
        }

        if (CommonUtil.isNotEmpty(prodModel.getProdStorage()))
        {
            prodPojo.setpStorage(prodModel.getProdStorage());
        }
    }

    /**
     * 设置让利比例与积分赠送比例
     * @param prodPojo
     * @param prodModel
     */
    public void setProdProportion(ProductPojo prodPojo, ProductModel prodModel)
    {
        // 让利比例默认20%，小于或等于13%默认13%，大于13%默认20%
        if (CommonUtil.isNotEmpty(prodModel.getProfitsProportion())
                && prodModel.getProfitsProportion().compareTo(new BigDecimal(13)) < 0)
        {
            prodPojo.setProfitsProportion(new BigDecimal(13));
            prodPojo.setIntegralPercent(new BigDecimal(50));
        }
        else
        {
            prodPojo.setProfitsProportion(new BigDecimal(20));
            prodPojo.setIntegralPercent(new BigDecimal(100));
        }
    }

    /**
     * 商品编号/批次号设置
     * @param prodPojo
     * @param prodModel
     */
    public void setProdCode(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException
    {
        // 商品编码不能为空
        if (CommonUtil.isEmpty(prodModel.getProdCode()))
        {
            throw new BusinessException("商品编码不能为空");
        }

        // 判断productCode是否存在
        ProductPojo prod = new ProductPojo();
        prod.setProdCode(prodModel.getProdCode());
        Object obj = productInfoServiceImpl.findProduct(prod);
        if (CommonUtil.isNotEmpty(obj))
        {
            throw new BusinessException("商品编号已经存在");
        }

        // 设置商品编号
        prodPojo.setProdCode(prodModel.getProdCode());
    }

    /**
     * 设置店铺信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setShopInfo(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException{
        if (CommonUtil.isEmpty(prodModel.getShopId())) {
            throw new BusinessException("店铺信息不能为空");
        }

        try {
            // 查询店铺信息
            //Map<String, Object> requestParams = new HashMap<>();
            BusiShopPojo shopPojo = new BusiShopPojo();
            shopPojo.setId(prodModel.getShopId());
            //requestParams.put("id", prodModel.getShopId()); // 店铺ID
            BusiShopPojo shop = shopInfoServiceImpl.selectOne(shopPojo);
            // 店铺模块接口返回信息
            //String response = HttpClientUtil.doGet(apiGetShopInfo, requestParams, null);
            if (CommonUtil.isNotEmpty(shop)) {
                prodPojo.setShopId(prodModel.getShopId());
            } else {
                LoggerUtil.info(AddProductExecutor.class, "调用店铺服务获取店铺信息失败");
                throw new Exception("获取店铺信息失败");
            }
        } catch (Exception e) {
            LoggerUtil.error(AddProductExecutor.class, e);
            throw new BusinessException("获取店铺信息失败");
        }
    }

    /**
     * 设置收货地址
     * @param prodPojo
     * @param prodModel
     */
    public void setShipAddress(ProductPojo prodPojo, ProductModel prodModel)
    {
        String address = "";
        if (CommonUtil.isNotEmpty(prodModel.getShipProvince()))
        {
            prodPojo.setShipProvince(prodModel.getShipProvince());
        }
        if (CommonUtil.isNotEmpty(prodModel.getShipCity()))
        {
            prodPojo.setShipCity(prodModel.getShipCity());
        }
        prodPojo.setShipAddress(address);
    }

    /**
     * 设置操作创建信息
     * @param prodPojo
     * @param prodModel
     */
    public void  setOperCreateInfo(ProductPojo prodPojo, ProductModel prodModel)
    {
        // 操作员信息
        if (CommonUtil.isNotEmpty(prodModel.getOperId()))
        {
            prodPojo.setCreateUser(prodModel.getOperId());
        }
        prodPojo.setCreateTime(new Date()); // 操作日期
    }

    /**
     * 设置操作更新信息
     * @param prodPojo
     * @param prodModel
     */
    public void setOperUpdateInfo(ProductPojo prodPojo, ProductModel prodModel)
    {
        // 操作员信息
        if (CommonUtil.isNotEmpty(prodModel.getOperId()))
        {
            prodPojo.setUpdateUser(prodModel.getOperId());
        }
        prodPojo.setUpdateTime(new Date()); // 更新日期
    }

    /**
     * 设置商品标签
     * @param prodPojo
     * @param prodModel
     */
    public void setProdTag(ProductPojo prodPojo, ProductModel prodModel)
    {
    }

    /**
     * 生成sku
     * @param pojo
     * @param skuSpeciList
     * @return
     */
    public String generateSku(ProductPojo pojo, List<ProductSpeciModel> skuSpeciList, int salt)
    {
        // (商品ID + 分类ID + 品牌ID + 规格值首字母)
        if (CommonUtil.isNotEmpty(pojo))
        {
            StringBuffer sb = new StringBuffer();
            sb.append(pojo.getId()).append(pojo.getProdCateId());

            if (CommonUtil.isNotEmpty(skuSpeciList))
            {
                for (ProductSpeciModel speci : skuSpeciList)
                {
                    // 将商品规格的拼音首字母添加到sku编码中
                    sb.append(CommonUtil.toHanyuPinyinFirst(speci.getSpeciValue(), true));

                    if (CommonUtil.oneZeroJudge(speci.getIsCustomed()))
                    {
                        // 当是自定义的规格需要添加到知识库
                    }
                }
            }

            // 添加盐值
            sb.append(CommonConstant.CHAR_UNDERLINE).append(salt);

            // 计算补零
            /*int scale = 10 - sb.length();
            if (scale > 0)
            {
                for (int i = 0; i < scale; i ++)
                {
                    sb.insert(0, 0);
                }
            }*/

            return sb.toString();
        }
        return null;
    }

    /**
     * 设置让利比例与积分赠送比例
     *
     * @param prodPojo
     * @param prodModel
     */
    public void setProportionSettle(ProductPojo prodPojo, ProductModel prodModel){
        // 让利比例默认20%，小于或等于13%默认13%，大于13%默认20%
        if (CommonUtil.isNotEmpty(prodModel.getProfitsProportion())
                && prodModel.getProfitsProportion().compareTo(new BigDecimal(13)) < 0) {
            prodPojo.setProfitsProportion(new BigDecimal(13));
            prodPojo.setIntegralPercent(new BigDecimal(50));
        } else {
            prodPojo.setProfitsProportion(new BigDecimal(20));
            prodPojo.setIntegralPercent(new BigDecimal(100));
        }
    }

    /**
     * 设置店铺内分类信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setShopCateId(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException{
        if (CommonUtil.isEmpty(prodModel.getShopCateId())) {
            throw new BusinessException("店铺内分类Id不能为空");
        }
        try {
            // 查询店铺分类信息
            ShopCategoryPojo shopCategoryPojo = new ShopCategoryPojo();
            shopCategoryPojo.setId(prodModel.getShopCateId());
            ShopCategoryPojo shopCategory = shopCategoryInfoServiceImpl.getOneShopCategory(shopCategoryPojo);
            if (CommonUtil.isNotEmpty(shopCategory)) {
                prodPojo.setShopCateId(prodModel.getShopCateId());
            } else {
                LoggerUtil.info(AddProductExecutor.class, "调用店铺服务获取店铺内分类信息失败");
                throw new Exception("获取店铺内分类信息失败");
            }
        } catch (Exception e) {
            LoggerUtil.error(AddProductExecutor.class, e);
            throw new BusinessException("获取店铺内分类信息失败");
        }
    }

    /**
     * 设置店铺内品牌分内信息
     * @param prodPojo
     * @param prodModel
     * @throws BusinessException
     */
    public void setShopBrandId(ProductPojo prodPojo, ProductModel prodModel)throws BusinessException{
        if (CommonUtil.isEmpty(prodModel.getShopBrandId())) {
            throw new BusinessException("店铺内品牌Id不能为空");
        }
        try {
            // 查询店铺内品牌信息
            BusiShopBrandPojo brandPojo = new BusiShopBrandPojo();
            brandPojo.setId(prodModel.getShopBrandId());
            //设置店铺内品牌
            BusiShopBrandPojo brand = shopBrandInfoServiceImpl.selectOne(brandPojo);
            BrandPojo BrandPojo =new BrandPojo();
            BrandPojo.setId(brand.getBrandId());
            BrandPojo=brandInfoServiceImpl.findBrand(BrandPojo);
            if (CommonUtil.isNotEmpty(BrandPojo)) {
                prodPojo.setShopBrandId(prodModel.getShopBrandId());
                prodPojo.setProdBrandName(BrandPojo.getBrandName());
            } else {
                LoggerUtil.info(AddProductExecutor.class, "获取店铺内品牌信息失败");
                throw new Exception("获取店铺内品牌信息失败");
            }
        } catch (Exception e) {
            LoggerUtil.error(AddProductExecutor.class, e);
            throw new BusinessException("获取店铺内品牌信息失败");
        }
    }

    /**
     * 设置抽成比例或者现金抽成
     * @param prodPojo
     * @param prodModel
     */
    public void setSettlementProportion(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException{
        if(CommonUtil.isEmpty(prodModel.getProfitsProportion()) && CommonUtil.isEmpty(prodModel.getSettlementPrice())){
            throw new BusinessException("店铺内品牌Id不能为空");
        }
        if(CommonUtil.isNotEmpty(prodModel.getProfitsProportion())){
            prodPojo.setProfitsProportion(prodModel.getProfitsProportion());
        }else{
            prodPojo.setSettlementPrice(prodModel.getSettlementPrice());
        }
    }

    /**
     * 邮费设置
     * @param prodPojo
     * @param prodModel
     */
    public void setIsFreightFree(ProductPojo prodPojo, ProductModel prodModel){
        if(CommonUtil.isEmpty(prodModel.getFreight())|| prodModel.getFreight().compareTo(BigDecimal.ZERO)==0){
            prodPojo.setIsFreightFree((short)1);
        }else{
            prodPojo.setIsFreightFree((short)0);
            prodPojo.setFreight(prodModel.getFreight());
        }
    }

    /**
     * 上下架设置
     * @param prodPojo
     * @param prodModel
     */
    public void setPstatus(ProductPojo prodPojo, ProductModel prodModel){
        if(CommonUtil.isNotEmpty(prodModel.getUpTime())){
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String upTime = format.format(prodModel.getUpTime());
            Date date=null;
            try {
                date=format.parse(upTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            prodPojo.setUpTime(date);
        }
        if(CommonUtil.isNotEmpty(prodModel.getUpTime()) && CommonUtil.isNotEmpty(prodModel.getDownTime())){
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String upTime = format.format(prodModel.getDownTime());
            Date date=null;
            try {
                date=format.parse(upTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            prodPojo.setDownTime(date);
        }
    }

    /**
     * 生成商品销量的初始随机数
     * @param productId
     */
    public void setProductSales(int productId){
        final int START = 50;   //定义范围开始数字

        final int END = 100; //定义范围结束数字
        //创建Random类对象
        Random random = new Random();
        //产生随机数
        int number = random.nextInt(END - START) + START;
        Map map = new HashMap();
        map.put("productId",productId);
        map.put("initSales",number);
        productBusiServiceImpl.addProductSales(map);
    }

    /**
     * 设置商品零售价 供货价 库存
     * @param prodPojo
     * @param prodModel
     */
    public void setShopProductPriceStorage(ProductPojo prodPojo, ProductModel prodModel){
        prodPojo.setRetailPrice(prodModel.getRetailPrice());
        prodPojo.setSupplyPrice(prodModel.getSupplyPrice());
        prodPojo.setpStorage(prodModel.getProdStorage());
    }


    /**
     * 生成店铺内规格sku
     * @param pojo
     * @param skuSpeciList
     * @return
     */
    public String generateShopSku(ProductPojo pojo, List<ProductSpeciModel> skuSpeciList, int salt)
    {
        // (商品ID + 分类ID + 店内品牌ID + 规格值首字母)
        if (CommonUtil.isNotEmpty(pojo))
        {
            StringBuffer sb = new StringBuffer();
            sb.append(pojo.getId()).append(pojo.getProdCateId()).append(pojo.getShopBrandId());

            if (CommonUtil.isNotEmpty(skuSpeciList))
            {
                for (ProductSpeciModel speci : skuSpeciList)
                {
                    // 将商品规格的拼音首字母添加到sku编码中
                    sb.append(CommonUtil.toHanyuPinyinFirst(speci.getSpeciValue(), true));

                    if (CommonUtil.oneZeroJudge(speci.getIsCustomed()))
                    {
                        // 当是自定义的规格需要添加到知识库
                    }
                }
            }
            // 添加盐值
            sb.append(CommonConstant.CHAR_UNDERLINE).append(salt);
            return sb.toString();
        }
        return null;
    }

    /**
     * 判断商品Id 跟toke信息 审核不通过 则修改时根据商品Id修改对应审核状态
     * @param token
     * @param productId
     */
    public ProductPojo setProductToke(String token, Integer productId) throws BusinessException{
        //判断该商品是否是登录店铺的商品
        ProductPojo prodPojoId = new ProductPojo();
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(token);
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            throw new BusinessException("非商家店铺");
        }
        if (CommonUtil.isNotEmpty(productId)) {
            prodPojoId.setId(productId);
            prodPojoId.setShopId(busiShopPojo.getId());
            prodPojoId = productInfoServiceImpl.findProduct(prodPojoId);
            if (CommonUtil.isEmpty(prodPojoId)) {
                LoggerUtil.info(AddShopProductExecutor.class, "修改商品未成功");
                throw new BusinessException("商品Id不存在");
            }
            //如果商品上下架状态为空可以进行修改  或者商品上下架状态不为空 则必须是下架状态才能进行修改
            if (CommonUtil.isNotEmpty(prodPojoId.getpStatus()) && prodPojoId.getpStatus() != 1) {
                LoggerUtil.info(AddShopProductExecutor.class, "下架商品才能进行修改");
                throw new BusinessException("下架商品才能进行修改");
            }
            //判断商品的审核状态如果是审核未通过的则进行修改审核状态未未审核
            if(prodPojoId.getAuditState()==2 || (CommonUtil.isNotEmpty(prodPojoId.getpStatus())&&prodPojoId.getpStatus()==1)){
                ProductPojo prodAuditState=new ProductPojo();
                prodAuditState.setAuditState((short) 0);
                prodAuditState.setId(productId);
                int auditState=productBusiServiceImpl.modifyProduct(prodAuditState);
                if(auditState!=1){
                    throw new BusinessException("修改审核状态失败");
                }
            }
        }
        return prodPojoId;
    }


    public void setPcShopInfo(ProductPojo prodPojo, ProductModel prodModel) throws BusinessException{
        //判断该商品是否是登录店铺的商品
        TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(prodModel.getToken());
        BusiShopPojo busiShopPojo = shopInfoServiceImpl.selectByCustomerId(tokenUser.getId());
        if(busiShopPojo == null){
            throw new BusinessException("店铺信息不存在");
        }
        prodPojo.setShopId(busiShopPojo.getId());
    }
}
