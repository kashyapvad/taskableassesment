package com.taskable.assessement.orders;


import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.Map;

public class OrderFactory {

    public static Order orderBuilder(Integer orderId, Float totalPrice, Integer customerId, Map<Integer, String> itemLine) throws NullParameterException, BadParameterException {

        Order order = new OrderImpl(orderId, totalPrice, customerId, itemLine);
        return order;

    }
}
