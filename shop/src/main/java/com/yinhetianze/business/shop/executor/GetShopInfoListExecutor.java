package com.yinhetianze.business.shop.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.business.shop.model.BusiShopPageModel;
import com.yinhetianze.business.shop.model.BusiShopSearchModel;
import com.yinhetianze.business.shop.service.info.ShopInfoService;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.product.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索店铺
 */

@Component
public class GetShopInfoListExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private ShopInfoService shopInfoServiceImpl;

    @Autowired
    private AreaModelUtils areaModelUtils;


    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiShopSearchModel busiShopSearchModel = (BusiShopSearchModel)model;

        if(busiShopSearchModel.getIsAll() == null){
            PageHelper.startPage(busiShopSearchModel.getCurrentPage(),busiShopSearchModel.getPageSize());
        }
        List<BusiShopSearchModel> list = shopInfoServiceImpl.selectShopInfoList(busiShopSearchModel);

        if(list!=null&&!list.isEmpty()){
            Map<String,Object> paraMap = new HashMap<String,Object>();
            paraMap.put("delFlag",0);
            paraMap.put("prodStatus",0);
            paraMap.put("auditState",3);
            for(BusiShopSearchModel searchModel : list){
                paraMap.put("shopId",searchModel.getId());
                try{
                    AreaInfo regionLocation =  areaModelUtils.getProvince(searchModel.getRegionLocation(),false);
                    searchModel.setRegionLocation(regionLocation.getName());
                }catch (Exception e){

                }
                if("pc".equals(busiShopSearchModel.getType())){
                    List<ProductPojo> pList = productInfoServiceImpl.getProductList(paraMap);
                    searchModel.setProductTotal((pList==null||pList.isEmpty()?0:pList.size()));
                    int size = 1;
                    for(ProductPojo pojo:pList){
                        if(size>5)
                            break;
                        Map<String,Object> temp = new HashMap<String,Object>();
                        temp.put("productId",pojo.getId());
                        temp.put("imgUrl",pojo.getProductImg());
                        searchModel.getProductList().add(temp);
                        size++;
                    }
                }
            }
            PageInfo<BusiShopSearchModel> page = new PageInfo<BusiShopSearchModel>(list);
            PageData<BusiShopSearchModel> pageData = new PageData<>(page.getList(),page.getTotal());
            return pageData;
        }
        PageData pageData = new PageData();
        return pageData;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiShopSearchModel busiShopSearchModel = (BusiShopSearchModel)model;
        if(CommonUtil.isNotEmpty(busiShopSearchModel.getSearchContent())){
            try{
                String searchContent = URLDecoder.decode( busiShopSearchModel.getSearchContent(), "UTF-8");
                busiShopSearchModel.setSearchContent(searchContent);
            }catch(Exception e){
                LoggerUtil.error(GetCompanyListExecutor.class,e.getMessage());
            }
        }
        return errorMessage;
    }
}
