package cc.mrbird.febs.tb.bean.haodian;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Joslyn
 * @Title: HaodianOrderCreateRep
 * @ProjectName febs_shiro_jwt
 * @Description: 好点订单创建
 * @date 2020-04-18 09:43
 */
@Data
public class HaodianOrderCreateRep {

    private int code;
    private String msg;
    @JsonProperty("trade_no")
    private String tradeNo;
    private String need;
    @JsonProperty("pay_alipay")
    private String payAlipay;
    @JsonProperty("pay_wxpay")
    private String payWxpay;
    @JsonProperty("pay_qqpay")
    private String payQqpay;
    @JsonProperty("pay_rmb")
    private String payRmb;
    @JsonProperty("user_rmb")
    private String userRmb;
}
