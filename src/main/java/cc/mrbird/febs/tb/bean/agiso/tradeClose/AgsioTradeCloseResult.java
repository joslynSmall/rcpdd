/**
 * Copyright 2020 bejson.com
 */
package cc.mrbird.febs.tb.bean.agiso.tradeClose;


/**
 * Auto-generated: 2020-03-17 17:8:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 * 关闭订单返回值
 */
@lombok.Data
public class AgsioTradeCloseResult {

    private boolean IsSuccess;
    private Data Data;
    private int Error_Code;
    private String Error_Msg;
    private String AllowRetry;

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

    public void setAllowRetry(String AllowRetry) {
        this.AllowRetry = AllowRetry;
    }

    public String getAllowRetry() {
        return AllowRetry;
    }

}
