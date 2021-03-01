package cc.mrbird.febs.tb.bean.haodian;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: HaodianOrderCreateRequest
 * @ProjectName febs_shiro_jwt
 * @Description: 好点生成订单
 * @date 2020-04-18 09:26
 */
@Data
public class HaodianOrderCreateRequest {

    private int tid;

    private String inputvalue;

    private int num;

    private String hashsalt;
}
