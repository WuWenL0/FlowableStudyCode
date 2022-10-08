package com.wuwenl.flowable.test;

import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Test04
 * @Author WuWenL0
 * @Date 2022/9/29 15:08
 */
public class Test04 {

    /**
     * 流程挂起和激活
     */
    @Test
    public void testDeploy() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        // 获取对应流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId("MyProcess:1:25004")
                .singleResult();
        boolean suspended = processDefinition.isSuspended();
        if (suspended) {
            // 激活
            System.out.println("激活流程：" + processDefinition.getId() + ":" + processDefinition.getName());
            repositoryService.activateProcessDefinitionById("MyProcess:1:25004");
        } else {
            System.out.println("挂起流程：" + processDefinition.getId() + ":" + processDefinition.getName());
            repositoryService.suspendProcessDefinitionById("MyProcess:1:25004");
        }
    }


    /**
     * 开启流程
     */
    @Test
    public void testRunProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variable = new HashMap<>();
        variable.put("employee", "张三");
        variable.put("nrOfHolidays", 3);
        variable.put("description", "世界那么大我想去看看");
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceById("MyProcess:1:25004", "order100001", variable);
        System.out.println("holidayRequest.getDeploymentId() = " + holidayRequest.getDeploymentId());
        System.out.println("holidayRequest.getActivityId() = " + holidayRequest.getActivityId());
        System.out.println("holidayRequest.getId() = " + holidayRequest.getId());
    }

    /**
     * 任务处理
     */
    @Test
    public void testCompleteTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("30001")
                .taskAssignee("zs")
                .singleResult();

        Map<String, Object> processVariables = task.getProcessVariables();
        for (String key : processVariables.keySet()) {
            System.out.println(key + ":" + processVariables.get(key));
        }

        processVariables.put("approved", false);
        processVariables.put("description", "out play");
        taskService.complete(task.getId(), processVariables);
    }

    /**
     * 任务完成
     */
    @Test
    public void testDoneTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("30001")
                .taskAssignee("ls")
                .singleResult();

        taskService.complete(task.getId());
    }


}
