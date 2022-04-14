package com.ljming.table.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title:BindBean
 * <p>
 * Description:绑定Bean字段
 * </p >
 * Author Jming.L
 * Date 2022/4/14 10:54
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
public @interface BindBean {
    // 字典>资源ID>资源
    String dict() default ""; // 字典

    Bind[] value() default {};//

}
