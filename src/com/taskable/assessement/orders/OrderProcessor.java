package com.taskable.assessement.orders;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;
import com.taskable.assessement.shipments.ShipmentManager;

import java.util.Map;

public class OrderProcessor {

    public static void orderProcessing() throws NullParameterException, BadParameterException {
        Order order = OrderManager.getInstance().getOrder();
        while (order != null) {
            applyRules(order);
            generateShipmentSlip(order);
            order = OrderManager.getInstance().getOrder();
        }
    }

    private static void applyRules(Order order) throws NullParameterException, BadParameterException {
        for (Map.Entry<Integer, String> entry : ((OrderImpl) order).getItemLine().entrySet()) {
            Integer itemId = entry.getKey();
            try {
                if (ItemCatalog.getInstance().getIdSet().contains(entry.getKey())) {
                    applyRule(order, itemId);
                } else throw new InvalidItemInXMLException();
            } catch (InvalidItemInXMLException e) {
                e.invalidItemInXML("The Item ID " + entry.getKey() + " in the Order of Order ID " + ((OrderImpl) order).getId() + " is Invalid");
                e.printStackTrace();
            }
        }
    }

    private static void applyRule(Order order, Integer itemId){
        // Business Rule 1
        if (order.isMembershipItem(itemId)) {
            processMembershipItem(order, itemId);
        }
        // Business Rule 2
        if (order.getPhysicalItemIds().contains(itemId)) {
            processPhysicalItem(order, itemId);
        }
    }

    private static void processMembershipItem(Order order, Integer id) {
        Integer customerId = ((OrderImpl) order).getCustomerId();
        String membership = order.getType(id);
        try {
            if (!CustomerManager.getInstance().membershipExists(customerId, membership)) {
                CustomerManager.getInstance().addMembership(customerId, membership);
            } else throw new InvalidItemInXMLException();
        } catch (InvalidItemInXMLException e) {
            e.invalidItemInXML("The Customer with ID " + customerId + " already has " + membership);
            e.printStackTrace();
        }
    }

    private static void processPhysicalItem(Order order, Integer itemId) {
        Integer customerId = ((OrderImpl) order).getCustomerId();
        order.addShipments(itemId);
        CustomerManager.getInstance().addPhysicalItem(customerId, order.getType(itemId), itemId);
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


