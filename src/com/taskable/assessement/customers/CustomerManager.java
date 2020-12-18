package com.taskable.assessement.customers;

import com.taskable.assessement.XML.XMLloaderCustomers;
import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerManager {
    private List<Order> customerList;
    private static CustomerManager ourInstance;

    static {
        try {
            ourInstance = new CustomerManager();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (BadParameterException e) {
            e.printStackTrace();
        }
    }

    public static final String FILENAME = "../taskableassesment/data/customers.xml";

    public static CustomerManager getInstance() {
        return ourInstance;
    }

    private CustomerManager() throws NullParameterException, BadParameterException {

        XMLloaderCustomers loader = new XMLloaderCustomers();
        customerList = loader.getCustomerDataFromXML(FILENAME);
    }

    public Set<Integer> getIdSet() {
        Set<Integer> customerIds = new HashSet<>();
        for (Order c : customerList) {
            customerIds.add(((CustomerImpl) c).getId());
        }
        return customerIds;
    }

    public void addMembership(Integer id, String membership) {
        for (Order c : customerList) {
            if (((CustomerImpl) c).getId().equals(id)) {
                c.addMembership(membership);
                break;
            }
        }
    }

    public void addPhysicalItem(Integer customerId, String type, Integer itemId) {
        for (Order c : customerList) {
            if (((CustomerImpl) c).getId().equals(customerId)) {
                c.addPhysicalItem(type, itemId);
                break;
            }
        }
    }

    public Boolean membershipExists(Integer customerId, String membership) {
        Boolean exists = false;
        for (Order c : customerList) {
            if (((CustomerImpl) c).getId().equals(customerId)) {
                exists = ((CustomerImpl) c).getMemberships().contains(membership);
            }
        }
        return exists;
    }

    public String getAddress(Integer customerId) {
        String address = "";
        for (Order c : customerList) {
            if (((CustomerImpl) c).getId().equals(customerId)) {
                address = ((CustomerImpl) c).getAddress();
                break;
            }
        }
        return address;
    }

    public void printReport() {
        for (Order c : customerList) {
            System.out.print("Id: " + ((CustomerImpl) c).getId() + "       ");
            System.out.print("Name: " + ((CustomerImpl) c).getName() + "       ");
            System.out.print("Memberships: " + ((CustomerImpl) c).getMemberships() + "       ");
            System.out.print("ItemsBought: " + ((CustomerImpl) c).getPhysicalItemsBought() + "       ");
            System.out.println();
        }
    }
}
