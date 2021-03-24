package cc.mrbird.febs.pdd.model.orderChargeDetail.param;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: orderChargeDetailParam
 * @ProjectName backend
 * @Description: 用户充值信息查询参数
 * @date 2021/3/20 19:08
 */
@Data
public class OrderChargeDetailParam {

    private String orderSn;

    private String source;
}
