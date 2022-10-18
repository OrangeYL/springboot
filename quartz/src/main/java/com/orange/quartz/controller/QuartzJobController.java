package com.orange.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.orange.common.utils.Result;
import com.orange.common.utils.ResultEnum;
import com.orange.quartz.entity.QuartzJob;
import com.orange.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 10:15
 * @description:
 */
@RestController
@RequestMapping("/quartz")
@Slf4j
public class QuartzJobController {
    @Autowired
    private QuartzJobService quartzJobService;
    @Autowired
    private Scheduler scheduler;

    /**
     * 添加定时任务
     * @param quartzJob
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody QuartzJob quartzJob) {
        List<QuartzJob> list = quartzJobService.findByJobClassName(quartzJob.getJobClassName());
        if (list != null && list.size() > 0) {
            return Result.error("该定时任务类名已存在");
        }
        quartzJobService.saveAndScheduleJob(quartzJob);
        return Result.success(ResultEnum.SUCCESS);
    }

    /**
     * 更新定时任务
     * @param quartzJob
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<?> edit(@RequestBody QuartzJob quartzJob) {
        try {
            quartzJobService.editAndScheduleJob(quartzJob);
        } catch (SchedulerException e) {
            log.error(e.getMessage(),e);
            return Result.error("更新定时任务失败!");
        }
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * 通过id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        QuartzJob quartzJob = quartzJobService.getById(id);
        if (quartzJob == null) {
            return Result.error("未找到对应实体");
        }
        quartzJobService.deleteAndStopJob(quartzJob);
        return Result.success(ResultEnum.SUCCESS);

    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        if (ids == null || "".equals(ids.trim())) {
            return Result.error("参数不识别！");
        }
        for (String id : Arrays.asList(ids.split(","))) {
            QuartzJob job = quartzJobService.getById(id);
            quartzJobService.deleteAndStopJob(job);
        }
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * 暂停定时任务
     * @param jobClassName
     * @return
     */
    //@RequiresRoles("admin")
    @GetMapping(value = "/pause")
    public Result<Object> pauseJob(@RequestParam(name = "jobClassName", required = true) String jobClassName) {
        QuartzJob job = null;
        job = quartzJobService.getOne(new LambdaQueryWrapper<QuartzJob>().eq(QuartzJob::getJobClassName, jobClassName));
        if (job == null) {
            return Result.error("定时任务不存在！");
        }
        quartzJobService.pause(job);
        return Result.success(ResultEnum.SUCCESS);
    }
    /**
     * 启动定时任务
     * @param jobClassName
     * @return
     */
    @GetMapping(value = "/resume")
    public Result<Object> resumeJob(@RequestParam(name = "jobClassName", required = true) String jobClassName) {
        QuartzJob job = quartzJobService.getOne(new LambdaQueryWrapper<QuartzJob>().eq(QuartzJob::getJobClassName, jobClassName));
        if (job == null) {
            return Result.error("定时任务不存在！");
        }
        quartzJobService.resumeJob(job);
        return Result.success(ResultEnum.SUCCESS);
    }

    /**
     * 立即执行，执行一次
     * @param id
     * @return
     */
    @GetMapping("/execute")
    public Result<?> execute(@RequestParam(name = "id", required = true) String id) {
        QuartzJob quartzJob = quartzJobService.getById(id);
        if (quartzJob == null) {
            return Result.error("未找到对应实体");
        }
        try {
            quartzJobService.execute(quartzJob);
        } catch (Exception e) {
            log.info("定时任务 立即执行失败>>"+e.getMessage());
            return Result.error("执行失败!");
        }
        return Result.success(ResultEnum.SUCCESS);
    }
}
