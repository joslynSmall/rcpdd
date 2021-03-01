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
public class Buyer {

    private boolean guestUser;
    private long id;
    private String memberUrl;
    private String nick;
    private List<Operations> operations;
    private String phoneNum;
    public void setGuestUser(boolean guestUser) {
         this.guestUser = guestUser;
     }
     public boolean getGuestUser() {
         return guestUser;
     }

    public void setId(long id) {
         this.id = id;
     }
     public long getId() {
         return id;
     }

    public void setMemberUrl(String memberUrl) {
         this.memberUrl = memberUrl;
     }
     public String getMemberUrl() {
         return memberUrl;
     }

    public void setNick(String nick) {
         this.nick = nick;
     }
     public String getNick() {
         return nick;
     }

    public void setOperations(List<Operations> operations) {
         this.operations = operations;
     }
     public List<Operations> getOperations() {
         return operations;
     }

    public void setPhoneNum(String phoneNum) {
         this.phoneNum = phoneNum;
     }
     public String getPhoneNum() {
         return phoneNum;
     }

}