package com.taskable.assessement.shipments;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShipmentImpl implements Shipment {

    private Integer id;
    private Integer customerId;
    private Integer orderId;
    private String shipmentAddress;
    private List<Integer> physicalItemLine = new ArrayList<>();

    public ShipmentImpl(Integer id, Integer customerId, Integer orderId, String shipmentAddress, List<Integer> physicalItemLine) throws NullParameterException, BadParameterException {
        setId(id);
        setCustomerId(customerId);
        setOrderId(orderId);
        setAddress(shipmentAddress);
        setPhysicalItemLine(physicalItemLine);
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return shipmentAddress;
    }

    public List<Integer> getPhysicalItemLine() {
        return physicalItemLine;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public void setPhysicalItemLine(List<Integer> physicalItemLine) {
        this.physicalItemLine = physicalItemLine;
    }

}
