package com.taskable.assessement.items;


import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

public class ItemFactory {

    public static Item itemBuilder(Integer id, String name, String type, Float price) throws NullParameterException, BadParameterException {

        ItemImpl item = new ItemImpl(id, name, type, price);
        return item;

    }
}
