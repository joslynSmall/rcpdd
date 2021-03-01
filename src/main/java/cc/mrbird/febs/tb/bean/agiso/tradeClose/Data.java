/**
  * Copyright 2020 bejson.com
  */
package cc.mrbird.febs.tb.bean.agiso.tradeClose;
import java.util.List;

/**
 * Auto-generated: 2020-03-17 17:8:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@lombok.Data
public class Data {

    private List<ErrorInfos> ErrorInfos;
    public void setErrorInfos(List<ErrorInfos> ErrorInfos) {
         this.ErrorInfos = ErrorInfos;
     }
     public List<ErrorInfos> getErrorInfos() {
         return ErrorInfos;
     }

}
