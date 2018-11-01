package com.yinhetianze.business.customer.executor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yinhetianze.business.customer.model.BusiCustomerCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerProductCollectorModel;
import com.yinhetianze.business.customer.model.BusiCustomerShopCollectorModel;
import com.yinhetianze.business.customer.service.info.CustomerCollectorInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.cachedata.area.AreaInfo;
import com.yinhetianze.common.business.sys.area.cachedata.AreaModelUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.PageData;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerReceiveaddressPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费者/会员 获取收藏记录列表
 */

@Component
public class GetCustomerCollectorListExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerCollectorInfoService customerCollectorInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private AreaModelUtils areaModelUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();
        if(busiCustomerCollectorModel.getIsAll() == null){
            PageHelper.startPage(busiCustomerCollectorModel.getCurrentPage(),busiCustomerCollectorModel.getPageSize());
        }

        if(busiCustomerCollectorModel.getcType() == 0){


           List<BusiCustomerProductCollectorModel> productList  =  customerCollectorInfoServiceImpl.selectCollectorProductList(busiCustomerCollectorModel);
           PageInfo pageInfo = new PageInfo(productList);
           PageData<BusiCustomerProductCollectorModel> pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
           return pageData;


        }else if(busiCustomerCollectorModel.getcType() == 1){


            List<BusiCustomerShopCollectorModel> shopList = customerCollectorInfoServiceImpl.selectCollectorShopList(busiCustomerCollectorModel);
            Map<String,Object> paraMap = new HashMap<String,Object>();
            paraMap.put("delFlag",0);
            paraMap.put("prodStatus",0);
            paraMap.put("auditState",3);
            for(BusiCustomerShopCollectorModel shop : shopList){
                paraMap.put("shopId",shop.getRelationId());
                try{
                    AreaInfo regionLocation =  areaModelUtils.getProvince(shop.getRegionLocation(),false);
                    shop.setRegionLocation(regionLocation.getName());
                }catch (Exception e){

                }
                if("pc".equals(busiCustomerCollectorModel.getType())){
                    List<ProductPojo> pList = productInfoServiceImpl.getProductList(paraMap);
                    shop.setProductTotal((pList==null||pList.isEmpty()?0:pList.size()));
                    int size = 1;
                    for(ProductPojo pojo:pList){
                        if(size>5)
                            break;
                        Map<String,Object> temp = new HashMap<String,Object>();
                        temp.put("productId",pojo.getId());
                        temp.put("imgUrl",pojo.getProductImg());
                        shop.getProductList().add(temp);
                        size++;
                    }
                }
            }
            PageInfo pageInfo = new PageInfo(shopList);
            PageData pageData = new PageData(pageInfo.getList(),pageInfo.getTotal());
            return pageData;
        }else{
            PageData pageData = new PageData();
            return pageData;
        }
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiCustomerCollectorModel busiCustomerCollectorModel = (BusiCustomerCollectorModel)model;BusiCustomerReceiveaddressPojo  busiCustomerReceiveaddressPojo = new BusiCustomerReceiveaddressPojo();

        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getToken())){
            errorMessage.rejectNull("token",null,"token");
            return  errorMessage;
        }

        if(CommonUtil.isEmpty(busiCustomerCollectorModel.getcType())){
            errorMessage.rejectNull("cType",null,"收藏类型");
            return  errorMessage;
        }

        TokenUser tokenUser = (TokenUser) redisUserDetails.getUserDetails(busiCustomerCollectorModel.getToken());
        busiCustomerCollectorModel.setCustomerId(tokenUser.getId());
        return errorMessage;
    }

}
