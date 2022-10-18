package com.orange.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.orange.quartz.entity.User;
import com.orange.quartz.mapper.UserMapper;
import com.orange.quartz.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Li ZhiCheng
 * @since 2022-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
