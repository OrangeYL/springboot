package com.orange.easypoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.easypoi.entity.Employer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/9 10:11
 * @description:
 */
@Mapper
public interface EmployerMapper extends BaseMapper<Employer> {
    /**
     * @description: 批量插入
     * @author: Li ZhiCheng
     * @date: 2022/9/1 14:51
     * @param: [list]
     * @return: void
     **/
    public Boolean insertBatch(List<Employer> list);

    /**
     * @description: 查询所有员工
     * @author: Li ZhiCheng
     * @date: 2022/9/1 16:20
     * @param: []
     * @return: java.util.List<com.orange.demo.entity.Employer>
     **/
    public List<Employer> selectAll();
}
