package com.orange.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.orange.quartz.entity.QuartzJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/17 19:21
 * @description:
 */
public interface QuartzJobService extends IService<QuartzJob> {
    /**
     * 通过类名寻找定时任务
     * @param jobClassName 类名
     * @return List<QuartzJob>
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
    /**
     * 保存定时任务
     * @param quartzJob
     * @return boolean
     */
    boolean saveAndScheduleJob(QuartzJob quartzJob);
    /**
     * 执行定时任务（执行一次）
     * @param quartzJob
     * @throws Exception
     */
    void execute(QuartzJob quartzJob) throws Exception;

    boolean editAndScheduleJob(QuartzJob quartzJob) throws SchedulerException;

    boolean deleteAndStopJob(QuartzJob quartzJob);

    boolean resumeJob(QuartzJob quartzJob);
    /**
     * 暂停任务
     * @param quartzJob
     * @throws SchedulerException
     */
    void pause(QuartzJob quartzJob);

}
