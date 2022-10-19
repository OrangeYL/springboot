package com.orange.quartz.job;

import com.orange.quartz.entity.User;
import com.orange.quartz.entity.UserCopy;
import com.orange.quartz.service.IUserCopyService;
import com.orange.quartz.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Li ZhiCheng
 * @create: 2022-10-2022/10/18 10:27
 * @description:
 */
@Slf4j
public class TestJob implements Job {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserCopyService iUserCopyService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        List<User> list = userService.list();
//        ArrayList<UserCopy> userCopies = new ArrayList<>();
//        for (User user:list){
//          UserCopy userCopy = new UserCopy();
//          BeanUtils.copyProperties(user,userCopy);
//          userCopies.add(userCopy);
//        }
//        iUserCopyService.saveBatch(userCopies);
        log.info("你好呀，这里是定时任务测试！");
    }
}
