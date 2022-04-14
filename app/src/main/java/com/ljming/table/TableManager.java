package com.ljming.table;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ljming.table.bean.BeanBinder;
import com.ljming.table.bean.Binder;
import com.ljming.table.bind.Bind;
import com.ljming.table.bind.BindBean;
import com.ljming.table.bind.BindTable;
import com.ljming.table.bind.BindView;
import com.ljming.table.bind.Dictionary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;

/**
 * Title:TableHelper
 * <p>
 * Description:
 * </p>
 * Author Jming.L
 * Date 2018/8/18 13:53
 */
public class TableManager {

    private static final String TAG = TableManager.class.getSimpleName();

    //Key:控件上key;Value:控件
    private Map<String, Binder> mKeyView;
    //Object:绑定对象
    private List<BeanBinder> mBeanBinders;

    private Object object;
    private Context mContext;
    private Dictionary dictionary;

    private static final String CODE_COMPART = ",";// 逗号分隔符
    private static final String CODE_VERTICAL = "\\|";

    private Comparator<String> comparator = new Comparator<String>() {
        //字符串由长到短排序
        @Override
        public int compare(String str1, String str2) {
            int len1 = str1.length();
            int len2 = str2.length();
            if (len1 > len2) {
                return -1;
            }
            if (len1 < len2) {
                return 1;
            }
            return 0;
        }
    };

    public TableManager() {

    }

    public TableManager(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public TableManager(Activity activity) {
        inject(activity);
    }

    public TableManager(Activity activity, Dictionary dictionary) {
        this.dictionary = dictionary;
        inject(activity);
    }

    public TableManager(Fragment fragment) {
        inject(fragment);
    }

    public TableManager(Fragment fragment, Dictionary dictionary) {
        this.dictionary = dictionary;
        inject(fragment);
    }

    /**
     * 若构造方法中未传入上下文则需要注入Activity
     *
     * @param activity Activity
     */
    public void inject(Activity activity) {
        this.mContext = activity;
        initView(activity);
    }

    /**
     * 若构造方法中未传入上下文则需要注入Fragment
     *
     * @param fragment Fragment
     */
    public void inject(Fragment fragment) {
        this.mContext = fragment.getContext();
        initView(fragment);
    }

    private <E> void initView(E e) {
        if (e != null) {
            this.object = e;
            mKeyView = new LinkedHashMap<>();
            mBeanBinders = new ArrayList<>();
            Class<?> clazz = e.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                field.setAccessible(true);
                try {
                    if (Table.class.isAssignableFrom(type)) {
                        Table table = (Table) field.get(e);
                        if (table == null) {
                            // 若控件没有赋值（findViewById或new）则提示初始化
                            Log.e(TAG, field.getType().getSimpleName() + " of " + clazz.getSimpleName()
                                    + " is null and must be initialized.");
                        } else {
                            // 如果存在注解则优先将注解设置为关键词
                            String key = getKey(field);
                            if (TextUtils.isEmpty(key)) continue;
                            if (mKeyView.containsKey(key)) {
                                throw new IllegalStateException("The " + key + " of key has already belonged to a View.");
                            } else {
                                // 若关键词不为空则创建绑定对象
                                Binder binder = new Binder();
                                binder.setKey(key);
                                binder.setTable(table);
                                binder.setViewField(field);
                                if (field.isAnnotationPresent(BindView.class)) {
                                    BindView bindView = field.getAnnotation(BindView.class);
                                    binder.setBindView(bindView);
                                }
                                mKeyView.put(key, binder);
                            }
                        }
                    }
                } catch (IllegalAccessException | IllegalArgumentException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取属性注解的Key
     *
     * @param field 属性
     * @return Key
     */
    private String getKey(Field field) {
        String key = null;
        if (field.isAnnotationPresent(BindTable.class)) {
            int resId = field.getAnnotation(BindTable.class).resId();
            if (resId != 0) {
                key = mContext.getResources().getString(resId);
            } else {
                key = field.getAnnotation(BindTable.class).key();
            }
        }
        return key;
    }

    /**
     * 获取所有的对象绑定关系
     *
     * @return 绑定关系集合
     */
    public List<BeanBinder> getBeanBinders() {
        return mBeanBinders;
    }


    /**
     * 注入JaveBean属性，关键词取Bean的指定索引
     *
     * @param object 对象
     */
    public void bind(Object object) {
        if (object != null) {
            ArrayList<String> keys = new ArrayList<>();
            ArrayList<Binder> binders = new ArrayList<>();
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                //获取Bean成员变量的key
                String beanKey = getKey(field);
                if (TextUtils.isEmpty(beanKey)) continue;
                if (keys.contains(beanKey)) {
                    throw new IllegalStateException("The " + beanKey + " of key has already belonged to a Field.");
                } else {
                    keys.add(beanKey);
                    if (mKeyView.containsKey(beanKey)) {
                        // 将相同key的Bean属性与View相互绑定
                        Binder binder = mKeyView.get(beanKey);
                        binder.setBeanField(field);
                        binders.add(binder);
                        Log.i(TAG, "BIND[Key=" + beanKey + "][View=" + binder.getViewField().getName() + "][Field=" + field.getName() + "]");
                    }
                }
            }

            BeanBinder beanBinder = new BeanBinder();
            beanBinder.setBean(object);
            beanBinder.setBinders(binders);
            mBeanBinders.add(beanBinder);
        }
    }

    /**
     * 接触对象的属性绑定
     */
    public boolean unBind(Object obj) {
        for (BeanBinder binder : mBeanBinders) {
            if (binder.getBean() == obj) {
                mBeanBinders.remove(binder);
                return true;
            }
        }
        return false;
    }

    /**
     * JavaBean向View赋值
     */
    public void beanToView() {
        for (BeanBinder beanBinder : mBeanBinders) {
            Object obj = beanBinder.getBean();   //绑定对象
            List<Binder> binders = beanBinder.getBinders();
            for (Binder binder : binders) {
                Table text = binder.getTable();
                if (text.isEditable()) {
                    List<String> result = getFieldText(obj, binder);
                    text.setTexts(result);
                }
            }
        }
    }

    // 获取Bean属性的字典文本赋值给View
    private List<String> getFieldText(Object obj, Binder binder) {
        Field field = binder.getBeanField();
        //属性的值
        String content = null;
        try {
            Object o = field.get(obj);
            if (o != null) {
                content = o.toString();
            }
        } catch (IllegalAccessException | IllegalArgumentException e1) {
            e1.printStackTrace();
            Log.e(TAG, e1.getMessage());
        }

        Field viewField = binder.getViewField();
        String keyName = binder.getKey();
        String viewName = viewField.getName();
        String beanName = field.getName();
        Log.i(TAG, "[Key:" + keyName + "][View:" + viewName + "]"
                + "[Field:" + beanName + "][Value:" + content + "]");
        String[] result = {};
        if (content != null) {
            result = new String[]{content};
            if (field.isAnnotationPresent(BindBean.class)) {
                BindBean bindBean = field.getAnnotation(BindBean.class);

                //通过注解的形式完成key-value映射
                Bind[] binds = bindBean.value();
                if (binds.length > 0) {
                    HashMap<String, String> map = new HashMap<>();
                    for (Bind bind : binds) {
                        map.put(bind.value(), bind.key());
                    }
                    //每个属性的值通过英文逗号切割为数组
                    String[] splitText = content.split(CODE_COMPART);// 索引
                    result = new String[splitText.length];//结果
                    //遍历属性的数组的值
                    for (int i = 0; i < result.length; i++) {
                        String position = splitText[i];
                        if (!TextUtils.isEmpty(position)) {
                            //获取属性值对应的选项，如果该选项不存在则返回原值
                            String text = map.get(position);
                            if (TextUtils.isEmpty(text)) {
                                result[i] = position;
                            } else {
                                result[i] = text;
                            }
                        }
                    }
                    return Arrays.asList(result);
                }

                //查询字典（需要查询字典的选项默认用英文逗号分隔）
                String type = bindBean.dict();
                if (!TextUtils.isEmpty(type)) {// 需要查询字典
                    String[] splitText = content.split(CODE_COMPART);
                    result = new String[splitText.length];
                    for (int i = 0; i < result.length; i++) {
                        String text = dictionary.codeToText(type, splitText[i]);
                        result[i] = text;
                    }
                    return Arrays.asList(result);
                }
            }
        }
        return Arrays.asList(result);

    }

    // 获取View的内容赋值给Bean属性
    private String getFieldCode(Object obj, Field field, Table view) {
        String result = "";
        List<String> texts = view.getTexts();
        if (field.isAnnotationPresent(BindBean.class)) {
            BindBean bindBean = field.getAnnotation(BindBean.class);

            //通过注解的形式完成key-value映射
            Bind[] binds = bindBean.value();
            if (binds.length > 0) {
                HashMap<String, String> map = new HashMap<>();
                for (Bind bind : binds) {
                    map.put(bind.key(), bind.value());
                }
                for (int i = 0; i < texts.size(); i++) {
                    String text = texts.get(i);
                    String code = map.get(text);
                    if (!TextUtils.isEmpty(result)) {
                        result += CODE_COMPART;
                    }
                    //有编码传编码，没编码传原文
                    if (TextUtils.isEmpty(code)) {
                        result += text;
                    } else {
                        result += code;
                    }
                }
                return result;
            }

            //查询字典
            String type = bindBean.dict();
            if (!TextUtils.isEmpty(type)) {// 需要查询字典
                for (int i = 0; i < texts.size(); i++) {
                    String text = texts.get(i);
                    String code = dictionary.textToCode(type, text);
                    if (!TextUtils.isEmpty(result)) {
                        result += CODE_COMPART;
                    }
                    result += code;
                }
                return result;
            }
        } else {
            for (int i = 0; i < texts.size(); i++) {
                String text = texts.get(i);
                if (!TextUtils.isEmpty(result)) {
                    result += CODE_COMPART;
                }
                result += text;
            }
            return result;
        }
        return result;

    }

    /**
     * View向JavaBean赋值
     */
    public void viewToBean() {
        for (BeanBinder beanBinder : mBeanBinders) {
            Object object = beanBinder.getBean();
            List<Binder> binders = beanBinder.getBinders();
            for (Binder binder : binders) {
                Field field = binder.getBeanField();
                Table view = binder.getTable();
                if (view.isEditable()) {
                    try {
                        String text = getFieldCode(object, field, view);
                        Object value = transform(field, text);
                        field.set(object, value);
                    } catch (IllegalAccessException | IllegalArgumentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //根据属性的类型进行对应的转换
    private Object transform(Field field, String text) {
        Class<?> aClass = field.getType();
        if (aClass == String.class) {
            return text;
        }
        if (aClass == Integer.class || aClass == int.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Integer.valueOf(text);
            }
        }
        if (aClass == Float.class || aClass == float.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Float.valueOf(text);
            }
        }
        if (aClass == Double.class || aClass == double.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Double.valueOf(text);
            }
        }
        if (aClass == Short.class || aClass == short.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Short.valueOf(text);
            }
        }
        if (aClass == Long.class || aClass == long.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Long.valueOf(text);
            }
        }
        if (aClass == Boolean.class || aClass == boolean.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Boolean.valueOf(text);
            }
        }
        if (aClass == Byte.class || aClass == byte.class) {
            if (TextUtils.isEmpty(text)) {
                return null;
            } else {
                return Byte.valueOf(text);
            }
        }
        return text;
    }

}
