package com.yinhetianze.web.interceptor;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;
import java.util.StringJoiner;


@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class,Integer.class})})
//@Component
public class MybatisDelSqlInterceptor implements Interceptor {

    /**
     * 拦截后要执行的方法
     */
    public Object intercept(Invocation invocation) throws Throwable {
       RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler)getFieldValue(handler, "delegate");
        MappedStatement mappedStatement = (MappedStatement)getFieldValue(delegate, "mappedStatement");
        //针对原始的delete方法进行修改  实际是进行update 操作 set del_flag = 1
        if(SqlCommandType.DELETE == mappedStatement.getSqlCommandType()){
            BoundSql boundSql = delegate.getBoundSql();
            // delete from xxx where xxxx
            // change to    update xxx set del_flag = 1 where xxxx
            String sql = boundSql.getSql();
            System.out.println("sql:"+sql);
            StringJoiner sj = new StringJoiner(" ","","");
            int index = sql.indexOf("WHERE");
            if(index > 0){
                String preSql = sql.substring(0,index-1);
                String sufSql = sql.substring(index,sql.length());
                preSql = preSql.replace("DELETE","").replace("FROM","");
                sj= sj.add("update ").add(preSql).add(" set del_flag = 1 ").add(sufSql);
            }
            System.out.println(sj.toString());
            setFieldValue(boundSql,"sql",sj.toString());
        }

        return invocation.proceed();
    }

    /**
     * 拦截器对应的封装原始对象的方法
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    public static Field getField(Object obj, String fieldName){
        Field field = null;
        for (Class<?> clazz=obj.getClass(); clazz != Object.class; clazz=clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getField(obj,fieldName);
        if(field != null){
            field.setAccessible(true);
            try{
                return field.get(obj);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }


    /**
     * 利用反射设置指定对象的指定属性为指定的值
     * @param obj 目标对象
     * @param fieldName 目标属性
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object obj, String fieldName,
                                     String fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
