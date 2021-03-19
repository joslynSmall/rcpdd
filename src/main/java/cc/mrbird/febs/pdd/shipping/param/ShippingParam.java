/**
 * Copyright 2021 jb51.net
 */
package cc.mrbird.febs.pdd.shipping.param;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2021-03-19 13:38:9
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class ShippingParam {

    @SerializedName("orderShipRequestList")
    private List<Ordershiprequestlist> ordershiprequestlist;
    @SerializedName("isSingleShipment")
    private int issingleshipment;
    @SerializedName("overWrite")
    private int overwrite;
    @SerializedName("functionType")
    private int functiontype;
    @SerializedName("isVirtualGoods")
    private boolean isvirtualgoods;
    @SerializedName("operateFrom")
    private String operatefrom;

    public void setOrdershiprequestlist(List<Ordershiprequestlist> ordershiprequestlist) {
        this.ordershiprequestlist = ordershiprequestlist;
    }

    public List<Ordershiprequestlist> getOrdershiprequestlist() {
        return ordershiprequestlist;
    }

    public void setIssingleshipment(int issingleshipment) {
        this.issingleshipment = issingleshipment;
    }

    public int getIssingleshipment() {
        return issingleshipment;
    }

    public void setOverwrite(int overwrite) {
        this.overwrite = overwrite;
    }

    public int getOverwrite() {
        return overwrite;
    }

    public void setFunctiontype(int functiontype) {
        this.functiontype = functiontype;
    }

    public int getFunctiontype() {
        return functiontype;
    }

    public void setIsvirtualgoods(boolean isvirtualgoods) {
        this.isvirtualgoods = isvirtualgoods;
    }

    public boolean getIsvirtualgoods() {
        return isvirtualgoods;
    }

    public void setOperatefrom(String operatefrom) {
        this.operatefrom = operatefrom;
    }

    public String getOperatefrom() {
        return operatefrom;
    }

}
