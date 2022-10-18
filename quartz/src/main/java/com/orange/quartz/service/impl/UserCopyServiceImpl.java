package com.orange.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.mybatisplus.entity.User;
import com.orange.mybatisplus.mapper.UserMapper;
import com.orange.mybatisplus.service.IUserService;
import com.orange.quartz.entity.UserCopy;
import com.orange.quartz.mapper.UserCopyMapper;
import com.orange.quartz.service.IUserCopyService;
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
public class UserCopyServiceImpl extends ServiceImpl<UserCopyMapper, UserCopy> implements IUserCopyService {

}
