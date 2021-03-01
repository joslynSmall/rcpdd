package cc.mrbird.febs.tb.bean.haodian;

import lombok.Data;
import lombok.ToString;

/**
 * @author Joslyn
 * @Title: HaodianPayRmbRep
 * @ProjectName febs_shiro_jwt
 * @Description: 好点支付订单返回
 * @date 2020-04-18 09:55
 */
@Data
@ToString
public class HaodianPayRmbRep {

    private int code;

    private String msg;
}
