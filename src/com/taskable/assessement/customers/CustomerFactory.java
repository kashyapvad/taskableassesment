package com.taskable.assessement.customers;


import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerFactory {

    public static Order customerBuilder(Integer id, String firstName, String lastName, String address, Set<String> memberships, Map<String, List<Integer>> physicalItemsBought) throws NullParameterException, BadParameterException {

        CustomerImpl customer = new CustomerImpl(id, firstName, lastName, address, memberships, physicalItemsBought);
        return customer;

    }
}
