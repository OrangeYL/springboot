package com.orange.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.mybatisplus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Li ZhiCheng
 * @since 2022-10-08
 */
public interface IUserService extends IService<User> {
    public Page<User> queryPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);

    public List<String> queryList(@Param("sql") String sql);
}
