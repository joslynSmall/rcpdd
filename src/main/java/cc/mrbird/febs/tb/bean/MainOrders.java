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
public class MainOrders {

    private Buyer buyer;
    private Extra extra;
    private String id;
    private List<Operations> operations;
    private OrderInfo orderInfo;
    private PayInfo payInfo;
    private StatusInfo statusInfo;
    private List<SubOrders> subOrders;
    public void setBuyer(Buyer buyer) {
         this.buyer = buyer;
     }
     public Buyer getBuyer() {
         return buyer;
     }

    public void setExtra(Extra extra) {
         this.extra = extra;
     }
     public Extra getExtra() {
         return extra;
     }

    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setOperations(List<Operations> operations) {
         this.operations = operations;
     }
     public List<Operations> getOperations() {
         return operations;
     }

    public void setOrderInfo(OrderInfo orderInfo) {
         this.orderInfo = orderInfo;
     }
     public OrderInfo getOrderInfo() {
         return orderInfo;
     }

    public void setPayInfo(PayInfo payInfo) {
         this.payInfo = payInfo;
     }
     public PayInfo getPayInfo() {
         return payInfo;
     }

    public void setStatusInfo(StatusInfo statusInfo) {
         this.statusInfo = statusInfo;
     }
     public StatusInfo getStatusInfo() {
         return statusInfo;
     }

    public void setSubOrders(List<SubOrders> subOrders) {
         this.subOrders = subOrders;
     }
     public List<SubOrders> getSubOrders() {
         return subOrders;
     }

}