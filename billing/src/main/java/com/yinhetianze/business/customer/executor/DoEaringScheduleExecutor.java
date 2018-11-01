package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.service.busi.CustomerEarningsBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollFlowInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.CustomerEarningsPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * 手动触发 统计余额收益定时任务 指定日期参数
 */

@Component
public class DoEaringScheduleExecutor extends AbstractRestBusiExecutor<Object> {

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerEarningsBusiService customerEarningsBusiServiceImpl;

    @Autowired
    private CustomerBankrollFlowInfoService customerBankrollFlowInfoServiceImpl;

    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        String currentDate = params[0].toString();
        Date currentDateObj = null;
        try{
            //查询日期为数据当前时间前一天
            currentDate = LocalDate.parse(currentDate).minusDays(1).toString();
            currentDateObj = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);
        }catch (Exception e){
            LoggerUtil.error(DoEaringScheduleExecutor.class,"parseDate error    "+e.getMessage());
            return "error";
        }

        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();

        busiCustomerPojo.setDelFlag((short)0);
        busiCustomerPojo.setIsMember((short)0);

        //查询会员 暂时是进行全表查询
        //这里后续做优化 将会员信息单独处理成一张表
        List<BusiCustomerPojo> list = customerInfoServiceImpl.select(busiCustomerPojo);
        if(list!=null&&!list.isEmpty()){

            Map<String,Object> paraMap= new HashMap<>();
            paraMap.put("currentDate",currentDate);
            for(BusiCustomerPojo pojo:list){
                paraMap.put("customerId",pojo.getId());

                //查询收益情况
                List<BusiCustomerBankrollFlowModel> marketList = customerBankrollFlowInfoServiceImpl.selectMarket(paraMap);

                if(marketList!=null&&!marketList.isEmpty()){
                    int length = 0;
                    List<CustomerEarningsPojo> addList = new ArrayList<>();
                    for(BusiCustomerBankrollFlowModel flowModel : marketList){
                        CustomerEarningsPojo customerEarningsPojo = new CustomerEarningsPojo();
                        customerEarningsPojo.setAmount(flowModel.getAmount());
                        customerEarningsPojo.setCreateId(flowModel.getCreateId());
                        customerEarningsPojo.setCustomerId(flowModel.getCustomerId());
                        customerEarningsPojo.setCreateTime(currentDateObj);

                        addList.add(customerEarningsPojo);
                        length+=1;
                        if(length>0 && length%10==0){
                            customerEarningsBusiServiceImpl.addInfoBatch(addList);
                            addList.clear();
                        }
                    }

                    //处理不足10条的记录 也需要入库
                    if(!addList.isEmpty()){
                        customerEarningsBusiServiceImpl.addInfoBatch(addList);
                    }
                }
            }
        }
        return "success";
    }

    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params){
        ErrorMessage errorMessage = new ErrorMessage();
        if(params==null||params.length == 0){
            errorMessage.rejectErrorMessage("","缺少必要时间参数","缺少必要时间参数");
            return errorMessage;
        }
        return null;
    }

}
