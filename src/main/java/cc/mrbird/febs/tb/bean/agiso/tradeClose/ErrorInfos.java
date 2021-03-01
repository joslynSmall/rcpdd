/**
  * Copyright 2020 bejson.com
  */
package cc.mrbird.febs.tb.bean.agiso.tradeClose;

import lombok.Data;

/**
 * Auto-generated: 2020-03-17 17:8:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class ErrorInfos {

    private long Tid;
    private String ErrorMsg;
    public void setTid(long Tid) {
         this.Tid = Tid;
     }
     public long getTid() {
         return Tid;
     }

    public void setErrorMsg(String ErrorMsg) {
         this.ErrorMsg = ErrorMsg;
     }
     public String getErrorMsg() {
         return ErrorMsg;
     }

}
