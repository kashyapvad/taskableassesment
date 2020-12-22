package com.taskable.assessement.orders;

import com.taskable.assessement.XML.XMLloaderOrders;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.List;

public class OrderManager {
    private List<Order> orderList;
    private static OrderManager ourInstance;

    static {
        try {
            ourInstance = new OrderManager();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (BadParameterException e) {
            e.printStackTrace();
        }
    }

    public static final String FILENAME = "../taskableassesment/data/orders.xml";

    public static OrderManager getInstance() {
        return ourInstance;
    }

    private OrderManager() throws NullParameterException, BadParameterException {
        XMLloaderOrders loader = new XMLloaderOrders();
        orderList = loader.getOrderDataFromXML(FILENAME);
    }

    public void processOrders() throws NullParameterException, BadParameterException {
        OrderProcessor.orderProcessing();
    }

    public void printOrders() {
        for (Order o : orderList) {
            System.out.print("Id: " + ((OrderImpl) o).getId() + "       ");
            System.out.print("Total Price: " + ((OrderImpl) o).getTotalPrice() + "       ");
            System.out.print("Customer ID: " + ((OrderImpl) o).getCustomerId() + "       ");
            System.out.print("Item Line: " + ((OrderImpl) o).getItemLine() + "       ");
            System.out.println();
        }
    }

    public Order getOrder() {
        if (orderList.size() >= 1) {
            Order order = orderList.get(0);
            orderList.remove(order);
            return order;
        } else return null;
    }
}
