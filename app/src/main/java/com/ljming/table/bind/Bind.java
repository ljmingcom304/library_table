package com.ljming.table.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title:Bind
 * <p>
 * Description:用于多选中的KeyValue映射
 * </p>
 * Author Jming.L
 * Date 2018/8/17 17:28
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
public @interface Bind {

    String key();

    String value();

}
