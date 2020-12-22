package com.taskable.assessement.orders;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;
import com.taskable.assessement.rules.Rule;
import com.taskable.assessement.rules.RulesManager;
import com.taskable.assessement.shipments.ShipmentManager;

import java.util.Map;

public class OrderProcessor {

    public static void orderProcessing() throws NullParameterException, BadParameterException {
        Order order = OrderManager.getInstance().getOrder();
        while (order != null) {
            for (Map.Entry<Integer, String> entry : ((OrderImpl) order).getItemLine().entrySet()) {
                Integer itemId = entry.getKey();
                try {
                    if (ItemCatalog.getInstance().getIdSet().contains(itemId)) {
                        for (Rule rule : RulesManager.getInstance().getRulesList()) {
                            rule.processRules(order, itemId);
                        }
                    } else throw new InvalidItemInXMLException();
                } catch (InvalidItemInXMLException e) {
                    e.invalidItemInXML("The Item ID " + entry.getKey() + " in the Order of Order ID " + ((OrderImpl) order).getId() + " is Invalid");
                    e.printStackTrace();
                }
            }
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


