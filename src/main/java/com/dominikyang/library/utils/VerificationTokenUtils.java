package com.dominikyang.library.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.dominikyang.library.entity.LogWarn;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;
import com.dominikyang.library.result.OperatorCode;
import com.dominikyang.library.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/9
 * 注释：null
 **/
public class VerificationTokenUtils {
    private VerificationTokenUtils(){}

    public static boolean redisToHeader(HttpServletRequest httpServletRequest, OperatorCode operatorCode, LogService logService, RedisTemplate<String,Object> redisTemplate) throws GlobalException {
        String token = "";
        String userId = "" ;
        String redisToken = "" ;
        try{
            token = httpServletRequest.getHeader("token");
            userId = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            throw new GlobalException(CodeMessage.WRING_REQUEST);
        }
        redisToken = (String) redisTemplate.opsForValue().get(userId);
        if(redisToken==null || redisToken.equals("")){
            LogWarn logWarn = new LogWarn(
                    CodeMessage.NO_TOKEN.getCode()+"",
                    CodeMessage.NO_TOKEN.getMessage(),
                    "进行操作："+operatorCode.getName()+" 时，Redis中Token过期",
                    new Date());
            logService.addLogWarn(logWarn);
            throw new GlobalException(CodeMessage.NO_TOKEN);
        }else if(token.equals(redisToken)){
            return true ;
        }else{
            LogWarn logWarn = new LogWarn(
                    CodeMessage.NO_TOKEN.getCode()+"",
                    CodeMessage.NO_TOKEN.getMessage(),
                    "进行操作："+operatorCode.getName()+" 时，Token校验失败",
                    new Date());
            logService.addLogWarn(logWarn);
            throw new GlobalException(CodeMessage.VERIFY_FAIL);
        }
    }
}
