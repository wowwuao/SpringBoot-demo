package com.example.demo.utils;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


@Slf4j
public class ConvertUtils {

    /**
     * @Description 主要解决查询时前端传参为空值 ("")
     * BeanUtils.copyProperties会把空值带入目标对象中
     * 使用目标对象作为查询对象到mybatisPlus进行查询会导致没有匹配数据;
     * 使用该方法将 空值 转换为 null,避免copy时带入到查询对象
     **/
    public static <T> void emptyToNull(T obj){
        Class<? extends Object> clazz = obj.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 可访问私有变量
            field.setAccessible(true);
            // 获取属性类型
            String type = field.getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if ("class java.lang.String".equals(type)) {
                // 将属性的首字母大写
                String methodName = field.getName().replaceFirst(field.getName().substring(0, 1),
                        field.getName().substring(0, 1).toUpperCase());
                try {
                    Method methodGet = clazz.getMethod("get" + methodName);
                    // 调用getter方法获取属性值
                    String str = (String) methodGet.invoke(obj);
                    //属性为null结束循环
                    if (str == null) {
                        continue;
                    }
                    if ("".equals(str)) {
                        // 为空 设置为null
                        field.set(obj, null);
                    }
                } catch (Exception e) {
                    log.error("emptyToNull 方法属性转换异常: {}",e.getMessage());
                }
            }

        }
    }
}