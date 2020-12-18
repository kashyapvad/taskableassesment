package com.taskable.assessement.items;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

public class ItemImpl implements Item {

    private Integer id;
    private String name;
    private String type;
    private Float price;

    public ItemImpl(Integer id, String name, String type, Float price) throws NullParameterException, BadParameterException {
        setId(id);
        setName(name);
        setType(type);
        setPrice(price);
    }

    public void setId(Integer id) throws NullParameterException, BadParameterException {
        if (id == null) {
            throw new NullParameterException("Null value passed in for ItemID");
        }
        if (id < 0 || id > Integer.MAX_VALUE || id.getClass() != Integer.class) {
            throw new BadParameterException("Bad value passed in for ItemID: " + id);
        }
        this.id = id;
    }

    public void setName(String name) throws NullParameterException, BadParameterException {
        if (name == null) {
            throw new NullParameterException("Null value passed in for Item Name");
        }
        if (name.length() > 50) {
            throw new BadParameterException("Bad value passed in for Item Name: " + name);
        }
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(Float price) throws NullParameterException, BadParameterException {
        if (price == null) {
            throw new NullParameterException("Null value passed in for price");
        }
        if (price > Integer.MAX_VALUE || price.getClass() != Float.class) {
            throw new BadParameterException("Bad value passed in for the value of price: " + price);
        }
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Float getPrice() {
        return price;
    }
}
