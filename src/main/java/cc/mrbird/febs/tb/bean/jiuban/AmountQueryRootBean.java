/**
  * Copyright 2019 bejson.com
  */
package cc.mrbird.febs.tb.bean.jiuban;

/**
 * Auto-generated: 2019-11-25 20:22:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AmountQueryRootBean {

    private boolean status;
    private String card_or_user;
    private Rows rows;
    public void setStatus(boolean status) {
         this.status = status;
     }
     public boolean getStatus() {
         return status;
     }

    public void setCard_or_user(String card_or_user) {
         this.card_or_user = card_or_user;
     }
     public String getCard_or_user() {
         return card_or_user;
     }

    public void setRows(Rows rows) {
         this.rows = rows;
     }
     public Rows getRows() {
         return rows;
     }

}
