package cc.mrbird.febs.tb.bean.agiso.tradeClose;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: TradeCloseBo
 * @ProjectName febs_shiro_jwt
 * @Description: 关闭订单参数
 * @date 2020-03-17 17:12
 */
@Data
public class TradeCloseBo {

    /**
     * 订单编号,多个订单编号间用,分隔开
     */
    private String tids;

    /**
     * 交易关闭原因。可以选择的理由有：1.未及时付款 2、买家不想买了 3、买家信息填写错误，重新拍 4、恶意买家/同行捣乱 5、缺货 6、买家拍错了 7、同城见面交易
     */
    private String closeReason;

    public String getTids() {
        return tids;
    }

    public void setTids(String tids) {
        this.tids = tids;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }
}
