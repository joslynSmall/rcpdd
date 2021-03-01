package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.enums.GoodsTimesTypeEnum;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.NumberUtils;
import cc.mrbird.febs.tb.bean.agiso.*;
import cc.mrbird.febs.tb.bean.agiso.tradeClose.TradeCloseBo;
import cc.mrbird.febs.tb.bean.contants.CommonContants;
import cc.mrbird.febs.tb.bean.contants.GoodsIdNameInRedis;
import cc.mrbird.febs.tb.service.*;
import cc.mrbird.febs.tb.utils.AgisoUtil;
import cc.mrbird.febs.tb.utils.Contant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: OrderCommitService
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-25 20:13
 */
@Slf4j
@Service
public class OrderCommitService implements IOrderCommitService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private IYihuiPayService yihuiPayService;
    @Autowired
    private IJiubanPayService jiubanPayService;
    @Autowired
    private IQidianPayService qidianPayService;
    @Autowired
    private IHaodianPayService haodianPayService;
    @Autowired
    private IKashangPayService kashangPayService;

    private static final String cardUrl = " http://www.wfhmw.com/index.php?m=Home&c=Card&a=cardinfo&id=42711&goods_type=766";

    private static final String HAOLAIWUPAYURL = "http://www.wfhmw.com/index.php?m=home&c=order&a=ly_add&id=42711&goods_type=766";

    private static final String yiHuioginUrl = "http://www.yihuikm.com/frontLogin.htm?sendVerifyCode=0";


    private static final String YihuiCookieRedisKey = "yihuicookierediskey";

    private static final String WangyouCookieRedisKey = "wangyoucookierediskey";

    private static final String whitchOrderWay = "whitchorderway";

    private static final String ORDERID = "orderid:";

    private static final String APP_SECRET = "7ftmmwavba5xhhv286bafh35wahtyerc";

    // 好莱坞月卡id
    private static String HAOLAIWU_GOODS_ID = "613992662757,617382538653";
    // 好莱坞年卡id
    private static final String HAOLAIWU_GOODS_ID2 = "614803292422";
    // 超级影视月卡id
    private static final String CHAOJIYINGSHI_GOODS_ID = "616335174432";
    // 好莱坞周卡id
    private static String HAOLAIWU_WEEK_GOODSID = "616417053198";
    // 美团外卖月卡
    private static final String MEITUAN_1_MOUTH_GOODS_ID = "629155079859";


    // 补单商品ids
    private static final String BUDAN_IDS = "617679754885,614149569965";


    @PostConstruct
    public void init() throws RedisConnectException {

        HAOLAIWU_GOODS_ID = this.redisService.get(GoodsIdNameInRedis.tbTxspMonthGoodsIds);
        HAOLAIWU_WEEK_GOODSID = this.redisService.get(GoodsIdNameInRedis.tbTxspWeekGoodsIds);
        log.info("淘宝商品id初始化完成{};{}", HAOLAIWU_GOODS_ID, HAOLAIWU_WEEK_GOODSID);

    }

    @Override
    public TradeInfo getTradeInfo(AgisoQueryBo agisoQueryBo) {
        String appKey = "2019112519362145451";
        String appSecret = "7ftmmwavba5xhhv286bafh35wahtyerc";
        String accessToken = "TbAldsv2us8r7fr95ehehcktv7ga4fbrugrpzhsvhywu5cn44v";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://gw.api.agiso.com/alds/Trade/TradeInfo");

        //设置头部
        httpPost.addHeader("Authorization", "Bearer " + accessToken);
        httpPost.addHeader("ApiVersion", "1");

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("tids", agisoQueryBo.getTid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //参数签名
        try {
            data.put("sign", AgisoUtil.Sign(data, appSecret));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return (TradeInfo) httpResponse.getEntity();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取补单链接
    private String getBudanUrl(String sellerNick) {

        return "tb9743103".equals(sellerNick) ? "https://item.taobao.com/item.htm?spm=a2126o.11854294.0.0.5af14831YI0QV3&id=614149569965" : "https://item.taobao.com/item.htm?spm=a2126o.11854294.0.0.41b84831R59VXB&id=617679754885";

    }

    @Override
    public String LogisticsDummySend(AgisoQueryBo queryBo, String sellerNick) {

        String appKey = "2019112519362145451";
        String appSecret = "7ftmmwavba5xhhv286bafh35wahtyerc";
        String accessToken = "tb9743103".equals(sellerNick) ? "TbAldsv2us8r7fr95ehehcktv7ga4fbrugrpzhsvhywu5cn44v" : "TbAldsanukffe8kxr2nbpmy6dnffut3zd23gwxvc8urnd67yea";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://gw.api.agiso.com/alds/Trade/LogisticsDummySend");

        //设置头部
        httpPost.addHeader("Authorization", "Bearer " + accessToken);
        httpPost.addHeader("ApiVersion", "1");

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("tids", queryBo.getTid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //参数签名
        try {
            data.put("sign", AgisoUtil.Sign(data, appSecret));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResponse.getEntity());
            } else {
                return ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ReturnBackResult beforeBuyPush(AgisoPushInfo agisoPushInfo) throws RedisConnectException, IOException, NoSuchAlgorithmException, InterruptedException, ScriptException {

        ReturnBackResult returnBackResult = new ReturnBackResult();

        // 判断是下单还是付款 1:下单   2:付款
        if (1 == agisoPushInfo.getAopic()) {

            AgsioBeforePayOrderInfo payOrderInfo = JSONObject.parseObject(agisoPushInfo.getJson(), AgsioBeforePayOrderInfo.class);
            String orderIdInRedis = this.redisService.get(ORDERID + payOrderInfo.getTid());
            // 如果已经存在就直接返回
            if (!StringUtil.isBlank(orderIdInRedis)) {
                return returnBackResult;
            }

            // 如果留言为空直接关单
            /*if (null == payOrderInfo.getBuyerMessage() || "".equals(payOrderInfo.getBuyerMessage())) {
                TradeCloseBo tradeCloseBo = new TradeCloseBo();
                tradeCloseBo.setTids(payOrderInfo.getTid());
                tradeCloseBo.setCloseReason("买家信息填写错误，重新拍");
                String s = this.closeTrade(tradeCloseBo);

                returnBackResult.setAliwwMsg("未留言充值Q号码信息,订单已自动关闭,再次下单记得给卖家留言Q信息 自动充值哦!");
            }*/


            // 加密校验
            // 业务参数
            Map<String, String> data = new HashMap<String, String>();
            data.put("json", JSON.toJSONString(payOrderInfo));
            data.put("timestamp", String.valueOf(agisoPushInfo.getTimestamp()));

            String sign = AgisoUtil.Sign(data, APP_SECRET);
            if (sign.equals(agisoPushInfo.getSign())) {
                System.out.println(sign);
                System.out.println(agisoPushInfo.getSign());

                // 测试  发送消息
                returnBackResult.setAliwwMsg("欢迎来到豫南充值平台,下单付款前别忘记备注q号哦,要不不会自动充值的.");
            }
            return returnBackResult;
            // 付款回调
        } else if (2 == agisoPushInfo.getAopic()) {

            log.info("买家付款啦,正在校验马上开始自动充值");
            AgsioPayOrderInfo payOrderInfo = JSONObject.parseObject(agisoPushInfo.getJson(), AgsioPayOrderInfo.class);
            log.info("下单订单信息:{}", payOrderInfo.toString());

            Orders orders = payOrderInfo.getOrders().get(0);
            log.info("下单商品信息:{}", orders.toString());

            // 判断订单是否处理过
            String orderIdInRedis = this.redisService.get(ORDERID + payOrderInfo.getTid());
            if (!StringUtil.isBlank(orderIdInRedis)) {
                log.info("此订单已经在系统充值过了,不再充值,直接返回", CommonContants.goodsType + payOrderInfo.getBuyerNick(), payOrderInfo.getBuyerMessage());
                return returnBackResult;
            }
            // 判断留言是否为空
            // 判断买家的备注信息
            String buyerMessage = payOrderInfo.getBuyerMessage();
            log.info("获取买家留言信息{}", buyerMessage);
            if (null == buyerMessage || "".equals(buyerMessage)) {
                log.info("获取买家留言信息为空值,发送提示信息给客户{}", payOrderInfo.getBuyerNick());
                if (StringUtils.isBlank(redisService.get(payOrderInfo.getBuyerNick()))) {
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick(), String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    returnBackResult.setAliwwMsg("你好,系统检测到您没有备注账号信息,无法自动充值,请拍下以下1分钱链接进行补单,这次不要忘记备注q哦." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                } else {
                    // 设置补单标识
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1, String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    returnBackResult.setAliwwMsg("你好,系统检测到您依旧忘记备注账号信息,无法自动充值,请拍下以下1分钱链接进行补单,这次一定不要忘记备注q哦." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                }
                // 设置一个字符金额判断补单补的产品
                if (redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype") == null && !BUDAN_IDS.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                    if (HAOLAIWU_GOODS_ID2.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(年)");
                    } else if (HAOLAIWU_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(月)");
                    } else if (HAOLAIWU_WEEK_GOODSID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(周)");
                    } else if (CHAOJIYINGSHI_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "超级影视(月)");
                    } else if (MEITUAN_1_MOUTH_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "美团外卖(月)");
                    }
                }
            } else {
                // 剥离数字
                String buyerMssageN = NumberUtils.getNumbers(buyerMessage);

                // 调用充值方法
                log.info("获取处理后的数字{}>>>>>>>>>>>>>>>>:", buyerMssageN);

                if (buyerMssageN.matches(Contant.REGEX_MOBILE) || StringUtils.isEmpty(buyerMssageN)) {
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick(), String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    // 设置一个字符金额判断补单补的产品
                    if (redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype") == null && !BUDAN_IDS.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        if (HAOLAIWU_GOODS_ID2.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                            redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(年)");
                        } else if (HAOLAIWU_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                            redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(月)");
                        } else if (HAOLAIWU_WEEK_GOODSID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                            redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "腾讯视频(周)");
                        } else if (CHAOJIYINGSHI_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                            redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "超级影视(月)");
                        }
                    }
                    returnBackResult.setAliwwMsg("你好,目前只支持Q账户充值,其他目前不支持,请拍下以下1分钱链接系统将进行补单,这次不要忘记备注q/号哦,补单链接就是下面这个请点击." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                    return returnBackResult;
                }

                boolean payResult = false;

                // 先把订单id 设置进去  先防止重复调用
                redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                String tbGoodsId = String.valueOf(payOrderInfo.getOrders().get(0).getNumIid());
                // 好莱坞月卡
                if (HAOLAIWU_GOODS_ID.contains(tbGoodsId)) {
                    payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.month.getCode());
                    // 下单好莱坞年卡
                } else if (HAOLAIWU_GOODS_ID2.contains(tbGoodsId)) {
                    payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.year.getCode());
                    // 下单超级影视
                } else if (CHAOJIYINGSHI_GOODS_ID.contains(tbGoodsId)) {
                    payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.cmonth.getCode());
                }
                // 下单好莱坞周卡
                else if (HAOLAIWU_WEEK_GOODSID.contains(tbGoodsId)) {
                    redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);
                    payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.week.getCode());
                }
                // 如果是补单商品
                else if (BUDAN_IDS.contains(tbGoodsId)) {
                    // 获取之前订单
                    String orderId = redisService.get(payOrderInfo.getBuyerNick());
                    if (StringUtils.isNotBlank(orderId)) {
                        if (!buyerMssageN.matches(Contant.REGEX_MOBILE)) {

                            //写入补单订单号记录
                            redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                            // 判断是月还是年
                            if ("腾讯视频(年)".equals(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype"))) {
                                payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.year.getCode());
                            } else if ("腾讯视频(月)".equals(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype"))) {
                                payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.month.getCode());
                            } else if ("腾讯视频(周)".equals(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype"))) {
                                payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.week.getCode());
                            } else if ("超级影视(月)".equals(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype"))) {
                                payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.cmonth.getCode());
                            }

                            if (payResult) {
                                // 给原先订单发货
                                // 调用发货方法给原先订单发货
                                AgisoQueryBo agisoQueryBo = new AgisoQueryBo();
                                agisoQueryBo.setTid(String.valueOf(orderId));
                                this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());
                                log.info("补单发货:非补单商品发货成功,订单号{}", orderId);

                                // 判断是否拍了两次补单
                                if (StringUtils.isNotBlank(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1))) {
                                    log.info("二次补单订单号:{};用户{},准备发货!", redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1), payOrderInfo.getBuyerNick());
                                    agisoQueryBo.setTid(String.valueOf(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1)));
                                    this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());
                                    log.info("二次补单订单号:{};用户{},发货成功!", redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1), payOrderInfo.getBuyerNick());
                                }

                                // 清除补单
                                redisService.del(payOrderInfo.getBuyerNick());
                                // 清楚月年标识
                                redisService.del(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype");
                            }
                        } else {
                            // 发送消息给客户
                            returnBackResult.setAliwwMsg("你好,目前只支持Q账户充值,其他目前不支持,请拍下以下1分钱链接进行补单,这次不要忘记备注q号啊!!补单链接就是下面这个请点击." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                            return returnBackResult;
                        }
                    } else {
                        returnBackResult.setAliwwMsg("单拍补单链接无效哦,此链接是之前订单忘记填写留言备注的人拍下重新填写账号充值补单用的.");
                        return returnBackResult;
                    }
                }

                if (payResult) {
                    // 充值成功 写入 redis 记录
                    redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                    // 发送通知

                    // 调用发货方法
/*                    AgisoQueryBo agisoQueryBo = new AgisoQueryBo();
                    agisoQueryBo.setTid(String.valueOf(payOrderInfo.getTid()));
                    this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());*/
                    log.info("--------------------------购买方法调用成功后发货成功,订单号{},购买人{}", payOrderInfo.getTid(), payOrderInfo.getBuyerNick());


                    // 并且发货
                    returnBackResult.setDoDummySend("true");
                    returnBackResult.setAliwwMsg("已经帮您提交订单啦,稍后就会到账您的客户端都会收到提示!小店小本经营,恳求大大们及时确认收货,求求大家啦!!!");
                }
            }

        }

        return returnBackResult;
    }

    @Override
    public ReturnBackResult beforeBuyPushNew(AgisoPushInfo agisoPushInfo) throws RedisConnectException, IOException, NoSuchAlgorithmException, InterruptedException, ScriptException {

        ReturnBackResult returnBackResult = new ReturnBackResult();

        // 判断是下单还是付款 1:下单   2:付款
        if (1 == agisoPushInfo.getAopic()) {

            AgsioBeforePayOrderInfo payOrderInfo = JSONObject.parseObject(agisoPushInfo.getJson(), AgsioBeforePayOrderInfo.class);
            String orderIdInRedis = this.redisService.get(ORDERID + payOrderInfo.getTid());
            // 如果已经存在就直接返回
            if (!StringUtil.isBlank(orderIdInRedis)) {
                return returnBackResult;
            }

            // 加密校验
            // 业务参数
            Map<String, String> data = new HashMap<String, String>();
            data.put("json", JSON.toJSONString(payOrderInfo));
            data.put("timestamp", String.valueOf(agisoPushInfo.getTimestamp()));

            String sign = AgisoUtil.Sign(data, APP_SECRET);
            if (sign.equals(agisoPushInfo.getSign())) {
                System.out.println(sign);
                System.out.println(agisoPushInfo.getSign());

                // 测试  发送消息
                returnBackResult.setAliwwMsg("欢迎来到豫南充值平台,下单付款前别忘记备注q号哦,要不不会自动充值的.");
            }
            return returnBackResult;
            // 付款回调
        } else if (2 == agisoPushInfo.getAopic()) {

            log.info("买家付款啦,正在校验马上开始自动充值");
            AgsioPayOrderInfo payOrderInfo = JSONObject.parseObject(agisoPushInfo.getJson(), AgsioPayOrderInfo.class);
            log.info("下单订单信息:{}", payOrderInfo.toString());

            Orders orders = payOrderInfo.getOrders().get(0);
            log.info("下单商品信息:{}", orders.toString());

            // 判断订单是否处理过
            String orderIdInRedis = this.redisService.get(ORDERID + payOrderInfo.getTid());
            if (!StringUtil.isBlank(orderIdInRedis)) {
                log.info("此订单已经在系统充值过了,不再充值,直接返回", CommonContants.goodsType + payOrderInfo.getBuyerNick(), payOrderInfo.getBuyerMessage());
                return returnBackResult;
            }
            // 判断留言是否为空
            // 判断买家的备注信息
            String buyerMessage = payOrderInfo.getBuyerMessage();
            log.info("获取买家留言信息{}", buyerMessage);
            if (null == buyerMessage || "".equals(buyerMessage)) {
                log.info("获取买家留言信息为空值,发送提示信息给客户{}", payOrderInfo.getBuyerNick());
                if (StringUtils.isBlank(redisService.get(payOrderInfo.getBuyerNick()))) {
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick(), String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    returnBackResult.setAliwwMsg("你好,系统检测到您没有备注账号信息,无法自动充值,请拍下以下1分钱链接进行补单,这次不要忘记备注手机号哦." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                } else {
                    // 设置补单标识
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1, String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    returnBackResult.setAliwwMsg("你好,系统检测到您依旧忘记备注账号信息,无法自动充值,请拍下以下1分钱链接进行补单,这次一定不要忘记备注手机号哦." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                }
                // 设置一个字符金额判断补单补的产品
                if (redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype") == null && !BUDAN_IDS.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                    if (MEITUAN_1_MOUTH_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "美团外卖(月)");
                    }
                }
            } else {
                // 剥离数字
                String buyerMssageN = NumberUtils.getNumbers(buyerMessage);

                // 调用充值方法
                log.info("获取处理后的数字{}>>>>>>>>>>>>>>>>:", buyerMssageN);

                if (!buyerMssageN.matches(Contant.REGEX_MOBILE)) {
                    redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick(), String.valueOf(payOrderInfo.getTid()), Long.valueOf(3600 * 1000 * 24));
                    // 设置一个字符金额判断补单补的产品
                    if (redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype") == null && !BUDAN_IDS.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                        if (MEITUAN_1_MOUTH_GOODS_ID.contains(String.valueOf(payOrderInfo.getOrders().get(0).getNumIid()))) {
                            redisService.set(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype", "美团外卖(月)");
                        }
                    }
                    returnBackResult.setAliwwMsg("你好,目前只支持手机账户充值,其他目前不支持,请拍下以下1分钱链接系统将进行补单,这次不要忘记备注手机号哦,补单链接就是下面这个请点击." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                    return returnBackResult;
                }

                boolean payResult = false;

                // 先把订单id 设置进去  先防止重复调用
                redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                String tbGoodsId = String.valueOf(payOrderInfo.getOrders().get(0).getNumIid());
                // 美团月卡
                if (MEITUAN_1_MOUTH_GOODS_ID.contains(tbGoodsId)) {
                    payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.mtwmmonth.getCode());
                }
                // 如果是补单商品
                else if (BUDAN_IDS.contains(tbGoodsId)) {
                    // 获取之前订单
                    String orderId = redisService.get(payOrderInfo.getBuyerNick());
                    if (StringUtils.isNotBlank(orderId)) {
                        if (!buyerMssageN.matches(Contant.REGEX_MOBILE)) {

                            //写入补单订单号记录
                            redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                            // 判断是月还是年
                            if ("美团外卖(月)".equals(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype"))) {
                                payResult = this.pay(buyerMssageN, orders.getNum(), GoodsTimesTypeEnum.mtwmmonth.getCode());
                            }

                            if (payResult) {
                                // 给原先订单发货
                                // 调用发货方法给原先订单发货
                                AgisoQueryBo agisoQueryBo = new AgisoQueryBo();
                                agisoQueryBo.setTid(String.valueOf(orderId));
                                this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());
                                log.info("补单发货:非补单商品发货成功,订单号{}", orderId);

                                // 判断是否拍了两次补单
                                if (StringUtils.isNotBlank(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1))) {
                                    log.info("二次补单订单号:{};用户{},准备发货!", redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1), payOrderInfo.getBuyerNick());
                                    agisoQueryBo.setTid(String.valueOf(redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1)));
                                    this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());
                                    log.info("二次补单订单号:{};用户{},发货成功!", redisService.get(CommonContants.goodsType + payOrderInfo.getBuyerNick() + 1), payOrderInfo.getBuyerNick());
                                }

                                // 清除补单
                                redisService.del(payOrderInfo.getBuyerNick());
                                // 清楚月年标识
                                redisService.del(CommonContants.goodsType + payOrderInfo.getBuyerNick() + "goodstype");
                            }
                        } else {
                            // 发送消息给客户
                            returnBackResult.setAliwwMsg("你好,目前只支持手机账户充值,其他目前不支持,请拍下以下1分钱链接进行补单,这次不要忘记备注手机号啊!!补单链接就是下面这个请点击." + this.getBudanUrl(payOrderInfo.getSellerNick()));
                            return returnBackResult;
                        }
                    } else {
                        returnBackResult.setAliwwMsg("单拍补单链接无效哦,此链接是之前订单忘记填写留言备注的人拍下重新填写账号充值补单用的.");
                        return returnBackResult;
                    }
                }

                if (payResult) {
                    // 充值成功 写入 redis 记录
                    redisService.set(ORDERID + payOrderInfo.getTid(), "1", (long) 432000000);

                    // 发送通知

                    // 调用发货方法
/*                    AgisoQueryBo agisoQueryBo = new AgisoQueryBo();
                    agisoQueryBo.setTid(String.valueOf(payOrderInfo.getTid()));
                    this.LogisticsDummySend(agisoQueryBo, payOrderInfo.getSellerNick());*/
                    log.info("--------------------------购买方法调用成功后发货成功,订单号{},购买人{}", payOrderInfo.getTid(), payOrderInfo.getBuyerNick());


                    // 并且发货
                    returnBackResult.setDoDummySend("true");
                    returnBackResult.setAliwwMsg("已经帮您提交订单啦,稍后就会到账您的客户端都会收到提示!小店小本经营,恳求大大们及时确认收货,求求大家啦!!!");
                }
            }

        }

        return returnBackResult;
    }

    public boolean pay(String buyerMssageN, int num, Integer goodsTimeType) throws RedisConnectException, IOException, InterruptedException, ScriptException {
        String orderWay = this.redisService.get(whitchOrderWay);
        // 如果为1 或者为空下载久伴
        boolean res = false;
        if ((orderWay == null || "1".equals(orderWay)) && GoodsTimesTypeEnum.month.getCode().equals(goodsTimeType)) {
            log.info("久伴下单腾讯视频月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = jiubanPayService.payJiubanTxspMonthOrder(buyerMssageN, num);
        } else if ("2".equals(orderWay) && GoodsTimesTypeEnum.month.getCode().equals(goodsTimeType)) {
            log.info("易慧下单腾讯视频月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = yihuiPayService.payYihuiTxspMonthOrder(buyerMssageN, num);
        } else if ("2".equals(orderWay) && GoodsTimesTypeEnum.year.getCode().equals(goodsTimeType)) {
            log.info("易慧下单腾讯视频年卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = yihuiPayService.payYihuiTxspMonthOrder(buyerMssageN, num);
        } else if ("2".equals(orderWay) && GoodsTimesTypeEnum.cmonth.getCode().equals(goodsTimeType)) {
            log.info("易慧下单超级影视月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = yihuiPayService.payYihuiYstMonthOrder(buyerMssageN, num);
        } else if ("3".equals(orderWay) && GoodsTimesTypeEnum.month.getCode().equals(goodsTimeType)) {
            log.info("起点下单腾讯视频月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = qidianPayService.payQidianTxspMonthOrder(buyerMssageN, num);
        } else if ("4".equals(orderWay) && GoodsTimesTypeEnum.month.getCode().equals(goodsTimeType)) {
            log.info("好点下单腾讯视频月会员准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = haodianPayService.haodianOrderCreate(buyerMssageN, num, goodsTimeType);
        } else if ("4".equals(orderWay) && GoodsTimesTypeEnum.week.getCode().equals(goodsTimeType)) {
            log.info("好点下单腾讯视频周会员准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = haodianPayService.haodianOrderCreate(buyerMssageN, num, goodsTimeType);
        } else if ("5".equals(orderWay) && GoodsTimesTypeEnum.month.getCode().equals(goodsTimeType)) {
            log.info("卡商下单腾讯视频月会员准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = kashangPayService.payTxspMonth(buyerMssageN, num);
        } else if ("5".equals(orderWay) && GoodsTimesTypeEnum.week.getCode().equals(goodsTimeType)) {
            log.info("卡商下单腾讯视频周会员准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = kashangPayService.payTxspWeek(buyerMssageN, num);
        } else if ("5".equals(orderWay) && GoodsTimesTypeEnum.year.getCode().equals(goodsTimeType)) {
            log.info("卡商下单腾讯视频年卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = kashangPayService.payTxspYear(buyerMssageN, num);
        } else if ("5".equals(orderWay) && GoodsTimesTypeEnum.cmonth.getCode().equals(goodsTimeType)) {
            log.info("卡商下单超级影视月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = kashangPayService.payCjysMonth(buyerMssageN, num);
        } else if ("5".equals(orderWay) && GoodsTimesTypeEnum.mtwmmonth.getCode().equals(goodsTimeType)) {
            log.info("卡商下单美团月卡准备调用>>>>>>>>>>>>>>>>>>>>>>");
            res = kashangPayService.payMtwmMonth(buyerMssageN, num);
        }
        return res;
    }

    @Override
    public String closeTrade(TradeCloseBo closeBo) {

        String appKey = "2019112519362145451";
        String appSecret = "7ftmmwavba5xhhv286bafh35wahtyerc";
        String accessToken = "TbAldsv2us8r7fr95ehehcktv7ga4fbrugrpzhsvhywu5cn44v";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://gw.api.agiso.com/alds/Trade/Close");

        //设置头部
        httpPost.addHeader("Authorization", "Bearer " + accessToken);
        httpPost.addHeader("ApiVersion", "1");

        //业务参数
        Map<String, String> data = new HashMap<String, String>();
        data.put("tids", closeBo.getTids());
        data.put("closeReason", closeBo.getCloseReason());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = System.currentTimeMillis() / 1000;
        data.put("timestamp", timestamp.toString());
        //参数签名
        try {
            data.put("sign", AgisoUtil.Sign(data, appSecret));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //发起POST请求
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResponse.getEntity());
            } else {
                return ("doPost Error Response: " + httpResponse.getStatusLine().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
