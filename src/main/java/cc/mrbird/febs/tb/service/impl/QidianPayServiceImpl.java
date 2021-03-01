package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.ObjectMapUtil;
import cc.mrbird.febs.tb.bean.contants.GoodsIdNameInRedis;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderRep;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderReq;
import cc.mrbird.febs.tb.service.IQidianPayService;
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
 * @Title: QidianPayServiceImpl
 * @ProjectName febs_shiro_jwt
 * @Description: 起点平台下单
 * http://www.qidiankm.com/front/main.htm?pageName=inter/goodDir.htm
 * @date 2020-04-06 18:10
 */
@Slf4j
@Service
public class QidianPayServiceImpl implements IQidianPayService {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean payQidianTxspMonthOrder(String buyerMessage, int num) throws InterruptedException, RedisConnectException, IOException {

        UploadOrderReq uploadOrderReq = new UploadOrderReq();
        uploadOrderReq.setSumprice("1");
        uploadOrderReq.setMainKey("0");

        uploadOrderReq.setGoodsId(this.redisService.get(GoodsIdNameInRedis.goodsIdTxspQidianMonthId));
        uploadOrderReq.setSaleprice("12.7");
        uploadOrderReq.setTextAccountName(buyerMessage);

        return this.payOrder(uploadOrderReq, num);

    }

    boolean payOrder(UploadOrderReq uploadOrderReq, int num) throws RedisConnectException, IOException, InterruptedException {
        UploadOrderRep uploadOrderRep = null;

        String referrer = "http://www.qidiankm.com/front/inter/buyGoods.htm?goodId=" + this.redisService.get(GoodsIdNameInRedis.goodsIdTxspQidianMonthId) + "&keyId=33682&dirId=0,0,0";
        String payForurl = "http://www.qidiankm.com/front/inter/uploadOrder.htm?salePwd=123123";

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
                    .header("Host", "www.qidiankm.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer(referrer)
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookies(this.getQidianCookie(false))
                    .method(Connection.Method.GET)
                    .execute();
            String body = execute.body();
            uploadOrderRep = JSONObject.parseObject(body, UploadOrderRep.class);
            log.info("下单方法调用,一共{}单,当前第{}已提交完成", num, i + 1);
            Thread.sleep(2000);
        }

        if ("订单购买成功!".equals(uploadOrderRep.getMess())) {
            log.info("--------------------------起点平台购买方法调用成功");
            return true;
        } else if ("账户余额不足!".equals(uploadOrderRep.getMess())) {
            log.info("--------------------------起点平台账户余额不足!");
            redisService.set("whitchorderway", "1");
            log.info("--------------------------起点平台....>>>切换为....>>>久伴平台");
            return false;
        } else {
            return false;
        }
    }

    private static final String qidianCookieRedisKey = "qidiancookierediskey";
    private static final String qidianLoginUrl = "http://www.qidiankm.com/frontLogin.htm?sendVerifyCode=0";

    @Override
    public Map<String, String> getQidianCookie(boolean boo) throws RedisConnectException, IOException {

        String qidianCookie = this.redisService.get(qidianCookieRedisKey);
        if (boo || qidianCookie == null) {
            // 调用登录方法 解析cookie
            Connection connect = Jsoup.connect(qidianLoginUrl);
            Map<String, String> data = new HashMap<>();
            data.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");

            data.put("loginTimes", "1");
            data.put("userName", "Joslyn");
            data.put("password", "qzwsx7410");
            Connection.Response execute = connect.data(data)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Accept", "*/*")
                    .header("Accept-Language", "zh-cn")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Host", "www.qidiankm.com")
                    .header("Origin", "http://www.qidiankm.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer("http://www.qidiankm.com/")
                    .header("Content-Length", "48")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .cookies(this.parseCookies(qidianCookie))
                    .method(Connection.Method.GET)
                    .execute();
            Map<String, String> cookies = execute.cookies();
            log.info("起点平台重新登录完成获取cookies>>>>>>>>>>>>>>>>" + cookies.toString());
            if (!"{}".equals(cookies.toString())) {
                String cookieString = cookies.toString().replace("{", "").replace("}", "") + "; ";
                cookieString += "alertmessage1=1; rightAlert=1; JSESSIONID=AF5832C9A2B12DE30F65F701CCA0F0F6.tomcatwork2; Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585795746; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585794840; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c";
                this.redisService.set(qidianCookieRedisKey, cookieString);
                return this.parseCookies(cookieString);
            } else {
//                this.getYihuiCookie(true);
            }
            return cookies;
        } else {
            return this.parseCookies(qidianCookie);
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
            this.redisService.set(qidianCookieRedisKey, "alertmessage1=1; rightAlert=1; JSESSIONID=4919A5521913C693FE2F388D71661492.tomcatwork2; zhuyishixiang_198682252=0; _9755xjdesxxd_=32; gdxidpyhxdE=7QTeMSRoO8VajmtmW2OW%2BaEP09KUEXV3rovgO8mcTMEXfktucMUICL051OOzIlU0wqeVaQhDZN2uZrVCXiUtyCjcEaIngAVWCXZlMans7R%5CEksLqZr9av4scB3yOZ4ypKZLckvObZ3NqDiBpjj4CiejOinDErfRV6rcYnW0YqYZxMOO%2F%3A1586505213111");
            cookieStr = "alertmessage1=1; rightAlert=1; JSESSIONID=4919A5521913C693FE2F388D71661492.tomcatwork2; zhuyishixiang_198682252=0; _9755xjdesxxd_=32; gdxidpyhxdE=7QTeMSRoO8VajmtmW2OW%2BaEP09KUEXV3rovgO8mcTMEXfktucMUICL051OOzIlU0wqeVaQhDZN2uZrVCXiUtyCjcEaIngAVWCXZlMans7R%5CEksLqZr9av4scB3yOZ4ypKZLckvObZ3NqDiBpjj4CiejOinDErfRV6rcYnW0YqYZxMOO%2F%3A1586505213111";
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
