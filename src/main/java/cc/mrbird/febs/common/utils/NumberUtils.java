package cc.mrbird.febs.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Joslyn
 * @Title: NumberUtils
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-30 09:57
 */
public class NumberUtils {

    public static String getNumbers(String qqStr) {
        char[] demo = qqStr.toCharArray();
        StringBuffer qqStringb = new StringBuffer();
        for (int i = 0; i < demo.length; i++) {
            if (demo[i] >= '0' && demo[i] <= '9') {
                qqStringb.append(demo[i]);
            }
        }
        System.out.println(qqStringb.toString());
        return qqStringb.toString();
    }

    public static boolean ifPhoneNum(String phone){
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if(phone.length() != 11){
            System.out.println("手机号应为11位数");
            return false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }
}
