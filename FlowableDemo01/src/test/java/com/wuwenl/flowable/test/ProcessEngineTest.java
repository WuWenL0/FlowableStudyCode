package com.wuwenl.flowable.test;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;

/**
 * @ClassName ProcessEngineTest
 * @Author WuWenL0
 * @Date 2022/9/29 13:56
 */
public class ProcessEngineTest {


    @Test
    public void processEngine01() {
        ProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-learn?characterEncoding=utf-8&serverTimezone=UTC");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }

    @Test
    public void processEngine02() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("defaultProcessEngine = " + defaultProcessEngine);
    }

    @Test
    public void processEngine03(){
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println("processEngine = " + processEngine);
    }
}
