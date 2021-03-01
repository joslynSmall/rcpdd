package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.ObjectMapUtil;
import cc.mrbird.febs.tb.bean.contants.CommonContants;
import cc.mrbird.febs.tb.bean.jiuban.CookieTimeOutResult;
import cc.mrbird.febs.tb.bean.jiuban.PayOrderRequest;
import cc.mrbird.febs.tb.bean.jiuban.PayOrderResult;
import cc.mrbird.febs.tb.service.IJiubanPayService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JiubanPayServiceImpl implements IJiubanPayService {
    private static final Logger log = LoggerFactory.getLogger(JiubanPayServiceImpl.class);
    @Autowired
    private RedisService redisService;
    private static final String HAOLAIWUPAYURL = "http://www.wfhmw.com/index.php?m=home&c=order&a=ly_add&id=42711&goods_type=766";
    private static final String wfhmwCookieRedisKey = "wfhmwcookierediskey";
    private static final String loginUrl = "http://www.wfhmw.com/index.php?m=Home&c=User&a=login&id=42711&goods_type=766";

    @Override
    public boolean payJiubanTxspMonthOrder(String buyerMessage, int num) throws RedisConnectException, IOException, InterruptedException {
        PayOrderRequest payOrderRequest = new PayOrderRequest();
        payOrderRequest.setNeed_num_0(1);
        payOrderRequest.setPay_type(1);
        payOrderRequest.setGoods_id(42711);
        payOrderRequest.setGoods_type(766);
        payOrderRequest.setQq(buyerMessage);
        String referrer = "http://www.wfhmw.com/index.php?m=Home&c=Goods&a=detail&id=42711&goods_type=766";
        String payForurl = "http://www.wfhmw.com/index.php?m=home&c=order&a=ly_add&id=42711&goods_type=766";
        Map<String, String> payOrderRequestMap = ObjectMapUtil.convertToMap(payOrderRequest);
        payOrderRequestMap.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");
        Connection connect = Jsoup.connect(payForurl);
        PayOrderResult payOrderResult = null;

        for (int i = 0; i < num; i++) {
            Response execute = connect.data(payOrderRequestMap).ignoreContentType(true)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language", "zh-cn").header("Accept-Encoding", "gzip, deflate")
                    .header("Host", "www.wfhmw.com").header("Origin", "http://www.wfhmw.com")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                    .header("Connection", "keep-alive").referrer(referrer).header("Content-Length", "65")
                    .header("X-Requested-With", "XMLHttpRequest").cookies(this.getCookie(false)).method(Method.POST)
                    .execute();
            String body = execute.body();
            log.info("久伴社区下单返回对象:{}", body);
            payOrderResult = JSONObject.parseObject(body,PayOrderResult.class);
            if (0 == payOrderResult.getStatus()) {
                this.getCookie(true);
                this.payJiubanTxspMonthOrder(buyerMessage, num);
            }
        }

        if (1 == payOrderResult.getStatus() && "下单成功！".equals(payOrderResult.getInfo())) {
            log.info("--------------------------久伴网购买方法调用成功");
            this.redisService.set(payOrderRequest.getQq(), payOrderRequest.getQq(), 155520000L);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized Map<String, String> getCookie(boolean boo) throws RedisConnectException, IOException, InterruptedException {
        String wfhmwCookie = this.redisService.get("wfhmwcookierediskey");
        if (!boo && wfhmwCookie != null) {
            return this.parseCookies(wfhmwCookie);
        } else {
            Connection connect = Jsoup.connect("http://www.wfhmw.com/index.php?m=Home&c=User&a=login&id=42711&goods_type=766");
            Map<String, String> data = new HashMap();
            data.put("MIME类型", "application/x-www-form-urlencoded; charset=UTF-8");
            data.put("cardno", "");
            data.put("password", "");
            data.put("username", "Joslyn");
            data.put("username_password", "qzwsx7410");
            data.put("sendpass_username", "");
            data.put("reg_username", "");
            data.put("reg_password", "");
            data.put("reg_sex", "0");
            data.put("reg_qq", "");
            data.put("code", "");
            data.put("id", "42711");
            Response execute = connect.data(data).ignoreContentType(true).header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Language", "zh-cn").header("Accept-Encoding", "gzip, deflate").header("Host", "www.wfhmw.com").header("Origin", "http://www.wfhmw.com").userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15").header("Connection", "keep-alive").referrer("http://www.wfhmw.com/index.php?m=Home&c=Goods&a=detail&id=42711&goods_type=766").header("Content-Length", "156").header("X-Requested-With", "XMLHttpRequest").cookies(this.parseCookies(wfhmwCookie)).method(Method.POST).execute();
            Map<String, String> cookies = execute.cookies();
            log.info("久伴社区重新登录完成获取cookies>>>>>>>>>>>>>>>>" + cookies.toString());
            CookieTimeOutResult cookieTimeOutResult = (CookieTimeOutResult) JSONObject.parseObject(execute.body(), CookieTimeOutResult.class);
            log.info("登录完成后返回信息{}", cookieTimeOutResult);
            if (cookieTimeOutResult.getStatus() == 1 && !"{}".equals(cookies.toString())) {
                String cookieString = cookies.toString().replace("{", "").replace("}", "") + "; ";
                cookieString = cookieString + "Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585634789; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585630353; JSESSIONID=F3663C1D6A5DD7AC3BEACF29E65CBA9F.tomcatwork2; userMess=b1e318bb57bfbd8d501332753de0ebab1ac855420824c301f43c5d194c238ebbcf3577806df060f65492ca63f3c7d296170850bc60183082576f9cd9868b413798e0a96895966138c0f6806cd8ea2a544e0c687c7152bf8b565fd06a3605aa186093228af585fe0d1a77ea5f898dadf7b062bccbf42d1bc708e3baf4573fab1076a437217f647d1791efa84e2d72a0c888b03ded1ab4faa6c77cc2a11d23466bbfe1c339dd5bc1d973cccc731937d397d478531d0f5e184facafdc1d0c31a7aca09a14b44de48625a14d857bd896bca58851a198797a068215164752432ccb510f86f066688388e8ea2c1c7d2f71ef4a7663bbb34df22c00254024b9f14513a7daf5055f793d33cab45626e5937a8bba7ec73f26cea048eca46e43b477b938d308fc35be0a6c77d44f6ebee23d605e045ac9ecf996dd513ac9a62fe4f427851f05b79a94e2b971639584492b993ed6ddf4271c66d1f0b00067b92a857408eb49fa3e0d7df423077839c5947fa1ce93c51e3b5a26b3355c01b0b06a8598f2a42b6d35945bbeef8f64925385da00c4af3ed332d474b2443abe76d205b2d4c1fb62747d1c901e82641fddb3471a725ad3e3691b92c540a7d392ae83a8c4860e5f5483fbf2ccd091efad5e31b42d9edf1d8839dca59040ba2f0cc27b783052a5908be051d48d65341afa175e33e37618555c67eae57db40f6ec0476e2e67b2205e1b3cfde064afc8b56d680d35eb8cc188b37edd3ad0b4f7430067319079d4f509738ad80eb5d6be006e523647b8d5d539e2f8da887d11ec8620d3cacaa53ada516f6d68336bdc94e04ef350d3829610fd99d1395b86738f9d7f89714ae883287e987b48911efbad61013067bc498d69decb859e822a1b6358703e42d1027701420d9137a667d9bff5aa08dbe9becf266b8b42cdb570badc013c7956b4445225a53adfa5bc91556723b568d581964476ea12b18161fe87b379fb03da95a604e4231108fc35be0a6c77d4d8f28c77013cba44c48a5a8df133848dfc9ca86a8d30f981020fed90642cea8f762bdfa3f971055a8faf52fcba8f1d13973e9bcf4cf5d237b5217829b0bf8175b176541d52d89a972d5b20fcb65576bec8d15051a9902d966c58e30e52b2ae656746c3211a72c86c2c7fcea511fd860e8f137afe67726801658f88e110258e18c65f3e20a6abcb9b5d7d8246aaa2316a0a13b21da0a99f9dc3f847d5089354031205ba7e39342465e67e281021396b47d418b6512cac5f31ec0684d5f196dec25afc0a0a77d2f27ed685a6525acd7550212dee9ee788fc3f87647a48d8025dd79e6082035327a7014970b73a015544577d634cf99c4c058576e3f1ff3d959afba386375df966d01c51fcaeff2373779071d5a9537d7be3ba9b1f58e51a161c91864ff65919c41442a7782b414859e7fc86d15db82cc42bc6c0fff41482c03a1cffb7f46a9aeb58c75f8662ed35b4b8dffd8ff5cfa2801adaec0a0e2ca6eb7528fd0bb985f1ee7fe58021c647423ec6829179e0a0fa895890f1015e93eaa5faadb40fc3539c90aa382ed31f40492fb445bf634527ec542b57d31304109108ff276a80b5600dbea23b3f0fffb5566e8c19d33b7b6b5787f7a7ec0a0e2ca6eb75280ad145f631ac9f73f39971f851025f5d4126f1fbf32ba69ec3addf3b7460d485e745635423d3f7e178f9429b62634de6dd9a5bb15a9dd60bde49401be19a6c422e1af51dfe3c47d8a3aa73d325a6b4928a8846c79d6c8fd78f2c2c185925400437b2eb2690e4532a180658a3c838d3d16d5f3f5cbdeb80da5b01622b3eeba140cbc08ee86f3419a61c71b814519423d32d2235d8235a954fe13d4f1fdd49e0d4b190c4bc111205a0b40fc3539c90aa38f6eaaed61d0c3097fde0eaddd8aaca27210b702648186aee0472707446064c2af2bdab6844b1ae6eb5c186060e3fcac3a4ba7364367adb9bf79c97ce78a95568980848df3385a293fa48fd386e553195d089b6e1a16bcfce8d4a896591e55cab3605544f76ec42d5ce132e7e427d44130da29fd2529b384c1db7b448f018ccdf30719751d0bd3d53b29d210c6ccfe8e9ec4da267a18377b26e0f203177cd1554f8f6210c8b9298b1f84ddad6733847afa8aa44d17c8c1eb38ac520a600a8ce2b456fea4f95a2fb8ab88281ca9ba546c7d5634f01c6bc4cbd41f465f3d59f1d9fc790b2f660b28abd28715988952993bb088c90d85e4c2a10330d93fbd6756cf55850ca3f40d47f38dc425fa5be0f988b; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c";
                log.info("设置久伴登录cookies{}", cookieString);
                this.redisService.set("wfhmwcookierediskey", cookieString);
                return this.parseCookies(this.redisService.get("wfhmwcookierediskey"));
            } else {
                if (cookieTimeOutResult.getStatus() == 0 && Integer.parseInt(this.redisService.get(CommonContants.loginTryCountRedisName)) < 2) {
                    this.redisService.set(CommonContants.loginTryCountRedisName, String.valueOf(Integer.parseInt(this.redisService.get(CommonContants.loginTryCountRedisName)) + 1), 1500000L);
                    this.getCookie(true);
                }

                return this.parseCookies(this.redisService.get("wfhmwcookierediskey"));
            }
        }
    }

    private Map<String, String> parseCookies(String cookieStr) throws RedisConnectException, IOException, InterruptedException {
        if (StringUtils.isBlank(cookieStr) || "{}".equals(cookieStr)) {
            cookieStr = "Hm_lpvt_900da5ea8be245b309ab2e2518d7dac7=1585634789; Hm_lvt_900da5ea8be245b309ab2e2518d7dac7=1585584694; CNZZDATA1272878914=2121387927-1585582547-http%253A%252F%252Fwww.chaofankm.com%252F%7C1585630353; JSESSIONID=F3663C1D6A5DD7AC3BEACF29E65CBA9F.tomcatwork2; userMess=b1e318bb57bfbd8d501332753de0ebab1ac855420824c301f43c5d194c238ebbcf3577806df060f65492ca63f3c7d296170850bc60183082576f9cd9868b413798e0a96895966138c0f6806cd8ea2a544e0c687c7152bf8b565fd06a3605aa186093228af585fe0d1a77ea5f898dadf7b062bccbf42d1bc708e3baf4573fab1076a437217f647d1791efa84e2d72a0c888b03ded1ab4faa6c77cc2a11d23466bbfe1c339dd5bc1d973cccc731937d397d478531d0f5e184facafdc1d0c31a7aca09a14b44de48625a14d857bd896bca58851a198797a068215164752432ccb510f86f066688388e8ea2c1c7d2f71ef4a7663bbb34df22c00254024b9f14513a7daf5055f793d33cab45626e5937a8bba7ec73f26cea048eca46e43b477b938d308fc35be0a6c77d44f6ebee23d605e045ac9ecf996dd513ac9a62fe4f427851f05b79a94e2b971639584492b993ed6ddf4271c66d1f0b00067b92a857408eb49fa3e0d7df423077839c5947fa1ce93c51e3b5a26b3355c01b0b06a8598f2a42b6d35945bbeef8f64925385da00c4af3ed332d474b2443abe76d205b2d4c1fb62747d1c901e82641fddb3471a725ad3e3691b92c540a7d392ae83a8c4860e5f5483fbf2ccd091efad5e31b42d9edf1d8839dca59040ba2f0cc27b783052a5908be051d48d65341afa175e33e37618555c67eae57db40f6ec0476e2e67b2205e1b3cfde064afc8b56d680d35eb8cc188b37edd3ad0b4f7430067319079d4f509738ad80eb5d6be006e523647b8d5d539e2f8da887d11ec8620d3cacaa53ada516f6d68336bdc94e04ef350d3829610fd99d1395b86738f9d7f89714ae883287e987b48911efbad61013067bc498d69decb859e822a1b6358703e42d1027701420d9137a667d9bff5aa08dbe9becf266b8b42cdb570badc013c7956b4445225a53adfa5bc91556723b568d581964476ea12b18161fe87b379fb03da95a604e4231108fc35be0a6c77d4d8f28c77013cba44c48a5a8df133848dfc9ca86a8d30f981020fed90642cea8f762bdfa3f971055a8faf52fcba8f1d13973e9bcf4cf5d237b5217829b0bf8175b176541d52d89a972d5b20fcb65576bec8d15051a9902d966c58e30e52b2ae656746c3211a72c86c2c7fcea511fd860e8f137afe67726801658f88e110258e18c65f3e20a6abcb9b5d7d8246aaa2316a0a13b21da0a99f9dc3f847d5089354031205ba7e39342465e67e281021396b47d418b6512cac5f31ec0684d5f196dec25afc0a0a77d2f27ed685a6525acd7550212dee9ee788fc3f87647a48d8025dd79e6082035327a7014970b73a015544577d634cf99c4c058576e3f1ff3d959afba386375df966d01c51fcaeff2373779071d5a9537d7be3ba9b1f58e51a161c91864ff65919c41442a7782b414859e7fc86d15db82cc42bc6c0fff41482c03a1cffb7f46a9aeb58c75f8662ed35b4b8dffd8ff5cfa2801adaec0a0e2ca6eb7528fd0bb985f1ee7fe58021c647423ec6829179e0a0fa895890f1015e93eaa5faadb40fc3539c90aa382ed31f40492fb445bf634527ec542b57d31304109108ff276a80b5600dbea23b3f0fffb5566e8c19d33b7b6b5787f7a7ec0a0e2ca6eb75280ad145f631ac9f73f39971f851025f5d4126f1fbf32ba69ec3addf3b7460d485e745635423d3f7e178f9429b62634de6dd9a5bb15a9dd60bde49401be19a6c422e1af51dfe3c47d8a3aa73d325a6b4928a8846c79d6c8fd78f2c2c185925400437b2eb2690e4532a180658a3c838d3d16d5f3f5cbdeb80da5b01622b3eeba140cbc08ee86f3419a61c71b814519423d32d2235d8235a954fe13d4f1fdd49e0d4b190c4bc111205a0b40fc3539c90aa38f6eaaed61d0c3097fde0eaddd8aaca27210b702648186aee0472707446064c2af2bdab6844b1ae6eb5c186060e3fcac3a4ba7364367adb9bf79c97ce78a95568980848df3385a293fa48fd386e553195d089b6e1a16bcfce8d4a896591e55cab3605544f76ec42d5ce132e7e427d44130da29fd2529b384c1db7b448f018ccdf30719751d0bd3d53b29d210c6ccfe8e9ec4da267a18377b26e0f203177cd1554f8f6210c8b9298b1f84ddad6733847afa8aa44d17c8c1eb38ac520a600a8ce2b456fea4f95a2fb8ab88281ca9ba546c7d5634f01c6bc4cbd41f465f3d59f1d9fc790b2f660b28abd28715988952993bb088c90d85e4c2a10330d93fbd6756cf55850ca3f40d47f38dc425fa5be0f988b; _9755xjdesxxd_=32; gdxidpyhxdE=6tk3D6s36%5CJu0zrwjClmppfj4JJ3qQCkMNQaOZJOXoPAmIZ7Ge2G40EbHdZk%5Csawgx%5C9AN2ZHTaQmXZLYm%2BwQDmpxrRXl4BRR%2BIPz5IAI1tJo2zi%5CUW2Kuw7geRde%2BsR8N65gjseJI8Qa%5CYxy9rgkOGrWBmGpbTlJaDHdDWBiOTpZKhL%3A1585585791185; UM_distinctid=1712c3661279e8-035970c2c8483a-481d3201-7e9000-1712c366128a3c";
            this.redisService.set("wfhmwcookierediskey", cookieStr);
        }

        Map<String, String> cookieMap = new HashMap();
        String[] cookieKVStr = cookieStr.split(";");

        for (int i = 0; i < cookieKVStr.length; i++) {
            String cookieKeyStr = cookieKVStr[i].split("=")[0].trim();
            String cookieValStr = cookieKVStr[i].split("=")[1].trim();
            cookieMap.put(cookieKeyStr, cookieValStr);
        }

        return cookieMap;
    }
}
