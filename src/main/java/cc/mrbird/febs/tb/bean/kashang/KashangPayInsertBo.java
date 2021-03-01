package cc.mrbird.febs.tb.bean.kashang;

import lombok.Data;

/**
 * @author Joslyn
 * @Title: KashangPayInsertBo
 * @ProjectName backend
 * @Description: TODO
 * @date 2020-04-26 16:08
 */
@Data
public class KashangPayInsertBo {

    private String _token;
    private String product_id;
    private String recharge_account;
    private int quantity;
    private String deal_password;
}
