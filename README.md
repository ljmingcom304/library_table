# 表单注入

## 单值
```
1.Bean字段带字典绑定，dict对应Dictionary中type
@BindBean(dict = DictBean.NATION)
@BindTable(key = "民族")
public String nationcode;              //民族
2.View字段绑定
@BindTable( key = "民族" )
private EditLayout mElNation;           //民族
```

## 多值
```
1.Bean字段
@BindTable(key = "随访方式")
private EditLayout mElFluMode;          //随访方式
2.View字段
@BindBean( {
        @Bind( key = "门诊", value = "1" ),
        @Bind( key = "家庭", value = "2" ),
        @Bind( key = "电话", value = "3" )
} )
@BindTable( key = "随访方式" )
public String sVisitingTypeCode = "";// 随访方式代码
```

## 注意事项
```
1.View需要实现Table接口
2.多选View给Bean注值通过英文逗号分隔
```