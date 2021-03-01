package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.enums.GoodsTimesTypeEnum;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.ObjectMapUtil;
import cc.mrbird.febs.tb.bean.contants.GoodsIdNameInRedis;
import cc.mrbird.febs.tb.bean.haodian.HaodianOrderCreateRep;
import cc.mrbird.febs.tb.bean.haodian.HaodianOrderCreateRequest;
import cc.mrbird.febs.tb.bean.haodian.HaodianPayRmbRep;
import cc.mrbird.febs.tb.service.IHaodianPayService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: HaodianPayServiceImpl
 * @ProjectName febs_shiro_jwt
 * @Description: http://www.haodiankm.com/?cid=59&tid=738
 * @date 2020-04-13 15:57
 */
@Service
@Log4j2
public class HaodianPayServiceImpl implements IHaodianPayService {

    private static String haodianCookies = "cookies_haodian";

    private static String payurl = "http://www.haodiankm.com/ajax.php?act=pay";
    private static String payRmburl = "http://www.haodiankm.com/ajax.php?act=payrmb";
    private static String mainurl = "www.haodiankm.com";
    private static String mainurlwithHttp = "www.haodiankm.com";


    @Autowired
    private RedisService redisService;

    private String getCookieStringFromRedis() {
        String cookieStr = null;
        try {
            cookieStr = this.redisService.get(haodianCookies);
        } catch (RedisConnectException e) {
            log.error(e);
        }
        return cookieStr;
    }

    private String getCookieWithOutSsidStringFromRedis() {
        String cookieStr = null;
        try {
            cookieStr = this.redisService.get(haodianCookies);
//            cookieStr = "PHPSESSID=r2pkdkhdm2aa9j48b7klhp6q5p; mysid=de7734dd5f7c613cdd6626ba6159997c; _aihecong_chat_visitorId=5e9aae317ac3f6487166acfb; _aihecong_chat_last=%7B%22time%22%3A1587195441487%2C%22source%22%3A%22http%3A%2F%2Fwww.haodiankm.com%2F%22%2C%22entranceUrl%22%3A%22http%3A%2F%2Fwww.haodiankm.com%2F%22%2C%22entranceTitle%22%3A%22%E5%A5%BD%E7%82%B9%E4%BB%A3%E5%88%B7%E7%BD%91-%E7%A9%BA%E9%97%B4%E4%B8%9A%E5%8A%A1%E6%89%B9%E5%8F%91%E5%B9%B3%E5%8F%B0%2CQQ%E4%BB%A3%E5%88%B7%E7%BD%91%EF%BC%8C%E4%BB%A3%E5%88%B7%E7%BD%91%E6%8E%92%E8%A1%8C%E7%AC%AC%E4%B8%80%EF%BC%8C24%E5%B0%8F%E6%97%B6%E8%87%AA%E5%8A%A9%E4%B8%8B%E5%8D%95%20-%22%7D; user_token=b977G84%2BTG%2B8B9bnUnIbgR8KwJsM2DE3dnHzyGp7aIsIIKrAslhBr7Gbde%2BR0jdbRcWSrd1ay8dwZZAN8y1ZV20AGw; sec_defend=7ec3770c46564a0b473714f77d4f08f31b889d770c62e331723d5af0f5d917a8; counter=9; _aihecong_chat_visibility=false";
            if (cookieStr.contains("PHPSESSID") && cookieStr.contains(";")) {
                Map<String, String> stringStringMap = this.parseCookies(cookieStr, ";");
                String phpsessid = stringStringMap.remove("PHPSESSID");
                cookieStr = stringStringMap.toString().replace("{", "").replace("}", "");
            } else if (cookieStr.contains("PHPSESSID") && cookieStr.contains(",")) {
                Map<String, String> stringStringMap = this.parseCookies(cookieStr, ",");
                String phpsessid = stringStringMap.remove("PHPSESSID");
                cookieStr = stringStringMap.toString().replace("{", "").replace("}", "");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return cookieStr;
    }

    @Override
    public boolean haodianOrderCreate(String buyerMessage, int num, Integer goodsTimeType) throws InterruptedException, RedisConnectException, javax.script.ScriptException, IOException {

        log.info("下单方法调用准备,一共{}单", num);
        int count = 0;
        for (int i = 0; i < num; i++) {
            if (goodsTimeType.equals(GoodsTimesTypeEnum.week.getCode())) {
                this.haodianTxspWeekCreate(buyerMessage);
            } else if (goodsTimeType.equals(GoodsTimesTypeEnum.month.getCode())) {
                this.haodianTxspMonthCreate(buyerMessage);
            }
            log.info("下单方法调用,一共{}单,当前第{}已提交完成,提交账号:{}", num, i + 1, buyerMessage);
            Thread.sleep(2000);
        }
        return true;
    }

    @Override
    public boolean haodianTxspMonthCreate(String buyerMessage) throws RedisConnectException, InterruptedException, ScriptException, IOException {

        log.info("好点月卡购买对象创建");
        HaodianOrderCreateRequest haodianOrderCreateRequest = new HaodianOrderCreateRequest();
        haodianOrderCreateRequest.setTid(Integer.parseInt(redisService.get(GoodsIdNameInRedis.goodsIdTxspMonthId)));
        haodianOrderCreateRequest.setNum(1);
        haodianOrderCreateRequest.setInputvalue(buyerMessage);
        haodianOrderCreateRequest.setHashsalt(this.getHashSalt());

        boolean payResult = this.pay(haodianOrderCreateRequest);

        return payResult;
    }

    @Override
    public boolean haodianTxspWeekCreate(String buyerMessage) throws InterruptedException, RedisConnectException, ScriptException, IOException {

        log.info("好点周卡购买对象创建");
        HaodianOrderCreateRequest haodianOrderCreateRequest = new HaodianOrderCreateRequest();
        haodianOrderCreateRequest.setTid(Integer.parseInt(redisService.get(GoodsIdNameInRedis.goodsIdTxspWeekId)));
        haodianOrderCreateRequest.setNum(1);
        haodianOrderCreateRequest.setInputvalue(buyerMessage);
        haodianOrderCreateRequest.setHashsalt(this.getHashSalt());

        boolean payResult = this.pay(haodianOrderCreateRequest);

        return payResult;
    }

    private Map<String, String> parseCookies(String cookieStr, String sp) {

        Map<String, String> cookieMap = new HashMap<>();
        String[] cookieKVStr = cookieStr.split(sp);
        for (int i = 0; i < cookieKVStr.length; i++) {
            String cookieKeyStr = cookieKVStr[i].split("=")[0].trim();
            String cookieValStr = cookieKVStr[i].split("=")[1].trim();
            cookieMap.put(cookieKeyStr, cookieValStr);
        }
        return cookieMap;
    }

    public String getDocument() throws IOException, InterruptedException, RedisConnectException {
        try {
            Connection connect2 = Jsoup.connect("http://www.haodiankm.com");
            Connection.Response execute = connect2
                    .ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-cn")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Host", "www.haodiankm.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer("http://www.haodiankm.com/?cid=59&tid=738")
                    .header("X-Requested-With", "XMLHttpRequest").execute();
//                    .cookies(this.parseCookies(cookieString)).get();
            Map<String, String> cookies = execute.cookies();
            log.info("调用第一次获取cookies{}", cookies.toString());
            Document document = null;
            if (!cookies.isEmpty()) {

                String ssid = cookies.toString().replace("{", "").replace("}", "") + ", ";
                ssid += this.getCookieWithOutSsidStringFromRedis();
                this.redisService.set(haodianCookies, ssid);
                Map<String, String> cookiemap = this.parseCookies(ssid, ",");
                document = connect2
                        .ignoreContentType(true)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .header("Accept-Language", "zh-cn")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("Host", "www.haodiankm.com")
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                        .header("Connection", "keep-alive")
                        .referrer("http://www.haodiankm.com/?cid=59&tid=738")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .cookies(cookiemap)
                        .get();
            }
            /*用來封裝要保存的参数*/
            Map<String, Object> map = new HashMap<String, Object>();
            /*取得script下面的JS变量*/
            log.info(document.getElementsByTag("script"));
            Elements e = document.getElementsByTag("script").eq(6);
            /*循环遍历script下面的JS变量*/
            for (Element element : e) {

                /*取得JS变量数组*/
                String[] data = element.data().toString().split("var");

                /*取得单个JS变量*/
                for (String variable : data) {

                    /*过滤variable为空的数据*/
                    if (variable.contains("=")) {

                        /*取到满足条件的JS变量*/
                        if (variable.contains("hashsalt")) {

                            String[] kvp = variable.split("=");
//                            log.info("获取好点盐值{}", kvp[1].trim().split(";")[0]);
                            return kvp[1].trim().split(";")[0];
                        }
                    }
                }
            }
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException, javax.script.ScriptException, RedisConnectException {
        HaodianPayServiceImpl haodianPayService = new HaodianPayServiceImpl();
//        String hashsalt = haodianPayService.getDocument();
//        haodianPayService.invokeExpression(hashsalt);
//        haodianPayService.haodianOrderCreate("1030263403", 1);
        haodianPayService.getCookieWithOutSsidStringFromRedis();
    }

    public String invokeExpression(String var) throws ScriptException {
//        log.info("当前盐值:{}", var);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
//        String js = "(+[]+[])+([][[]]+[])[!+[]+!![]]+(+{}+[])[+!![]]+(!+[]+!![]+[])+(!+[]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+[])+(!+[]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+[])+([]+{})[!+[]+!![]+!![]+!![]+!![]]+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(+{}+[])[+!![]]+(+{}+[])[+!![]]+(!+[]+!![]+!![]+[])+([][[]]+[])[!+[]+!![]+!![]]+(!+[]+!![]+[])+([]+{})[!+[]+!![]+!![]+!![]+!![]]+(!+[]+!![]+!![]+[])+(+!![]+[])+(!+[]+!![]+!![]+!![]+[])+(+{}+[])[+!![]]+(+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(+!![]+[])+([][[]]+[])[!+[]+!![]+!![]]+([]+{})[!+[]+!![]]+([]+{})[!+[]+!![]+!![]+!![]+!![]]+(+[]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]+!![]+[])";
        Object eval = engine.eval(var);
        log.info("当前盐值解析后:{}", eval.toString());
        return eval.toString();
    }

    private String getHashSalt() throws InterruptedException, RedisConnectException, IOException, javax.script.ScriptException {
        return this.invokeExpression(this.getDocument());
    }

    private boolean pay(HaodianOrderCreateRequest haodianOrderCreateRequest) throws IOException, RedisConnectException {

        boolean result = false;

        Map<String, String> payOrderRequestMap = ObjectMapUtil.convertToMap(haodianOrderCreateRequest);
        payOrderRequestMap.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");

        Connection connect = Jsoup.connect(payurl);
        Connection.Response execute = connect.data(payOrderRequestMap)
                .ignoreContentType(true)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "zh-cn")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Host", mainurl)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                .header("Connection", "keep-alive")
                .referrer(mainurlwithHttp + "/?cid=59&tid=" + haodianOrderCreateRequest.getTid())
                .header("X-Requested-With", "XMLHttpRequest")
//                    .cookies(this.parseCookies(redisService.get(haodianCookies)))
                .cookies(this.parseCookies(this.getCookieStringFromRedis(), ","))
                .method(Connection.Method.POST)
                .execute();
        String body = execute.body();
        HaodianOrderCreateRep haodianOrderCreateRep = JSONObject.parseObject(body, HaodianOrderCreateRep.class);
        if (haodianOrderCreateRep.getCode() == 0) {
            // 余额支付订单
            Map<String, String> payRmbMap = new HashMap<>();
            payRmbMap.put("orderid", haodianOrderCreateRep.getTradeNo());
            Connection connect2 = Jsoup.connect(payRmburl);
            Connection.Response execute2 = connect2.data(payRmbMap)
                    .ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "zh-cn")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Host", mainurl)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive")
                    .referrer(mainurlwithHttp + "/?cid=59&tid=" + haodianOrderCreateRequest.getTid())
                    .header("X-Requested-With", "XMLHttpRequest")
//                        .cookies(this.parseCookies(redisService.get(haodianCookies)))
                    .cookies(this.parseCookies(this.getCookieStringFromRedis(), ","))
                    .method(Connection.Method.POST)
                    .execute();
            String body2 = execute2.body();
            HaodianPayRmbRep haodianPayRmbRep = JSONObject.parseObject(body2, HaodianPayRmbRep.class);
            log.info(haodianPayRmbRep);
            if (haodianPayRmbRep.getCode() == -3) {
                //余额不足切换到易慧网络下单
                log.info("--------------------------好点代刷账户余额不足!");
                redisService.set("whitchorderway", "2");
                log.info("--------------------------好点代刷平台....>>>切换为....>>>易慧网络平台");
            } else if (haodianOrderCreateRep.getCode() == 1 || haodianOrderCreateRep.getCode() == 0) {
                log.info("好点代刷下单成功{}", haodianPayRmbRep);
                result = true;
            } else if (-4 == haodianOrderCreateRep.getCode()) {
                // -4  你还未登录
                log.info("好点登录失效了请更换coolie");
            } else {
                log.error("支付异常:返回状态码:{},返回状态信息:{}", haodianOrderCreateRep.getCode(), haodianOrderCreateRep.getMsg());
            }
        }
        return result;
    }
}
