package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiCustomerModel;
import com.yinhetianze.business.customer.model.BusiRegeisterModel;
import com.yinhetianze.business.customer.service.busi.CustomerBusiService;
import com.yinhetianze.business.customer.service.info.CustomerInfoService;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.CommonUtil;
import com.yinhetianze.core.utils.MD5CoderUtil;
import com.yinhetianze.core.utils.RedisManager;
import com.yinhetianze.pojo.customer.BusiCustomerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  消费者注册接口
 */

@Component
public class RegeisterExecutor extends AbstractRestBusiExecutor<Object> {


    @Autowired
    private CustomerBusiService customerBusiServiceImpl;

    @Autowired
    private CustomerInfoService customerInfoServiceImpl;

    @Autowired
    private RedisManager redisManager;



    @Override
    protected Object executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;
        int customerId = customerBusiServiceImpl.addRegeisterCustomer(busiRegeisterModel);
        redisManager.deleteValue(busiRegeisterModel.getPhone()+CustomerConstant.regeisterSufixKey);

        if("false".equalsIgnoreCase(busiRegeisterModel.getCheckSmsCode())){
            //手机号短信验证码登陆之后绑定 推荐码 在这里 需要删除 操作标记
            redisManager.deleteValue(busiRegeisterModel.getPhone()+CustomerConstant.currentUserOperator);
        }

        BusiCustomerModel busiCustomerModel  = new BusiCustomerModel();
        busiCustomerModel.setCheckPassword(false);
        busiCustomerModel.setId(customerId);
        Map<String,Object> userInfo = customerInfoServiceImpl.login(busiCustomerModel);
        return userInfo;
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage  = new ErrorMessage();
        BusiRegeisterModel busiRegeisterModel = (BusiRegeisterModel)model;
        if(CommonUtil.isEmpty(busiRegeisterModel.getPhone())){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }
        /**
         * 利用redis 来控制同一个号码并发注册 校验结束之后
         *
        //步骤1
        boolean setIfAbsent = redisManager.getRedisTemplate().
                opsForValue().setIfAbsent(busiRegeisterModel.getPhone(),System.nanoTime());
        //步骤2
        redisManager.getRedisTemplate().expire(busiRegeisterModel.getPhone(),1000, TimeUnit.MILLISECONDS);
        if(!setIfAbsent){
            if(redisManager.getRedisTemplate().getExpire(busiRegeisterModel.getPhone()) == -1){
                //进入这里 代表之前操作的步骤2失败
                //获取key对应的值   value当时设置这个key的 nanotime
                //这里就是继续要有一个控制并发的操作
                //利用getandset 获取之前的nanotime 同时将目前最新的nanotime设置进去
                //将获取的到的nanotime和当前nanotime做比较
                //满足小于条件的  则代表抢占到锁  其余的则异常处理
                long nanoTime = System.nanoTime()+10000;
                long lastNanoTime = (Long)redisManager.getRedisTemplate().opsForValue().getAndSet(busiRegeisterModel.getPhone(),nanoTime);
                if(lastNanoTime < nanoTime){
                    redisManager.getRedisTemplate().expire(busiRegeisterModel.getPhone(),1000, TimeUnit.MILLISECONDS);
                }else{
                    errorMessage.rejectError("phone","BC0026","");
                    return errorMessage;
                }
            }else{
                errorMessage.rejectError("phone","BC0026","");
                return errorMessage;
            }
        }
        redisManager.getRedisTemplate().delete(busiRegeisterModel.getPhone());
         */
        String phone = busiRegeisterModel.getPhone();
        if(!phone.matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        //String phone = getMobile();
        //busiRegeisterModel.setPhone(phone);
        //--------------------- 查看注册号码是否已存在 --------------------------------------------------------
        BusiCustomerPojo busiCustomerPojo = new BusiCustomerPojo();
        busiCustomerPojo.setPhone(phone);
        busiCustomerPojo = customerInfoServiceImpl.selectOne(busiCustomerPojo);
        if(busiCustomerPojo != null){
            errorMessage.rejectError("phone","BC0026","");
            return errorMessage;
        }

        //--------------------- 校验密码是否存在 --------------------------------------------------------
        /*
        String loginPassword = busiRegeisterModel.getLoginPassword();
        if(CommonUtil.isEmpty(loginPassword)){
            errorMessage.rejectError("loginPassword","BC0029","");
            return errorMessage;
        }
        */

        //--------------------- 密码格式复杂度校验 --------------------------------------------------------
        //TODO



        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------

        if("true".equalsIgnoreCase(busiRegeisterModel.getCheckSmsCode())){
            String smsCode = busiRegeisterModel.getSmsCode();

            if(CommonUtil.isEmpty(smsCode)){
                errorMessage.rejectNull("smsCode",null,"短信验证码");
                return errorMessage;
            }

            if(CustomerConstant.commonSmsCode.equals(smsCode) ||  "backSendRequest".equals(busiRegeisterModel.getModelName())){
                //return null;
            }else{
                Object code = redisManager.getValue(phone+CustomerConstant.regeisterSufixKey);
                if(CommonUtil.isEmpty(code)){
                    errorMessage.rejectError("smsCode","BC0029","");
                    return errorMessage;
                }

                if(!code.toString().equals(smsCode)){
                    errorMessage.rejectError("smsCode","BC0053","");
                    return errorMessage;
                }
            }
        }else if("false".equalsIgnoreCase(busiRegeisterModel.getCheckSmsCode())){

            Object value = redisManager.getValue(busiRegeisterModel.getPhone()+ CustomerConstant.currentUserOperator);
            if(CommonUtil.isEmpty(value)){
                errorMessage.rejectErrorMessage("info","操作超时!","操作超时!");
                return errorMessage;
            }
        }else{
            errorMessage.rejectErrorMessage("info","CheckSmsCode参数异常!","CheckSmsCode参数异常!");
            return errorMessage;
        }


        //--------------------- 校验短信验证码内容非空 以及 内容是否匹配 start-----------------------------------


        //--------------------- 校验推荐人推荐码 以及 推荐人身份 是否合格 start-----------------------------------
        /*
        if(CommonUtil.isEmpty(busiRegeisterModel.getRecommendCode())){
            errorMessage.rejectNull("recommendCode",null,"会员推荐码");
            return errorMessage;
        }

        if(busiRegeisterModel.getRecommendCode().equalsIgnoreCase(CustomerConstant.systemRecommend)){

        }else{
            busiCustomerPojo = customerInfoServiceImpl.selectByRecommendCode(busiRegeisterModel.getRecommendCode());
            if(busiCustomerPojo == null){
                errorMessage.rejectErrorMessage("recommendCode","推荐码不存在","推荐码不存在");
                return errorMessage;
            }
            //TODO 用户身份是否为 会员 或者 合伙人
            if(busiCustomerPojo.getIsMember() == 1){
                errorMessage.rejectError("isMember","","推荐人不是会员");
                return errorMessage;
            }

            //若是扫码过来的信息 需要校验一下 secret

            //if(CommonUtil.isNotEmpty(busiRegeisterModel.getSecret())
            //    && !busiRegeisterModel.getSecret().equals(busiCustomerPojo.getQrcodeSecret())){
            //    errorMessage.rejectError("phone","BC0025","");
            //    return errorMessage;
            //}

        }
        */
        //--------------------- 校验推荐人号码 以及 推荐人身份 是否合格 end-----------------------------------
        //TODO 获取gameId
        if(CommonUtil.isNotEmpty(busiRegeisterModel.getGameKey())){
            Map<String,Object> dataMap = (Map<String,Object>)redisManager.getValue(busiRegeisterModel.getGameKey());
            boolean gameInfoOk = true;
            if(dataMap != null){
                String gameId = dataMap.getOrDefault("gameId","").toString();
                if(CommonUtil.isNotEmpty(gameId)){
                    busiCustomerPojo = customerInfoServiceImpl.selectByGameId(Integer.parseInt(gameId));
                    if(busiCustomerPojo != null){
                        errorMessage.rejectErrorMessage("gameId","游戏信息已绑定!","游戏信息已绑定!");
                        return errorMessage;
                    }
                    busiRegeisterModel.setGameId(gameId.isEmpty()?null:Integer.parseInt(gameId));
                }else{
                    gameInfoOk = false;
                }


                String pGameId = dataMap.getOrDefault("pGameId","").toString();
                if(CommonUtil.isNotEmpty(pGameId))
                busiRegeisterModel.setpGameId(Integer.parseInt(pGameId));
            }else{
                gameInfoOk = false;
            }

            if(!gameInfoOk){
                    errorMessage.rejectErrorMessage("gameId","游戏信息异常!","游戏信息异常!");
                    return errorMessage;
            }
        }
        return null;
    }

    public static boolean isMobileNO(String mobiles) {

        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher matcher = pattern.matcher(mobiles);

        return matcher.matches();
    }

    // 返回随机电话号码
    public static String getMobile() {

        while (true) {
            String randomPhone = randomPhone();
            if (isMobileNO(randomPhone)) {
                return randomPhone;
            }

        }

    }

    // 产生随机电话号码格式数字
    public static String randomPhone() {
        String phone = "1";

        Random random = new Random();
        int nextInt = random.nextInt(3);

        if (nextInt == 0) {
            phone = phone + "3" + randomNumber();
        } else if (nextInt == 1) {
            phone = phone + "5" + randomNumber();
        } else {
            phone = phone + "8" + randomNumber();
        }
        return phone;
    }

    // 生成长度为9的随机数
    public static String randomNumber() {

        Random random = new Random();
        int nextInt = random.nextInt(900000000) + 100000000;
        int abs = Math.abs(nextInt);
        String valueOf = String.valueOf(abs);
        return valueOf;
    }

    public static void main(String[] args) {
        int test = 1;
        while (test > 0) {
            System.out.println(getMobile());
            test--;
        }

        System.out.println(MD5CoderUtil.encode("sys_yhtz_recommend"));
    }

}
