package cc.mrbird.febs.tb.bean.agiso;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: AgisoPushInfo
 * @ProjectName febs_shiro_jwt
 * @Description: 公用推送接收参数
 * @date 2019-12-01 16:34
 */
public class AgisoPushInfo {

    private int timestamp;

    //推送类型，1:买家拍下;2:买家付款
    private int aopic;

    /**
     * 签名算法：
     * 仅对 json 和 timestamp 参数进行md5摘要，将参数名和参数值拼装在一起，先 json 后 timestamp 。 把拼装好的字符串采用 utf-8 编码，在拼装的字符串前后加上 appsecret 后，使用MD5算法进行摘要；
     * sign说明举例（实际中的json会很长，文档暂时......代替）：
     * _appsecret: 9f8g9d78sg9d8f8ew9f89ds9f8ds9af8
     * json: {"Tid":2067719225654838,"Status":"WAIT_BUYER_CONFIRM_GOODS",......,"TotalFee":"3.00"}
     * timestamp: 11222212121
     * 连接后的串: 9f8g9d78sg9d8f8ew9f89ds9f8ds9af8json{"Tid":2067719225654838,"Status":"WAIT_BUYER_CONFIRM_GOODS",......,"TotalFee":"3.00"}timestamp112222121219f8g9d78sg9d8f8ew9f89ds9f8ds9af8
     * MD5结果: f8aa165fc951f266667e0605d78b93af（不区分大小写）
     */
    private String sign;

    private String json;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getAopic() {
        return aopic;
    }

    public void setAopic(int aopic) {
        this.aopic = aopic;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
