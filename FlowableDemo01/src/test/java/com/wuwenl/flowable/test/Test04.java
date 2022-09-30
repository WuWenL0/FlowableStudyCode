package com.wuwenl.flowable.test;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
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
}
