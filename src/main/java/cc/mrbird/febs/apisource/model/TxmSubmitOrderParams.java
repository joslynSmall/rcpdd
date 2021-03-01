package cc.mrbird.febs.apisource.model;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: TxmSubmitOrderParams
 * @ProjectName backend
 * @Description: 淘小蜜推送提交参数
 * @date 2/28/21 12:27 PM
 */
@Data
public class TxmSubmitOrderParams {

    /**
     * Pdd订单编号
     */
    private String pddOrderNumer;
    /**
     * pdd 商品id
     */
    private String pddGoodsId;

    /**
     * 购买数量
     */
    private int buyNum;

    /**
     * 提交账号
     */
    private String rechargeAccount;

    /**
     * md5校验值
     */
    private String sign;
}
