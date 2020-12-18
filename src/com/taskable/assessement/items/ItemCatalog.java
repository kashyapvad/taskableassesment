package com.taskable.assessement.items;

import com.taskable.assessement.XML.XMLloaderItems;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemCatalog {
    private List<Item> itemList;
    private static ItemCatalog ourInstance;
    private final List<String> MEMBERSHIP_TYPES = List.of("Book Club Membership", "Video Club Membership");
    private final List<String> PHYSICAL_PRODUCT_TYPES = List.of("Book", "Video");

    static {
        try {
            ourInstance = new ItemCatalog();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (BadParameterException e) {
            e.printStackTrace();
        }
    }

    public static final String FILENAME = "../taskableassesment/data/items.xml";

    public static ItemCatalog getInstance() {
        return ourInstance;
    }


    private ItemCatalog() throws NullParameterException, BadParameterException {
        XMLloaderItems loader = new XMLloaderItems();
        itemList = loader.getItemDataFromXML(FILENAME);
    }


    public Float getPrice(Integer itemId) {
        Float cost = 0f;
        for (Item i : itemList) {
            if (((ItemImpl) i).getId().equals(itemId)) {
                cost = ((ItemImpl) i).getPrice();
            }
        }
        return cost;
    }

    public Set<Integer> getIdSet() {
        Set<Integer> itemIds = new HashSet<>();
        for (Item i : itemList) {
            itemIds.add(((ItemImpl) i).getId());
        }
        return itemIds;
    }

    public List<String> getPhysicalProductTypes() {
        return this.PHYSICAL_PRODUCT_TYPES;
    }

    public List<String> getMembershipTypes() {
        return this.MEMBERSHIP_TYPES;
    }

}
