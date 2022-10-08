package com.wuwenl.flowable.test;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程变量
 *
 * @ClassName Test06
 * @Author WuWenL0
 * @Date 2022/10/8 14:47
 */
public class Test06 {
    @Test
    public void testDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("出差申请单.bpmn20.xml")
                .name("出差申请单")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    @Test
    public void startProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assignee0", "张三");
        variables.put("assignee1", "李四");
        variables.put("assignee2", "王五");
        variables.put("assignee3", "赵会计");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("evection:1:55004", variables);
        System.out.println("processInstance.getDeploymentId() = " + processInstance.getDeploymentId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    /**
     * 执行任务，绑定流程变量
     */
    @Test
    public void completeTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("evection:1:55004")
                .taskAssignee("张三")
                .singleResult();
        Map<String, Object> variables = task.getProcessVariables();
        variables.put("num", 2);
        taskService.complete(task.getId(), variables);
    }

    @Test
    public void completeTaskLisi() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("evection:1:55004")
                .taskAssignee("李四")
                .singleResult();
        taskService.complete(task.getId());
    }

    /**
     * 根据Task编号更新流程变量
     */
    @Test
    public void updateLocalVariable() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("evection:1:55004")
                .taskAssignee("李四")
                .singleResult();
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("num", 5);
        taskService.setVariablesLocal(task.getId(), processVariables);
    }

    /**
     * 根据Task编号更新流程变量
     */
    @Test
    public void updateVariable() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("evection:1:55004")
                .taskAssignee("李四")
                .singleResult();
        Map<String, Object> processVariables = task.getProcessVariables();
        processVariables.put("num", 1);
        taskService.setVariables(task.getId(), processVariables);
    }
}
