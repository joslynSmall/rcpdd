/**
 * Copyright 2021 jb51.net
 */
package cc.mrbird.febs.pdd.shipping.result;

import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated: 2021-03-19 14:41:58
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class Ordershipresultlist {

    @SerializedName("orderSn")
    private String ordersn;
    @SerializedName("warningInfo")
    private String warninginfo;
    @SerializedName("shippingResultCode")
    private int shippingresultcode;
    @SerializedName("shippingResultMsg")
    private String shippingresultmsg;

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setWarninginfo(String warninginfo) {
        this.warninginfo = warninginfo;
    }

    public String getWarninginfo() {
        return warninginfo;
    }

    public void setShippingresultcode(int shippingresultcode) {
        this.shippingresultcode = shippingresultcode;
    }

    public int getShippingresultcode() {
        return shippingresultcode;
    }

    public void setShippingresultmsg(String shippingresultmsg) {
        this.shippingresultmsg = shippingresultmsg;
    }

    public String getShippingresultmsg() {
        return shippingresultmsg;
    }

}
