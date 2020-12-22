package com.taskable.assessement.rules;

import com.taskable.assessement.orders.Order;

public interface Rule {
    void processRules(Order order, Integer itemId);
}
