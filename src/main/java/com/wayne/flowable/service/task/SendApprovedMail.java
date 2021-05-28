package com.wayne.flowable.service.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 19:53
 **/
public class SendApprovedMail implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("send approved mail to " + delegateExecution.getVariable("employeeId"));
    }
}
