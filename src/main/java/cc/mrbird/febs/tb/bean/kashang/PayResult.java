package cc.mrbird.febs.tb.bean.kashang;

import lombok.Data;
import lombok.ToString;

/**
 * @author Joslyn
 * @Title: PayResult
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-04-26 16:35
 */
@Data
@ToString
public class PayResult {

    private boolean success;

    private String info;

    private String order_id;
}
