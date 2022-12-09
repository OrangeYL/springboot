package com.orange.flowable.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 15:04
 * @description: 请求被拒绝之后 额外做的事 比如发邮件通知
 */
public class Reject implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        System.out.println("请假被拒绝:"+variables);
    }
}
