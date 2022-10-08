package com.orange.mybatis.interceptor;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: Li ZhiCheng
 * @create: 2022-09-2022/9/6 16:00
 * @description: Mybatis拦截器 自动注入创建时间和修改时间
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class CreateTimeSetInterceptor implements Interceptor {

    private static final String CREATE_TIME_SETTER = "setCreateTime";
    private static final String UPDATE_TIME_SETTER = "setUpdateTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            setTimeIfNecessary(parameter, CREATE_TIME_SETTER);
            setTimeIfNecessary(parameter, UPDATE_TIME_SETTER);
        } else if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            setTimeIfNecessary(parameter, UPDATE_TIME_SETTER);
        }
        return invocation.getMethod().invoke(invocation.getTarget(), invocation.getArgs());
    }

    private void setTimeIfNecessary(Object param, String methodName) {
        Class<?> cls = param.getClass();

        if (cls == MapperMethod.ParamMap.class) {
            @SuppressWarnings("unchecked")
            MapperMethod.ParamMap<Object> map = (MapperMethod.ParamMap<Object>) param;
            map.entrySet().forEach(entry -> {
                if (!entry.getKey().equals("et")) {
                    setIfSetterExist(entry.getValue(), methodName);
                }
            });
        } else {
            setIfSetterExist(param, methodName);
        }
    }

    private void setIfSetterExist(Object param, String methodName) {
        Class<?> cls = param.getClass();
        try {
            Method m = null;
            try {
                m = cls.getDeclaredMethod(methodName, new Class[] { Date.class });
                if (m != null) {
                    m.setAccessible(true);
                    m.invoke(param, new Date());
                }
            } catch (NoSuchMethodException e1) {
                m = cls.getDeclaredMethod(methodName, new Class[] { Timestamp.class });
                if (m != null) {
                    m.setAccessible(true);
                    m.invoke(param, new Timestamp(System.currentTimeMillis()));
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}