package cc.mrbird.febs.tb.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Joslyn
 * @Title: AgisoUtil
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-12-01 13:09
 */
public class AgisoUtil {


    private Map sing() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> data = new HashMap<String, String>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data.put("modifyTimeStart", "2016-07-13 10:44:30");
        data.put("pageNo", "1");
        data.put("pageSize", "20");
//timestamp 为调用Api的公共参数，详细说明参考接入指南
        data.put("timestamp", "1468476350");//假设当前时间为2016/7/14 14:5:50
//对键排序
        String[] keys = data.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
//头加入AppSecret ，假设AppSecret值为******************
        query.append(this.getClientSecret());
        for (String key : keys) {
            String value = data.get(key);
            query.append(key).append(value);
        }
//到这query的值为******************modifyTimeStart2016-07-13 10:44:30pageNo1pageSize20timestamp1468476350
//尾加入AppSecret
        query.append(this.getClientSecret()); //query=******************modifyTimeStart2016-07-13 10:44:30pageNo1pageSize20timestamp1468476350******************
        byte[] md5byte = encryptMD5(query.toString());
//sign 为调用Api的公共参数，详细说明参考接入指南
        data.put("sign", AgisoUtil.byte2hex(md5byte)); //byte2hex(md5byte) = 935671331572EBF7F419EBB55EA28558
        return data;
    }

    private boolean getClientSecret() {

        return false;
    }


    //参数签名
    public static String Sign(Map<String, String> params, String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }
        query.append(appSecret);

        byte[] md5byte = encryptMD5(query.toString());

        return byte2hex(md5byte);
    }

    public static String sign_ks(Map<String, String> params, String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }

        return DigestUtils.md5DigestAsHex(query.toString().getBytes());

//        byte[] md5byte = encryptMD5(query.toString());

//        return byte2hex(md5byte);
    }


    public static String sign_txm(Map<String, String> params, String appSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] keys = params.keySet().toArray(new String[0]);

        StringBuilder query = new StringBuilder();
        query.append(appSecret);
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }

        return DigestUtils.md5DigestAsHex(query.toString().getBytes());

    }

    //byte数组转成16进制字符串
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }

    //Md5摘要
    public static byte[] encryptMD5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return md5.digest(data.getBytes("UTF-8"));
    }
}


