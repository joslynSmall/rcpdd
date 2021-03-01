package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.ObjectMapUtil;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderRep;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderReq;
import cc.mrbird.febs.tb.service.IYihuiPayService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: YihuiPayServiceImpl
 * @ProjectName febs_shiro_jwt
 * @Description: 易慧网络下单
 * @date 2020-04-06 18:10
 */
@Slf4j
@Service
public class YihuiPayServiceImpl implements IYihuiPayService {
    @Override
    public boolean payYihuiTxspMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException {

        UploadOrderReq uploadOrderReq = new UploadOrderReq();
        uploadOrderReq.setSumprice("1");
        uploadOrderReq.setMainKey("0");

        uploadOrderReq.setGoodsId("206813675");
        uploadOrderReq.setSaleprice("12.72");
        uploadOrderReq.setTextAccountName(buyerMessage);

        return this.payOrder(uploadOrderReq, num);

    }

    @Override
    public boolean payYihuiTxspYearOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException {


        UploadOrderReq uploadOrderReq = new UploadOrderReq();
        uploadOrderReq.setSumprice("1");
        uploadOrderReq.setMainKey("0");

        uploadOrderReq.setGoodsId("206813704");
        uploadOrderReq.setSaleprice("130.66");
        uploadOrderReq.setTextAccountName(buyerMessage);

        return this.payOrder(uploadOrderReq, num);
    }

    @Override
    public boolean payYihuiYstMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException {

        UploadOrderReq uploadOrderReq = new UploadOrderReq();
        uploadOrderReq.setSumprice("1");
        uploadOrderReq.setMainKey("0");

        uploadOrderReq.setGoodsId("206813705");
        uploadOrderReq.setSaleprice("20.67");
        uploadOrderReq.setTextAccountName(buyerMessage);

        return this.payOrder(uploadOrderReq, num);
    }

    boolean payOrder(UploadOrderReq uploadOrderReq, int num) throws RedisConnectException, IOException, InterruptedException {
        UploadOrderRep uploadOrderRep = null;

        String referrer = "http://www.yihuikm.com/front/inter/buyGoods.htm?goodId=206813675&keyId=68964&dirId=61034027,0,0";
        String payForurl = "http://www.yihuikm.com/front/inter/uploadOrder.htm?salePwd=123123";

        Map<String, String> payOrderRequestMap = ObjectMapUtil.convertToMap(uploadOrderReq);
        payOrderRequestMap.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");
        log.info("下单方法调用准备,一共{}单", num);
        for (int i = 0; i < num; i++) {
            Connection connect = Jsoup.connect(payForurl);
            Connection.Response execute = connect.data(payOrderRequestMap)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-cn")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Host", "www.yihuikm.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer(referrer)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookies(this.getYihuiCookie(false))
                    .method(Connection.Method.GET)
                    .execute();
            String body = execute.body();
            uploadOrderRep = JSONObject.parseObject(body, UploadOrderRep.class);
            log.info("下单方法调用,一共{}单,当前第{}已提交完成", num, i + 1);
            Thread.sleep(2000);
        }

        if ("订单购买成功!".equals(uploadOrderRep.getMess())) {
            log.info("--------------------------易惠网购买方法调用成功");
            return true;
        } else if ("账户余额不足!".equals(uploadOrderRep.getMess())) {
            log.info("--------------------------易惠网账户余额不足!");
            redisService.set("whitchorderway", "1");
            log.info("--------------------------易惠网....>>>切换为....>>>久伴平台");
            return false;
        } else {
            return false;
        }
    }

    private static final String YihuiCookieRedisKey = "yihuicookierediskey";
    private static final String yiHuioginUrl = "http://www.yihuikm.com/frontLogin.htm?sendVerifyCode=0";

    @Autowired
    private RedisService redisService;

    private Map<String, String> getYihuiCookie(boolean boo) throws RedisConnectException, IOException {

        String yihuiCookie = this.redisService.get(YihuiCookieRedisKey);
        if (boo || yihuiCookie == null) {
            // 调用登录方法 解析cookie
            Connection connect = Jsoup.connect(yiHuioginUrl);
            Map<String, String> data = new HashMap<>();
            data.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");

            data.put("loginTimes", "1");
            data.put("userName", "Jolsyn");
            data.put("password", "qazwsx1234");
            Connection.Response execute = connect.data(data)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Accept", "*/*")
                    .header("Accept-Language", "zh-cn")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Host", "www.yihuikm.com")
                    .header("Origin", "http://www.yihuikm.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer("http://www.yihuikm.com/")
                    .header("Content-Length", "48")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookies(this.parseCookies(yihuiCookie))
                    .method(Connection.Method.GET)
                    .execute();
            Map<String, String> cookies = execute.cookies();
            log.info("易惠网重新登录完成获取cookies>>>>>>>>>>>>>>>>" + cookies.toString());
            if (!"{}".equals(cookies.toString())) {
                String cookieString = cookies.toString().replace("{", "").replace("}", "") + "; ";
                cookieString += "alertmessage1=1; rightAlert=1; JSESSIONID=AF5832C9A2B12DE30F65F701CCA0F0F6.tomcatwork2; Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585795746; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585794840; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c";
                this.redisService.set(YihuiCookieRedisKey, cookieString);
                return this.parseCookies(cookieString);
            } else {
//                this.getYihuiCookie(true);
            }
            return cookies;
        } else {
            return this.parseCookies(yihuiCookie);
        }
    }


    /**
     * cookie字符串转集合EntityUtils
     *
     * @param cookieStr
     * @return
     */
    private Map<String, String> parseCookies(String cookieStr) throws RedisConnectException {
        if (StringUtils.isBlank(cookieStr) || "{}".equals(cookieStr)) {
            this.redisService.set(YihuiCookieRedisKey, "alertmessage1=1; rightAlert=1; JSESSIONID=AF5832C9A2B12DE30F65F701CCA0F0F6.tomcatwork2; Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585795746; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585794840; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c");
            cookieStr = "alertmessage1=1; rightAlert=1; JSESSIONID=AF5832C9A2B12DE30F65F701CCA0F0F6.tomcatwork2; Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585795746; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585794840; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c";
        }
        Map<String, String> cookieMap = new HashMap<>();
        String[] cookieKVStr = cookieStr.split(";");
        for (int i = 0; i < cookieKVStr.length; i++) {
            String cookieKeyStr = cookieKVStr[i].split("=")[0].trim();
            String cookieValStr = cookieKVStr[i].split("=")[1].trim();
            cookieMap.put(cookieKeyStr, cookieValStr);
        }
        return cookieMap;
    }
}
