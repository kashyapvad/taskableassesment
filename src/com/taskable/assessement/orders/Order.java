package com.taskable.assessement.orders;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public interface Order {
    List<Integer> getPhysicalItemIds();

    Boolean isMembershipItem(Integer id);

    String getType(Integer id);

}
