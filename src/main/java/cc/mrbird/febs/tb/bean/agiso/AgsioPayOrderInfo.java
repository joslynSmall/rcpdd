package cc.mrbird.febs.tb.bean.agiso;

import lombok.ToString;

import java.util.List;

/**
 * @author Joslyn
 * @Title: AgsioPayOrderInfo
 * @ProjectName febs_shiro_jwt
 * @Description: 订单支付接收json类型参数
 * {
 * "Tid": 2067719225654838,
 * "Status": "WAIT_BUYER_CONFIRM_GOODS",
 * "SellerNick": "168休闲馆",
 * "BuyerNick": "agiso",
 * "BuyerMessage": null,
 * "Price": "3.00",
 * "Num": 1,
 * "TotalFee": "3.00",
 * "Payment": "3.00",
 * "PayTime": "2016-07-11 11:20:20",
 * "Created": "2016-07-11 11:20:09",
 * "Orders": [
 * {
 * "Num": 1,
 * "NumIid": 45533870790,
 * "Oid": 2067719225654838,
 * "OuterIid": "ALDS_1000",
 * "OuterSkuId": "ALDS_SKU_1000",
 * "Payment": "3.00",
 * "Price": "3.00",
 * "SkuPropertiesName": null,
 * "Title": "宝贝标题",
 * "TotalFee": "3.00"
 * }
 * ]
 * }
 * @date 2019-12-01 20:48
 */
@ToString
public class AgsioPayOrderInfo {

    private long Tid;
    private String Status;
    private String SellerNick;
    private String BuyerNick;
    private String BuyerMessage;
    private String Price;
    private int Num;
    private String TotalFee;
    private String Payment;
    private String PayTime;
    private String Created;
    private List<Orders> Orders;

    public void setTid(long Tid) {
        this.Tid = Tid;
    }

    public long getTid() {
        return Tid;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return Status;
    }

    public void setSellerNick(String SellerNick) {
        this.SellerNick = SellerNick;
    }

    public String getSellerNick() {
        return SellerNick;
    }

    public void setBuyerNick(String BuyerNick) {
        this.BuyerNick = BuyerNick;
    }

    public String getBuyerNick() {
        return BuyerNick;
    }

    public void setBuyerMessage(String BuyerMessage) {
        this.BuyerMessage = BuyerMessage;
    }

    public String getBuyerMessage() {
        return BuyerMessage;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPrice() {
        return Price;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }

    public int getNum() {
        return Num;
    }

    public void setTotalFee(String TotalFee) {
        this.TotalFee = TotalFee;
    }

    public String getTotalFee() {
        return TotalFee;
    }

    public void setPayment(String Payment) {
        this.Payment = Payment;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public String getCreated() {
        return Created;
    }

    public void setOrders(List<Orders> Orders) {
        this.Orders = Orders;
    }

    public List<Orders> getOrders() {
        return Orders;
    }
}
