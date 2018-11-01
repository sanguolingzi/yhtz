package com.yinhetianze.business.customer.executor;

import com.yinhetianze.business.customer.model.BusiSmsCodeModel;
import com.yinhetianze.business.customer.util.CustomerConstant;
import com.yinhetianze.common.business.sys.sysproperties.cachedata.SysPropertiesUtils;
import com.yinhetianze.core.business.AbstractRestBusiExecutor;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.httprequest.BasicModel;
import com.yinhetianze.core.business.httpresponse.message.ErrorMessage;
import com.yinhetianze.core.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 获取短信验证码
 */

@Component
public class GetSmsCodeExecutor extends AbstractRestBusiExecutor<String> {


    @Autowired
    private RedisManager redisManager;

    @Autowired
    private SysPropertiesUtils sysPropertiesUtils;

    @Override
    protected String executeBusiness(HttpServletRequest request, HttpServletResponse response, BasicModel model, Object... params) throws BusinessException {
        BusiSmsCodeModel busiSmsCodeModel = (BusiSmsCodeModel)model;
        String busiType = busiSmsCodeModel.getBusiType();
        try{
            String smsCode = busiSmsCodeModel.getSmsCode();
            String suffix = busiSmsCodeModel.getSuffix();
            Map map = new HashMap();

            //------------------ 注册短信验证码 -----------------------------
            if("1".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_regist,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 修改密码短信验证码 -----------------------------
            }else if("2".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_loginPassword,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
            }
            //------------------ 微信绑定短信验证码 -----------------------------
            else if("3".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_wxBound,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 忘记密码短信验证码 -----------------------------
            }else if("4".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_forgetPassword,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 修改支付密码短信验证码 -----------------------------
            }else if("5".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_payPassword,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 修改手机号短信验证码 -----------------------------
            }else if("6".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_verifyCode,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 登陆短信验证码 -----------------------------
            }else if("7".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_login,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
                //------------------ 游戏用户绑定已有账号短信验证码 -----------------------------
            }else if("8".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_gameBound,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
            }else if("9".equalsIgnoreCase(busiType)){
                map.put("code",smsCode);
                if(SendSmsUtil.sendSms(SendSmsUtil.sms_template_accountNotification,busiSmsCodeModel.getPhone(),map)){
                    //if(true){
                    //redis 3分钟有效期 和 短信平台默认过期时间 一致
                    String key = busiSmsCodeModel.getPhone()+ suffix;
                    redisManager.setValue(key,smsCode,new Long(1000*180));
                    return "success";
                }
            }

            throw new Exception();
        }catch(Exception e){
            LoggerUtil.error(GetSmsCodeExecutor.class,e.toString());
            throw new BusinessException("系统忙,请稍后再试!");
        }
    }


    @Override
    protected ErrorMessage validate(HttpServletRequest request, BasicModel model, Object[] params) {
        ErrorMessage errorMessage = new ErrorMessage();
        BusiSmsCodeModel busiSmsCodeModel = (BusiSmsCodeModel)model;
        String phone = busiSmsCodeModel.getPhone();
        if(CommonUtil.isEmpty(phone)){
            errorMessage.rejectNull("phone",null,"手机号");
            return errorMessage;
        }
        if(!phone.matches(CustomerConstant.phoneRegex)){
            errorMessage.rejectError("phone","BC0017","");
            return errorMessage;
        }

        if(CommonUtil.isEmpty(busiSmsCodeModel.getBusiType())){
            errorMessage.rejectNull("busiType",null,"短信业务类型");
            return errorMessage;
        }
        String ip = getIp(request);
        String busiType = busiSmsCodeModel.getBusiType();

        RedisScript redisScript = new DefaultRedisScript(script,String.class);
        RedisTemplate redisTemplate = redisManager.getRedisTemplate();
        //redisManager.getRedisTemplate().execute(redisScript,);
        //redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new StringRedisSerializer());
        //redisManager.getRedisTemplate().afterPropertiesSet();

        //ip访问指定时间
        String ipTimeLimit = sysPropertiesUtils.getStringValue("ipTimeLimit","1");
        //ip访问次数限制 ipTimeLimit 时间内访问ipCountLimit次数
        String ipCountLimit = sysPropertiesUtils.getStringValue("ipCountLimit","100");
        //限制访问时间
        String ipTime = sysPropertiesUtils.getStringValue("ipTime","3");
        //业务访问时间限制
        String businessTimeLimit = sysPropertiesUtils.getStringValue("businessTimeLimit","3");
        //业务访问次数限制  businessTimeLimit 时间内 访问businessCountLimit次数
        String businessCountLimit = sysPropertiesUtils.getStringValue("businessCountLimit","5");

        Object result = redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(),new ArrayList<>()
                ,ip,busiType,phone,ipTimeLimit,ipCountLimit,businessTimeLimit,businessCountLimit,ipTime);

        if("success".equals(result)){

            String suffix = getSmsCodeSuffix(busiType);
            if(suffix == null){
                errorMessage.rejectErrorMessage("busiType","短信业务参数不正确!","短信业务参数不正确!");
                return  errorMessage;
            }
            busiSmsCodeModel.setSuffix(suffix);

            //判断是否有未消费的短信验证码 若存在 则拿出来同一个验证码继续发送
            long expire = redisManager.getRedisTemplate().getExpire(phone+suffix);
            //代表已过期 则重新生成验证码
            if(expire == -2 || expire == 0){
                String smsCode = SecurityCode.getSecurityCode(6, SecurityCode.SecurityCodeLevel.Simple,false);
                busiSmsCodeModel.setSmsCode(smsCode);
            }else{//防止有验证码为null 进行二次判断
                Object smsCode = redisTemplate.opsForValue().get(phone+suffix);
                if(CommonUtil.isEmpty(smsCode)){
                    busiSmsCodeModel.setSmsCode(SecurityCode.getSecurityCode(6, SecurityCode.SecurityCodeLevel.Simple,false));
                }else{
                    busiSmsCodeModel.setSmsCode(smsCode.toString());
                }
            }

        }else if("ipLimit".equals(result) || "businessCountLimit".equals(result)){
            errorMessage.rejectErrorMessage("limit","访问过于频繁!","访问过于频繁!");
            LoggerUtil.error(GetSmsCodeExecutor.class,"访问过于频繁!  ip:"+ip+"...phone:"+phone+"...busiType:"+busiType);
            return errorMessage;
        }

        //errorMessage.rejectErrorMessage("limit","测试用报错","测试用报错");
        return errorMessage;
    }
    //阿里云默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天。

    //得到ip 判断是否被限制
    //判断是否有ip+'..limit' 对象 若有 则被限制 返回

    //接着判断是否存在 key为 ip+'-key'的对象
    //     若存在 则代表近ipTimeLimit分钟内有短信发送次数 判断次数是否达到设置值ipCountLimit
    //            达到设置值 则进行限制 同时 设置ip+'..limit' 对象 存在时间为
    //            未达到设置值 则该次数累加
    //     若不存在 则代表近ipTimeLimit分钟内 没有短信发送 设置一个  ip+'-key'的对象 初始次数为1

    // 判断ip+phone+busiType 组合key 是否存在
    //     若存在 则代表近businessTimeLimit内有短信发送次数 判断该次数值是否达到businessCountLimit
    //            达到设置值 则返回限制状态
    //            未达到设置值 则该次数累加
    //     若不存在 则代表近businessTimeLimit内有短信发送次数 没有短信发送 设置一个  ip+phone+busiType 组合key的对象 初始次数为1
    //
    private static String script = "" +
            //
            " local ip = ARGV[1]" +
            " local busiType = ARGV[2]" +
            " local phone = ARGV[3]" +
            //ip业务判断时间 1分钟 默认1分钟
            " local ipTimeLimit = tonumber(ARGV[4])" +
            //ip业务判断次数 在ipTimeLimit 时间内 访问 ipCountLimit次 默认500
            " local ipCountLimit = tonumber(ARGV[5])" +
            //个人业务判断时间 默认3分钟
            " local businessTimeLimit = tonumber(ARGV[6])" +
            //个人业务判断访问次数 在businessTimeLimit时间内容 访问businessCountLimit次数 默认5
            " local businessCountLimit =  tonumber(ARGV[7])" +
            //限制ip访问时间 默认3分钟
            " local ipTime = tonumber(ARGV[8])" +

            " local ipKey = ip..'-key'" +
            " local ipLimit = ip..'-limit'" +
            " local businessLimit = ip..'-'..phone..'-'..busiType..'-business'" +

            //" redis.log(redis.LOG_DEBUG,'ipTimeLimit:'..ipTimeLimit) "+
            //" redis.log(redis.LOG_DEBUG,'ipCountLimit:'..ipCountLimit) "+
            //" redis.log(redis.LOG_DEBUG,'businessTimeLimit:'..businessTimeLimit) "+
            //" redis.log(redis.LOG_DEBUG,'businessCountLimit:'..businessCountLimit) "+
            //" redis.log(redis.LOG_DEBUG,'ipTime:'..ipTime) "+

            //"--判断是否存在ip限制" +
            " local ipLimitTtl = redis.call('ttl',ipLimit)" +
            " if(ipLimitTtl ~= -2 and ipLimitTtl ~= 0)" +
            " then" +
            "   return 'ipLimit'" +
            " end" +
            "" +
            //"--判断是否存在ip次数限制 " +
            " local ipTtl = redis.call('ttl',ipKey)" +
            " local ipCount = 0"+
            " if(ipTtl ~= -2 and ipTtl ~= 0)" +
            " then" +
            "     ipCount = tonumber(redis.call('get',ipKey));   " +
            " end"+
            //"--大于指定次数 设置一个限制" +
            " if(ipCount >= ipCountLimit)" +
            " then" +
            "    redis.call('setex',ipLimit,ipTime*60,ip)" +
            "    return 'ipLimit'" +
            " end"+
            //"--判断业务号码 是否达到限制条件3分钟5次 3分钟 通过判断是否过期来实现" +
            " local businessLimitTtl = redis.call('ttl',businessLimit)" +
            " local businessCount = 0;" +
            " if(businessLimitTtl ~= -2 and businessLimitTtl ~= 0)" +
            " then" +
            "    businessCount = tonumber(redis.call('get',businessLimit))" +
            " end" +
            "" +
            " if(businessCount >= businessCountLimit)" +
            " then" +
            "    return 'businessCountLimit'" +
            " end" +
            "" +
            " if(businessLimitTtl == -2 or businessLimitTtl == 0)" +
            " then" +
            "   redis.call('setex',businessLimit,businessTimeLimit*60,1)" +
            " else" +
            "   redis.call('incr',businessLimit)" +
            " end" +
            "" +
            "" +
            " if(ipTtl == -2 or ipTtl == 0)" +
            " then" +
            "   redis.call('setex',ipKey,ipTimeLimit*60,1)" +
            " else" +
            "   redis.call('incr',ipKey)" +
            " end" +
            "" +
            " return 'success'";
    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }


    private String getSmsCodeSuffix(String busiType){

        if("1".equals(busiType)){
            return CustomerConstant.regeisterSufixKey;   //注册业务
        }else if("2".equals(busiType)){
            return CustomerConstant.updPasswordSufixKey; //------------------ 修改密码短信验证码 -----------------------------
        }else if("3".equals(busiType)){
            return CustomerConstant.wechatBindSufixKey; //------------------ 微信绑定短信验证码 -----------------------------
        }else if("4".equals(busiType)){
            return CustomerConstant.forgetPassSufixKey; //------------------ 忘记密码短信验证码 -----------------------------
        }else if("5".equals(busiType)){
            return CustomerConstant.updPayPasswordSufixKey; //------------------ 修改支付密码短信验证码 -----------------------------
        }else if("6".equals(busiType)){
            return CustomerConstant.updPhoneSufixKey;//------------------ 修改手机号短信验证码 -----------------------------
        }else if("7".equals(busiType)){
            return CustomerConstant.userLoginSufixKey;//------------------ 登陆短信验证码 -----------------------------
        }else if("8".equals(busiType)){
            return CustomerConstant.userBindGameSufixKey;//------------------ 游戏用户绑定已有账号短信验证码 -----------------------------
        }else if("9".equals(busiType)){
            return CustomerConstant.accountNotification;//------------------ 游戏用户绑定已有账号短信验证码 -----------------------------
        }
        return null;
    }

}
