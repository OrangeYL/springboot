package com.orange.flowable.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author: Li ZhiCheng
 * @create: 2022-12-2022/12/7 15:01
 * @description: 请求通过之后 额外做的事 比如发邮件通知
 */
public class Approve implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("申请通过:"+delegateExecution.getVariables());
    }
}
