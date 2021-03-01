package cc.mrbird.febs.tb.bean.jiuban;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: PayOrderRequest
 * @ProjectName febs_shiro_jwt
 * @Description: TODO
 * @date 2019-11-25 20:20
 */
@Data
public class PayOrderRequest {

//    private String MIME类型;

    private String qq;

    private int need_num_0;

    private int goods_id;

    private int goods_type;

    private int pay_type;

}
