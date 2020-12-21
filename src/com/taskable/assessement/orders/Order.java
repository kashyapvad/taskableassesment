package com.taskable.assessement.orders;

import java.util.List;

public interface Order {
    List<Integer> getPhysicalItemIds();

    Boolean isMembershipItem(Integer id);

    String getType(Integer id);

    void addShipments(Integer itemId);
}
