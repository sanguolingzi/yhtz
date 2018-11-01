package com.yinhetianze.core.business.handlers.impl;

import com.yinhetianze.cachedata.errorcode.ErrorCodeInfo;
import com.yinhetianze.common.business.sys.errorcode.cachedata.ErrorCodeUtils;
import com.yinhetianze.core.business.BusinessException;
import com.yinhetianze.core.business.handlers.ExceptionHandler;
import com.yinhetianze.core.business.httpresponse.ResponseConstant;
import com.yinhetianze.core.business.httpresponse.ResponseData;
import com.yinhetianze.core.business.httpresponse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

@Service
public class CommonExceptionHandlerImpl implements ExceptionHandler{

    @Autowired
    private ErrorCodeUtils errorCodeUtils;

    @Override
    public ResponseData handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ResponseData responseData = new ResponseData();
        Result result = null;
        if(e instanceof BusinessException){
            BusinessException businessException = (BusinessException)e;
            ErrorCodeInfo errorCodeInfo = errorCodeUtils.getErrorCodeInfo( businessException.getErrorCode());
            String message = errorCodeInfo!=null?errorCodeInfo.getErrorMessage():"";
            String errorMessage = businessException.getErrorMessage();
            String[] params = errorMessage!=null?errorMessage.split(","):new String[]{};
            StringJoiner stringJoiner = new StringJoiner(",","","");
            //获取 errorfield 对应的 错误信息内容
            //错误信息  如{}信息不存在、 {0}长度不能小于{1}  {}占位符可能有0个 也可能有多个
            //相应的内容 由 businessException.getErrorMessage 来填充 {} 有几个 businessException.getErrorMessage 则需要有对应数量的内容 由,分隔
            if(!StringUtils.isEmpty(message)){
                if(message.indexOf("{")>=0 && params.length > 0 ){
                    for(int i = 0;i<params.length;i++){
                        message=message.replace("{"+i+"}",params[i].toString());
                    }
                    stringJoiner=stringJoiner.add(message);
                }else{
                    stringJoiner=stringJoiner.add(message);
                }
            }else{//模板和错误信息没有 则取 businessException.getErrorMessage()
                stringJoiner=stringJoiner.add(errorMessage);
            }
            result = new Result();
            /**
             * code     ResponseConstant.RESULT_CODE_BUSI_FAILED 固定 此类 代表业务异常
             * message  ResponseConstant.RESULT_DESCRIPTION_FAILED 固定 此类 代表业务异常
             * Description  根据businessException 中 errorcode 找到表中对应的错误信息模板 若有模板 则用ErrorMessage进行填充 否则 直接使用ErrorMessage
             *          否则 同 Description
             */
            result.setCode(ResponseConstant.RESULT_CODE_BUSI_FAILED);
            result.setMessage(ResponseConstant.RESULT_DESCRIPTION_FAILED);
            result.setDescription(stringJoiner.toString());
        }else{
            /**
             * 非businessException 统一返回 RESULT_DESCRIPTION_FAILED
             * 具体exception内容 由日志记录 不返回给客户端
             */
            result = new Result(ResponseConstant.RESULT_DESCRIPTION_FAILED);
        }
        responseData.setResultInfo(result);
        return responseData;
    }
}
