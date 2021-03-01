/**
  * Copyright 2019 bejson.com
  */
package cc.mrbird.febs.tb.bean.agiso;

import lombok.ToString;

/**
 * Auto-generated: 2019-12-01 13:54:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@ToString
public class Orders {

    private int Num;
    private long NumIid;
    private long Oid;
    private String OuterIid;
    private String Payment;
    private String Price;
    private String SkuPropertiesName;
    private String Title;
    private String TotalFee;
    public void setNum(int Num) {
         this.Num = Num;
     }
     public int getNum() {
         return Num;
     }

    public void setNumIid(long NumIid) {
         this.NumIid = NumIid;
     }
     public long getNumIid() {
         return NumIid;
     }

    public void setOid(long Oid) {
         this.Oid = Oid;
     }
     public long getOid() {
         return Oid;
     }

    public void setOuterIid(String OuterIid) {
         this.OuterIid = OuterIid;
     }
     public String getOuterIid() {
         return OuterIid;
     }

    public void setPayment(String Payment) {
         this.Payment = Payment;
     }
     public String getPayment() {
         return Payment;
     }

    public void setPrice(String Price) {
         this.Price = Price;
     }
     public String getPrice() {
         return Price;
     }

    public void setSkuPropertiesName(String SkuPropertiesName) {
         this.SkuPropertiesName = SkuPropertiesName;
     }
     public String getSkuPropertiesName() {
         return SkuPropertiesName;
     }

    public void setTitle(String Title) {
         this.Title = Title;
     }
     public String getTitle() {
         return Title;
     }

    public void setTotalFee(String TotalFee) {
         this.TotalFee = TotalFee;
     }
     public String getTotalFee() {
         return TotalFee;
     }

}
