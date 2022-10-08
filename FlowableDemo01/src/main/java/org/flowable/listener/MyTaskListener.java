package org.flowable.listener;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

/**
 * @ClassName MyTaskListener
 * @Author WuWenL0
 * @Date 2022/10/8 13:32
 */
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("MyTaskListener触发了" + delegateTask.getName());
        if ("创建请假流程".equals(delegateTask.getName()) && "create".equals(delegateTask.getEventName())) {
            delegateTask.setAssignee("小明");
        } else {
            delegateTask.setAssignee("小李");
        }
    }
}
