package cc.mrbird.febs.pdd.service.impl;

import cc.mrbird.febs.agiso.constants.AgisoContans;
import cc.mrbird.febs.agiso.constants.RedisKeysContans;
import cc.mrbird.febs.apisource.model.KsPayResult;
import cc.mrbird.febs.apisource.model.KsResult;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.JsoupHttpUtil;
import cc.mrbird.febs.pdd.model.Pageitems;
import cc.mrbird.febs.pdd.model.PddOrderListParam;
import cc.mrbird.febs.pdd.model.PddResult;
import cc.mrbird.febs.pdd.model.Result;
import cc.mrbird.febs.pdd.service.IPddOrderService;
import cc.mrbird.febs.tb.bean.agiso.AgisoPushInfo;
import cc.mrbird.febs.tb.bean.agiso.ReturnBackResult;
import cc.mrbird.febs.tb.bean.common.JrequestBo;
import cc.mrbird.febs.tb.utils.AgisoUtil;
import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public PddResult getOrderList() throws InterruptedException, RedisConnectException, IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

//        JsoupHttpUtil.trustEveryone();

        JrequestBo jrequestBo = new JrequestBo();

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
        headersMap.put("referer", "https://mms.pinduoduo.com/orders/list");
        headersMap.put("Content-Type", "application/json;charset=utf-8");
//        headersMap.put("anti-content","0aoAfxnUDOcys9TVHW5Tg9Y_Ymwtku9EZl4LREbfUSwvZgwc_0gtIOd1jQLl0Ei7Fi7DGe9ATuJ9v1gA9w3MS97St4-ScTd_g97kkwhNXRr-FrkpTQ9Bpqgvkx0NFk6qkitLa_Rt7bTRt6JdWB7JZmuxBYEJgzhCbJ1PT9uq5305-d00q2AP7q79uiIxwWdyBqpwh5bhaPKWaGNJbrXCBBwcFMWyNy5YxqGq15qADtSv2FaveSrLd7Dm_-3hiyXbFYQm9uFKHQkTPWytx4EZo87mdPREGbFdxrP6N-2Lpn-YN2a79hzXvlMDy82W_oFmuYogExxtGtgxL8gm5j6wCJngxbE_4Mmo4DzGVvz7lg-GVcgcR9BvEkpkKvGxd8WHXVoScfzsaYb6wQDX1ouSSp9jx6GonHZA8dOadwgw8FhiZJy8P9X5oL6frWDwlksP9mZG1SOXHIk1VT-0T8GmgQQnaC5G8MtjRxQ4LDaHVOl38YiOmLeO2FsHU3zr82jms5TG4RZy88aNfvyF5m5xw1yhAZhhrjopDyIWk9dDeqaApR5u_jJV0zHso9Dx2W8jZmhRBnKiclJJ1WL1Rqer3IElNRuRTY2ttss");

        jrequestBo.setReferrer("https://mms.pinduoduo.com/orders/list");

        PddOrderListParam param = new PddOrderListParam();
        param.setAfterSaleType(0);
        param.setOrderType(0);
        param.setRemarkStatus(-1);
        param.setUrgeShippingStatus(-1);
        param.setGroupEndTime(1615865525);
        param.setGroupStartTime(1608089525);
        param.setPageNumber(1);
        param.setPageSize(20);
        param.setSortType(7);

//        redisService.set(RedisKeysContans.PDD_LOGIN_KOOKIES, "api_uid=rBQ5Ll/5TcoaNEziJOokAg==; _nano_fp=XpEaX0g8XqCblpdxn9_GHk7hikcjtkFY0ViqCl8q; _bee=iQfod5OVsbalhFW4n4eenutCyrWl7GQL; _f77=a903e106-ade0-436c-9d90-78bbb25fa8e2; _a42=39236969-89a1-4625-84ae-d0beb0051449; rckk=iQfod5OVsbalhFW4n4eenutCyrWl7GQL; ru1k=a903e106-ade0-436c-9d90-78bbb25fa8e2; ru2k=39236969-89a1-4625-84ae-d0beb0051449; terminalFinger=3pzHfU3WZ0Sjm8OxZBLr2w1KmordS97S; mms_b84d1838=120,140,3397,3434,1202,1203,1204,1205; PASS_ID=1-6iOKVWPSvTR7a0/w1+oOobQC4uJa5zgW2vTNFvYIhtIf7JusdIGJLXWfD80Jbdy/EOic9tBzRW7V8qUQbAXR6A_789295185_91605948; x-visit-time=1615881768818; JSESSIONID=F662AE5C47631A6B78DF3248ABE20DE9");
        Map<String, String> payOrderRequestMap = BeanUtils.describe(param);
        jrequestBo.setPayOrderRequestMap(payOrderRequestMap);
        jrequestBo.setCookieStr(redisService.get(RedisKeysContans.PDD_LOGIN_KOOKIES));
        jrequestBo.setPayurl("https://mms.pinduoduo.com/mangkhut/mms/recentOrderList");
        Connection.Response response = JsoupHttpUtil.excutePostJson(jrequestBo, headersMap);
        String body = response.body();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        PddResult pddResult = gson.fromJson(body, new TypeToken<PddResult<Result<Pageitems>>>() {
        }.getType());

        return pddResult;
    }
}
