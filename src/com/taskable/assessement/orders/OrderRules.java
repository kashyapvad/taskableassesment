package com.taskable.assessement.orders;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.items.ItemCatalog;

import java.util.Map;

public class OrderRules {
    public static void applyRules(Order order) {
        for (Map.Entry<Integer, String> entry : ((OrderImpl) order).getItemLine().entrySet()) {
            Integer itemId = entry.getKey();
            try {
                if (ItemCatalog.getInstance().getIdSet().contains(itemId)) {
                    applyMembershipItemRules(order, itemId);
                    applyPhysicalItemRules(order, itemId);
                } else throw new InvalidItemInXMLException();
            } catch (InvalidItemInXMLException e) {
                e.invalidItemInXML("The Item ID " + entry.getKey() + " in the Order of Order ID " + ((OrderImpl) order).getId() + " is Invalid");
                e.printStackTrace();
            }
        }
    }

    private static void applyMembershipItemRules(Order order, Integer itemId){
        if (order.isMembershipItem(itemId)) {
            processMembershipItem(order, itemId);
        }
    };
    private static void applyPhysicalItemRules(Order order, Integer itemId){
        if (order.getPhysicalItemIds().contains(itemId)) {
            processPhysicalItem(order, itemId);
        }
    };

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
}
