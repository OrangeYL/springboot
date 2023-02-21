package com.orange.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.orange.mybatisplus.entity.User;
import com.orange.mybatisplus.mapper.UserMapper;
import com.orange.mybatisplus.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public Page<User> queryPage(Page<User> page, Wrapper<User> queryWrapper) {
        return page.setRecords(this.baseMapper.queryPage(page,queryWrapper));
    }

    @Override
    public List<String> queryList(String sql) {
        return this.baseMapper.queryList(sql);
    }

    @Override
    public List<User> queryData() {
        return this.baseMapper.queryData();
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        for(Integer num : integers){
            System.out.print(num + " ");
        }
        System.out.println();
        List<Integer> list = integers.subList(0, 0);
        System.out.println(list.size());
    }
}
