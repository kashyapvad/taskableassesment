package com.taskable.assessement.XML;


import com.taskable.assessement.customers.CustomerManager;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;
import com.taskable.assessement.orders.Order;
import com.taskable.assessement.orders.OrderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XMLloaderOrders {


    public List<Order> getOrderDataFromXML(String filename) throws NullParameterException, BadParameterException {
        List<Order> data = new ArrayList<>();
        File xmlFile = new File(filename);
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        NodeList orderList = doc.getElementsByTagName("Order");
        for (int i = 0; i < orderList.getLength(); i++) {
            if (orderList.item(i).getNodeType() == Node.TEXT_NODE) {
                continue;
            }

            String entryName = orderList.item(i).getNodeName();

            if (!entryName.equals("Order")) {
                System.err.println("Unexpected node found: " + entryName);
                return null;
            }
            Element orderElement = (Element) orderList.item(i);
            String idContent = orderElement.getElementsByTagName("OrderId").item(0).getTextContent();
            String customerIdContent = orderElement.getElementsByTagName("CustomerId").item(0).getTextContent();

            Integer id = Integer.parseInt(idContent);
            Integer customerId = Integer.parseInt(customerIdContent);

            try {
                if (!CustomerManager.getInstance().getIdSet().contains(customerId))
                    throw new InvalidItemInXMLException();
            } catch (InvalidItemInXMLException e) {
                e.invalidItemInXML("The Customer ID " + id + " in the order with Order ID " + id + " does not Exist");
                e.printStackTrace();
            }

            Float totalPrice = 0f;
            Map<Integer, String> itemLine = new LinkedHashMap<>();
            NodeList orderItemList = orderElement.getElementsByTagName("Item");
            addItemLine(orderItemList, itemLine, id, totalPrice);
            for (Map.Entry<Integer, String> entry : itemLine.entrySet()) {
                totalPrice += ItemCatalog.getInstance().getPrice(entry.getKey());
            }
            data.add(OrderFactory.orderBuilder(id, totalPrice, customerId, itemLine));
        }

        return data;
    }

    public static void addItemLine(NodeList orderItemList, Map<Integer, String> itemLine, Integer id, Float totalPrice) {
        for (int k = 0; k < orderItemList.getLength(); k++) {
            if (orderItemList.item(k).getNodeType() == Node.TEXT_NODE) {
                continue;
            }

            String entryName = orderItemList.item(k).getNodeName();
            if (!entryName.equals("Item")) {
                System.err.println("Unexpected node found: " + entryName);
            }

            Element orderItemElement = (Element) orderItemList.item(k);

            String itemIdContent = orderItemElement.getElementsByTagName("Id").item(0).getTextContent();
            String type = orderItemElement.getElementsByTagName("Type").item(0).getTextContent();
            int itemId = Integer.parseInt(itemIdContent);
            try {
                if (ItemCatalog.getInstance().getIdSet().contains(itemId))
                    itemLine.put(itemId, type);
                else throw new InvalidItemInXMLException();
            } catch (InvalidItemInXMLException e) {
                e.invalidItemInXML("The Item ID " + id + " in the order with Order ID " + itemIdContent + " does not Exist");
                e.printStackTrace();
            }
        }
    }
}


