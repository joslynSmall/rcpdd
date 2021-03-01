package cc.mrbird.febs.tb.bean.yihui;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: UploadOrderReq
 * @ProjectName febs_shiro_jwt
 * @Description: 易惠网下单参数
 * @date 2020-04-02 00:02
 */
@Data
public class UploadOrderReq {

    private String goodsId;

    private String mainKey;

    private String textAccountName;

    private String sumprice;

    private String saleprice;
}
