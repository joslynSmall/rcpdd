package cc.mrbird.febs.tb.bean.jiuban;

import lombok.Builder;
import lombok.Data;

/**
 * @author Joslyn
 * @Title: PayOrderResult
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-25 20:17
 */
@Data
public class PayOrderResult {

    private int status;

    private String info;

    private String order_id;

    private String url;

    private double after_use_rmb;

    private int after_use_cardnum;
}
