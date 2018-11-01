package com.yinhetianze.business.evaluate.executor;


import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.evaluate.model.EvaluateDto;
import com.yinhetianze.business.evaluate.model.EvaluateModel;
import com.yinhetianze.business.evaluate.service.EvaluateBusiService;
import com.yinhetianze.business.order.service.OrderBusiService;
import com.yinhetianze.business.order.service.OrderInfoService;
import com.yinhetianze.business.product.service.ProductDetailInfoService;
import com.yinhetianze.business.product.service.ProductInfoService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.FileUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.product.ProductPojo;
import com.yinhetianze.security.custom.userdetails.UserDetailsService;
import com.yinhetianze.security.custom.userdetails.impl.TokenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddEvaluateExecutor extends AbstractRestBusiExecutor {

    @Autowired
    private EvaluateBusiService evaluateBusiServiceImpl;

    @Autowired
    private OrderBusiService orderBusiServiceImpl;

    @Autowired
    private OrderInfoService orderInfoServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private UserDetailsService redisUserDetails;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private ProductInfoService productInfoServiceImpl;

    @Autowired
    private ProductDetailInfoService productDetailInfoServiceImpl;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        EvaluateDto evaluateDto=(EvaluateDto)model;
        try{
            TokenUser tokenUser = (TokenUser)redisUserDetails.getUserDetails(model.getToken());
            if(CommonUtil.isEmpty(tokenUser)){
                throw new BusinessException("用户没有登录");
            }
            //用户信息
            BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setId(tokenUser.getId());
            busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
            if (CommonUtil.isEmpty(busiCustomerPojo)) {
                throw new BusinessException("没有获取到用户信息");
            }
            Map<String,Object> map=new HashMap<>();
            map.put("orderId",evaluateDto.getOrderId());
            List<Map<String,Object>> orderList=orderInfoServiceImpl.findOrder(map);
            if(CommonUtil.isNotEmpty(orderList)){
                evaluateDto.setOrderNo(orderList.get(0).get("ordersNo")+"");
            }else{
                throw new BusinessException("订单信息不存在");
            }
            for(EvaluateModel evaluateModel:evaluateDto.getEvaluateList()){
                //查询商品信息
                ProductPojo prodPojo = new ProductPojo();
                prodPojo.setId(evaluateModel.getProductId());
                prodPojo = productInfoServiceImpl.findProduct(prodPojo);
                if (CommonUtil.isEmpty(prodPojo)){
                    throw new BusinessException("商品信息不存在");
                }
                Map<String, Object> detailParam = new HashMap<>();
                detailParam.put("prodId",evaluateModel.getProductId());
                detailParam.put("skuCode",evaluateModel.getProductSuk());
                detailParam.put("delFlag", 0); // 没有删除的标记
                detailParam.put("status", 0); // 有效状态0
                // 商品详情信息单品列表
                List<Map<String, Object>> detailList = productDetailInfoServiceImpl.getProductDetail(detailParam);
                if(CommonUtil.isEmpty(detailList)){
                    throw new BusinessException("商品规格信息不存在");
                }
                evaluateModel.setOrderNo(evaluateDto.getOrderNo());
                evaluateModel.setProductName(prodPojo.getProdName());
                evaluateModel.setProductTitle(prodPojo.getpTitle());
                evaluateModel.setProductImg(prodPojo.getProductImg());
                evaluateModel.setProductSpec(detailList.get(0).get("prodSpeci")+"");
                evaluateModel.setEvaluateUser(tokenUser.getId());
                evaluateModel.setOrderId(evaluateDto.getOrderId());
                evaluateModel.setShopId(prodPojo.getShopId());
                evaluateModel.setAddEvaluate(evaluateDto.getAddEvaluate());
                if(evaluateDto.getAddEvaluate()==0){
                    if(CommonUtil.isEmpty(evaluateModel.getProductStar())){
                        evaluateModel.setProductStar(5);
                    }
                    if(CommonUtil.isEmpty(evaluateModel.getServiceStar())){
                        evaluateModel.setServiceStar(5);
                    }
                    if(CommonUtil.isEmpty(evaluateModel.getLogisticsStar())){
                        evaluateModel.setLogisticsStar(5);
                    }
                }
                if(CommonUtil.isNotEmpty(evaluateModel.getEvaluateImg())){
                    String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
                    String orderOssRootPath = sysPropertiesUtils.getStringValue("orderOssRootPath");
                    String evaluateImagePath = sysPropertiesUtils.getStringValue("evaluateImagePath");
                    String ossPath = orderOssRootPath+evaluateImagePath;
                    String storeFilePath = fileUploadPath+ossPath;
                    String key="";
                    String[] evaluateImgList=evaluateModel.getEvaluateImg().split(",");
                    for(int i=0;i<evaluateImgList.length;i++){
                        LoggerUtil.info(AddEvaluateExecutor.class, "help add read local file "+storeFilePath+ File.separatorChar+evaluateImgList[i]);
                        //读取本地文件
                        File file = FileUtil.loadFile(storeFilePath,evaluateImgList[i]);
                        //上传oss
                        String ossKey=ossFileUpload.fileUpload(file, ossPath);
                        if(ossKey == null){
                            throw new BusinessException("上传图片失败");
                        }
                        if(i==evaluateImgList.length-1){
                            key=key+ossKey;
                        }else{
                            key=key+ossKey+",";
                        }
                        //删除本地文件
                        //file.delete();
                    }
                    evaluateModel.setEvaluateImg(key);
                }
            }
            evaluateBusiServiceImpl.addEvaluate(evaluateDto,tokenUser.getId());
        }catch (Exception e){
            LoggerUtil.error(AddEvaluateExecutor.class,e);
            throw new BusinessException("评论失败");
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage error=new ErrorMessage();
        EvaluateDto evaluateDto=(EvaluateDto)model;
        if(CommonUtil.isEmpty(evaluateDto.getOrderId())){
            error.rejectNull("orderId",null,"订单ID");
        }
        if(CommonUtil.isEmpty(evaluateDto.getEvaluateList())){
            error.rejectNull("evaluateList",null,"评价的商品信息");
        }
        if(CommonUtil.isEmpty(evaluateDto.getAddEvaluate())){
            error.rejectNull("addEvaluate",null,"是否为追评");
        }
        return error;
    }
}
