package com.orange.mybatis.service;


import com.orange.mybatis.entity.User;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/6 10:58
 * @description:
 */
public interface UserService {
    /**
     * 删除操作
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入操作
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 插如操作
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 根据id查询操作
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 更新操作
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新操作
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);
}
