package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component
public class MadeTestDataExecutor extends AbstractRestBusiExecutor<Object> {

    private String[] amountArrMinus = new String[]{"1","3","15","17","18"};
    private String[] amountArrAdd = new String[]{"9","16"};

    private String[] ACoinMinus = new String[]{"5"};
    private String[] ACoinArrAdd = new String[]{"8","14"};

    private String[] BCoinMinus = new String[]{"20"};
    private String[] BCoinArrAdd = new String[]{"21","19"};

    private Short[] flowCategoryArr = new Short[]{1,3,6};

    @Autowired
    private CustomerBankrollFlowBusiService customerBankrollFlowBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = (BusiCustomerBankrollFlowModel)model;
        BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo = new BusiCustomerBankrollFlowPojo();
        BeanUtils.copyProperties(busiCustomerBankrollFlowModel,busiCustomerBankrollFlowPojo);


        if(busiCustomerBankrollFlowPojo.getFlowCategory() == null){
            int i = new Random().nextInt(flowCategoryArr.length-1);
            busiCustomerBankrollFlowPojo.setFlowCategory(flowCategoryArr[i]);
        }


        if(busiCustomerBankrollFlowPojo.getFlowCategory() == 1){

            if(busiCustomerBankrollFlowPojo.getFlowType() == 0){
                int i = new Random().nextInt(amountArrAdd.length-1);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(amountArrAdd[i]));
            }else{
                int i = new Random().nextInt(amountArrMinus.length-1);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(amountArrMinus[i]));
            }



        }else if(busiCustomerBankrollFlowPojo.getFlowCategory() == 3){

            if(busiCustomerBankrollFlowPojo.getFlowType() == 0){
                int i = new Random().nextInt(ACoinArrAdd.length-1);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(ACoinArrAdd[i]));
            }else{
                int i = new Random().nextInt(ACoinMinus.length);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(ACoinMinus[i]));
            }


        }else if(busiCustomerBankrollFlowPojo.getFlowCategory() == 6){

            if(busiCustomerBankrollFlowPojo.getFlowType() == 0){
                int i = new Random().nextInt(BCoinArrAdd.length-1);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(BCoinArrAdd[i]));
            }else{
                int i = new Random().nextInt(BCoinMinus.length);
                busiCustomerBankrollFlowPojo.setFlowDescription(Short.parseShort(BCoinMinus[i]));
            }
        }


        busiCustomerBankrollFlowPojo.setFlowNumber(CommonUtil.getSerialnumber());

        BusiCustomerBankrollPojo busiCustomerBankrollPojo  = new BusiCustomerBankrollPojo();
        List<BusiCustomerBankrollPojo> list = customerBankrollInfoServiceImpl.select(busiCustomerBankrollPojo);
        Random r = new Random();
        int i = r.nextInt(list.size()-1);
        busiCustomerBankrollFlowPojo.setBankrollId(list.get(i).getId());
        busiCustomerBankrollFlowPojo.setAmount(new BigDecimal(r.nextDouble()*r.nextInt(1000)).setScale(2,BigDecimal.ROUND_HALF_UP).movePointRight(2));
        customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
        return "success";
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        return null;
    }


    public static void main(String[] rags){


        int i = new Random().nextInt(100);
        System.out.println("i:"+i);

        System.out.println("i:"+new BigDecimal(new Random().nextDouble()*i).setScale(2,BigDecimal.ROUND_HALF_UP));


    }
}
