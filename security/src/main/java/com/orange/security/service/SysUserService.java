package com.orange.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.common.utils.Result;
import com.orange.security.entity.SysUser;
import com.orange.security.vo.LoginVo;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/9 10:43
 * @description:
 */
public interface SysUserService  extends IService<SysUser> {

    //登录接口
    public Result<?> login(LoginVo loginVo);
}
