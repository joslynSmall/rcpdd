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
public class StatusInfo {

    private List<Operations> operations;
    private String text;
    private String type;
    public void setOperations(List<Operations> operations) {
         this.operations = operations;
     }
     public List<Operations> getOperations() {
         return operations;
     }

    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

}