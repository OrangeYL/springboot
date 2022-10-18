package com.orange.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orange.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/17 19:18
 * @description:
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
    /**
     * 根据jobClassName查询
     * @param jobClassName 任务类名
     * @return
     */
    public List<QuartzJob> findByJobClassName(@Param("jobClassName") String jobClassName);
}
