package cc.mrbird.febs.pdd.service.impl;

import cc.mrbird.febs.agiso.constants.AgisoContans;
import cc.mrbird.febs.agiso.constants.RedisKeysContans;
import cc.mrbird.febs.apisource.model.*;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.pdd.service.IkashangService;
import cc.mrbird.febs.tb.utils.AgisoUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.java.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: kashangServiceImpl
 * @ProjectName backend
 * @Description: 卡商网络下单
 * @date 2/8/21 11:54 AM
 */
@Service
@Log
public class kashangServiceImpl implements IkashangService {

    @Autowired
    private RedisService redisService;

    @Override
    public KsResult getProduct() throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {

        redisService.hset(RedisKeysContans.PDD_GOODS_IDS, "pdd123", "40891");
        redisService.hset(RedisKeysContans.PDD_GOODS_IDS, "pdd456", "212222");

        KsResult ksResult = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://www.kashangwl.com/api/customer");

        //设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); //设置传输的数据格式

        Map<String, String> map = new HashMap();
        map.put("customer_id", AgisoContans.APP_NUM);
//        map.put("timestamp", "1600672258000");
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        //参考签名算法
        String checkSign = AgisoUtil.sign_ks(map, AgisoContans.APP_SECRET_KS);

        //业务参数
        map.put("sign", checkSign);

        //参数签名

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Gson gson = new Gson();
                ksResult = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), new TypeToken<KsResult<KsAccountData>>() {
                }.getType());
//                ksResult = JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity()), KsResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ksResult;
    }

    @Override
    public KsResult summitPddOrder(TxmSubmitOrderParams params) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {


        //计算提交签名
        String sign = params.getSign();
        String befsign = params.getPddOrderNumer()+params.getPddGoodsId()+params.getBuyNum()+"joslyn";
        String afsign = DigestUtils.md5DigestAsHex(befsign.getBytes());
        if (!sign.equals(afsign)){
            return KsResult.error("-3","签名校验失败");
        }
        if (params.getPddOrderNumer().equals(redisService.hget(RedisKeysContans.PDD_ORDER_LIST, String.valueOf(params.getPddOrderNumer())))){
            return KsResult.error("-4","订单重复提交");
        }

        KsResult ksResult = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://www.kashangwl.com/api/buy");

        //设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); //设置传输的数据格式

        Map<String, String> map = new HashMap();
        map.put("customer_id", AgisoContans.APP_NUM);
        map.put("product_id", redisService.hget(RedisKeysContans.PDD_GOODS_IDS, String.valueOf(params.getPddGoodsId())));
        map.put("recharge_account", params.getRechargeAccount());


        //参数签名
        // 处理多单问题
        for (int i = 0; i < params.getBuyNum(); i++) {

            map.put("quantity", "1");
            map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            //参考签名算法
            String checkSign = AgisoUtil.sign_ks(map, AgisoContans.APP_SECRET_KS);

            //业务参数
            map.put("sign", checkSign);


            List<BasicNameValuePair> valuePairs = new ArrayList<BasicNameValuePair>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            //发起POST请求
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
                HttpResponse httpResponse = httpclient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                    ksResult = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), new TypeToken<KsResult<KsPayResult>>() {
                    }.getType());
                    if (ksResult.getCode().equals("ok")){
                        KsPayResult result = (KsPayResult)ksResult.getData();
                        redisService.hset(RedisKeysContans.PDD_ORDER_LIST,params.getPddOrderNumer(), String.valueOf(result.getState()));
                        redisService.hset(RedisKeysContans.PDD_KS_ORDER_LIST,params.getPddOrderNumer(), result.getOrderId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        log.info(ksResult.toString());
        return ksResult;
    }

    @Override
    public KsResult getOrderStatusByOrderId(String pddOrderNumer,String sign) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {


        //计算提交签名
        String befsign = pddOrderNumer+"joslyn";
        String afsign = DigestUtils.md5DigestAsHex(befsign.getBytes());
        if (!sign.equals(afsign)){
            return KsResult.error("-3","签名校验失败");
        }

        String ksOrderId = redisService.hget(RedisKeysContans.PDD_KS_ORDER_LIST,pddOrderNumer);

        KsResult ksResult = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://www.kashangwl.com/api/order");

        //设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); //设置传输的数据格式

        Map<String, String> map = new HashMap();
        map.put("customer_id", AgisoContans.APP_NUM);
        map.put("order_id", ksOrderId);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        //参考签名算法
        String checkSign = AgisoUtil.sign_ks(map, AgisoContans.APP_SECRET_KS);

        //业务参数
        map.put("sign", checkSign);

        //参数签名

        List<BasicNameValuePair> valuePairs = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(valuePairs, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                ksResult = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), new TypeToken<KsResult<KsOrderResult>>() {
                }.getType());
                if (ksResult.getCode().equals("ok")){
                    KsOrderResult result = (KsOrderResult)ksResult.getData();
                    redisService.hset(RedisKeysContans.PDD_ORDER_LIST,pddOrderNumer, String.valueOf(result.getState()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        log.info(ksResult.toString());
        return ksResult;
    }
}
