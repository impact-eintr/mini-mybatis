package org.eintr.mybatis.reflection.invoker;

import java.lang.reflect.Method;

// 反射方法调用器
public class MethodInvoker implements Invoker {
    private Class<?> type;
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;
        if (method.getParameters().length == 1) { // setter
            type = method.getParameterTypes()[0];
        } else { // getter
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
