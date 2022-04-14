package com.ljming.table.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title:BindView
 * <p>
 * Description:绑定View字段，用于日期格式化，formats表示View字段支持日期格式，result表示Bean字段日期格式
 * </p >
 * Author Jming.L
 * Date 2022/4/14 10:54
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
public @interface BindView {

    String[] formats() default {"yyyy年MM月dd日","yyyy年MM月dd号"};//日期格式化

    String result() default "yyyy-MM-dd";//目标格式
}
