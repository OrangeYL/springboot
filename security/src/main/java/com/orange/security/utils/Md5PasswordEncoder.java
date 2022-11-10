package com.orange.security.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/8 19:28
 * @description: 自定义密码加密,MD5加密
 */
@Component
public class Md5PasswordEncoder implements PasswordEncoder {


    @Override
    public String encode(CharSequence password) {
        return MD5.encrypt(password.toString());
    }

    @Override
    public boolean matches(CharSequence password, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(password.toString()));
    }

    public static void main(String[] args) {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        String password = passwordEncoder.encode("111111");
        System.out.println(password);
    }
}
