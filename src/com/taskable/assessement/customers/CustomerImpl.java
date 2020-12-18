package com.taskable.assessement.customers;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;

import java.util.*;

public class CustomerImpl implements Customer {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private Set<String> memberships = new HashSet<>();
    private Map<String, List<Integer>> physicalItemsBought = new LinkedHashMap<>();

    public CustomerImpl(int id, String firstName, String lastName, String address, Set<String> memberships, Map<String, List<Integer>> itemsBought) throws NullParameterException, BadParameterException {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setMemberships(memberships);
        setPhysicalItemsBought(itemsBought);
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Integer id) throws NullParameterException, BadParameterException {
        if (id == null) {
            throw new NullParameterException("Null value passed in for CustomerID");
        }
        if (id < 0 || id > Integer.MAX_VALUE || id.getClass() != Integer.class) {
            throw new BadParameterException("Bad value passed in for CustomerID: " + id);
        }
        this.id = id;
    }

    public void setFirstName(String firstName) throws NullParameterException, BadParameterException {
        if (firstName == null) {
            throw new NullParameterException("Null value passed in for First Name");
        }
        if (firstName.length() > 20) {
            throw new BadParameterException("Bad value passed in for First Name: " + firstName);
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws NullParameterException, BadParameterException {
        if (lastName == null) {
            throw new NullParameterException("Null value passed in for Last Name");
        }
        if (lastName.length() > 20) {
            throw new BadParameterException("Bad value passed in for Last Name: " + lastName);
        }
        this.lastName = lastName;
    }

    public Set<String> getMemberships() {
        return memberships;
    }

    public Map<String, List<Integer>> getPhysicalItemsBought() {
        return physicalItemsBought;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMemberships(Set<String> memberships) {
        this.memberships = memberships;
    }

    public void setPhysicalItemsBought(Map<String, List<Integer>> physicalItemsBought) throws NullParameterException, BadParameterException {
        for (Map.Entry<String, List<Integer>> entry : physicalItemsBought.entrySet()) {
            String type = entry.getKey();
            List<Integer> itemIDs = entry.getValue();
            if (type == null) {
                throw new NullParameterException("Null value passed in for Item type");
            }
            for (Integer i : itemIDs) {
                if (i == null) {
                    throw new NullParameterException("Null value passed in for ItemID");
                }
                if (!ItemCatalog.getInstance().getIdSet().contains(i)) {
                    throw new BadParameterException("Bad value passed in for id of the item: " + i);
                }
                if (!ItemCatalog.getInstance().getPhysicalProductTypes().contains(type)) {
                    throw new BadParameterException("Bad value passed in for type of the item with id " + i);
                }
            }
        }
        this.physicalItemsBought = physicalItemsBought;
    }

    public String getName(){
        return getFirstName() + " " + getLastName();
    }

    public void addMembership(String type) { this.memberships.add(type); }

    public void addPhysicalItem(String type, Integer itemId) {
        if (physicalItemsBought.keySet().contains(type)) {
            physicalItemsBought.get(type).add(itemId);
        } else {
            List<Integer> itemIds = new ArrayList<>();
            itemIds.add(itemId);
            physicalItemsBought.put(type, itemIds);
        }
    }
}
