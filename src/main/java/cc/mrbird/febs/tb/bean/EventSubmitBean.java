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
public class EventSubmitBean {

    private String error;
    private Extra extra;
    private List<MainOrders> mainOrders;
    private Page page;
    private Query query;
    private QueryOption queryOption;
    private List<Tabs> tabs;
    public void setError(String error) {
         this.error = error;
     }
     public String getError() {
         return error;
     }

    public void setExtra(Extra extra) {
         this.extra = extra;
     }
     public Extra getExtra() {
         return extra;
     }

    public void setMainOrders(List<MainOrders> mainOrders) {
         this.mainOrders = mainOrders;
     }
     public List<MainOrders> getMainOrders() {
         return mainOrders;
     }

    public void setPage(Page page) {
         this.page = page;
     }
     public Page getPage() {
         return page;
     }

    public void setQuery(Query query) {
         this.query = query;
     }
     public Query getQuery() {
         return query;
     }

    public void setQueryOption(QueryOption queryOption) {
         this.queryOption = queryOption;
     }
     public QueryOption getQueryOption() {
         return queryOption;
     }

    public void setTabs(List<Tabs> tabs) {
         this.tabs = tabs;
     }
     public List<Tabs> getTabs() {
         return tabs;
     }

}
