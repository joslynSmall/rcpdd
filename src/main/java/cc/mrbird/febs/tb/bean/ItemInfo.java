/**
  * Copyright 2019 bejson.com 
  */
package cc.mrbird.febs.tb.bean;
import java.util.List;

/**
 * Auto-generated: 2019-11-24 17:45:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ItemInfo {

    private List<Extra> extra;
    private String itemUrl;
    private String pic;
    private List<ServiceIcons> serviceIcons;
    private int skuId;
    private List<String> skuText;
    private String title;
    public void setExtra(List<Extra> extra) {
         this.extra = extra;
     }
     public List<Extra> getExtra() {
         return extra;
     }

    public void setItemUrl(String itemUrl) {
         this.itemUrl = itemUrl;
     }
     public String getItemUrl() {
         return itemUrl;
     }

    public void setPic(String pic) {
         this.pic = pic;
     }
     public String getPic() {
         return pic;
     }

    public void setServiceIcons(List<ServiceIcons> serviceIcons) {
         this.serviceIcons = serviceIcons;
     }
     public List<ServiceIcons> getServiceIcons() {
         return serviceIcons;
     }

    public void setSkuId(int skuId) {
         this.skuId = skuId;
     }
     public int getSkuId() {
         return skuId;
     }

    public void setSkuText(List<String> skuText) {
         this.skuText = skuText;
     }
     public List<String> getSkuText() {
         return skuText;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

}