/**
 * Copyright 2021 jb51.net
 */
package cc.mrbird.febs.pdd.shipping.result;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Auto-generated: 2021-03-19 14:41:58
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class Result {

    @SerializedName("orderShipResultList")
    private List<Ordershipresultlist> ordershipresultlist;

    public void setOrdershipresultlist(List<Ordershipresultlist> ordershipresultlist) {
        this.ordershipresultlist = ordershipresultlist;
    }

    public List<Ordershipresultlist> getOrdershipresultlist() {
        return ordershipresultlist;
    }

}
