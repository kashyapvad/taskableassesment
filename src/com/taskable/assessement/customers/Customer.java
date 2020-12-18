package com.taskable.assessement.customers;


public interface Customer {
    String getName();

    void addMembership(String type);

    void addPhysicalItem(String type, Integer itemId);
}
