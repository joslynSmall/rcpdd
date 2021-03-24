package cc.mrbird.febs.pdd.model.orderChargeDetail.result;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: Result
 * @ProjectName backend
 * @Description: 用户充值账号返回值
 * @date 2021/3/20 19:11
 */
@Data
public class Result {

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 充值号码
     */
    private String chargeNumber;
}
