package com.orange.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.common.exception.BizException;
import com.orange.common.utils.DateUtils;
import com.orange.quartz.entity.QuartzJob;
import com.orange.quartz.mapper.QuartzJobMapper;
import com.orange.quartz.service.QuartzJobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/17 19:22
 * @description:
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper quartzJobMapper;

    /**
     * 立即执行的任务分组
     */
    private static final String JOB_TEST_GROUP = "test_group";

    /**
     * @description:  通过类名寻找定时任务
     * @author: Li ZhiCheng
     * @date: 2022/10/18 15:40
     * @param: [jobClassName]
     * @return: java.util.List<com.orange.quartz.entity.QuartzJob>
     **/
    @Override
    public List<QuartzJob> findByJobClassName(String jobClassName) {
        return quartzJobMapper.findByJobClassName(jobClassName);
    }
    /**
     * @description: 保存定时任务
     * @author: Li ZhiCheng
     * @date: 2022/10/18 15:40
     * @param: [quartzJob]
     * @return: boolean
     **/
    @Override
    public boolean saveAndScheduleJob(QuartzJob quartzJob) {
        //如果的是0，表示任务保存并且按照cron表达式执行，否则只保存任务信息
        if (quartzJob.getStatus()==0) {
            // 定时器添加
            this.schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim());
        }
        return this.save(quartzJob);
    }
    /**
     * 执行定时任务
     * @param cronExpression
     */
    private void schedulerAdd(String jobClassName, String cronExpression) {
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new BizException("创建定时任务失败", e);
        } catch (RuntimeException e) {
            throw new BizException(e.getMessage(), e);
        }catch (Exception e) {
            throw new BizException("后台找不到该类名：" + jobClassName, e);
        }
    }
    /**
     * @description: 执行定时任务，执行一次
     * @author: Li ZhiCheng
     * @date: 2022/10/18 15:41
     * @param: [quartzJob]
     * @return: void
     **/
    @Override
    public void execute(QuartzJob quartzJob) throws Exception {
        String jobName = quartzJob.getJobClassName().trim();
        Date startDate = new Date();
        String ymd = DateUtils.date2Str(startDate,DateUtils.yyyymmddhhmmss.get());
        String identity =  jobName + ymd;
        //3秒后执行 只执行一次
        startDate.setTime(startDate.getTime()+3000L);
        // 定义一个Trigger
        SimpleTrigger trigger = (SimpleTrigger)TriggerBuilder.newTrigger()
                .withIdentity(identity, JOB_TEST_GROUP)
                .startAt(startDate)
                .build();
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobName).getClass()).withIdentity(identity).build();
        // 将trigger和 jobDetail 加入这个调度
        scheduler.scheduleJob(jobDetail, trigger);
        // 启动scheduler
        scheduler.start();
    }

    /**
     * 编辑&启停定时任务
     * @throws SchedulerException
     */
    @Override
    public boolean editAndScheduleJob(QuartzJob quartzJob) throws SchedulerException {
        if (quartzJob.getStatus()==1) {
            schedulerDelete(quartzJob.getJobClassName().trim());
            schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim());
        }else{
            scheduler.pauseJob(JobKey.jobKey(quartzJob.getJobClassName().trim()));
        }
        return this.updateById(quartzJob);
    }

    /**
     * 删除&停止删除定时任务
     */
    @Override
    public boolean deleteAndStopJob(QuartzJob job) {
        schedulerDelete(job.getJobClassName().trim());
        boolean ok = this.removeById(job.getId());
        return ok;
    }

    /**
     * @description: 恢复定时任务
     * @author: Li ZhiCheng
     * @date: 2022/10/18 15:46
     * @param: [quartzJob]
     * @return: boolean
     **/
    @Override
    public boolean resumeJob(QuartzJob quartzJob) {
        schedulerDelete(quartzJob.getJobClassName().trim());
        schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim());
        quartzJob.setStatus(0);
        return this.updateById(quartzJob);
    }

    /**
     * @description: 暂停定时任务
     * @author: Li ZhiCheng
     * @date: 2022/10/18 16:02
     * @param: [quartzJob]
     * @return: void
     **/
    @Override
    public void pause(QuartzJob quartzJob) {
        schedulerDelete(quartzJob.getJobClassName().trim());
        quartzJob.setStatus(-1);
        this.updateById(quartzJob);
    }

    private static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }
    /**
     * 删除定时任务
     * @param jobClassName
     */
    private void schedulerDelete(String jobClassName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BizException("删除定时任务失败",e);
        }
    }
}
