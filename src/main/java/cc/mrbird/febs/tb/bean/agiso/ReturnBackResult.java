/**
 * Copyright 2019 bejson.com
 */
package cc.mrbird.febs.tb.bean.agiso;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2019-12-01 16:38:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ReturnBackResult {

    @JsonProperty("DoDummySend")
    private String doDummySend = "false";
    @JsonProperty("DoMemoUpdate")
    private DoMemoUpdate doMemoUpdate;
    @JsonProperty("AliwwMsg")
    private String aliwwMsg;

    public String getDoDummySend() {
        return doDummySend;
    }

    public void setDoDummySend(String doDummySend) {
        this.doDummySend = doDummySend;
    }

    public DoMemoUpdate getDoMemoUpdate() {
        return doMemoUpdate;
    }

    public void setDoMemoUpdate(DoMemoUpdate doMemoUpdate) {
        this.doMemoUpdate = doMemoUpdate;
    }

    public String getAliwwMsg() {
        return aliwwMsg;
    }

    public void setAliwwMsg(String aliwwMsg) {
        this.aliwwMsg = aliwwMsg;
    }
}
