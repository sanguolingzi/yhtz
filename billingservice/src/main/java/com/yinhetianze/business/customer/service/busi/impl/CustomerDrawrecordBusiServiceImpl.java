package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerDrawrecordBusiMapper;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerDrawrecordModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerDrawrecordBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerDrawrecordInfoService;
import com.yinhetianze.business.customer.util.AccountingCustomerConstant;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerDrawrecordPojo;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerDrawrecordBusiServiceImpl implements CustomerDrawrecordBusiService
{
    @Autowired
    private CustomerDrawrecordBusiMapper customerDrawrecordBusiMapper;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerDrawrecordInfoService customerDrawrecordInfoServiceImpl;

    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;

    @Override
    public int AddCustomerDrawrecordInfo(BusiCustomerBankrollPojo busiCustomerBankrollPojo, BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException {
        /**
         * 增加提现记录
         */
        busiCustomerDrawrecordPojo.setBankrollId(busiCustomerBankrollPojo.getId());
        busiCustomerDrawrecordPojo.setDrawNumber(CommonUtil.getSerialnumber());
        busiCustomerDrawrecordPojo.setDrawType((short)1);


        BigDecimal drawAmount = busiCustomerDrawrecordPojo.getDrawAmount();
        BigDecimal finalAmount = busiCustomerDrawrecordPojo.getFinalAmount();
        BigDecimal serviceChargeResult = busiCustomerDrawrecordPojo.getServiceCharge();

        busiCustomerDrawrecordPojo.setServiceCharge(serviceChargeResult.movePointRight(2));
        busiCustomerDrawrecordPojo.setFinalAmount(finalAmount.movePointRight(2));
        busiCustomerDrawrecordPojo.setDrawAmount(drawAmount.movePointRight(2));
        customerDrawrecordBusiMapper.insertSelective(busiCustomerDrawrecordPojo);


        /**
         * 构建修改用户账户 封装数据
         */
        BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
        busiUpdateBankrollModel.setAmount(finalAmount);
        busiUpdateBankrollModel.setCustomerId(busiCustomerBankrollPojo.getCustomerId());
        busiUpdateBankrollModel.setAmountAddOrMinus(BankrollInfoEnum.EXPENSE.getValue());

        List<BusiCustomerBankrollFlowModel> list = new ArrayList<BusiCustomerBankrollFlowModel>();
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
        busiCustomerBankrollFlowModel.setAmount(finalAmount);
        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.AMOUNT.getValue());
        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.DRAW.getValue());
        busiCustomerBankrollFlowModel.setFlowNumber(busiCustomerDrawrecordPojo.getDrawNumber());
        busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.EXPENSE.getValue());
        busiCustomerBankrollFlowModel.setRelationId(busiCustomerDrawrecordPojo.getId());
        list.add(busiCustomerBankrollFlowModel);
        busiUpdateBankrollModel.setList(list);
        customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
        return 1;
    }


    @Override
    public int updateCustomerDrawrecordInfoForWechat(BusiCustomerBankrollPojo busiCustomerBankrollPojo, BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException {

        BusiCustomerDrawrecordPojo temp = new BusiCustomerDrawrecordPojo();
        temp.setId(busiCustomerDrawrecordPojo.getId());
        temp.setAuditStatus(busiCustomerDrawrecordPojo.getAuditStatus());
        temp.setPaymentNo(busiCustomerDrawrecordPojo.getPaymentNo());
        temp.setPaymentTime(busiCustomerDrawrecordPojo.getPaymentTime());
        temp.setOpenId(busiCustomerDrawrecordPojo.getOpenId());
        customerDrawrecordBusiMapper.updateByPrimaryKeySelective(temp);

        /**
         * 构建修改用户账户 封装数据
         */

        //提现金额+手续费
        BigDecimal finalAmount = busiCustomerDrawrecordPojo.getFinalAmount().movePointLeft(2);
        BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
        busiUpdateBankrollModel.setAmount(finalAmount);
        busiUpdateBankrollModel.setCustomerId(busiCustomerBankrollPojo.getCustomerId());
        busiUpdateBankrollModel.setAmountAddOrMinus(BankrollInfoEnum.EXPENSE.getValue());

        List<BusiCustomerBankrollFlowModel> list = new ArrayList<BusiCustomerBankrollFlowModel>();
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
        busiCustomerBankrollFlowModel.setAmount(finalAmount);
        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.AMOUNT.getValue());
        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.DRAW.getValue());
        busiCustomerBankrollFlowModel.setFlowNumber(busiCustomerDrawrecordPojo.getDrawNumber());
        busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.EXPENSE.getValue());
        busiCustomerBankrollFlowModel.setRelationId(busiCustomerDrawrecordPojo.getId());
        list.add(busiCustomerBankrollFlowModel);
        busiUpdateBankrollModel.setList(list);
        customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
        return 1;
    }

    @Override
    public int updateCustomerDrawrecordInfoForWechat(BusiCustomerDrawrecordPojo temp) throws BusinessException {

        temp.setAuditStatus((short)2);
        customerDrawrecordBusiMapper.updateByPrimaryKeySelective(temp);

        //提现记录 状态修改为失败
        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo  = customerDrawrecordInfoServiceImpl.selectOne(temp.getId());

        BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
        busiCustomerBankrollPojo.setId(busiCustomerDrawrecordPojo.getBankrollId());
        busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);
        /**
         *     //提现失败 资金账户返还
         */

        //BigDecimal drawAmount = busiCustomerDrawrecordPojo.getDrawAmount().movePointLeft(2);

        //提现金额+手续费
        BigDecimal finalAmount = busiCustomerDrawrecordPojo.getFinalAmount().movePointLeft(2);
        BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
        busiUpdateBankrollModel.setAmount(finalAmount);
        busiUpdateBankrollModel.setCustomerId(busiCustomerBankrollPojo.getCustomerId());
        busiUpdateBankrollModel.setAmountAddOrMinus(BankrollInfoEnum.INCOME.getValue());

        List<BusiCustomerBankrollFlowModel> list = new ArrayList<BusiCustomerBankrollFlowModel>();
        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
        busiCustomerBankrollFlowModel.setAmount(finalAmount);
        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.AMOUNT.getValue());
        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.DRAWBACK.getValue());
        busiCustomerBankrollFlowModel.setFlowNumber(busiCustomerDrawrecordPojo.getDrawNumber());
        busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
        busiCustomerBankrollFlowModel.setRelationId(busiCustomerDrawrecordPojo.getId());
        list.add(busiCustomerBankrollFlowModel);
        busiUpdateBankrollModel.setList(list);
        customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
        return 1;
    }

    @Override
    public int AddCustomerDrawrecordInfoForWechat(BusiCustomerBankrollPojo busiCustomerBankrollPojo, BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) throws BusinessException {


        /**
         * 增加提现记录 微信支付 0 成功 1 处理中 2失败 默认处理中
         */
        busiCustomerDrawrecordPojo.setBankrollId(busiCustomerBankrollPojo.getId());
        busiCustomerDrawrecordPojo.setDrawNumber(CommonUtil.getSerialnumber());
        busiCustomerDrawrecordPojo.setDrawType((short)1);
        busiCustomerDrawrecordPojo.setAuditStatus((short)1);

        BigDecimal drawAmount = busiCustomerDrawrecordPojo.getDrawAmount();
        //提现申请金额
        busiCustomerDrawrecordPojo.setDrawAmount(drawAmount.movePointRight(2));
        //最终扣减金额
        busiCustomerDrawrecordPojo.setFinalAmount(busiCustomerDrawrecordPojo.getFinalAmount().movePointRight(2));
        //手续费
        busiCustomerDrawrecordPojo.setServiceCharge(busiCustomerDrawrecordPojo.getServiceCharge().movePointRight(2));

        customerDrawrecordBusiMapper.insertSelective(busiCustomerDrawrecordPojo);
        return busiCustomerDrawrecordPojo.getId();
    }

    @Override
    public int updateInfo(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) {
        BusiCustomerDrawrecordPojo temp = new BusiCustomerDrawrecordPojo();

        temp.setId(busiCustomerDrawrecordPojo.getId());
        temp.setReason(busiCustomerDrawrecordPojo.getReason());
        temp.setPaymentNo(busiCustomerDrawrecordPojo.getPaymentNo());
        temp.setPaymentTime(busiCustomerDrawrecordPojo.getPaymentTime());
        temp.setOpenId(busiCustomerDrawrecordPojo.getOpenId());
        temp.setErr_code(busiCustomerDrawrecordPojo.getErr_code());
        temp.setErr_code_des(busiCustomerDrawrecordPojo.getErr_code_des());
        temp.setAuditStatus(busiCustomerDrawrecordPojo.getAuditStatus());
        return customerDrawrecordBusiMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public int updateCustomerDrawrecordInfo(BusiCustomerDrawrecordModel busiCustomerDrawrecordModel) throws BusinessException{

        BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo
                = customerDrawrecordInfoServiceImpl.selectOne(busiCustomerDrawrecordModel.getId());

        if(busiCustomerDrawrecordPojo == null)
            return 0;

        BusiCustomerDrawrecordPojo temp = new BusiCustomerDrawrecordPojo();
        temp.setId(busiCustomerDrawrecordPojo.getId());
        temp.setAuditStatus(busiCustomerDrawrecordModel.getAuditStatus());
        if(busiCustomerDrawrecordModel.getAuditStatus() == 0){//审核通过
            int result = customerDrawrecordBusiMapper.updateForAudit(temp);
            if(result == 0){
                LoggerUtil.error(CustomerDrawrecordBusiServiceImpl.class,"updateForAudit failed id:"+temp.getId());
                return 0;
            }

            BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
            busiCustomerBankrollPojo.setId(busiCustomerDrawrecordPojo.getBankrollId());
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);

            BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
            busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
            busiMessageDetailPojo.setmType((short)1);
            busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.drawTitleTemplate);
            busiMessageDetailPojo.setmContent(String.format(AccountingCustomerConstant.drawPassTemplate,busiCustomerDrawrecordPojo.getDrawNumber(),busiCustomerDrawrecordPojo.getDrawAmount().movePointLeft(2).setScale(2)));
            messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);
            return 1;
        }else if(busiCustomerDrawrecordModel.getAuditStatus() == 1){//审核不通过 需要添加原因 以及 退还余额
            int result = customerDrawrecordBusiMapper.updateForAudit(temp);
            if(result == 0){
                LoggerUtil.error(CustomerDrawrecordBusiServiceImpl.class,"updateForAudit failed id:"+temp.getId());
                return 0;
            }

            temp.setReason(busiCustomerDrawrecordModel.getReason());
            temp.setAuditTime(new Date());
            customerDrawrecordBusiMapper.updateByPrimaryKeySelective(temp);

            BusiCustomerBankrollPojo busiCustomerBankrollPojo = new BusiCustomerBankrollPojo();
            busiCustomerBankrollPojo.setId(busiCustomerDrawrecordPojo.getBankrollId());
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectOne(busiCustomerBankrollPojo);


            BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
            busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
            busiMessageDetailPojo.setmType((short)1);
            busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.drawTitleTemplate);
            busiMessageDetailPojo.setmContent(String.format(AccountingCustomerConstant.drawFailTemplate,
                    busiCustomerDrawrecordPojo.getDrawNumber(),
                    busiCustomerDrawrecordPojo.getDrawAmount().movePointLeft(2).setScale(2),
                    busiCustomerDrawrecordModel.getReason()));
            messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);


            /**
             * 构建修改用户账户 封装数据
             */
            BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
            //这里从数据库取出来的 所以需要 左移2位
            busiUpdateBankrollModel.setAmount(busiCustomerDrawrecordPojo.getDrawAmount().movePointLeft(2));
            busiUpdateBankrollModel.setCustomerId(busiCustomerBankrollPojo.getCustomerId());
            busiUpdateBankrollModel.setAmountAddOrMinus(BankrollInfoEnum.INCOME.getValue());

            List<BusiCustomerBankrollFlowModel> list = new ArrayList<BusiCustomerBankrollFlowModel>();
            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
            busiCustomerBankrollFlowModel.setAmount(busiCustomerDrawrecordPojo.getDrawAmount().movePointLeft(2));
            busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.AMOUNT.getValue());
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.DRAWBACK.getValue());
            busiCustomerBankrollFlowModel.setFlowNumber(CommonUtil.getSerialnumber());
            busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
            busiCustomerBankrollFlowModel.setRelationId(busiCustomerDrawrecordPojo.getId());
            list.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list);
            customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            return 1;
        }
        return 0;
    }


    @Override
    public int updateByPrimaryKeySelective(BusiCustomerDrawrecordPojo busiCustomerDrawrecordPojo) {
        return customerDrawrecordBusiMapper.updateByPrimaryKeySelective(busiCustomerDrawrecordPojo);
    }
}