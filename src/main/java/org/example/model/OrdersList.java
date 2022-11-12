package org.example.model;

import java.util.List;

public class OrdersList {
    private List<OrderActive> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<OrderActive> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderActive> orders) {
        this.orders = orders;
    }
}
