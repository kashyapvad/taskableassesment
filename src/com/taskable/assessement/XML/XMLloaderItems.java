package com.taskable.assessement.XML;


import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.Item;
import com.taskable.assessement.items.ItemFactory;
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
import java.util.List;

public class XMLloaderItems {

    public List<Item> getItemDataFromXML(String filename) throws NullParameterException, BadParameterException {
        List<Item> data = new ArrayList<>();
        File xmlFile = new File(filename);
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        NodeList itemList = doc.getElementsByTagName("Item");
        for (int i = 0; i < itemList.getLength(); i++) {
            if (itemList.item(i).getNodeType() == Node.TEXT_NODE) {
                continue;
            }

            String entryName = itemList.item(i).getNodeName();

            if (!entryName.equals("Item")) {
                System.err.println("Unexpected node found: " + entryName);
                return null;
            }
            Element itemElement = (Element) itemList.item(i);
            String idContent = itemElement.getElementsByTagName("Id").item(0).getTextContent();
            String nameContent = itemElement.getElementsByTagName("Name").item(0).getTextContent();
            String typeContent = itemElement.getElementsByTagName("Type").item(0).getTextContent();
            String priceContent = itemElement.getElementsByTagName("Price").item(0).getTextContent();

            Integer id = Integer.parseInt(idContent);
            Float price = Float.parseFloat(priceContent);

            data.add(ItemFactory.itemBuilder(id, nameContent, typeContent, price));
        }

        return data;
    }
}
