package com.ljming.table.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title:BindTable
 * <p>
 * Description:Bean的Field与View必须是一对一的绑定关系
 * </p>
 * Author Jming.L
 * Date 2018/6/8 16:32
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
public @interface BindTable {

    String key() default "";//关键词

    int resId() default 0;//资源ID

}
