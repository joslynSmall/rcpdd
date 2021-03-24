package cc.mrbird.febs.pdd.service.impl;

import cc.mrbird.febs.agiso.constants.AgisoContans;
import cc.mrbird.febs.agiso.constants.RedisKeysContans;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.JsoupHttpUtil;
import cc.mrbird.febs.pdd.contants.PDDUrlContants;
import cc.mrbird.febs.pdd.contants.PddOrderContants;
import cc.mrbird.febs.pdd.model.Pageitems;
import cc.mrbird.febs.pdd.model.PddOrderListParam;
import cc.mrbird.febs.pdd.model.PddResult;
import cc.mrbird.febs.pdd.model.Result;
import cc.mrbird.febs.pdd.model.orderChargeDetail.param.OrderChargeDetailParam;
import cc.mrbird.febs.pdd.model.shipping.param.Ordershiprequestlist;
import cc.mrbird.febs.pdd.model.shipping.param.ShippingParam;
import cc.mrbird.febs.pdd.service.IPddOrderService;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import cc.mrbird.febs.tb.bean.common.JrequestBo;
import cc.mrbird.febs.tb.utils.AgisoUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Joslyn
 * @Title: PddOrderServiceImpl
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-10-18 22:36
 */
@Service
public class PddOrderServiceImpl implements IPddOrderService {

    @Autowired
    private RedisService redisService;

    static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Override
    public ReturnBackResult beforeBuyPushNew(AgisoPushInfo agisoPushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        this.validateSign(agisoPushInfo);

        return null;
    }

    @Override
    public boolean validateSign(AgisoPushInfo pushInfo) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Map<String, String> map = new HashMap();
        map.put("json", pushInfo.getJson());
        map.put("timestamp", String.valueOf(pushInfo.getTimestamp()));
        //参考签名算法
        String checkSign = AgisoUtil.Sign(map, AgisoContans.APP_SECRET);
        if (!checkSign.equals(pushInfo.getSign())) {
            return false;
        }

        return true;
    }

    private Map<String, String> buildHeaderMap2(String referer) {

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("authority", "cmtw.pinduoduo.com");
        headersMap.put("method", "post");
//        headersMap.put("path", "/api/mms");
        headersMap.put("path", "/robot/shop/orders/shipping");
        headersMap.put("scheme", "https");
//        headersMap.put("accept", "*/*");
        headersMap.put("accept", "application/json");
        headersMap.put("accept-encoding", "gzip, deflate, br");
        headersMap.put("accept-language", "zh-CN,zh;q=0.9");
        headersMap.put("cache-control", "no-cache");
        headersMap.put("content-length", "182");
        headersMap.put("origin", "https://mms.pinduoduo.com");
        headersMap.put("pragma", "no-cache");
        headersMap.put("referer", referer);


        headersMap.put("sec-ch-ua", "Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99");
        headersMap.put("sec-ch-ua-mobile", "?0");
        headersMap.put("sec-fetch-dest", "empty");
        headersMap.put("ssec-fetch-mode", "cors");
        headersMap.put("ssec-fetch-site", "same-origin");

        return headersMap;
    }

    private Map<String, String> buildHeaderMap(String referer) {

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("authority", "cmtw.pinduoduo.com");
        headersMap.put("method", "post");
        headersMap.put("path", "/api/mms");
        headersMap.put("scheme", "https");
        headersMap.put("accept", "*/*");
        headersMap.put("accept-encoding", "gzip, deflate, br");
        headersMap.put("accept-language", "zh-CN,zh;q=0.9");
        headersMap.put("cache-control", "no-cache");
        headersMap.put("content-length", "465");
        headersMap.put("origin", "https://mms.pinduoduo.com");
        headersMap.put("pragma", "no-cache");
        headersMap.put("referer", referer);
        headersMap.put("Content-Type", "application/json;charset=utf-8");

        return headersMap;
    }

    private String postJsonWithCookies(Object postBean, String payUrl, String referrer) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, RedisConnectException, IOException, InterruptedException {

        /**
         *
         * sec-ch-ua: "Google Chrome";v="89", "Chromium";v="89", ";Not A Brand";v="99"
         * sec-ch-ua-mobile: ?0
         * sec-fetch-dest: empty
         * sec-fetch-mode: cors
         * sec-fetch-site: same-origin
         *
         *
         */

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("authority", "cmtw.pinduoduo.com");
        headersMap.put("method", "post");
        headersMap.put("path", "/mars/shop/orderChargeDetail");
        headersMap.put("scheme", "https");
        headersMap.put("accept", "application/json");
        headersMap.put("accept-encoding", "gzip, deflate, br");
        headersMap.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        headersMap.put("anti-content","0aoAfxsUgNIgYg9a7u0oeXMGif22H_TCnjuMu-uqvVzDzXaDExf8k0ItAxW_pn-uMA3Xx7e61T60aJGMCvw0ab3gIxt4G8yJXmTR9bZzIoG0a3r88c7yq9noRaNJNqryhaKW-aWijwik6xy5DW-6Rn7Efxchz3m0E8jbLv80AQmRnBrumBRoghiRuJFQaYeMWbXk3McjMkAtYhxP5HHvLG_XIWGt-lvH5l-ExLc3y1xoXzdnFdwP9uZVnENarggEON2s2gWBCNlIKbu3u8gxPXcfeLIEYSm31YLa4gcT5b26A_n5UmwC0p2GhvCBIXKTJxaVs6mxefKUVttgVG7h0JoFSO2Am_sZB8QC19yZsnA3sGnnH2lSeU91o9XTa92ab-C4z30p_UsAr3_QOAjV8YGgN01Nrk0a8A9J6XB6IsY7Ie2uykvlDXLyPtzZyuBPT55kvqaW3h5vZ_q7T68Fo7XFJ7XEYgMJzVicf2tLMzQFXKV-tcrzLjbknEs80JTmMHKJGZlaVQ2HlcR7BqyHF05vN7lHxWfDMfxiLN743LYiihvyOXNtdZf6jD5sxf2jp0_-RO2r2AsKK84");
        headersMap.put("cache-control", "no-cache");
        headersMap.put("content-length", "51");
        headersMap.put("origin", "https://mms.pinduoduo.com");
        headersMap.put("pragma", "no-cache");
        headersMap.put("referer", referrer);
        headersMap.put("Content-Type", "application/json;charset=utf-8");
        headersMap.put("sec-ch-ua","\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"");
        headersMap.put("sec-ch-ua-mobile","?0");
        headersMap.put("sec-fetch-dest","empty");
        headersMap.put("sec-fetch-mode","cors");
        headersMap.put("sec-fetch-site","same-origin");

        Connection connect = Jsoup.connect(PDDUrlContants.ORDERCHARGEDETAIL);
        Connection.Response response = connect.requestBody(gson.toJson(postBean))
                .headers(headersMap)
                .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36")
                .timeout(30000)
                .referrer(referrer)
                .cookies(JsoupHttpUtil.parseCookies(redisService.get(RedisKeysContans.PDD_LOGIN_KOOKIES)))
                .method(Connection.Method.POST)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .execute();

        return response.body();
    }

    @Override
    public PddResult getOrderList(PddOrderListParam param, String referrer) throws InterruptedException, RedisConnectException, IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

//        JsoupHttpUtil.trustEveryone();

        JrequestBo jrequestBo = new JrequestBo();

//        headersMap.put("anti-content","0aoAfxnUDOcys9TVHW5Tg9Y_Ymwtku9EZl4LREbfUSwvZgwc_0gtIOd1jQLl0Ei7Fi7DGe9ATuJ9v1gA9w3MS97St4-ScTd_g97kkwhNXRr-FrkpTQ9Bpqgvkx0NFk6qkitLa_Rt7bTRt6JdWB7JZmuxBYEJgzhCbJ1PT9uq5305-d00q2AP7q79uiIxwWdyBqpwh5bhaPKWaGNJbrXCBBwcFMWyNy5YxqGq15qADtSv2FaveSrLd7Dm_-3hiyXbFYQm9uFKHQkTPWytx4EZo87mdPREGbFdxrP6N-2Lpn-YN2a79hzXvlMDy82W_oFmuYogExxtGtgxL8gm5j6wCJngxbE_4Mmo4DzGVvz7lg-GVcgcR9BvEkpkKvGxd8WHXVoScfzsaYb6wQDX1ouSSp9jx6GonHZA8dOadwgw8FhiZJy8P9X5oL6frWDwlksP9mZG1SOXHIk1VT-0T8GmgQQnaC5G8MtjRxQ4LDaHVOl38YiOmLeO2FsHU3zr82jms5TG4RZy88aNfvyF5m5xw1yhAZhhrjopDyIWk9dDeqaApR5u_jJV0zHso9Dx2W8jZmhRBnKiclJJ1WL1Rqer3IElNRuRTY2ttss");

        jrequestBo.setReferrer(referrer);

//        param.setAfterSaleType(0);
//        param.setOrderType(0);
        param.setRemarkStatus(-1);
        param.setUrgeShippingStatus(-1);
        param.setGroupEndTime(DateUtil.currentSeconds());
        param.setGroupStartTime(DateUtil.offsetMonth(new Date(), -1).getTime() / 1000);
        param.setPageNumber(1);
        param.setPageSize(20);
        param.setSortType(7);

//        redisService.set(RedisKeysContans.PDD_LOGIN_KOOKIES, "api_uid=rBQ5Ll/5TcoaNEziJOokAg==; _nano_fp=XpEaX0g8XqCblpdxn9_GHk7hikcjtkFY0ViqCl8q; _bee=iQfod5OVsbalhFW4n4eenutCyrWl7GQL; _f77=a903e106-ade0-436c-9d90-78bbb25fa8e2; _a42=39236969-89a1-4625-84ae-d0beb0051449; rckk=iQfod5OVsbalhFW4n4eenutCyrWl7GQL; ru1k=a903e106-ade0-436c-9d90-78bbb25fa8e2; ru2k=39236969-89a1-4625-84ae-d0beb0051449; terminalFinger=3pzHfU3WZ0Sjm8OxZBLr2w1KmordS97S; mms_b84d1838=120,140,3397,3434,1202,1203,1204,1205; PASS_ID=1-6iOKVWPSvTR7a0/w1+oOobQC4uJa5zgW2vTNFvYIhtIf7JusdIGJLXWfD80Jbdy/EOic9tBzRW7V8qUQbAXR6A_789295185_91605948; x-visit-time=1615881768818; JSESSIONID=F662AE5C47631A6B78DF3248ABE20DE9");
        Map<String, String> payOrderRequestMap = BeanUtils.describe(param);
        jrequestBo.setPayOrderRequestMap(payOrderRequestMap);
        jrequestBo.setCookieStr(redisService.get(RedisKeysContans.PDD_LOGIN_KOOKIES));
        jrequestBo.setPayurl("https://mms.pinduoduo.com/mangkhut/mms/recentOrderList");
        Connection.Response response = JsoupHttpUtil.excutePostJson(jrequestBo, this.buildHeaderMap(referrer));
        String body = response.body();

        PddResult pddResult = gson.fromJson(body, new TypeToken<PddResult<Result<Pageitems>>>() {
        }.getType());

        return pddResult;
    }

    @Override
    public PddResult getNoshipmentOrderList() throws InterruptedException, RedisConnectException, IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        PddResult orderList = this.getOrderList(PddOrderListParam.builder().orderType(PddOrderContants.orderType_one).afterSaleType(PddOrderContants.orderType_one).build()
                , PDDUrlContants.RECENTORDERLIST);

        return orderList;
    }

    @Override
    public PddResult getOrderChargeDetail(String orderSn) throws IllegalAccessException, InvocationTargetException, InterruptedException, IOException, RedisConnectException, NoSuchMethodException {


        JrequestBo jrequestBo = new JrequestBo();
        jrequestBo.setReferrer(PDDUrlContants.ORDERCHARGEDETAIL_REF+orderSn);

        OrderChargeDetailParam param = new OrderChargeDetailParam();
        param.setOrderSn(orderSn);
        param.setSource("MMS");
        Map<String, String> payOrderRequestMap = BeanUtils.describe(param);

        jrequestBo.setPayOrderRequestMap(payOrderRequestMap);
        jrequestBo.setCookieStr(redisService.get(RedisKeysContans.PDD_LOGIN_KOOKIES));
        jrequestBo.setPayurl(PDDUrlContants.ORDERCHARGEDETAIL);
        Connection.Response response = JsoupHttpUtil.excutePostJson(jrequestBo, this.buildHeaderMap(PDDUrlContants.ORDERCHARGEDETAIL_REF+orderSn));
        String body = response.body();

        PddResult pddResult = gson.fromJson(body, new TypeToken<PddResult<cc.mrbird.febs.pdd.model.orderChargeDetail.result.Result>>() {
        }.getType());

        return pddResult;
    }

    @Override
    public PddResult shipping(String orderSn) throws IllegalAccessException, InvocationTargetException, InterruptedException, IOException, RedisConnectException, NoSuchMethodException {

        ShippingParam param = new ShippingParam();
        param.setFunctiontype(6);
        param.setIssingleshipment(1);
        param.setIsvirtualgoods(true);
        param.setOperatefrom("MMS_VIRTUAL");
        param.setOverwrite(1);

        List<Ordershiprequestlist> ordershiprequestlists = new ArrayList<>();
        Ordershiprequestlist orderShipRequestList = new Ordershiprequestlist();
        orderShipRequestList.setOrdersn(orderSn);
        orderShipRequestList.setDeliverytype(0);
        ordershiprequestlists.add(orderShipRequestList);

        param.setOrdershiprequestlist(ordershiprequestlists);


        Connection connect = Jsoup.connect(PDDUrlContants.SHIPPING);
        Connection.Response execute = connect.requestBody(JSONObject.toJSONString(param))
                .headers(this.buildHeaderMap(PDDUrlContants.SHIPPINGREF + orderSn))
                .userAgent(
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36")
                .timeout(30000)
                .referrer(PDDUrlContants.SHIPPINGREF + orderSn)
                .cookies(JsoupHttpUtil.parseCookies(redisService.get(RedisKeysContans.PDD_LOGIN_KOOKIES)))
                .method(Connection.Method.POST)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .execute();

//        String res = this.postJsonWithCookies(param, PDDUrlContants.SHIPPING, PDDUrlContants.SHIPPINGREF + orderSn);
        PddResult pddResult = gson.fromJson(execute.body(), new TypeToken<PddResult<cc.mrbird.febs.pdd.model.shipping.result.Result>>() {
        }.getType());
        return pddResult;
    }
}
