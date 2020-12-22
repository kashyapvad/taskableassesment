package com.taskable.assessement;

import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.orders.OrderManager;
import com.taskable.assessement.shipments.ShipmentManager;

public class Main {

    public static void main(String[] args) throws NullParameterException, BadParameterException, ClassNotFoundException {
        System.out.println("Customer's state before orders are processed");
        CustomerManager.getInstance().printReport();
        System.out.println("==================================================");
        System.out.println("Orders to be processed");
        OrderManager.getInstance().printOrders();
        System.out.println("==================================================");
        System.out.println("Processing orders");
        OrderManager.getInstance().processOrders();
        System.out.println("==================================================");
        System.out.println("Processing done");
        System.out.println("==================================================");
        System.out.println("Shipping slips generated ");
        ShipmentManager.getInstance().printReport();
        System.out.println("Customer's state after orders are processed");
        CustomerManager.getInstance().printReport();
        System.out.println("==================================================");

    }
}
