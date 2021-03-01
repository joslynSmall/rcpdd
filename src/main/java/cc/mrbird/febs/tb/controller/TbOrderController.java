package cc.mrbird.febs.tb.controller;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.AgisoQueryBo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import cc.mrbird.febs.tb.bean.agiso.TradeInfo;
import cc.mrbird.febs.tb.bean.jiuban.PayOrderRequest;
import cc.mrbird.febs.tb.bean.jiuban.PayOrderResult;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderRep;
import cc.mrbird.febs.tb.bean.yihui.UploadOrderReq;
import cc.mrbird.febs.tb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: TbOrderController
 * @ProjectName febs_shiro_jwt
 * @Description: 淘宝充值订单
 * @date 2019-11-29 15:18
 */
@RestController
public class TbOrderController {

    @Autowired
    private IOrderCommitService orderCommitService;
    @Autowired
    private IQidianPayService qidianPayService;
    @Autowired
    private IJiubanPayService jiubanPayService;
    @Autowired
    private IHaodianPayService haodianPayService;
    @Autowired
    private IKashangPayService kashangPayService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("createOrder")
    public PayOrderResult createOrder(PayOrderRequest payOrderRequest) throws RedisConnectException, IOException {

//        PayOrderResult pay = this.orderCommitService.createOrder(payOrderRequest);
//
//        return pay;
        return null;
    }

    @PostMapping("/createYihuiOrder")
    public UploadOrderRep createYihuiOrder(UploadOrderReq uploadOrderReq) throws RedisConnectException, IOException, InterruptedException {
//        return this.orderCommitService.createYihuiOrder(uploadOrderReq, 1);
        return null;
    }

    // 获取交易信息
    @PostMapping("getTradeInfo")
    public TradeInfo getTradeInfo(AgisoQueryBo queryBo) {

        return this.orderCommitService.getTradeInfo(queryBo);
    }

    // 手动调用发货
    @PostMapping("logisticsDummySend")
    public String logisticsDummySend(AgisoQueryBo queryBo) {
        return this.orderCommitService.LogisticsDummySend(queryBo,"tb9743103");
    }

    // 接收下单推送
    @RequestMapping("beforeBuyPush")
    public ReturnBackResult beforeBuyPush(AgisoPushInfo agisoPushInfo) throws IOException, RedisConnectException, NoSuchAlgorithmException, InterruptedException, ScriptException {
        return this.orderCommitService.beforeBuyPushNew(agisoPushInfo);

    }

    // 久伴登录测试
    @PostMapping("/test/jiubanlogin")
    public Map<String, String> jiubanlogin() throws InterruptedException, RedisConnectException, IOException {
        return this.jiubanPayService.getCookie(true);
    }

    // 起点平台登录测试
    @RequestMapping("/qidianLogin")
    public Map<String, String> qidianLogin() throws RedisConnectException, IOException {
        return this.qidianPayService.getQidianCookie(true);
    }

    @RequestMapping("/whitchorderway")
    public String whitchorderwaySet(String way) throws RedisConnectException {
        String whitchorderway = this.redisService.set("whitchorderway", way);
        return this.redisService.get("whitchorderway");
    }

    @GetMapping("/test/login/jiuban")
    public Map<String, String> login() throws InterruptedException, RedisConnectException, IOException {
        return jiubanPayService.getCookie(true);
    }

    @PostMapping("/test/haodianpay")
    public void haodianpay(String buyerMessage, int num, Integer goodsTimesType) throws InterruptedException, IOException, RedisConnectException, ScriptException {
        haodianPayService.haodianOrderCreate(buyerMessage, num, goodsTimesType);
    }

    @PostMapping("/test/kashangpay/month")
    public boolean kashangpay(String rechargeAccount, int num) throws InterruptedException, RedisConnectException, IOException {
        return kashangPayService.payTxspMonth(rechargeAccount, num);
    }

}
