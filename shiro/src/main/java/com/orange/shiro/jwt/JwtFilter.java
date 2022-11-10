package com.orange.shiro.jwt;

import com.alibaba.fastjson.JSONObject;
import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/21 13:57
 * @description: 鉴权登录拦截器
 */
@Slf4j
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 过滤器拦截请求的入口方法
     * 是否允许访问，如果带有 token，则对 token 进行检查，否则直接通过
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("第一步->检查请求是否携带token");
        if(isLoginAttempt(request,response)){//如为true，则代表携带token,进入executeLogin 方法，检查token是否正确
            log.info("第二步->该请求携带token,进行token正确性检查");
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                throw new AuthenticationException("token无效或错误");
            }
        }
        return false;
    }
    /**
     * Shiro认证操作
     * executeLogin实际上就是先调用createToken来获取token，这里我们重写了这个方法，就不会自动去调用createToken来获取token
     * 然后调用getSubject方法来获取当前用户再调用login方法来实现登录
     * 这也解释了我们为什么要自定义jwtToken，因为我们不再使用Shiro默认的UsernamePasswordToken了。
     * */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        JwtToken jwtToken = new JwtToken(token);
        try {
            //交给自定义的realm对象去登录，如果错误他会抛出异常并被捕获
            log.info("第三步->进行shiro认证");
            getSubject(request,response).login(jwtToken);
            return true;
        } catch (AuthenticationException e) {
            throw new AuthenticationException("shiro认证失败",e);
        }
    }

    /**
     * @description: 判断请求头中是否携带token,即检查用户是否登录
     * @author: Li ZhiCheng
     * @date: 2022/10/21 14:01
     * @param: [request, response]
     * @return: boolean
     **/
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //获取请求头
        String token = httpServletRequest.getHeader("token");
        return token != null;
    }
    /**
     * 认证失败时，自定义返回json数据
     *
     * @param request  请求
     * @param response 响应
     * @return boolean* @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Result result = Result.error("token无效");
        Object parse = JSONObject.toJSON(result);
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(parse);
        return super.onAccessDenied(request, response);
    }

    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
