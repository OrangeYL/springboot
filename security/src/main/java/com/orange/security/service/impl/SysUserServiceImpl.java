package com.orange.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.BizException;
import com.orange.common.utils.Result;
import com.orange.security.entity.SysUser;
import com.orange.security.mapper.SysUserMapper;
import com.orange.security.service.SysUserService;
import com.orange.security.utils.JwtUtils;
import com.orange.security.utils.MD5;
import com.orange.security.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Li ZhiCheng
 * @create: 2022-11-2022/11/9 10:44
 * @description:
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    //登录接口
    @Override
    public Result<?> login(LoginVo loginVo) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginVo.getUsername());
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if(null == sysUser) {
            throw new BizException("账号不存在！");
        }
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new BizException("密码错误！");
        }
        if(sysUser.getStatus() == 0) {
            throw new BizException("账号已停用！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtUtils.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.success(map);
    }
}
