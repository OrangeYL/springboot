package com.orange.mybatisplus.mapper;

import com.orange.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
