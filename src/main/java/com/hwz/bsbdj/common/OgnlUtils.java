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
     * @return
     */
    public static Number getNumber(String ognl, Map root) {
        System.out.println("156");
        Number result = null;
        try {
            Object val = Ognl.getValue(ognl, root);
            if (val != null) {
                if (val instanceof Number){
                    result = (Number) val;
                }else if (val instanceof String){
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
     * @param ognl
     * @param root
     * @return
     */
    public static Boolean Boolean(String ognl, Map root) {
        Boolean result = null;

        return  result;
    }
}
