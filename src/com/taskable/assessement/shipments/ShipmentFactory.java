package com.taskable.assessement.shipments;


import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.List;
import java.util.Map;

public class ShipmentFactory {

    public static Shipment shipmentBuilder(Integer id, Integer customerId, Integer orderId, String shipmentAddress, List<Integer> physicalItemLine) throws NullParameterException, BadParameterException {

        ShipmentImpl shipment = new ShipmentImpl(id, customerId, orderId, shipmentAddress, physicalItemLine);
        return shipment;

    }
}
