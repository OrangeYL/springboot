package com.orange.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Li ZhiCheng
 * @since 2022-10-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    public List<User> queryPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);

    public List<String> queryList(@Param("sql") String sql);

    public List<User> queryData();
}
