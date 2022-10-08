package com.orange.mybatisplus.service.impl;

import com.orange.mybatisplus.entity.User;
import com.orange.mybatisplus.mapper.UserMapper;
import com.orange.mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
