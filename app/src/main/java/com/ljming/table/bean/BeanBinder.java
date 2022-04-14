package com.ljming.table.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Title:BeanBinder
 * <p>
 * Description:对象绑定器
 * </p>
 * Author Jming.L
 * Date 2018/8/18 13:33
 */
public class BeanBinder implements Serializable {

    private static final long serialVersionUID = -6124365249201206108L;

    private Object bean;    //绑定对象

    private List<Binder> binders;


    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public List<Binder> getBinders() {
        return binders;
    }

    public void setBinders(List<Binder> binders) {
        this.binders = binders;
    }
}
