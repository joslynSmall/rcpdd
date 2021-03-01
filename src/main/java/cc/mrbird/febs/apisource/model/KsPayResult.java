package cc.mrbird.febs.apisource.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Joslyn
 * @Title: KsPayResult
 * @ProjectName backend
 * @Description: 卡商充值返回结果
 * @date 2/28/21 1:36 PM
 */
@Data
public class KsPayResult {

    /**
     * 订单号
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * 商品价格
     */
    @SerializedName("product_price")
    private String productPrice;

    /**
     * 总支付价格
     */
    @SerializedName("total_pirce")
    private String totalPirce;

    /**
     * 卡密充值网址
     */
    @SerializedName("recharge_url")
    private String rechargeUrl;

    /**
     * 订单状态（100：等待发货，200：交易成功，500：交易失败）
     */
    private int state;

    /**
     * 卡密（仅当订单成功并且商品类型为卡密或租号时返回此数据）
     * "cards": [{
     *             "card_no": "vip009",
     *             "card_password": "DDd2kpCxZosyy7d"
     *         }, {
     *             "card_no": "vip010",
     *             "card_password": "UwQ4WnAKfi6wby6"
     *         }, {
     *             "card_no": "vip011",
     *             "card_password": "qKgITDZUuahfmiu"
     *         }]
     */
    private String cards;
}
