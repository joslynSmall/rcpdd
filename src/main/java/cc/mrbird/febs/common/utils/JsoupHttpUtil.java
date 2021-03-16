package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.agiso.constants.RedisKeysContans;
import cc.mrbird.febs.common.exception.RedisConnectException;
import cc.mrbird.febs.tb.bean.common.JrequestBo;
import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joslyn
 * @Title: JsoupHttpUtil
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-04-26 15:27
 */
public class JsoupHttpUtil {

    public static Connection.Response excute(JrequestBo bo) throws InterruptedException, RedisConnectException, IOException {

        Connection connect = Jsoup.connect(bo.getPayurl());
        Connection.Response execute = connect.data(bo.getPayOrderRequestMap())
                .ignoreContentType(true)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "zh-cn")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Upgrade-Insecure-Requests", "1")
                .header("X-CSRF-TOKEN", "")
                .header("Host", bo.getHost())
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Safari/605.1.15")
                .header("Connection", "keep-alive")
                .referrer(bo.getReferrer())
                .header("X-Requested-With", "XMLHttpRequest")
//                    .cookies(this.parseCookies(redisService.get(haodianCookies)))
                .cookies(parseCookies(bo.getCookieStr()))
                .method(Connection.Method.POST)
                .execute();

        return execute;
    }

    public static Connection.Response excutePostJson(JrequestBo bo, Map<String, String> hearsMap) throws InterruptedException, RedisConnectException, IOException {

        Connection connect = Jsoup.connect(bo.getPayurl());
        Connection.Response execute = connect.requestBody(new Gson().toJson(bo.getPayOrderRequestMap()))
                .headers(hearsMap)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                .timeout(30000)
                .referrer(bo.getReferrer())
                .cookies(parseCookies(bo.getCookieStr()))
                .method(Connection.Method.POST)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .execute();

        return execute;
    }

    public static Map<String, String> getCookies(JrequestBo bo) throws InterruptedException, RedisConnectException, IOException {
        Connection.Response excute = JsoupHttpUtil.excute(bo);
        return excute.cookies();
    }

    public static Connection.Response get(JrequestBo bo) throws InterruptedException, RedisConnectException, IOException {
        bo.setMethod(Connection.Method.GET);
        return JsoupHttpUtil.excute(bo);
    }

    ;


    public static Map<String, String> parseCookies(String cookieStr) throws RedisConnectException, IOException, InterruptedException {
        Map<String, String> cookieMap = new HashMap();
        String[] cookieKVStr = cookieStr.split(";");

        for (int i = 0; i < cookieKVStr.length; i++) {
            String cookieKeyStr = cookieKVStr[i].split("=")[0].trim();
            String cookieValStr = cookieKVStr[i].split("=")[1].trim();
            cookieMap.put(cookieKeyStr, cookieValStr);
        }

        return cookieMap;
    }


    /**
     * 信任任何站点，实现https页面的正常访问
     */

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
        }
    }
}
