package com.yinhetianze.business.task.schedule;

import com.github.binarywang.wxpay.bean.result.WxEntPayQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.yinhetianze.business.customer.service.busi.CustomerDrawQueueBusiService;
import com.yinhetianze.business.customer.service.info.CustomerDrawQueueInfoService;
import com.yinhetianze.core.task.InterruptableJob;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerDrawQueuePojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信提现针对待处理提现结果处理
 */
public class WechatDrawSchedule extends InterruptableJob{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{


            CustomerDrawQueueInfoService customerDrawQueueInfoServiceImpl = (CustomerDrawQueueInfoService) ApplicationContextFactory.getBean("customerDrawQueueInfoServiceImpl");
            Map<String,Object> paraMap = new HashMap<>();

            paraMap.put("minuteInterval","2");
            List<BusiCustomerDrawQueuePojo> list  = customerDrawQueueInfoServiceImpl.selectList(paraMap);
            if(list == null || list.isEmpty()){
                return ;
            }

            CustomerDrawQueueBusiService customerDrawQueueBusiService = (CustomerDrawQueueBusiService) ApplicationContextFactory.getBean("customerDrawQueueBusiServiceImpl");
            WxPayService payService = (WxPayService) ApplicationContextFactory.getBean("payService");
            WxPayService appPayService = (WxPayService) ApplicationContextFactory.getBean("appPayService");

            for(BusiCustomerDrawQueuePojo busiCustomerDrawQueuePojo:list){
                try{
                    WxEntPayQueryResult wxEntPayQueryResult = null;
                    if(busiCustomerDrawQueuePojo.getIdType() == 1){
                        wxEntPayQueryResult = payService.queryEntPay(busiCustomerDrawQueuePojo.getDrawNumber());
                    }else if(busiCustomerDrawQueuePojo.getIdType() == 2){
                        wxEntPayQueryResult = appPayService.queryEntPay(busiCustomerDrawQueuePojo.getDrawNumber());
                    }

                    if("SUCCESS".equals(wxEntPayQueryResult.getReturnCode())){

                        if("SUCCESS".equals(wxEntPayQueryResult.getResultCode())){

                            //付款成功
                            if("SUCCESS".equals(wxEntPayQueryResult.getStatus())){
                                //更改 提现记录状态 为支付成功
                                customerDrawQueueBusiService.updateForPaySuccess(busiCustomerDrawQueuePojo.getId(),
                                        busiCustomerDrawQueuePojo.getDrawId());
                                //付款失败
                            }else if("FAILED".equals(wxEntPayQueryResult.getStatus())){

                                //更改 提现记录状态 为支付失败
                                BusiCustomerDrawQueuePojo queuePojo = new BusiCustomerDrawQueuePojo();
                                queuePojo.setId(busiCustomerDrawQueuePojo.getId());

                                BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo = new BusiCustomerDrawrecordPojo();
                                busiCustomerDrawrecordPojo.setId(busiCustomerDrawQueuePojo.getDrawId());
                                busiCustomerDrawrecordPojo.setReason(wxEntPayQueryResult.getReason());
                                busiCustomerDrawrecordPojo.setErr_code(wxEntPayQueryResult.getErrCode());
                                busiCustomerDrawrecordPojo.setErr_code_des(wxEntPayQueryResult.getErrCodeDes());
                                customerDrawQueueBusiService.updateForPayFailed(queuePojo,
                                        busiCustomerDrawrecordPojo);

                                //微信正在处理中 重试次数+1 后续继续处理
                            }else if("PROCESSING".equals(wxEntPayQueryResult.getStatus())){

                                //修改该条队列记录状态
                                BusiCustomerDrawQueuePojo queuePojo = new BusiCustomerDrawQueuePojo();
                                queuePojo.setId(busiCustomerDrawQueuePojo.getId());
                                if(busiCustomerDrawQueuePojo.getRetryCount() > 0){
                                    Integer tempCount = busiCustomerDrawQueuePojo.getRetryCount().intValue();
                                    tempCount+=1;
                                    queuePojo.setRetryCount(tempCount.shortValue());
                                }
                                customerDrawQueueBusiService.updateByPrimaryKeySelective(queuePojo);
                            }
                        }
                    }

                }catch(Exception e){
                    LoggerUtil.error(WechatDrawSchedule.class,e.getMessage()+ "..........queueId:"+busiCustomerDrawQueuePojo.getId());
                    if(e instanceof WxPayException){
                        WxPayException wxPayException =  (WxPayException)e;
                        //修改该条队列记录状态
                        BusiCustomerDrawQueuePojo queuePojo = new BusiCustomerDrawQueuePojo();
                        queuePojo.setId(busiCustomerDrawQueuePojo.getId());
                        if("NOT_FOUND".equals(wxPayException.getErrCode())
                                || "SYSTEMERROR".equals(wxPayException.getErrCode())
                                ||"FREQ_LIMIT".equals(wxPayException.getErrCode())){
                            if(busiCustomerDrawQueuePojo.getRetryCount() > 0){
                                Integer tempCount = busiCustomerDrawQueuePojo.getRetryCount().intValue();
                                tempCount+=1;
                                queuePojo.setRetryCount(tempCount.shortValue());
                            }
                            customerDrawQueueBusiService.updateByPrimaryKeySelective(queuePojo);
                        }
                        else{
                            queuePojo.setIsHandle((short)1);
                            queuePojo.setStatus((short)1);
                            queuePojo.setErrorDesc(wxPayException.getErrCodeDes());
                            if(wxPayException.getErrCodeDes() != null){
                                queuePojo.setErrorCode(wxPayException.getErrCode());
                            }else{
                                queuePojo.setErrorCode(wxPayException.toString());
                            }
                            customerDrawQueueBusiService.updateByPrimaryKeySelective(queuePojo);
                        }
                    }

                }

            }
    }
}


