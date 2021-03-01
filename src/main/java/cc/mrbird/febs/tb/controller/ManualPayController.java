package cc.mrbird.febs.tb.controller;

import cc.mrbird.febs.apisource.model.KsResult;
import cc.mrbird.febs.apisource.model.TxmSubmitOrderParams;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.pdd.service.IkashangService;
import cc.mrbird.febs.tb.service.IKashangPayService;
import org.quartz.utils.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Joslyn
 * @Title: ManualPayController
 * @ProjectName backend
 * @Description: 手动调用controller下单
 * @date 2020-11-15 11:37
 */
@RestController
@RequestMapping("/agiso/manual")
public class ManualPayController {

    @Autowired
    private IKashangPayService kashangPayService;

    @PostMapping("/mtyk")
    public boolean PayKsOrder(String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException {

        boolean b = kashangPayService.payMtwmMonth(rechargeAccount, num);

        return b;
    }

    @PostMapping("/payByiId")
    public boolean PayKsById(String goodsId,String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException {

        boolean b = kashangPayService.payKsById(goodsId,rechargeAccount, num);

        return b;
    }

    @Autowired
    private IkashangService kashangService;

    @PostMapping("/mmtest")
    public boolean test() throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {

        kashangService.getProduct();

        return false;
    }

    @PostMapping("/payById")
    public KsResult notifyPddOrder(TxmSubmitOrderParams params) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {

        KsResult ksResult = kashangService.summitPddOrder(params);

        return ksResult;
    }

    @RequestMapping("/getOrderStatusByOrderId")
    public KsResult getOrderStatusByOrderId(String pddOrderNumer,String sign) throws UnsupportedEncodingException, NoSuchAlgorithmException, RedisConnectException {

        KsResult ksResult =  kashangService.getOrderStatusByOrderId(pddOrderNumer,sign);

        return ksResult;
    }
}
