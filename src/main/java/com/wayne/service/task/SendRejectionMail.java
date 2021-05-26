package com.wayne.service.task;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 19:48
 **/
public class SendRejectionMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("send rejected mail to " + delegateExecution.getVariable("employeeId"));
    }
}
