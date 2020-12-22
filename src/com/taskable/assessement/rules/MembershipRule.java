package com.taskable.assessement.rules;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.orders.Order;
import com.taskable.assessement.orders.OrderImpl;

public class MembershipRule implements Rule {
    public void processRules(Order order, Integer itemId){
        if (order.isMembershipItem(itemId)) {
            Integer customerId = ((OrderImpl) order).getCustomerId();
            String membership = order.getType(itemId);
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
}
