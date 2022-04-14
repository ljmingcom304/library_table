package com.ljming.table.bean;

import com.ljming.table.bind.BindView;
import com.ljming.table.Table;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Title:Binder
 * <p>
 * Description:属性映射器
 * </p>
 * Author Jming.L
 * Date 2018/8/18 13:42
 */
public class Binder implements Serializable {

    private static final long serialVersionUID = 1652766057929298991L;

    private String key;             //映射的关键词

    private Field beanField;        //Bean对象属性

    private Table table;        //与属性对应的View

    private Field viewField;        //View的Field

    private BindView bindView;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Field getBeanField() {
        return beanField;
    }

    public void setBeanField(Field beanField) {
        this.beanField = beanField;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table view) {
        this.table = view;
    }

    public Field getViewField() {
        return viewField;
    }

    public void setViewField(Field viewField) {
        this.viewField = viewField;
    }

    public BindView getBindView() {
        return bindView;
    }

    public void setBindView(BindView bindView) {
        this.bindView = bindView;
    }
}
