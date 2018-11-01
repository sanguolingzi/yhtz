package com.yinhetianze.business.customer.service.busi.impl;

import com.yinhetianze.business.customer.mapper.busi.CustomerBusiMapper;
import com.yinhetianze.business.customer.model.BusiCustomerBankrollFlowModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.model.BusiUpdateBankrollModel;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBankrollFlowBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerRecommendRelationBusiService;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerBankrollInfoService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.service.info.CustomerRecommendRelationInfoService;
import com.yinhetianze.business.customer.util.BankrollInfoEnum;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.business.customer.util.CustomerUtil;
import com.yinhetianze.business.message.service.busi.MessageBusiService;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.common.fileupload.alioss.OSSFileUpload;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.utils.*;
import com.yinhetianze.pojo.customer.BusiCustomerBankrollPojo;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import com.yinhetianze.pojo.customer.BusiCustomerRecommendRelationPojo;
import com.yinhetianze.pojo.customer.BusiCustomerWechatPojo;
import com.yinhetianze.pojo.message.BusiMessagePojo;
import com.yinhetianze.systemservice.thirdpart.model.GameBusinessModel;
import com.yinhetianze.systemservice.thirdpart.service.info.GameRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = {Exception.class})
public class CustomerBusiServiceImpl implements CustomerBusiService
{
    @Autowired
    private CustomerBusiMapper customerBusiMapper;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private CustomerRecommendRelationBusiService customerRecommendRelationBusiServiceImpl;

    @Autowired
    private CustomerRecommendRelationInfoService customerRecommendRelationInfoServiceImpl;

    @Autowired
    private CustomerBankrollBusiService customerBankrollBusiServiceImpl;

    @Autowired
    private CustomerBankrollInfoService customerBankrollInfoServiceImpl;

    @Autowired
    private CustomerBankrollFlowBusiService customerBankrollFlowBusiServiceImpl;

    @Autowired
    private MessageBusiService messageBusiServiceImpl;

    @Autowired
    private OSSFileUpload ossFileUpload;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Autowired
    private GameRecordInfoService gameRecordInfoServiceImpl;

    @Override
    public int insert(BusiCustomerPojo record) {
        return customerBusiMapper.insert(record);
    }

    @Override
    public int insertSelective(BusiCustomerPojo record) {
        return customerBusiMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BusiCustomerPojo record){
        return customerBusiMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(BusiCustomerPojo record) {
        return customerBusiMapper.updateByPrimaryKey(record);
    }

    @Override
    public int addCustomerInfo(BusiCustomerPojo busiCustomerPojo) throws BusinessException {
        /**
         * 登陆密码进行2次md5加密
         */
        if(busiCustomerPojo.getLoginPassword() != null){
            String loginPassword = busiCustomerPojo.getLoginPassword();
            busiCustomerPojo.setLoginPassword(CustomerUtil.createPassword(loginPassword));
        }

        //生成随即用户名
        String accout = SecurityCode.getSecurityCode(10, SecurityCode.SecurityCodeLevel.Hard,false);
        busiCustomerPojo.setAccount("yhtz_"+accout);
        //暂时设置为30
        busiCustomerPojo.setProportion(sysPropertiesUtils.getDecimalValue("",new BigDecimal("30")));

        //String sign = MD5CoderUtil.encode(busiCustomerPojo.getId()+""+System.nanoTime());
        //busiCustomerPojo.setQrcodeSecret(sign);

        //根据gameId生成推荐二维码
        if(busiCustomerPojo.getGameId() != null){
            this.createQrcode(busiCustomerPojo);
        }
        insertSelective(busiCustomerPojo);

        if(busiCustomerPojo.getGameId() == null){
            BusiCustomerBankrollPojo busiCustomerBankrollPojo= new BusiCustomerBankrollPojo();
            busiCustomerBankrollPojo.setCustomerId(busiCustomerPojo.getId());
            customerBankrollBusiServiceImpl.add(busiCustomerBankrollPojo);
        }else{
            BusiCustomerBankrollPojo busiCustomerBankrollPojo = customerBankrollInfoServiceImpl.selectByGameId(busiCustomerPojo.getGameId());
            //游戏用户 没有做过奖励任务 故可能不存在事先生成的账户信息 所以还需要重新生成一次
            if(busiCustomerBankrollPojo == null){
                busiCustomerBankrollPojo= new BusiCustomerBankrollPojo();
                busiCustomerBankrollPojo.setCustomerId(busiCustomerPojo.getId());
                busiCustomerBankrollPojo.setGameId(busiCustomerPojo.getGameId());
                customerBankrollBusiServiceImpl.add(busiCustomerBankrollPojo);
            }else{
                if(busiCustomerBankrollPojo.getCustomerId() == null){
                    BusiCustomerBankrollPojo temp = new BusiCustomerBankrollPojo();
                    temp.setId(busiCustomerBankrollPojo.getId());
                    temp.setCustomerId(busiCustomerPojo.getId());
                    customerBankrollBusiServiceImpl.updateByPrimaryKeySelective(temp);
                }
                /*
                Map<String,Object> paraMap = new HashMap();
                paraMap.put("bankrollId",busiCustomerBankrollPojo.getId());
                paraMap.put("customerBankrollId",busiCustomerBankrollPojo.getId());
                customerBankrollFlowBusiServiceImpl.updateGameIdFlowToCustomer(paraMap);
                */
            }
        }


        BusiMessagePojo busiMessagePojo = new BusiMessagePojo();
        busiMessagePojo.setId(busiCustomerPojo.getId());
        messageBusiServiceImpl.addInfo(busiMessagePojo);
        return busiCustomerPojo.getId();
    }


    @Override
    public int addRegeisterCustomer(BusiRegeisterModel busiRegeisterModel) throws BusinessException {

        try{
            BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
            busiCustomerPojo.setPhone(busiRegeisterModel.getPhone());
            busiCustomerPojo.setLoginPassword(busiRegeisterModel.getLoginPassword());
            //正对游戏用户通过购物进行自动注册时关联平台用户用
            busiCustomerPojo.setGameId(busiRegeisterModel.getGameId());

            Integer customerId= addCustomerInfo(busiCustomerPojo);


            //系统生成用户 recommendCode 为指定的值 则认为是系统生成用户
            if(CommonUtil.isNotEmpty(busiRegeisterModel.getRecommendCode())
                &&busiRegeisterModel.getRecommendCode().equalsIgnoreCase(CustomerConstant.systemRecommend)){
                return customerId;
            }

            /*
            //推荐人号码存在 则添加推荐人关系
            BusiCustomerPojo temp = customerInfoServiceImpl.selectByRecommendCode(busiRegeisterModel.getRecommendCode());
            if(temp == null){
                throw new BusinessException("recommendCode error  phone:"+temp.getPhone()+"....recommendCode:"+busiRegeisterModel.getRecommendCode());
            }

            //if(CommonUtil.isNotEmpty(busiRegeisterModel.getRecommendCode())){
            //    temp = customerInfoServiceImpl.selectByRecommendCode(busiRegeisterModel.getRecommendCode());
            //}else if(CommonUtil.isNotEmpty(busiRegeisterModel.getRecommendId())
            //   && CommonUtil.isNotEmpty(busiRegeisterModel.getSecret())){
            //    temp = customerInfoServiceImpl.selectById(busiRegeisterModel.getRecommendId());
            //    if(!busiRegeisterModel.getSecret().equals(temp.getQrcodeSecret())){
            //        LoggerUtil.info(CustomerBusiServiceImpl.class,"扫码添加推荐人失败!   busiRegeisterModel.getSecret():"+busiRegeisterModel.getSecret()+".....QrcodeSecret:"+temp.getQrcodeSecret());
            //        temp = null;
            //    }
            //}

            //推荐人存在 并且身份是会员
            if(temp.getIsMember() == 1){
                throw new BusinessException("非会员推荐! phone:"+temp.getPhone()+"....isMember:"+temp.getIsMember());
            }
            */
            //TODO
            if(CommonUtil.isNotEmpty(busiRegeisterModel.getpGameId())){
                BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo = new BusiCustomerRecommendRelationPojo();
                busiCustomerRecommendRelationPojo.setRecomedGameId(busiRegeisterModel.getGameId());
                busiCustomerRecommendRelationPojo.setRecomGameId(busiRegeisterModel.getpGameId());
                customerRecommendRelationBusiServiceImpl.bindRelation(busiCustomerRecommendRelationPojo);


                //pGameId 若不为空则尝试生成一条账户记录  这里可能有并发存在 故需要捕获主键重复的异常
                if(customerBankrollInfoServiceImpl.selectByGameId(busiRegeisterModel.getpGameId()) == null){
                    BusiCustomerBankrollPojo record =new BusiCustomerBankrollPojo();
                    record.setGameId(busiRegeisterModel.getpGameId());
                    try{
                        customerBankrollBusiServiceImpl.insertSelective(record);
                    }catch (Exception e){
                        if(!(e instanceof DuplicateKeyException)){
                            throw new BusinessException();
                        }
                    }
                }
            }
            //赠送U币 给注册者
            BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
            busiUpdateBankrollModel.setCustomerId(customerId);
            busiUpdateBankrollModel.setRewardAmountAddOrMinus(BankrollInfoEnum.INCOME.getValue());
            busiUpdateBankrollModel.setRewardAmount(sysPropertiesUtils.getDecimalValue("regeisterUAmount",new BigDecimal("0")));

            List<BusiCustomerBankrollFlowModel> list= new ArrayList<>();

            BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
            busiCustomerBankrollFlowModel.setAmount(busiUpdateBankrollModel.getRewardAmount());
            busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
            busiCustomerBankrollFlowModel.setFlowNumber(CommonUtil.getSerialnumber());
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.REGEISTER.getValue());
            busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.REWARDAMOUNT.getValue());
            list.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list);
            customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);




            //赠送U币 给注册推荐人
             /*
            busiUpdateBankrollModel = new BusiUpdateBankrollModel();
            busiUpdateBankrollModel.setCustomerId(temp.getId());
            busiUpdateBankrollModel.setRewardAmountAddOrMinus(BankrollInfoEnum.INCOME.getValue());
            busiUpdateBankrollModel.setRewardAmount(sysPropertiesUtils.getDecimalValue("recommendUAmount",new BigDecimal("0")));

            list= new ArrayList<>();

            busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
            busiCustomerBankrollFlowModel.setAmount(busiUpdateBankrollModel.getRewardAmount());
            busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
            busiCustomerBankrollFlowModel.setFlowNumber(CommonUtil.getSerialnumber());
            busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.RECOMMENDREWARD.getValue());
            busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.REWARDAMOUNT.getValue());
            list.add(busiCustomerBankrollFlowModel);
            busiUpdateBankrollModel.setList(list);
            //customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
            customerBankrollBusiServiceImpl.addBankrollRewardAmount(busiUpdateBankrollModel);


            //赠送旗豆 给注册推荐人
            GameBusinessModel gameBusinessModel = new GameBusinessModel();
            //获取配置的赠送房卡数量
            int recommendGameNumber = sysPropertiesUtils.getIntValue("recommendGameNumber",50);
            int productType = sysPropertiesUtils.getIntValue("productType");
            SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
            //绑定成功调用通知游戏端接口
            gameBusinessModel.setGameId(temp.getGameId() );
            gameBusinessModel.setTradeNo(CommonUtil.getSerialnumber());
            gameBusinessModel.setAmount(new BigDecimal(0));
            gameBusinessModel.setCustomerId(String.valueOf(temp.getId()));
            gameBusinessModel.setTradeDesc("推荐注册赠送旗豆:"+recommendGameNumber);
            gameBusinessModel.setPaymentTime(sf.format(new Date()));
            gameBusinessModel.setNum(recommendGameNumber);
            gameBusinessModel.setTradeType(productType);
            gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
            */



            //判断是否是游戏用户过来注册 若是游戏用户过来注册 则需要调用接口赠送绑定账号所得的UB
            if(busiCustomerPojo.getGameId() != null){
                GameBusinessModel gameBusinessModel = new GameBusinessModel();
                //获取配置的赠送房卡数量
                int gameNumber = sysPropertiesUtils.getIntValue("gameNumber");
                SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
                int productType = sysPropertiesUtils.getIntValue("productType");
                //绑定成功调用通知游戏端接口
                gameBusinessModel.setGameId(busiCustomerPojo.getGameId() );
                gameBusinessModel.setTradeNo(CommonUtil.getSerialnumber());
                gameBusinessModel.setAmount(new BigDecimal(0));
                gameBusinessModel.setCustomerId(String.valueOf(customerId));
                gameBusinessModel.setTradeDesc("注册商城赠送旗豆:"+gameNumber);
                gameBusinessModel.setPaymentTime(sf.format(new Date()));
                gameBusinessModel.setNum(gameNumber);
                gameBusinessModel.setTradeType(productType);
                gameRecordInfoServiceImpl.consumeMessage(gameBusinessModel);
            }


            return customerId;
        }catch(Exception e){
            if (e instanceof DuplicateKeyException){
                DuplicateKeyException t = (DuplicateKeyException)e;
                if(t.getMessage().indexOf("uk_game_id") >= 0){
                    throw new BusinessException("游戏信息已绑定!");
                }
                throw new BusinessException("该号码已注册!");
            }
            LoggerUtil.error(CustomerBusiServiceImpl.class,e.getMessage());
            //throw new BusinessException("BC0050", ResponseConstant.RESULT_DESCRIPTION_FAILED);
            throw new BusinessException("注册失败!");

        }
    }

    @Override
    public int updatePartner(Integer customerId,Integer gameId)  throws BusinessException  {

        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(customerId);
        int result = customerBusiMapper.setPartner(busiCustomerPojo);
        if(result <= 0){
            LoggerUtil.error(CustomerBusiServiceImpl.class,"updatePartner updateByPrimaryKeySelective error customerId:"+customerId);
            return 0;
        }

        busiCustomerPojo.setGameId(gameId);
        result = customerBusiMapper.updatePartner(busiCustomerPojo);
        if(result == 0){
            LoggerUtil.warn(CustomerBusiServiceImpl.class,"updatePartner result = 0 customerId:"+customerId);
            //return 0;
        }


        //手动激活合伙人 需要删除当前 需要激活的用户 的推荐关系
        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo = customerRecommendRelationInfoServiceImpl.selectRecommendUser(gameId);
        if(busiCustomerRecommendRelationPojo != null){
            BusiCustomerRecommendRelationPojo temp = new BusiCustomerRecommendRelationPojo();
            temp.setId(busiCustomerRecommendRelationPojo.getId());
            temp.setDelFlag((short)1);
            customerRecommendRelationBusiServiceImpl.updateByPrimaryKeySelective(temp);
        }
        return 1;
    }

    @Override
    public int cancelPartner(Integer customerId,Integer gameId)  throws BusinessException  {
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setId(customerId);
        busiCustomerPojo.setIsPartner((short)1);
        int result = updateByPrimaryKeySelective(busiCustomerPojo);
        if(result <= 0){
            LoggerUtil.error(CustomerBusiServiceImpl.class,"cancelPartner updateByPrimaryKeySelective error customerId:"+customerId);
            return 0;
        }

        busiCustomerPojo.setGameId(gameId);
        result = customerBusiMapper.cancelPartner(busiCustomerPojo);
        if(result <= 0){
            LoggerUtil.warn(CustomerBusiServiceImpl.class,"cancelPartner result = 0 customerId:"+customerId);
            //return 0;
        }
        return 1;
    }

    @Override
    public int updateMember(Integer customerId) throws BusinessException {

        LoggerUtil.info(CustomerBusiServiceImpl.class,"updateMember start customerId:"+customerId);
        BusiCustomerPojo updatePojo = new BusiCustomerPojo();
        //生成推荐码
        updatePojo.setRecommendCode(customerId+SecurityCode.getSecurityCode(1, SecurityCode.SecurityCodeLevel.Simple,false));
        updatePojo.setIsMember((short)0);
        updatePojo.setId(customerId);
        createQrcode(updatePojo);
        int result = updateByPrimaryKeySelective(updatePojo);
        LoggerUtil.info(CustomerBusiServiceImpl.class,"updateMember end customerId:"+customerId);

        /*
        BusiCustomerRecommendRelationPojo busiCustomerRecommendRelationPojo = customerRecommendRelationInfoServiceImpl.selectRecommendUser(customerId);
        //后台生成会员  没有推荐人。。
        if(busiCustomerRecommendRelationPojo != null){
            BusiCustomerPojo temp = customerInfoServiceImpl.selectById(busiCustomerRecommendRelationPojo.getRecomCustomerId());
            if(temp != null && temp.getRecommendReward() == 0){
                //未获得奖励 标记
                //TODO 计算用户推荐 是否符合奖励规则
                boolean isOk = customerRecommendRelationInfoServiceImpl.doRecommendReward(temp.getId());
                if(isOk){
                    int updateRecommendRewardResult  = customerBusiMapper.updateRecommendReward(temp);
                    if(updateRecommendRewardResult > 0){
                        //增加账户余额
                        BusiUpdateBankrollModel busiUpdateBankrollModel = new BusiUpdateBankrollModel();
                        busiUpdateBankrollModel.setCustomerId(temp.getId());
                        busiUpdateBankrollModel.setAmountAddOrMinus(BankrollInfoEnum.INCOME.getValue());
                        busiUpdateBankrollModel.setAmount(sysPropertiesUtils.getDecimalValue("recommendReward",new BigDecimal("0")));
                        List<BusiCustomerBankrollFlowModel> list= new ArrayList<>();
                        BusiCustomerBankrollFlowModel busiCustomerBankrollFlowModel = new BusiCustomerBankrollFlowModel();
                        busiCustomerBankrollFlowModel.setAmount(busiUpdateBankrollModel.getAmount());
                        busiCustomerBankrollFlowModel.setFlowType(BankrollInfoEnum.INCOME.getValue());
                        busiCustomerBankrollFlowModel.setFlowNumber(CommonUtil.getSerialnumber());
                        busiCustomerBankrollFlowModel.setFlowDescription(BankrollInfoEnum.RECOMMEND.getValue());
                        busiCustomerBankrollFlowModel.setFlowCategory(BankrollInfoEnum.AMOUNT.getValue());
                        list.add(busiCustomerBankrollFlowModel);
                        busiUpdateBankrollModel.setList(list);
                        customerBankrollBusiServiceImpl.updateBankrollForOrder(busiUpdateBankrollModel);
                        LoggerUtil.info(CustomerBusiServiceImpl.class,"execute RecommendReward success:"+temp.getId());
                    }

                }

            }
        }
        */
        return result;
    }


    @Override
    public int cancelMember(Integer customerId) {
        BusiCustomerPojo updatePojo = new BusiCustomerPojo();
        updatePojo.setId(customerId);
        return  customerBusiMapper.cancelMember(updatePojo);
    }

    @Override
    public void createQrcode(BusiCustomerPojo busiCustomerPojo) {
        try{
            String fileUploadPath = sysPropertiesUtils.getStringValue("fileUploadPath");
            //String fileUploadPath = "d:\\testFile";
            String customerOssRootPath = sysPropertiesUtils.getStringValue("customerOssRootPath");
            String qrcodeImagePath = sysPropertiesUtils.getStringValue("qrcodeImagePath");
            String fileName = CommonUtil.getSerialnumber()+".png";

            String dir = fileUploadPath+customerOssRootPath+qrcodeImagePath;
            String finalPath = dir+File.separator+fileName;
            String inviteUrl = sysPropertiesUtils.getStringValue("inviteUrl");
            //String sign = MD5CoderUtil.encode(busiCustomerPojo.getId()+""+System.nanoTime());

            File logFile = new File(sysPropertiesUtils.getStringValue("qrCodeLogoPath"));
            //https://yqwx.gaoqi99.cn/url/weChatBind?agentId=2082&redirectUrl=https%3A%2F%2Fyqwx.gaoqi99.cn%2Furl%2Ffenxiang
            byte[] b = CustomerUtil.createQrcode("https://yqwx.gaoqi99.cn/url/weChatBind?agentId="+busiCustomerPojo.getGameId()+"&redirectUrl=https%3A%2F%2Fyqwx.gaoqi99.cn%2Furl%2Ffenxiang",logFile);
            java.io.File fileDir = new  java.io.File(dir);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }
            java.io.File qrcodeFile = new  java.io.File(finalPath);
            FileOutputStream fos = new FileOutputStream(qrcodeFile);
            fos.write(b);

            String key = ossFileUpload.fileUpload(qrcodeFile,customerOssRootPath+qrcodeImagePath);
            if(key == null){
                throw new Exception(" oss 用户邀请二维码文件上传失败!"+finalPath);
            }else{
                //busiCustomerPojo.setQrcodeSecret(sign);
                busiCustomerPojo.setPersonQrcode(key);
            }
        }catch(Exception e){
            LoggerUtil.error(CustomerBusiServiceImpl.class,"updateMember 生成用户邀请二维码失败!  "+e.getMessage());
        }
    }

    @Override
    public int bindCustomerByGameId(BusiCustomerPojo busiCustomerPojo)  throws BusinessException{

        //给用户信息设置gameId
        int result = customerBusiMapper.updateGameId(busiCustomerPojo);
        if(result <= 0){
            LoggerUtil.error(CustomerBusiServiceImpl.class,"bindCustomerByGameId failed 用户gameId 可能已存在 id:"+busiCustomerPojo.getId()+"...gameId:"+busiCustomerPojo.getGameId());
            return 0;
        }

        /**
         *gameId查找的账户  若customerId 已存在  则不符合此次操作的业务逻辑
         */
        BusiCustomerBankrollPojo gameUserBankRoll = customerBankrollInfoServiceImpl.selectByGameId(busiCustomerPojo.getGameId());
        if(gameUserBankRoll != null
                && gameUserBankRoll.getCustomerId() == null){

            //清除game用户账户绑定的gameId
            BusiCustomerBankrollPojo tempGame = new BusiCustomerBankrollPojo();
            tempGame.setId(gameUserBankRoll.getId());
            //首先清空原有gameId 防止唯一索引报错
            result = customerBankrollBusiServiceImpl.clearGameId(gameUserBankRoll.getId());
            if(result <= 0){
                throw new BusinessException("clearGameId failed bankrollId:"+gameUserBankRoll.getId());
            }

            BusiCustomerBankrollPojo customerDB = customerBankrollInfoServiceImpl.selectByCustomerId(busiCustomerPojo.getId());
            BusiCustomerBankrollPojo customerTemp = new BusiCustomerBankrollPojo();
            customerTemp.setId(customerDB.getId());
            customerTemp.setGameId(gameUserBankRoll.getGameId());
            customerTemp.setRewardAmount(gameUserBankRoll.getRewardAmount());
            //然后 转移gameId 以及游戏U币账户
            result = customerBankrollBusiServiceImpl.bindGameIdByCustomerId(customerTemp);
            if(result <= 0){
                throw new BusinessException("bindGameIdByCustomerId failed bankrollId:"+customerDB.getId()+"....gameId:"+gameUserBankRoll.getGameId()+"...");
            }

            //修改 UB账户流水
            Map<String,Object> paraMap = new HashMap();
            paraMap.put("bankrollId",gameUserBankRoll.getId());
            paraMap.put("customerBankrollId",customerTemp.getId());
            result = customerBankrollFlowBusiServiceImpl.updateGameIdFlowToCustomer(paraMap);
            //if(result <= 0){
            //    throw new BusinessException("updateGameIdFlowToCustomer failed bankrollId:"+gameUserBankRoll.getId() +"....gameId:"+busiCustomerPojo.getGameId());
            //}
        }else{
            //账户信息设置gameId
            BusiCustomerBankrollPojo customerBankrollPojo = customerBankrollInfoServiceImpl.selectByCustomerId(busiCustomerPojo.getId());
            customerBankrollPojo.setGameId(busiCustomerPojo.getGameId());
            result = customerBankrollBusiServiceImpl.setGameId(customerBankrollPojo);
            if(result <= 0){
                throw new BusinessException("setGameId failed bankrollId:"+customerBankrollPojo.getId() +"....gameId:"+busiCustomerPojo.getGameId());
            }
        }
        return 1;
    }

    @Override
    public int updateGameId(BusiCustomerPojo customerIdPojo){
        return customerBusiMapper.updateGameId(customerIdPojo);
    }

    @Override
    public int handleUserInfo(BusiCustomerPojo busiCustomerPojo, BusiCustomerWechatPojo busiCustomerWechatPojo) {
        try{
            if(busiCustomerWechatPojo == null || busiCustomerPojo == null)
                return 0;

            //处理用户头像和昵称
            BusiCustomerPojo customerTemp = new BusiCustomerPojo();
            customerTemp.setId(busiCustomerPojo.getId());
            boolean doUpdate = false;
            /*
            if(CommonUtil.isEmpty(busiCustomerPojo.getNickName())){
                customerTemp.setNickName(busiCustomerWechatPojo.getNickName());
                doUpdate = true;
            }
            */

            if(CommonUtil.isEmpty(busiCustomerPojo.getPhotoUrl())){
                customerTemp.setPhotoUrl(busiCustomerWechatPojo.getHeadImgUrl());
                doUpdate = true;
            }

            if(CommonUtil.isEmpty(busiCustomerPojo.getSex())){
                if(busiCustomerWechatPojo.getSex() == 1){
                    customerTemp.setSex((short)1);
                }else if(busiCustomerWechatPojo.getSex() == 2){
                    customerTemp.setSex((short)0);
                }else{
                    customerTemp.setSex((short)2);
                }
                doUpdate = true;
            }
            if(doUpdate){
                return customerBusiMapper.updateByPrimaryKeySelective(customerTemp);
            }
            return 0;
        }catch(Exception e){
            LoggerUtil.error(CustomerBusiServiceImpl.class,"微信绑定 修改用户头像、昵称、性别 出错! :"+e.getMessage()+" customerId:"+busiCustomerPojo.getId()+".....openId:"+busiCustomerWechatPojo.getOpenId());
            return 0;
        }
    }

}