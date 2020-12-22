package com.taskable.assessement.orders;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.shipments.ShipmentManager;

public class OrderProcessor {

    public static void orderProcessing() throws NullParameterException, BadParameterException {
        Order order = OrderManager.getInstance().getOrder();
        while (order != null) {
            OrderRules.applyRules(order);
            generateShipmentSlip(order);
            order = OrderManager.getInstance().getOrder();
        }
    }

    private static void generateShipmentSlip(Order order) throws NullParameterException, BadParameterException {
        if (!order.getPhysicalItemIds().isEmpty()) {
            Integer customerId = ((OrderImpl) order).getCustomerId();
            Integer orderId = ((OrderImpl) order).getId();
            String shipmentAddress = CustomerManager.getInstance().getAddress(customerId);
            ShipmentManager.getInstance().generateShipment(customerId, orderId, shipmentAddress, ((OrderImpl) order).getShipmentList());
        }
    }
}


