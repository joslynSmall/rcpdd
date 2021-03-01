/**
  * Copyright 2019 bejson.com 
  */
package cc.mrbird.febs.tb.bean;

/**
 * Auto-generated: 2019-11-24 17:45:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SubOrders {

    private ItemInfo itemInfo;
    private PriceInfo priceInfo;
    private String quantity;
    public void setItemInfo(ItemInfo itemInfo) {
         this.itemInfo = itemInfo;
     }
     public ItemInfo getItemInfo() {
         return itemInfo;
     }

    public void setPriceInfo(PriceInfo priceInfo) {
         this.priceInfo = priceInfo;
     }
     public PriceInfo getPriceInfo() {
         return priceInfo;
     }

    public void setQuantity(String quantity) {
         this.quantity = quantity;
     }
     public String getQuantity() {
         return quantity;
     }

}