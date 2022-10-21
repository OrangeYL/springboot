package com.orange.shiro.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/21 13:44
 * @description: 实现AuthenticationToken 使Realm的doGetAuthenticationInfo能够获取到token进行验证
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
