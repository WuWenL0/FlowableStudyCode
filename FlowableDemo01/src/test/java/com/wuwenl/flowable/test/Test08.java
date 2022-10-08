package com.wuwenl.flowable.test;

import org.flowable.engine.IdentityService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.idm.api.User;
import org.junit.Test;

/**
 * 用户和组的维护
 *
 * @ClassName Test08
 * @Author WuWenL0
 * @Date 2022/10/8 17:02
 */
public class Test08 {

    /**
     * 维护用户
     */
    @Test
    public void createUser() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        User user = identityService.newUser("田佳");
        user.setFirstName("tian");
        user.setLastName("jia");
        user.setEmail("tianjia@qq.com");
        identityService.saveUser(user);
    }
}
