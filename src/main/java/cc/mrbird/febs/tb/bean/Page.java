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
public class Page {

    private int currentPage;
    private int pageSize;
    private boolean queryForTitle;
    private int totalNumber;
    private int totalPage;
    public void setCurrentPage(int currentPage) {
         this.currentPage = currentPage;
     }
     public int getCurrentPage() {
         return currentPage;
     }

    public void setPageSize(int pageSize) {
         this.pageSize = pageSize;
     }
     public int getPageSize() {
         return pageSize;
     }

    public void setQueryForTitle(boolean queryForTitle) {
         this.queryForTitle = queryForTitle;
     }
     public boolean getQueryForTitle() {
         return queryForTitle;
     }

    public void setTotalNumber(int totalNumber) {
         this.totalNumber = totalNumber;
     }
     public int getTotalNumber() {
         return totalNumber;
     }

    public void setTotalPage(int totalPage) {
         this.totalPage = totalPage;
     }
     public int getTotalPage() {
         return totalPage;
     }

}