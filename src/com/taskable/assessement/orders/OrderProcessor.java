package com.taskable.assessement.orders;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;
import com.taskable.assessement.shipments.ShipmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
    //private static List<LinkedHashMap<String, Object>> solutionList = new ArrayList<>();

    public static void orderProcessing() throws NullParameterException, BadParameterException {
        Order order = OrderManager.getInstance().getOrder();
        while (order != null) {
            for (Map.Entry<Integer, String> entry : ((OrderImpl) order).getItemLine().entrySet()) {
                Integer itemId = entry.getKey();
                try {
                    if (ItemCatalog.getInstance().getIdSet().contains(entry.getKey())) {
                        if (order.isMembershipItem(itemId)) {
                            processMembershipItem(order, itemId);
                        }
                    } else throw new InvalidItemInXMLException();
                } catch (InvalidItemInXMLException e) {
                    e.invalidItemInXML("The Item ID " + entry.getKey() + " in the Order of Order ID " + ((OrderImpl) order).getId() + " is Invalid");
                    e.printStackTrace();
                }
            }
            if(!order.getPhysicalItemIds().isEmpty()) {
                Integer customerId = ((OrderImpl) order).getCustomerId();
                Integer orderId = ((OrderImpl) order).getId();
                String shipmentAddress = CustomerManager.getInstance().getAddress(customerId);
                ShipmentManager.getInstance().generateShipment(customerId, orderId, shipmentAddress, order.getPhysicalItemIds());
            }
            order = OrderManager.getInstance().getOrder();
        }
    }

    private static void processMembershipItem(Order order, Integer id) throws NullParameterException, BadParameterException {
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
}


