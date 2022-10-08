package com.wuwenl.flowable.test;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;

/**
 * @ClassName Test05
 * @Author WuWenL0
 * @Date 2022/10/8 11:04
 */
public class Test05 {

    @Test
    public void testDeploy() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("请假流程2.bpmn20.xml")
                .name("请假流程2")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    @Test
    public void testStartProcess() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = defaultProcessEngine.getRuntimeService();
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("assignee0", "zs");
//        variables.put("assignee1", "ls");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holiday-new:2:47504");
        System.out.println("processInstance.getDeploymentId() = " + processInstance.getDeploymentId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    @Test
    public void testComplete(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = defaultProcessEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processInstanceId("50001")
                .taskAssignee("小明")
                .singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void testDelete(){
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        repositoryService.deleteDeployment("55001",true);
    }
}
