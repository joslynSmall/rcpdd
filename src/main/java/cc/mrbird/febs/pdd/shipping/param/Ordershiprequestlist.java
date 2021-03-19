/**
 * Copyright 2021 jb51.net
 */
package cc.mrbird.febs.pdd.shipping.param;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2021-03-19 13:38:9
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class Ordershiprequestlist {

    @SerializedName("orderSn")
    private String ordersn;
    @SerializedName("deliveryType")
    private int deliverytype;

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setDeliverytype(int deliverytype) {
        this.deliverytype = deliverytype;
    }

    public int getDeliverytype() {
        return deliverytype;
    }

}
