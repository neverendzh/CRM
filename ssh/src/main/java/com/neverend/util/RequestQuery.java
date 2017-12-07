package com.neverend.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/7.
 */
public class RequestQuery {
    private String parameterName;
    private String equalType;
    private Object value;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getEqualType() {
        return equalType;
    }

    public void setEqualType(String equalType) {
        this.equalType = equalType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    public static List<RequestQuery> builderRequestQuery(HttpServletRequest httpServletRequest){
        List<RequestQuery> requestQueryList = new ArrayList<>();
//        获取所有查询参数
        Enumeration<String> enumeration = httpServletRequest.getParameterNames();
//        如果有下一个参数则循环
        while (enumeration.hasMoreElements()){
//            获取url中键
            String queryKey = enumeration.nextElement();
//            获取键中的值
            String value = httpServletRequest.getParameter(queryKey);

//            判断url中参数是否符合约定
            if (queryKey.startsWith("q_")&&!"".equals(value)&& value != null){
//              q_xxx_eq_s
//              如果符合约定，那么根据下划线分隔截取值，获得Spring数组
                String[] array = queryKey.split("_");
                if (array == null || array.length != 4){
                    throw new IllegalArgumentException("查询参数异常"+queryKey);
                }
                RequestQuery requestQuery = new RequestQuery();
                requestQuery.setParameterName(array[1]);
                requestQuery.setEqualType(array[2]);
                requestQuery.setValue(tranValueType(array[3],value));
                requestQueryList.add(requestQuery);

            }
        }
          return requestQueryList;
    }

    private static Object tranValueType(String valueType,String value){
        if("s".equalsIgnoreCase(valueType)){
            return value;
        }else if("d".equalsIgnoreCase(valueType)){
            return Double.valueOf(value);
        }else if("f".equalsIgnoreCase(valueType)){
            return Float.valueOf(value);
        }else if("i".equalsIgnoreCase(valueType)){
            return Integer.valueOf(value);
        }else if("bd".equalsIgnoreCase(valueType)){
            return new BigDecimal(value);
        }
        return null;
    }
}