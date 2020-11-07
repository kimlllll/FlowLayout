package com.kimliu.study.CustomRetrofit;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 */
public class ServiceMethod {



    public static class Builder{


        private final Annotation[] annotations;
        private final Annotation[][] parameterAnnotations;

        public Builder(EnjoyRetrofit retrofit, Method method){
            // 获取方法上的所有注释
            annotations = method.getAnnotations();
            // 获取方法参数上的所有注释
            parameterAnnotations = method.getParameterAnnotations();


        }

        public ServiceMethod build(){

            return null;
        }

    }




}
