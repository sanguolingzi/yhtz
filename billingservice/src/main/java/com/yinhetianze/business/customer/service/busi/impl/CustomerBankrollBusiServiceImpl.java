package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerBankrollBusiMapper;
import com.yinhetianze.business.customer.mapper.info.CustomerBankrollInfoMapper;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollModel;
import com.yinhetianze.business.customer.model.BusiCustomerStarCoinInfoModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.util.AccountingCustomerConstant;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.message.service.busi.MessageDetailBusiService;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.LoggerUtil;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollFlowPojo;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.message.BusiMessageDetailPojo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerBankrollBusiServiceImpl implements CustomerBankrollBusiService
{
    @Autowired
    private CustomerBankrollBusiMapper customerBankrollBusiMapper;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowBusiService customerBankrollFlowBusiServiceImpl;

    private String starCoinPercent="0.003";

    @Autowired
    private MessageDetailBusiService messageDetailBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoMapper customerBankrollInfoMapper;

    @Override
    public int insert(BusiCustomerBankrollPojo record) {
        return customerBankrollBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiCustomerBankrollPojo record) {
        return customerBankrollBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerBankrollPojo record) {
        return customerBankrollBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiCustomerBankrollPojo record) {
        return customerBankrollBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int add(BusiCustomerBankrollPojo busiCustomerBankrollPojo) {
        return insertSelective(busiCustomerBankrollPojo);
    }

    @Override
    public int deleteInfo(BusiCustomerBankrollPojo busiCustomerBankrollPojo) {
        busiCustomerBankrollPojo.setDelFlag((short)1);
        return updateByPrimaryKeySelective(busiCustomerBankrollPojo);
    }

    @Override
    public String updateBankrollForOrder(BusiUpdateBankrollModel busiUpdateBankrollModel)
    throws BusinessException{

        StringJoiner stringJoiner = new StringJoiner(",","","");
        BusiCustomerBankrollPojo busiCustomerBankrollPojo =
                customerBankrollInfoServiceImpl.selectByCustomerId(busiUpdateBankrollModel.getCustomerId());
        //busiCustomerBankrollPojo customerId 查不到 则通过gameId 查询
        if(busiCustomerBankrollPojo == null){
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByGameId(busiUpdateBankrollModel.getGameId());
        }


        if(busiCustomerBankrollPojo == null){
            LoggerUtil.error(CustomerBankrollBusiServiceImpl.class,"busiCustomerBankrollPojo not exist customerId:"+busiUpdateBankrollModel.getCustomerId()+"...gameId:"+busiUpdateBankrollModel.getGameId());
            throw new BusinessException("账户信息不存在!");
        }

        int bankRollId = busiCustomerBankrollPojo.getId();
        boolean doUpdate = false;


        //账户 额度字段 以及  账户 操作类型(收入 还是 支出 ) 不为空 则允许进行操作
        if(busiUpdateBankrollModel.getAmount() != null && busiUpdateBankrollModel.getAmountAddOrMinus() != null){
           int result =  customerBankrollBusiMapper.updateBankrollAmount(busiCustomerBankrollPojo.getId()
                    ,busiUpdateBankrollModel.getAmount().movePointRight(2),busiCustomerBankrollPojo.getAmount(),
                   busiUpdateBankrollModel.getAmountAddOrMinus()==1?BankrollInfoEnum.EXPENSE.getValue():BankrollInfoEnum.INCOME.getValue());

           if(result <= 0)
               throw new BusinessException("BC0037", "余额修改失败");

            doUpdate = true;
        }

        if(busiUpdateBankrollModel.getIntegral() != null && busiUpdateBankrollModel.getIntegralAddOrMinus() != null){
            int result =  customerBankrollBusiMapper.updateBankrollIntegral(busiCustomerBankrollPojo.getId()
                    ,busiUpdateBankrollModel.getIntegral().movePointRight(2),busiCustomerBankrollPojo.getIntegral(),
                    busiUpdateBankrollModel.getIntegralAddOrMinus()==1?BankrollInfoEnum.EXPENSE.getValue():BankrollInfoEnum.INCOME.getValue());

            if(result <= 0)
                throw new BusinessException("BC0037", "积分修改失败");
            doUpdate = true;
        }

        if(busiUpdateBankrollModel.getStarCoin() != null && busiUpdateBankrollModel.getStarCoinAddOrMinus() != null){
            int result =  customerBankrollBusiMapper.updateBankrollStarCoin(busiCustomerBankrollPojo.getId()
                    ,busiUpdateBankrollModel.getStarCoin().movePointRight(2),busiCustomerBankrollPojo.getStarCoin(),
                    busiUpdateBankrollModel.getStarCoinAddOrMinus()==1?BankrollInfoEnum.EXPENSE.getValue():BankrollInfoEnum.INCOME.getValue());

            if(result <= 0)
                throw new BusinessException("BC0037", "友旗币修改失败");
            doUpdate = true;
        }

        if(busiUpdateBankrollModel.getRewardAmount() != null && busiUpdateBankrollModel.getRewardAmountAddOrMinus() != null){
            int result =  customerBankrollBusiMapper.updateBankrollRewardAmount(busiCustomerBankrollPojo.getId()
                    ,busiUpdateBankrollModel.getRewardAmount().movePointRight(2),busiCustomerBankrollPojo.getRewardAmount(),
                    busiUpdateBankrollModel.getRewardAmountAddOrMinus()==1?BankrollInfoEnum.EXPENSE.getValue():BankrollInfoEnum.INCOME.getValue());

            if(result <= 0)
                throw new BusinessException("BC0037", "U币修改失败");


            if(busiUpdateBankrollModel.getRewardAmountAddOrMinus() == 1){
                //累加U币账户的消费金额
                customerBankrollBusiMapper.addBankrollUsedRewardAmount(busiCustomerBankrollPojo.getId(),busiUpdateBankrollModel.getRewardAmount().movePointRight(2));
            }

            doUpdate = true;
        }

        if(!doUpdate){
            throw new BusinessException("未执行任何账户修改操作!");
        }

        List<BusiCustomerBankrollFlowModel> list = busiUpdateBankrollModel.getList();
        list.forEach(busiCustomerBankrollFlowModel -> {
            BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo = new BusiCustomerBankrollFlowPojo();
            BeanUtils.copyProperties(busiCustomerBankrollFlowModel,busiCustomerBankrollFlowPojo);
            busiCustomerBankrollFlowPojo.setBankrollId(bankRollId);
            //额度扩大100 存入数据库
            busiCustomerBankrollFlowPojo.setAmount(busiCustomerBankrollFlowModel.getAmount().movePointRight(2));
            customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
            stringJoiner.add(String.valueOf(busiCustomerBankrollFlowPojo.getId()));
        });

        if(stringJoiner.length() == 0){
            throw new BusinessException("BC0050", "账户流水添加失败");
        }

        //查询操作过过后的余额 若根据customerId查询不到信息 则不发送消息
        BusiCustomerBankrollPojo dbPojo =
                customerBankrollInfoServiceImpl.selectByCustomerId(busiUpdateBankrollModel.getCustomerId());
        if(dbPojo != null){
            for(BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel : list) {
                if (busiCustomerBankrollFlowModel.getFlowCategory() == 1) {//余额

                    BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
                    busiMessageDetailPojo.setmContent(AccountingCustomerConstant.getContentTemplate(
                            BankrollInfoEnum.AMOUNT.getDesc(),
                            busiUpdateBankrollModel.getAmountAddOrMinus() == 1 ? BankrollInfoEnum.EXPENSE.getDesc() : BankrollInfoEnum.INCOME.getDesc(),
                            busiCustomerBankrollFlowModel.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.AMOUNT.getDesc(),
                            dbPojo.getAmount().movePointLeft(2).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.getDesc(busiCustomerBankrollFlowModel.getFlowDescription())));
                    busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.AMOUNT.getDesc()));
                    busiMessageDetailPojo.setmType((short) 1);
                    busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
                    messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);

                } else if (busiCustomerBankrollFlowModel.getFlowCategory() == 2) {//积分


                    BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
                    busiMessageDetailPojo.setmContent( AccountingCustomerConstant.getContentTemplate(
                            BankrollInfoEnum.INTEGRAL.getDesc(),
                            busiUpdateBankrollModel.getIntegralAddOrMinus() == 1 ? BankrollInfoEnum.EXPENSE.getDesc() : BankrollInfoEnum.INCOME.getDesc(),
                            busiCustomerBankrollFlowModel.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.INTEGRAL.getDesc(),
                            dbPojo.getIntegral().movePointLeft(2).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.getDesc(busiCustomerBankrollFlowModel.getFlowDescription())));
                    busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.INTEGRAL.getDesc()));
                    busiMessageDetailPojo.setmType((short) 1);
                    busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
                    messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);

                } else if (busiCustomerBankrollFlowModel.getFlowCategory() == 3) {//友旗币


                    BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
                    busiMessageDetailPojo.setmContent(AccountingCustomerConstant.getContentTemplate(
                            BankrollInfoEnum.STARCOIN.getDesc(),
                            busiUpdateBankrollModel.getStarCoinAddOrMinus() == 1 ? BankrollInfoEnum.EXPENSE.getDesc() : BankrollInfoEnum.INCOME.getDesc(),
                            busiCustomerBankrollFlowModel.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.STARCOIN.getDesc(),
                            dbPojo.getStarCoin().movePointLeft(2).setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                            BankrollInfoEnum.getDesc(busiCustomerBankrollFlowModel.getFlowDescription())));
                    busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.STARCOIN.getDesc()));
                    busiMessageDetailPojo.setmType((short) 1);
                    busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
                    messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);

                } else if (busiCustomerBankrollFlowModel.getFlowCategory() == 6) {//U币


                        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
                        busiMessageDetailPojo.setmContent( AccountingCustomerConstant.getContentTemplate(
                                BankrollInfoEnum.REWARDAMOUNT.getDesc(),
                                busiUpdateBankrollModel.getRewardAmountAddOrMinus() == 1 ? BankrollInfoEnum.EXPENSE.getDesc() : BankrollInfoEnum.INCOME.getDesc(),
                                busiCustomerBankrollFlowModel.getAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                                BankrollInfoEnum.REWARDAMOUNT.getDesc(),
                                dbPojo.getRewardAmount().movePointLeft(2).setScale(2,BigDecimal.ROUND_HALF_UP).toString(),
                                BankrollInfoEnum.getDesc(busiCustomerBankrollFlowModel.getFlowDescription())));
                        busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.REWARDAMOUNT.getDesc()));
                        busiMessageDetailPojo.setmType((short) 1);
                        busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
                        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);

                    }

            };
        }
        return stringJoiner.toString();
    }

    @Override
    public int addStarCoin(BusiCustomerStarCoinInfoModel busiCustomerStarCoinInfoModel) throws BusinessException {

        BusiCustomerBankrollPojo busiCustomerBankrollPojo =
                customerBankrollInfoServiceImpl.selectByCustomerId(busiCustomerStarCoinInfoModel.getCustomerId());

        //计算摘星额度
        BigDecimal integral = busiCustomerBankrollPojo.getIntegral().multiply(new BigDecimal(starCoinPercent)
               ).setScale(2,BigDecimal.ROUND_HALF_UP);

        int result =  customerBankrollBusiMapper.updateBankrollIntegral(busiCustomerBankrollPojo.getId()
                ,integral,busiCustomerBankrollPojo.getIntegral(), BankrollInfoEnum.EXPENSE.getValue());
        if(result <= 0)
            throw new BusinessException("BC0037", "积分修改失败");

        result =  customerBankrollBusiMapper.updateBankrollStarCoin(busiCustomerBankrollPojo.getId()
                ,integral,busiCustomerBankrollPojo.getStarCoin(),BankrollInfoEnum.INCOME.getValue());
        if(result <= 0)
            throw new BusinessException("BC0037", "消费券修改失败");

        //积分支出流水
        BusiCustomerBankrollFlowPojo integralFlowModel = new BusiCustomerBankrollFlowPojo();
        integralFlowModel.setFlowDescription(BankrollInfoEnum.INTEGRALTOSTARCOIN.getValue());
        integralFlowModel.setBankrollId(busiCustomerBankrollPojo.getId());
        integralFlowModel.setFlowType(BankrollInfoEnum.EXPENSE.getValue());
        integralFlowModel.setFlowNumber(CommonUtil.getSerialnumber());
        integralFlowModel.setFlowCategory(BankrollInfoEnum.INTEGRAL.getValue());
        integralFlowModel.setAmount(integral);
        customerBankrollFlowBusiServiceImpl.insertSelective(integralFlowModel);


        //消费券收入流水
        BusiCustomerBankrollFlowPojo starCoinFlowModel = new BusiCustomerBankrollFlowPojo();
        starCoinFlowModel.setFlowDescription(BankrollInfoEnum.ADDSTARCOIN.getValue());
        starCoinFlowModel.setBankrollId(busiCustomerBankrollPojo.getId());
        starCoinFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
        starCoinFlowModel.setFlowNumber(integralFlowModel.getFlowNumber());
        starCoinFlowModel.setFlowCategory(BankrollInfoEnum.STARCOIN.getValue());
        starCoinFlowModel.setAmount(integral);
        customerBankrollFlowBusiServiceImpl.insertSelective(starCoinFlowModel);


        //积分支出消息
        BusiMessageDetailPojo busiMessageDetailPojo = new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmContent( AccountingCustomerConstant.getContentTemplate(
                BankrollInfoEnum.INTEGRAL.getDesc(),
                BankrollInfoEnum.EXPENSE.getDesc(),
                integral.movePointLeft(2).toString(),
                BankrollInfoEnum.INTEGRAL.getDesc(),
                "",//TODO
                BankrollInfoEnum.INTEGRALTOSTARCOIN.getDesc()));
        busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.INTEGRAL.getDesc()));
        busiMessageDetailPojo.setmType((short) 1);
        busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);


        //消费券收入消息
        busiMessageDetailPojo = new BusiMessageDetailPojo();
        busiMessageDetailPojo.setmContent( AccountingCustomerConstant.getContentTemplate(
                BankrollInfoEnum.STARCOIN.getDesc(),
                BankrollInfoEnum.INCOME.getDesc(),
                integral.movePointLeft(2).toString(),
                BankrollInfoEnum.STARCOIN.getDesc(),
                "",//TODO
                BankrollInfoEnum.ADDSTARCOIN.getDesc()));
        busiMessageDetailPojo.setmTitle(AccountingCustomerConstant.getTitleTemplate(BankrollInfoEnum.STARCOIN.getDesc()));
        busiMessageDetailPojo.setmType((short) 1);
        busiMessageDetailPojo.setMessageId(busiCustomerBankrollPojo.getCustomerId());
        messageDetailBusiServiceImpl.addInfo(busiMessageDetailPojo);
        return 1;
    }


    /**
     * 定时返利定时器
     * @param listBankroll
     * @param rewardsRatio
     */
    @Override
    public void updateRewards(List<Map> listBankroll, String rewardsRatio)  throws BusinessException{
        try {
            listBankroll.forEach(Map ->{
                //判断用户今天是都返利 busi_fanli_yoka
                Map fanliMap=customerBankrollInfoMapper.getfanliYoka(Integer.parseInt(Map.get("customerId").toString()));
                if(CommonUtil.isEmpty(fanliMap)){
                    BigDecimal amount= new BigDecimal(Map.get("notYokaAmount").toString()).multiply(new BigDecimal(rewardsRatio));
                    Map rewardsMap=new HashMap();
                    rewardsMap.put("customerId",Map.get("customerId"));
                    rewardsMap.put("notYokaAmount",new BigDecimal(Map.get("notYokaAmount").toString()));
                    rewardsMap.put("amount",amount);
                    //更新用户用户资金信息
                    int updateRewardsBankrollAmount=customerBankrollBusiMapper.updateRewardsBankrollAmount(rewardsMap);
                    Map fanliYokaMap=new HashMap();
                    fanliYokaMap.put("customerId",Integer.parseInt(Map.get("customerId").toString()));
                    fanliYokaMap.put("recordNo",CommonUtil.getSerialnumber());
                    fanliYokaMap.put("yokaPointAmount",amount);
                    fanliYokaMap.put("yokaAmount",new BigDecimal(Map.get("notYokaAmount").toString()));
                    //生成消费券返利流水
                    int updateFanliYoka=customerBankrollBusiMapper.updateFanliYoka(fanliYokaMap);
//                if(updateRewardsBankrollAmount!=1|| updateFanliYoka!=1){
//                    throw new BusinessException("用户ID为"+Map.get("customerId")+"的用户返利失败");
//
//                }
                }
            });
        }catch (Exception e) {
            LoggerUtil.error(CustomerBankrollBusiServiceImpl.class, e.getMessage());
            throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
    }

    @Override
    public int bindCustomerByGameId(BusiCustomerBankrollPojo busiCustomerBankrollPojo) {
        return customerBankrollBusiMapper.bindCustomerByGameId(busiCustomerBankrollPojo);
    }

    @Override
    public int clearGameId(Integer id) {
        return customerBankrollBusiMapper.clearGameId(id);
    }

    @Override
    public int setGameId(BusiCustomerBankrollPojo busiCustomerBankrollPojo) {
        return customerBankrollBusiMapper.setGameId(busiCustomerBankrollPojo);
    }

    @Override
    public int bindGameIdByCustomerId(BusiCustomerBankrollPojo busiCustomerBankrollPojo) {
        return customerBankrollBusiMapper.bindGameIdByCustomerId(busiCustomerBankrollPojo);
    }

    @Override
    public String addBankrollRewardAmount(BusiUpdateBankrollModel busiUpdateBankrollModel) {
        BusiCustomerBankrollPojo busiCustomerBankrollPojo = null;
        if(CommonUtil.isNotEmpty(busiUpdateBankrollModel.getGameId())){
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByGameId(busiUpdateBankrollModel.getGameId());
        }else{
            busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(busiUpdateBankrollModel.getCustomerId());
        }
        customerBankrollBusiMapper.addBankrollRewardAmount(busiCustomerBankrollPojo.getId(),
                busiUpdateBankrollModel.getRewardAmount().movePointRight(2));

        int bankRollId = busiCustomerBankrollPojo.getId();
        StringJoiner stringJoiner = new StringJoiner(",","","");
        List<BusiCustomerBankrollFlowModel> list = busiUpdateBankrollModel.getList();
        list.forEach(busiCustomerBankrollFlowModel -> {
            BusiCustomerBankrollFlowPojo busiCustomerBankrollFlowPojo = new BusiCustomerBankrollFlowPojo();
            BeanUtils.copyProperties(busiCustomerBankrollFlowModel,busiCustomerBankrollFlowPojo);
            busiCustomerBankrollFlowPojo.setBankrollId(bankRollId);
            //额度扩大100 存入数据库
            busiCustomerBankrollFlowPojo.setAmount(busiCustomerBankrollFlowModel.getAmount().movePointRight(2));
            customerBankrollFlowBusiServiceImpl.insertSelective(busiCustomerBankrollFlowPojo);
            stringJoiner.add(String.valueOf(busiCustomerBankrollFlowPojo.getId()));
        });

        return stringJoiner.toString();
    }
}