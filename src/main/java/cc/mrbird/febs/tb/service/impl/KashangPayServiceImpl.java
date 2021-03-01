package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.JsoupHttpUtil;
import cc.mrbird.febs.common.utils.ObjectMapUtil;
import cc.mrbird.febs.tb.bean.common.JrequestBo;
import cc.mrbird.febs.tb.bean.contants.CommonContants;
import cc.mrbird.febs.tb.bean.contants.GoodsIdNameInRedis;
import cc.mrbird.febs.tb.bean.kashang.KashangPayInsertBo;
import cc.mrbird.febs.tb.bean.kashang.PayResult;
import cc.mrbird.febs.tb.service.IKashangPayService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: KashangPayServiceImpl
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-04-26 15:52
 */
@Service
@Slf4j
public class KashangPayServiceImpl implements IKashangPayService {

    @Autowired
    private RedisService redisService;

    private static String host = "www.kashangwl.com";

    @Override
    public boolean payKsById(String goodsId, String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException {
        return this.pay(goodsId,rechargeAccount,num);
    }

    private boolean pay(String goodsId, String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException {

        boolean flag = true;

        for (int i = 0; i < num; i++) {
            PayResult payResult = this.validateOrder(goodsId, rechargeAccount);
            if (payResult.isSuccess()) {
                PayResult re = this.submitOrder(goodsId, rechargeAccount);
                if (!re.isSuccess()) {
                    flag = false;
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public String setKashangCookiesMap(String cookiesStr) throws RedisConnectException {

        return this.redisService.set(CommonContants.cookies_kashang, cookiesStr);

    }

    @Override
    public Map<String, String> getKashangCookiesMap() throws RedisConnectException, IOException, InterruptedException {

        String cookies = this.redisService.get(CommonContants.cookies_kashang);

        return JsoupHttpUtil.parseCookies(cookies);
    }

    private PayResult validateOrder(String goodsId, String rechargeAccount) throws InterruptedException, RedisConnectException, IOException {
        JrequestBo jrequestBo = this.buildJrequestBo("http://www.kashangwl.com/buy/validate-order-params", goodsId);
        jrequestBo.setPayOrderRequestMap(this.buildPayOrderRequestMap(goodsId, rechargeAccount));
        Connection.Response excute = JsoupHttpUtil.excute(jrequestBo);
        String body = excute.body();
        log.info(excute.cookies().toString());
        this.redisService.set(CommonContants.cookies_kashang, excute.cookies().toString().replace("{", "").replace("}", ""));
        PayResult payResult = JSONObject.parseObject(body, PayResult.class);
        return payResult;
    }

    private PayResult submitOrder(String goodsId, String rechargeAccount) throws InterruptedException, RedisConnectException, IOException {
        JrequestBo jrequestBo = this.buildJrequestBo("http://www.kashangwl.com/buy/submit-order", goodsId);
        jrequestBo.setPayOrderRequestMap(this.buildPayOrderRequestMap(goodsId, rechargeAccount));
        Connection.Response excute = JsoupHttpUtil.excute(jrequestBo);
        String body = excute.body();
        log.info(excute.cookies().toString());
        this.redisService.set(CommonContants.cookies_kashang, excute.cookies().toString().replace("{", "").replace("}", ""));
        PayResult payResult = JSONObject.parseObject(body, PayResult.class);
        return payResult;
    }

    @Override
    public boolean payTxspWeek(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdTxspKashangWeekId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payTxspMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdTxspKashangMonthId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payTxspSeason(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdTxspKashangSeasonId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payTxspYear(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdTxspKashangYearId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payCjysMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdCjysKashangMonthId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payCjysYear(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdCjysKashangYearId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    @Override
    public boolean payMtwmMonth(String rechargeAccount, int num) throws RedisConnectException, IOException, InterruptedException {
        String goodsId = this.redisService.get(GoodsIdNameInRedis.goodsIdMtwm1mouthId);
        return this.pay(goodsId, rechargeAccount, num);
    }

    public static void main(String[] args) throws InterruptedException, RedisConnectException, IOException {
       /* KashangPayServiceImpl payService = new KashangPayServiceImpl();
        JrequestBo bo = JrequestBo.builder()
                .cookieStr("laravel_session=eyJpdiI6IkxmOUoySDJCSEhlUE80TVwvTVhiYm1RPT0iLCJ2YWx1ZSI6IndlbDZmSlZIXC8yWnpGYkpXOEFkNUZERW40N0VYOXBIXC9sMitYSmIrUkpOUXl6VVJBTndBblMxZmFkRDlVUGNqVCIsIm1hYyI6IjQyNzA2ZjJiNTBjYThkMWQ0Zjg0ZWM4YTAyZTQ2NmQxOGI2MzA3NTg0M2NlNTRiNWU0YWMwNWMwZmQ0NDE5ZGMifQ%3D%3D")
                .host("www.kashangwl.com")
                .payurl("http://www.kashangwl.com/buy/validate-order-params")
                .referrer("http://www.kashangwl.com/buy/37849?return_product_category_id=2185")
                .xcsrtoken("ZzhVesCUbCaRXuUFCn61pm5ris0eTTCv220TqC9T")
                .payOrderRequestMap(KashangPayServiceImpl.buildPayOrderRequestMap("37849","822346113"))
                .build();*/
//        payService.pay(bo);
    }

    public Map<String, String> buildPayOrderRequestMap(String productId, String rechargeAccount) {
        KashangPayInsertBo insertBo = new KashangPayInsertBo();
        insertBo.set_token("3qmA4BXn6Sl9da2K2XdHBNUyyhH81gD7ec0dBeXm");
        insertBo.setProduct_id(productId);
        insertBo.setQuantity(1);
        insertBo.setRecharge_account(rechargeAccount);
        insertBo.setDeal_password("U68ZxvwO");
        return ObjectMapUtil.convertToMap(insertBo);
    }

    public JrequestBo buildJrequestBo(String payUrl, String goodsId) throws RedisConnectException {
        JrequestBo jr = JrequestBo.builder()
                .payurl(payUrl)
                .host(host)
                .xcsrtoken("3qmA4BXn6Sl9da2K2XdHBNUyyhH81gD7ec0dBeXm")
                .referrer("http://www.kashangwl.com/buy/" + goodsId + "?return_product_category_id=")
                .cookieStr(this.redisService.get(CommonContants.cookies_kashang))
                .build();
        return jr;
    }
}
