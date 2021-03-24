/**
 * Copyright 2021 json.cn
 */
package cc.mrbird.febs.pdd.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Auto-generated: 2021-03-16 14:58:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PddOrderListParam {

    private int orderType;
    private int afterSaleType;
    private int remarkStatus;
    private int urgeShippingStatus;
    private long groupStartTime;
    private long groupEndTime;
    private int pageNumber;
    private int pageSize;
    private int sortType;
    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
    public int getOrderType() {
        return orderType;
    }

    public void setAfterSaleType(int afterSaleType) {
        this.afterSaleType = afterSaleType;
    }
    public int getAfterSaleType() {
        return afterSaleType;
    }

    public void setRemarkStatus(int remarkStatus) {
        this.remarkStatus = remarkStatus;
    }
    public int getRemarkStatus() {
        return remarkStatus;
    }

    public void setUrgeShippingStatus(int urgeShippingStatus) {
        this.urgeShippingStatus = urgeShippingStatus;
    }
    public int getUrgeShippingStatus() {
        return urgeShippingStatus;
    }

    public void setGroupStartTime(long groupStartTime) {
        this.groupStartTime = groupStartTime;
    }
    public long getGroupStartTime() {
        return groupStartTime;
    }

    public void setGroupEndTime(long groupEndTime) {
        this.groupEndTime = groupEndTime;
    }
    public long getGroupEndTime() {
        return groupEndTime;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
    public int getSortType() {
        return sortType;
    }

}
