package com.wuwenl.flowable.test;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.task.api.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 候选人组
 *
 * @ClassName Test07
 * @Author WuWenL0
 * @Date 2022/10/8 15:58
 */
public class Test09 {
    @Test
    public void testDeploy() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("请假流程-候选人组.bpmn20.xml")
                .name("请假流程-候选人组")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    @Test
    public void startProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.createGroupQuery()
                .groupId("group1")
                .singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("g1", group.getId());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("holiday-group:1:85004", variables);
        System.out.println("processInstance.getDeploymentId() = " + processInstance.getDeploymentId());
        System.out.println("processInstance.getActivityId() = " + processInstance.getActivityId());
        System.out.println("processInstance.getId() = " + processInstance.getId());
    }

    @Test
    public void queryTaskCandidateGroup() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.createGroupQuery().groupMember("邓彪").singleResult();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionId("holiday-group:1:85004")
                .taskCandidateGroup(group.getId())
                .list();
        for (Task task : list) {
            System.out.println("task.getId() = " + task.getId());
            System.out.println("task.getName() = " + task.getName());
        }
    }

    @Test
    public void claimTaskCandidate() {
        String userId = "邓彪";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        Group group = identityService.createGroupQuery().groupMember(userId).singleResult();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-group:1:85004")
                .taskCandidateGroup(group.getId())
                .singleResult();
        if (Objects.nonNull(task)) {
            taskService.claim(task.getId(), userId);
            System.out.println("任务拾取成功");
        }
    }

    @Test
    public void unClaimTaskCandidate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:67504")
                .taskAssignee("张三")
                .singleResult();
        if (Objects.nonNull(task)) {
            taskService.unclaim(task.getId());
            System.out.println("任务退回成功");
        }
    }

    /**
     * 任务交接/转移
     */
    @Test
    public void taskCandidate() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-candidate:1:67504")
                .taskAssignee("李四")
                .singleResult();
        if (Objects.nonNull(task)) {
            taskService.setAssignee(task.getId(), "王五");
            System.out.println("任务交接成功");
        }
    }

    @Test
    public void completeTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionId("holiday-group:1:85004")
                .taskAssignee("邓彪")
                .singleResult();
        taskService.complete(task.getId());
    }


}
