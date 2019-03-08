package com.hwz.bsbdj.common;

import ognl.Ognl;
import ognl.OgnlException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author hwz
 * @date 2019/2/22
 */
public class OgnlUtils {
    /**
     * 取字符串
     *
     * @param ognl
     * @param root
     * @return
     */
    public static String getString(String ognl, Map root) {

        try {
            return Ognl.getValue(ognl, root).toString();
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取数字
     *
     * @param ognl
     * @param root
     * @return Number是所有数字的父类
     */
    public static Number getNumber(String ognl, Map root) {
        Number result = null;
        try {
            Object val = Ognl.getValue(ognl, root);
            if (val != null) {
                if (val instanceof Number) {
                    result = (Number) val;
                } else if (val instanceof String) {
                    result = new BigDecimal((String) val);
                }
            }


        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Boolean类型
     *
     * @param ognl
     * @param root
     * @return
     */
    public static Boolean getBoolean(String ognl, Map root) {
        Boolean result = null;
        try {
            Object value = Ognl.getValue(ognl, root);
            if (value != null) {
                if (value instanceof Boolean) {
                    result = (Boolean) value;
                } else if (value instanceof String) {
                    result = ((String) value).equalsIgnoreCase("true") ? true : false;
                } else if (value instanceof Number) {
                    if (((Number) value).intValue() == 1) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        } catch (OgnlException e) {
            e.printStackTrace();
        }
        return result;
    }

}
