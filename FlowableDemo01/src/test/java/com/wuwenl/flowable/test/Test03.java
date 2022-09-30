package com.wuwenl.flowable.test;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Test01
 * @Author WuWenL0
 * @Date 2022/9/28 17:46
 */
public class Test03 {

    @Test
    public void testProcessEngine() {
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-learn?characterEncoding=utf-8&serverTimezone=UTC");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }

    ProcessEngineConfiguration configuration = null;

    @Before
    public void before() {
        configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-learn?characterEncoding=utf-8&serverTimezone=UTC");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    /**
     * 部署流程
     */
    @Test
    public void testDeploy() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("MyProcess.bpmn20.xml")
                .name("Flowable请求流程")
                .deploy();
        System.out.println("deploy.getId() = " + deploy.getId());
        System.out.println("deploy.getName() = " + deploy.getName());
    }

    /**
     * 查询流程定义的信息
     */
    @Test
    public void testDeployQuery() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId("1").singleResult();
        System.out.println("processDefinition.getDeploymentId() = " + processDefinition.getDeploymentId());
        System.out.println("processDefinition.getName() = " + processDefinition.getName());
        System.out.println("processDefinition.getDescription() = " + processDefinition.getDescription());
        System.out.println("processDefinition.getId() = " + processDefinition.getId());
    }

    @Test
    public void testDeleteDeploy() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        repositoryService.deleteDeployment("1");
        // 级联删除
        repositoryService.deleteDeployment("2501", true);
    }

    /**
     * 开启流程
     */
    @Test
    public void testRunProcess() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variable = new HashMap<>();
        variable.put("employee", "张三");
        variable.put("nrOfHolidays", 3);
        variable.put("description", "世界那么大我想去看看");
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceById("MyHolidayUI:1:15004", variable);
        System.out.println("holidayRequest.getDeploymentId() = " + holidayRequest.getDeploymentId());
        System.out.println("holidayRequest.getActivityId() = " + holidayRequest.getActivityId());
        System.out.println("holidayRequest.getId() = " + holidayRequest.getId());
    }

    /**
     * 任务查询
     */
    @Test
    public void testQueryTask() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("MyHolidayUI")
                .taskAssignee("user1")
                .list();
        for (Task task : list) {
            System.out.println("task.getProcessDefinitionId() = " + task.getProcessDefinitionId());
            System.out.println("task.getName() = " + task.getName());
            System.out.println("task.getAssignee() = " + task.getAssignee());
            System.out.println("task.getDescription() = " + task.getDescription());
            System.out.println("task.getId() = " + task.getId());
        }
    }


    @Test
    public void testCompleteTask() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("MyHolidayUI")
                .taskAssignee("user2")
                .singleResult();
        Map<String, Object> variable = new HashMap<>();
        variable.put("approved", false);
        taskService.complete(task.getId(), variable);
    }

    /**
     * 历史数据
     */
    @Test
    public void testHistory() {
        ProcessEngine processEngine = configuration.buildProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processDefinitionId("MyHolidayUI:1:15004")
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        for (HistoricActivityInstance history : list) {
            System.out.println("---------------------------------------------------");
            System.out.println("history.getActivityName() = " + history.getActivityName());
            System.out.println("history.getAssignee() = " + history.getAssignee());
            System.out.println("history.getActivityId() = " + history.getActivityId());
            System.out.println("history.getDurationInMillis() = " + history.getDurationInMillis());
            System.out.println("---------------------------------------------------");
        }
    }
}
