package com.taskable.assessement.rules;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.orders.Order;
import com.taskable.assessement.orders.OrderImpl;

public class PhysicalItemRule implements Rule {
    public void processRules(Order order, Integer itemId) {
        if (order.getPhysicalItemIds().contains(itemId)) {
            Integer customerId = ((OrderImpl) order).getCustomerId();
            order.addShipments(itemId);
            CustomerManager.getInstance().addPhysicalItem(customerId, order.getType(itemId), itemId);
        }
    }
}
