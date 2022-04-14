package com.ljming.table;

import java.util.List;

/**
 * Title:Table
 * <p>
 * Description:View实现该接口的可以进行动态注入
 * </p>
 * Author Jming.L
 * Date 2018/10/31 14:29
 */
public interface Table {

    /**
     * 设置内容
     *
     * @param texts 内容
     */
    void setTexts(List<String> texts);

    /**
     * 获取内容
     *
     * @return 内容
     */
    List<String> getTexts();

    /**
     * 非编辑状态不可以赋值
     *
     * @return 编辑状态
     */
    boolean isEditable();

}
