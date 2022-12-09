package com.orange.flowable.service.impl;

import com.orange.common.utils.Result;
import com.orange.flowable.entity.ApproveRejectVO;
import com.orange.flowable.entity.AskForLeaveVO;
import com.orange.flowable.entity.HistoryInfo;
import com.orange.flowable.service.AskForLeaveService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 14:21
 * @description:
 */
@Service
public class AskForLeaveServiceImpl implements AskForLeaveService {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    /**
     * @description: 用户提交请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:10
     * @param: [askForLeaveVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> askForLeave(AskForLeaveVO askForLeaveVO) {
        //封装参数，这个参数会存在act_ru_variable（正在运行的流程），act_hi_varinst(历史流程)
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("name",askForLeaveVO.getName());
        variables.put("days",askForLeaveVO.getDays());
        variables.put("reason",askForLeaveVO.getReason());

        try {
            //开启流程 第一个参数表示流程的名字； 第二个参数为流程的key，后面可以使用key查询所有申请；第三个参数是我们的变量
            runtimeService.startProcessInstanceByKey("holidayRequest",askForLeaveVO.getName(),variables);
            return Result.success("已提交请假申请！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("提交申请失败！");
    }

    /**
     * @description: 展示所有用户提交的申请（经理查看）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:29
     * @param: [identity] 用户的身份
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> leaveList(String identity) {
        //查询该用户需要处理的任务
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(identity).list();
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0;i < tasks.size();i++){
            Task task = tasks.get(i);
            //获取任务对应的参数
            Map<String,Object> variables = taskService.getVariables(task.getId());
            variables.put("id",task.getId());
            list.add(variables);
        }
        return Result.success(list);
    }

    /**
     * @description: 审批请假申请
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:36
     * @param: [approveRejectVO]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> askForLeaveHandler(ApproveRejectVO approveRejectVO) {
        try {
            boolean approved = approveRejectVO.getApprove();
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("approved", approved);
            variables.put("employee", approveRejectVO.getName());

            Task task = taskService.createTaskQuery().taskId(approveRejectVO.getTaskId()).singleResult();
            taskService.complete(task.getId(),variables);
            if(approved){
                //如果是同意，还需要继续走一步
                Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                taskService.complete(t.getId());
            }
            return Result.success("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("操作失败");
    }

    /**
     * @description: 用户查询自己提交的记录（只查询流程结束的记录）
     * @author: Li ZhiCheng
     * @date: 2022/12/9 10:40
     * @param: [name]
     * @return: com.orange.common.utils.Result<?>
     **/
    @Override
    public Result<?> searchResult(String name) {
        List<HistoryInfo> historyInfos = new ArrayList<>();
        //通过key查询用户提交的记录，只查询流程结束的
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(name).finished().orderByProcessInstanceEndTime().desc().list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            HistoryInfo historyInfo = new HistoryInfo();
            Date startTime = historicProcessInstance.getStartTime();
            Date endTime = historicProcessInstance.getEndTime();
            //根据流程实例id查询流程变量
            List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .list();
            for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
                String variableName = historicVariableInstance.getVariableName();
                Object value = historicVariableInstance.getValue();
                if ("reason".equals(variableName)) {
                    historyInfo.setReason((String) value);
                } else if ("days".equals(variableName)) {
                    historyInfo.setDays(Integer.parseInt(value.toString()));
                } else if ("approved".equals(variableName)) {
                    historyInfo.setStatus((Boolean) value);
                } else if ("name".equals(variableName)) {
                    historyInfo.setName((String) value);
                }
            }
            historyInfo.setStartTime(startTime);
            historyInfo.setEndTime(endTime);
            historyInfos.add(historyInfo);
        }
        return Result.success(historyInfos);
    }
}
