package cc.mrbird.febs.apisource.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

/**
 * @author Joslyn
 * @Title: KsOrderResult
 * @ProjectName backend
 * @Description: ks充值状态查看
 * @date 2/28/21 3:32 PM
 */
@Data
public class KsOrderResult {

    private long id;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_type")
    private int productType;
    @SerializedName("product_price")
    private String productPrice;
    private int quantity;
    @SerializedName("total_price")
    private String totalPrice;
    @SerializedName("refunded_amount")
    private String refundedAmount;
    @SerializedName("buyer_customer_id")
    private long buyerCustomerId;
    @SerializedName("buyer_customer_name")
    private String buyerCustomerName;
    @SerializedName("seller_customer_id")
    private long sellerCustomerId;
    @SerializedName("seller_customer_name")
    private String sellerCustomerName;
    private int state;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("progress_init")
    private String progressInit;
    @SerializedName("progress_now")
    private String progressNow;
    @SerializedName("progress_target")
    private String progressTarget;
    @SerializedName("recharge_account")
    private String rechargeAccount;
    @SerializedName("recharge_params")
    private String rechargeParams;
    @SerializedName("recharge_info")
    private String rechargeInfo;

}
