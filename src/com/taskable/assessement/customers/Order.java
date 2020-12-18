package com.taskable.assessement.customers;


public interface Order {
    void addMembership(String type);

    void addPhysicalItem(String type, Integer itemId);
}
