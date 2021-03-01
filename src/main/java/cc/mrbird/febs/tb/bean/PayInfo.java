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
public class PayInfo {

    private String actualFee;
    private List<Icons> icons;
    private String postType;
    public void setActualFee(String actualFee) {
         this.actualFee = actualFee;
     }
     public String getActualFee() {
         return actualFee;
     }

    public void setIcons(List<Icons> icons) {
         this.icons = icons;
     }
     public List<Icons> getIcons() {
         return icons;
     }

    public void setPostType(String postType) {
         this.postType = postType;
     }
     public String getPostType() {
         return postType;
     }

}