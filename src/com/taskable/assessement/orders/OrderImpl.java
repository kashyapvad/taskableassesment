package com.taskable.assessement.orders;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.items.ItemCatalog;

import java.util.*;

public class OrderImpl implements Order {

    private Integer id;
    private Float totalPrice;
    private Integer customerId;
    private Map<Integer, String> itemLine = new HashMap<>();

    public OrderImpl(Integer orderId, Float totalPrice, Integer customerId, Map<Integer, String> itemLine) throws NullParameterException, BadParameterException {

        setId(orderId);
        setTotalPrice(totalPrice);
        setCustomerId(customerId);
        setItemLine(itemLine);
    }

    public Integer getId() {
        return this.id;
    }

    public Float getTotalPrice() {
        return this.totalPrice;
    }

    public Integer getCustomerId() {
        return this.customerId;
    }

    public Map<Integer, String> getItemLine() {
        return itemLine;
    }

    public void setId(Integer id) throws BadParameterException, NullParameterException {
        if (id == null) {
            throw new NullParameterException("Null value passed in for ItemID");
        }
        if (id < 0 || id > Integer.MAX_VALUE || id.getClass() != Integer.class) {
            throw new BadParameterException("Bad value passed in for ItemID: " + id);
        }
        this.id = id;
    }

    public void setTotalPrice(Float totalPrice) throws BadParameterException, NullParameterException {
        if (totalPrice == null) {
            throw new NullParameterException("Null value passed in for ItemID");
        }
        if (totalPrice < 0 || totalPrice > Integer.MAX_VALUE || totalPrice.getClass() != Float.class) {
            throw new BadParameterException("Bad value passed in for cost: " + totalPrice);
        }
        this.totalPrice = totalPrice;
    }

    public void setCustomerId(Integer customerId) throws BadParameterException, NullParameterException {
        if (customerId == null) {
            throw new NullParameterException("Null value passed in for ItemID");
        }
        if (customerId < 0 || customerId > Integer.MAX_VALUE || customerId.getClass() != Integer.class) {
            throw new BadParameterException("Bad value passed in for facility name: " + id);
        }
        this.customerId = customerId;
    }

    public void setItemLine(Map<Integer, String> itemLine) throws NullParameterException, BadParameterException {
        for (Map.Entry<Integer, String> entry : itemLine.entrySet()) {
            Integer id = entry.getKey();
            String type = entry.getValue();
            if (id == null) {
                throw new NullParameterException("Null value passed in for ItemID");
            }
            if (type.length() > 50) {
                throw new BadParameterException("Bad value passed in for qunatity of item: " + type);
            }
        }
        this.itemLine = itemLine;
    }

    public List<Integer> getPhysicalItemIds() {
        List<Integer> physicalItemIds = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : this.itemLine.entrySet()) {
            if (!ItemCatalog.getInstance().getMembershipTypes().contains(entry.getValue())) {
                physicalItemIds.add(entry.getKey());
            }
        }
        return physicalItemIds;
    }

    public Boolean isMembershipItem(Integer id) {
        String type = this.itemLine.get(id);
        if (ItemCatalog.getInstance().getMembershipTypes().contains(type)) {
            return true;
        } else {
            return false;
        }
    }

    public String getType(Integer id) {
        return this.itemLine.get(id);
    }
}
