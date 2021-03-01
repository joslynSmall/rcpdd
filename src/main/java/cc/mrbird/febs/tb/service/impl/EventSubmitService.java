package cc.mrbird.febs.tb.service.impl;

import cc.mrbird.febs.tb.bean.EventSubmitBean;
import cc.mrbird.febs.tb.service.IeventSubmitService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joslyn
 * @Title: eventSubmitService
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-24 17:54
 */
@Service
public class EventSubmitService implements IeventSubmitService {

    private static final String urlNameString = "https://trade.taobao.com/trade/itemlist/asyncSold.htm?event_submit_do_query=1&_input_charset=utf8";

    private static final String methodKey = ":method";
    private static final String methodValue = "POST";

    private static final String schemeKey = ":scheme";
    private static final String schemeValue = "https";

    private static final String authorityKey = ":authority";
    private static final String authorityValue = ":trade.taobao.com";

    private static final String pathKey = ":path";
    private static final String pathValue = "/trade/itemlist/asyncSold.htm?event_submit_do_query=1&_input_charset=utf8";

    private static final String ContentType = "Content-Type";
    private static final String ContentTypeValue = "application/x-www-form-urlencoded; charset=UTF-8";

    private static final String Accept = "Accept";
    private static final String AcceptValue = "application/json, text/javascript, */*; q=0.01";

    private static final String AcceptLanguageKey = "Accept-Language";
    private static final String AcceptLanguageValue = "zh-cn";

    private static final String AcceptEncodingKey = "Accept-Encoding";
    private static final String AcceptEncodingValue = "gzip, deflate, br";

    private static final String HostKey = "Host";
    private static final String HostValue = "trade.taobao.com";

    private static final String OriginKey = "Origin";
    private static final String OriginValue = "https://trade.taobao.com";

    private static final String UserAgentKey = "User-Agent";
    private static final String UserAgentValue = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15";

    private static final String ConnectionKey = "Connection";
    private static final String ConnectionValue = "keep-alive";

    private static final String RefererKey = "Referer";
    private static final String RefererValue = "https://trade.taobao.com/trade/itemlist/list_sold_items.htm?action=itemlist/SoldQueryAction&event_submit_do_query=1&auctionStatus=PAID&tabCode=waitSend";

    private static final String ContentLengthKey = "Content-Length";
    private static final String ContentLengthValue = "441";

    private static final String XRequestedWithKey = "X-Requested-With";
    private static final String XRequestedWithValue = "441";

    //cookies
    private String cookieStr = "isg=BODgUEUsqflzPhWWCr2KXSuEs-iy6cSzjTUfX1rw6vuDVYF_AvgPQrsj7X1VfnyL; l=dBgP9r94qkznw1xUBOfaourza77tEIO08kPzaNbMiICP_cC2ysEhWZpN-X8yCn1VnsQvR3WzYchgBuLUjy4thIUITh_kvKJIndLh.; uc1=cookie14=UoTbmVUwAViGjw%3D%3D&lng=zh_CN&cookie16=VT5L2FSpNgq6fDudInPRgavC%2BQ%3D%3D&existShop=true&cookie21=WqG3DMC9Edo1SB5NASby9w%3D%3D&tag=8&cookie15=VFC%2FuZ9ayeYq2g%3D%3D&pas=0; _m_h5_tk=c799f15f80736526f6f644c8937ebfed_1574585625334; _m_h5_tk_enc=49472ca3ecf6c6553fb810828466c75e; _cc_=U%2BGCWk%2F7og%3D%3D; _l_g_=Ug%3D%3D; _nk_=tb9743103; cookie1=B0P9zufqCbcG9Wn5ISM5k7MAGFLP10ArIsa7%2BUepLHQ%3D; cookie17=Vv7NiGI10Zte; cookie2=17524d514fef30bc4da212f15d01ad04; csg=839ee272; dnk=tb9743103; existShop=MTU3NDU3NDgzMg%3D%3D; lgc=tb9743103; mt=ci=6_1; publishItemObj=Ng%3D%3D; sg=326; skt=5e7534f2563b9637; t=2f166721c93916191297abed3467d99d; tg=0; tracknick=tb9743103; uc3=id2=Vv7NiGI10Zte&lg2=VFC%2FuZ9ayeYq2g%3D%3D&nk2=F5RMHl60xEea&vt3=F8dByuQCLDpmXs8zuxg%3D; uc4=id4=0%40VHjx7yZzw6mg%2BhqStAaSIXc5Jto%3D&nk4=0%40FY4HWyt%2Blzqy4ha4ZDi92735gkA%3D; unb=504985802; ctoken=n8bHtHapoBvahGchTkYSrhllor; _tb_token_=5eb7503da5fe5; ali_ab=39.162.142.41.1574229316029.4; v=0; everywhere_service_strategy=cco_busi:ads_crmwx_wanxiang_guard_crowd:20191007@1; everywhere_tool_welcome=true; enc=H5kBJ1pxGq5HglPBC9alYAWUhXuvZE9SYyHHbmP2SkS8XKf0hIgP3QB35IXZB1A8hkT%2Frba57t6shZhFDGQOVA%3D%3D; cna=cjzTEwmS+zMCATyxIUEfKo7t; UM_distinctid=16daf6a842566c-0b53b5be31fade8-16222b12-7e9000-16daf6a842681f; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; miid=1190745350921990393";

    private void setCookies(String cookieStr, HttpsURLConnection urlConnection) {
        String[] cookieItem = cookieStr.split(";");
        List<String> cookieList = Arrays.asList(cookieItem);
        cookieList.forEach(cookieItemStrng -> {
            String[] cookieKv = cookieItemStrng.split("=");
            urlConnection.setRequestProperty(cookieKv[0].trim(), cookieKv[1].trim());
        });
    }

    private static final String SSL = "ssl";

    @Override
    public EventSubmitBean getTbEventSubmitList() throws IOException {
        StringBuilder result = new StringBuilder();

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance(SSL);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        URL console = new URL(urlNameString);
        HttpsURLConnection connection = (HttpsURLConnection) console.openConnection();

        this.setCookies(cookieStr, connection);
//        connection.setRequestProperty(methodKey, methodValue);
//        connection.setRequestProperty(schemeKey, schemeValue);
//        connection.setRequestProperty(authorityKey, authorityValue);
//        connection.setRequestProperty(pathKey, pathValue);
        connection.setRequestProperty(ContentType, ContentTypeValue);
        connection.setRequestProperty(Accept, AcceptValue);
        connection.setRequestProperty(AcceptLanguageKey, AcceptLanguageValue);
        connection.setRequestProperty(AcceptEncodingKey, AcceptEncodingValue);
        connection.setRequestProperty(HostKey, HostValue);
        connection.setRequestProperty(OriginKey, OriginValue);
        connection.setRequestProperty(UserAgentKey, UserAgentValue);
        connection.setRequestProperty(ConnectionKey, ConnectionValue);
        connection.setRequestProperty(RefererKey, RefererValue);
        connection.setRequestProperty(ContentLengthKey, ContentLengthValue);
        connection.setRequestProperty(XRequestedWithKey, XRequestedWithValue);

        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
        connection.connect();
        InputStream is = connection.getInputStream();
        EventSubmitBean eventSubmitBean = JSONObject.parseObject(is, EventSubmitBean.class);
        /*BufferedReader indata = new BufferedReader(new InputStreamReader(is));
        String ret = "";
        while (ret != null) {
            ret = indata.readLine();
            if (ret != null && !ret.trim().equals("")) {
                result.append(ret);
            }
        }*/
        connection.disconnect();
//        indata.close();

        return eventSubmitBean;
    }


    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            //trust anything
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            //trust anything
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
