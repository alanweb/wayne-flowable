package com.wayne.controller;

import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请假流程
 *
 * @author Foolish
 * @description
 * @date: 2018/9/4 16:03
 */

@RestController
@RequestMapping(value = "holiday")
public class HolidayController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    @Qualifier("processEngine")
    private ProcessEngine processEngine;
    /**
     * 部署请假流程
     */
    @PostMapping(value = "deploy")
    public String deploy() {
        //部署流程
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
                .name("请假流程")
                .addClasspathResource("flowable/Holiday-request.bpmn20.xml")
                .deploy();
        return "部署成功.流程Id为：" + deployment.getId();
    }


    /**
     * 发起请假流程
     *
     * @param employee    用户Id
     * @param nrOfHolidays     报销金额
     * @param description 描述
     */
    @PostMapping(value = "add")
    public String addHoliday(String employee, Integer nrOfHolidays, String description, String processDefinitionKey) {
        //启动流程
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        Authentication.setAuthenticatedUserId("Foolish");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionKey, variables);
        Authentication.setAuthenticatedUserId(null);
        return "请假审批流程开始.流程Id为：" + processInstance.getId();
    }

    /**
     * 获取审批管理列表
     */
    @GetMapping(value = "/list")
    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.toArray().toString();
    }

    /**
     * 查看任务信息
     */
    @GetMapping(value = "show")
    public Object show(String taskId){
        Map<String, Object> processVariables = taskService.getVariables(taskId);
        System.out.println(processVariables);
        return processVariables;
    }

    /**
     * 执行管理员任务
     */
    @PostMapping(value = "/approve")
    public Object approve(boolean approve, String taskId){
        System.out.println(approve? "批准":"退回");
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", approve);
        taskService.complete(taskId, map);
        return approve? "批准":"退回";
    }

    /**
     * 执行管理员任务
     */
    @PutMapping(value = "/execute")
    public Object execute(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }
        Task task = tasks.get(0);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " 想请 " +
                processVariables.get("nrOfHolidays") + " 三天假. 同意？");

        System.out.println("批准");
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", true);
        taskService.complete(task.getId(), map);

        System.out.println("===== jack任务审批 ======");
        List<Task> tasks2 = taskService.createTaskQuery().taskCandidateOrAssigned("jack").list();
        System.out.println("jack have " + tasks2.size() + " tasks:");
        for (int i=0; i<tasks2.size(); i++) {
            System.out.println((i+1) + ") " + tasks2.get(i).getName());
        }
        taskService.complete(tasks2.get(0).getId(), map);
        return "请假审批流程完成";
    }
}
