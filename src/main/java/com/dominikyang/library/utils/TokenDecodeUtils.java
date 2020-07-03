package com.dominikyang.library.utils;

import com.auth0.jwt.JWT;
import com.dominikyang.library.exception.GlobalException;
import com.dominikyang.library.result.CodeMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * 创建人：肖易安
 * 创建时间：  2020/7/3
 * 注释：null
 **/
public class TokenDecodeUtils {
    private TokenDecodeUtils(){}

    public static String getUserId(HttpServletRequest httpServletRequest) throws GlobalException {
        String userId = null;
        try{
            userId = JWT.decode(httpServletRequest.getHeader("token")).getAudience().get(0);
        }catch (Exception e){
            throw new GlobalException(CodeMessage.WRING_REQUEST);
        }
        if(userId==null || userId.length()<=0){
            throw new GlobalException(CodeMessage.WRING_REQUEST);
        }
        return userId;
    }
}
