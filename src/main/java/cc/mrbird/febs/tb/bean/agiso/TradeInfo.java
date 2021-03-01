/**
  * Copyright 2019 bejson.com
  */
package cc.mrbird.febs.tb.bean.agiso;

/**
 * Auto-generated: 2019-12-01 13:54:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TradeInfo {

    private boolean IsSuccess;
    private Data Data;
    private int Error_Code;
    private String Error_Msg;
    public void setIsSuccess(boolean IsSuccess) {
         this.IsSuccess = IsSuccess;
     }
     public boolean getIsSuccess() {
         return IsSuccess;
     }

    public void setData(Data Data) {
         this.Data = Data;
     }
     public Data getData() {
         return Data;
     }

    public void setError_Code(int Error_Code) {
         this.Error_Code = Error_Code;
     }
     public int getError_Code() {
         return Error_Code;
     }

    public void setError_Msg(String Error_Msg) {
         this.Error_Msg = Error_Msg;
     }
     public String getError_Msg() {
         return Error_Msg;
     }

}
