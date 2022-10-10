package com.orange.easypoi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.easypoi.entity.Education;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/9 10:12
 * @description:
 */
@Mapper
public interface EducationMapper extends BaseMapper<Education> {
    /**
     * @description: 批量插入
     * @author: Li ZhiCheng
     * @date: 2022/10/10 10:26
     * @param: [list]
     * @return: java.lang.Boolean
     **/
    public Boolean insertBatch(List<Education> list);

    /**
     * @description: 根据员工id查询
     * @author: Li ZhiCheng
     * @date: 2022/10/10 10:27
     * @param: [employerId]
     * @return: java.util.List<com.orange.easypoi.entity.Education>
     **/
    public List<Education> selectByEmployerId(int employerId);
}
