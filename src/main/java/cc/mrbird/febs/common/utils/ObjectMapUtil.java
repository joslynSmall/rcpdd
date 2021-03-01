package cc.mrbird.febs.common.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: ObjectMapUtil
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-25 20:36
 */
public class ObjectMapUtil {
    public static Map<String, String> convertToMap(Object obj) {
        try {
            if (obj instanceof Map) {
                return (Map)obj;
            }
            Map<String, String> returnMap = BeanUtils.describe(obj);
            returnMap.remove("class");
            return returnMap;
        } catch (IllegalAccessException e1) {
            e1.getMessage();
        } catch (InvocationTargetException e2) {
            e2.getMessage();
        } catch (NoSuchMethodException e3) {
            e3.getMessage();
        }
        return new HashMap();
    }
}
