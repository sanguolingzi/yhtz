package com.yinhetianze.business.shop.service.busi.impl;

import com.yinhetianze.business.customer.util.AccountingCustomerConstant;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.business.shop.mapper.busi.ShopDrawrecordBusiMapper;
import com.yinhetianze.business.shop.service.busi.ShopBankrollFlowBusiService;
import com.yinhetianze.business.shop.service.busi.ShopBankrollBusiService;
import com.yinhetianze.business.shop.service.busi.ShopDrawrecordBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import com.yinhetianze.pojo.shop.BusiShopBankrollFlowPojo;
import com.yinhetianze.pojo.shop.BusiShopBankrollPojo;
import com.yinhetianze.pojo.shop.BusiShopDrawrecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = {Exception.class})
public class ShopDrawrecordBusiServiceImpl implements ShopDrawrecordBusiService {
    @Autowired
    private ShopDrawrecordBusiMapper shopDrawrecordBusiMapper;

    @Autowired
    private ShopBankrollBusiService shopBankrollBusiServiceImpl;

    @Autowired
    private ShopBankrollFlowBusiService shopBankrollFlowBusiServiceImpl;

    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;

    @Override
    public int insert(BusiShopDrawrecordPojo record) {
        return shopDrawrecordBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiShopDrawrecordPojo record) {
        return shopDrawrecordBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiShopDrawrecordPojo record) {
        return shopDrawrecordBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiShopDrawrecordPojo record) {
        return shopDrawrecordBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int AddShopDrawrecordInfo(BusiShopBankrollPojo busiShopBankrollPojo,
                                     BusiShopDrawrecordPojo busiShopDrawrecordPojo) throws BusinessException {

        /**
         * 扣减账户余额
         */
        int result = shopBankrollBusiServiceImpl.updateGoodsAmount(busiShopBankrollPojo.getId(),
                busiShopDrawrecordPojo.getDrawAmount().movePointRight(2),busiShopBankrollPojo.getGoodsAmount());
        if(result <= 0) {
            throw new BusinessException("BC0037", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        busiShopDrawrecordPojo.setDrawType((short)1);
        busiShopDrawrecordPojo.setBankrollId(busiShopBankrollPojo.getId());
        busiShopDrawrecordPojo.setDrawNumber(CommonUtil.getSerialnumber());
        /**
         * 增加提现记录
         */
        insertSelective(busiShopDrawrecordPojo);

        /**
         * 增加账户流水
         */
        BusiShopBankrollFlowPojo busiShopBankrollFlowPojo = new BusiShopBankrollFlowPojo();
        busiShopBankrollFlowPojo.setFlowCategory((short)1);
        busiShopBankrollFlowPojo.setFlowType((short)1);
        busiShopBankrollFlowPojo.setAmount(busiShopDrawrecordPojo.getDrawAmount().movePointRight(2));
        busiShopBankrollFlowPojo.setFlowDescription((short)1);
        busiShopBankrollFlowPojo.setFlowNumber(busiShopDrawrecordPojo.getDrawNumber());
        busiShopBankrollFlowPojo.setBankrollId(busiShopBankrollPojo.getId());
        busiShopBankrollFlowPojo.setRelationId(busiShopDrawrecordPojo.getId());

        shopBankrollFlowBusiServiceImpl.insertSelective(busiShopBankrollFlowPojo);

        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmContent(AccountingCustomerConstant.getContentTemplate(
                BankrollInfoEnum.AMOUNT.getDesc(),
                BankrollInfoEnum.EXPENSE.getDesc(),
                busiShopDrawrecordPojo.getDrawAmount().toString(),
                BankrollInfoEnum.AMOUNT.getDesc(),
                "",//TODO
                BankrollInfoEnum.DRAW.getDesc()
        ));
        busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.AMOUNT.getDesc()));
        busiMessageDetailPojo.setmType((short) 1);
        busiMessageDetailPojo.setMessageId(busiShopBankrollPojo.getShopId());
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);
        return 1;
    }
}