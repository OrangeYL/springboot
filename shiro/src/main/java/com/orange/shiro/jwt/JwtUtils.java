package com.orange.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.orange.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/21 13:46
 * @description: Jwt工具类，用来生成token,校验token
 */
@Slf4j
public class JwtUtils {
    //过期时间30分钟
    private static final long EXPIRE_TIME=30*60*1000;

    /**
     * 生成token签名且在EXPIRE_TIME分钟后过期
     * @param username 名字
     * @param secret 密码
     * @return
     */
    public static String sign(String username,String secret){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                // 附带username信息
                .withClaim("username",username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
    /**
     * 校验token是否正确
     * @param token
     * @param username
     * @param secret
     * @return
     */
    public static boolean verify(String token,String username,String secret){
        try {
            //根据密码生成JWT校验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            //校验token
            verifier.verify(token);
            log.info("第五步->验证成功");
            return true;
        } catch (Exception e) {
            log.info("验证失败");
            return false;
        }
    }
    /**
     * 获得token中的信息,一般是username,无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getUsername(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (Exception e) {
            throw new BizException("token错误",e);
        }
    }
}
