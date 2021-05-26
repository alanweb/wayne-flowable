package com.wayne.controller;

import com.wayne.common.api.ApiResult;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: wayne_flowable
 * @description:
 * @author: wayne
 * @create: 2021-04-27 19:55
 **/
@RestController
@RequestMapping(value = "holiday")
public class HolidayController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
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
                .addClasspathResource("flowable/holiday-request.bpmn20.xml")
                .deploy();
        return "部署成功.流程Id为：" + deployment.getId();
    }

    /**
     * 申请休假
     */
    @PostMapping(value = "/add")
    public ApiResult<String> add(String employeeId, String managerId, int days, String desc) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("employeeId", employeeId);
        map.put("managerId", managerId);
        map.put("days", days);
        map.put("desc", desc);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", map);
        return ApiResult.success("提交成功.流程Id为：" + processInstance.getId());
    }

    /**
     * 获取task列表
     */
    @GetMapping(value = "/listTask")
    public ApiResult<String> listTask() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managerId").list();
        return ApiResult.success(tasks.toString());
    }

    /**
     * 获取process列表
     */
    @GetMapping(value = "/listProcess")
    public ApiResult<String> listProcess() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc().list();
        return ApiResult.success(list.toString());
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @PostMapping(value = "/apply")
    public ApiResult<String> apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ApiResult.failed("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", true);
        taskService.complete(taskId, map);
        return ApiResult.success("processed ok!");
    }

    /**
     * 拒绝
     *
     * @param taskId 任务ID
     */
    @PostMapping(value = "/reject")
    public ApiResult<String> reject(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return ApiResult.failed("流程不存在");
        }
        //未通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", false);
        taskService.complete(taskId, map);
        return ApiResult.success("processed failed!");
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @GetMapping(value = "/processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

//        流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        BpmnAutoLayout bpmnAutoLayout = new BpmnAutoLayout(bpmnModel);
        bpmnAutoLayout.setTaskHeight(120);
        bpmnAutoLayout.setTaskWidth(120);
        bpmnAutoLayout.execute();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int length = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
