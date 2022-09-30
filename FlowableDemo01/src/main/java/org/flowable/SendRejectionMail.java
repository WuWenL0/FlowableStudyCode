package org.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @ClassName SendRejectionMail
 * @Author WuWenL0
 * @Date 2022/9/29 11:00
 */
public class SendRejectionMail implements JavaDelegate {
    /**
     * 触发器
     * @param execution
     */
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("请假申请被拒绝....");
    }
}
