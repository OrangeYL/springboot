package com.orange.security.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/8 14:19
 * @description: Jwt工具类，用来生成token,校验token
 */
public class JwtUtils {

    //过期时间30分钟
    private static final long EXPIRE_TIME = 30*60*1000;
    //密钥
    private static final String SECRET = "123456";

    /**
     * 生成token签名且在EXPIRE_TIME分钟后过期
     * @param username 用户名字
     * @param id 用户id
     * @return
     */
    public static String createToken(Long id,String username){
        String token = Jwts.builder()
                .setSubject("ORANGE")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .claim("id", id)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    /**
     * 获得token中的信息--id,无需secret解密也能获得
     * @param token
     * @return
     */
    public static Long getId(String token){
        try {
            if(StringUtils.isEmpty(token)){
                return null;
            }
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Long id = (Long) claims.get("id");
            return id;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获得token中的信息--username,无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getUsername(String token){
        try {
            if(StringUtils.isEmpty(token)){
                return null;
            }
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String username = (String) claims.get("username");
            return username;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String token = createToken(1L, "orange");
        System.out.println(token);
        String username = getUsername(token);
        System.out.println(username);
    }
}
