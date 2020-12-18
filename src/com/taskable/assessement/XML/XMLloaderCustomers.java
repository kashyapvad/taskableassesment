package com.taskable.assessement.XML;


import com.taskable.assessement.customers.Order;
import com.taskable.assessement.customers.CustomerFactory;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.InvalidItemInXMLException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.Item;
import com.taskable.assessement.items.ItemCatalog;
import com.taskable.assessement.items.ItemFactory;
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
import java.util.*;

public class XMLloaderCustomers {

    public List<Order> getCustomerDataFromXML(String filename) throws NullParameterException, BadParameterException {
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
        NodeList customerList = doc.getElementsByTagName("Customer");
        for (int i = 0; i < customerList.getLength(); i++) {
            if (customerList.item(i).getNodeType() == Node.TEXT_NODE) {
                continue;
            }

            String entryName = customerList.item(i).getNodeName();

            if (!entryName.equals("Customer")) {
                System.err.println("Unexpected node found: " + entryName);
                return null;
            }
            Element customerElement = (Element) customerList.item(i);
            String idContent = customerElement.getElementsByTagName("Id").item(0).getTextContent();
            String firstName = customerElement.getElementsByTagName("FirstName").item(0).getTextContent();
            String lastName = customerElement.getElementsByTagName("LastName").item(0).getTextContent();
            String address = customerElement.getElementsByTagName("Address").item(0).getTextContent();
            String name = firstName + " " + lastName;

            Integer id = Integer.parseInt(idContent);
            Set<String> memberships = new HashSet<>();
            Map<String, List<Integer>> physicalItemsBought = new LinkedHashMap<>();
            NodeList membershipList = customerElement.getElementsByTagName("Membership");
            NodeList itemsList = customerElement.getElementsByTagName("Item");
            for (int k = 0; k < membershipList.getLength(); k++) {
                if (membershipList.item(k).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                entryName = membershipList.item(k).getNodeName();
                if (!entryName.equals("Membership")) {
                    System.err.println("Unexpected node found: " + entryName);
                    return null;
                }

                Element membershipElement = (Element) membershipList.item(k);

                String membershipType = membershipElement.getElementsByTagName("Type").item(0).getTextContent();
                try {
                    if (ItemCatalog.getInstance().getMembershipTypes().contains(membershipType))
                        memberships.add(membershipType);
                    else throw new InvalidItemInXMLException();
                } catch (InvalidItemInXMLException e) {
                    e.invalidItemInXML("The Membership Type " + membershipType + " provided for the customer " + name + " is Invalid");
                    e.printStackTrace();
                }
            }

            for (int k = 0; k < itemsList.getLength(); k++) {
                if (itemsList.item(k).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                entryName = itemsList.item(k).getNodeName();
                if (!entryName.equals("Item")) {
                    System.err.println("Unexpected node found: " + entryName);
                    return null;
                }

                Element itemElement = (Element) itemsList.item(k);

                String itemType = itemElement.getElementsByTagName("Type").item(0).getTextContent();
                String itemIdContent = itemElement.getElementsByTagName("ItemId").item(0).getTextContent();
                Integer itemId = Integer.parseInt(itemIdContent);
                try {
                    if (ItemCatalog.getInstance().getPhysicalProductTypes().contains(itemType)) {
                        try {
                            if (ItemCatalog.getInstance().getIdSet().contains(itemId)) {
                                if (physicalItemsBought.keySet().contains(itemType)) {
                                    physicalItemsBought.get(itemType).add(itemId);
                                } else {
                                    List<Integer> itemIds = new ArrayList<>();
                                    itemIds.add(itemId);
                                    physicalItemsBought.put(itemType, itemIds);
                                }
                            } else throw new InvalidItemInXMLException();
                        } catch (InvalidItemInXMLException e) {
                            e.invalidItemInXML("The Item id " + itemId + " is Invalid");
                            e.printStackTrace();
                        }
                    } else throw new InvalidItemInXMLException();
                } catch (InvalidItemInXMLException e) {
                    e.invalidItemInXML("The Membership Type " + itemType + " provided for the customer " + name + " is Invalid");
                    e.printStackTrace();
                }
            }

            data.add(CustomerFactory.customerBuilder(id, firstName, lastName, address, memberships, physicalItemsBought));
        }
        return data;
    }
}


