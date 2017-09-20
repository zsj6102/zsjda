package com.property.colpencil.colpencilandroidlibrary.Function.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:Activity、Fragment初始化的用到的注解
 * @author 汪 亮
        * @Email  DramaScript@outlook.com
* @date 2016/6/30
        */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ActivityFragmentInject {

    /**
     * 布局id
     *
     * @return
     */
    int contentViewId() default -1;


}
