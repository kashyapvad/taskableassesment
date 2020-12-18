package com.taskable.assessement.shipments;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;

import java.util.ArrayList;
import java.util.List;

public class ShipmentManager {
    private List<Shipment> shipmentList = new ArrayList<>();
    private static ShipmentManager ourInstance;

    static {
        try {
            ourInstance = new ShipmentManager();
        } catch (NullParameterException e) {
            e.printStackTrace();
        } catch (BadParameterException e) {
            e.printStackTrace();
        }
    }

    public static final String FILENAME = "../taskableassesment/data/orders.xml";

    public static ShipmentManager getInstance() {
        return ourInstance;
    }

    private ShipmentManager() throws NullParameterException, BadParameterException {

    }

    public void printReport() {
        for (Shipment s : shipmentList) {
            System.out.print("Id: " + ((ShipmentImpl) s).getId() + "       ");
            System.out.print("Customer Id: " + ((ShipmentImpl) s).getCustomerId() + "       ");
            System.out.print("orderID: " + ((ShipmentImpl) s).getOrderId() + "       ");
            System.out.print("Items to be shipped: " + ((ShipmentImpl) s).getPhysicalItemLine() + "       ");
            System.out.print("Address: " + ((ShipmentImpl) s).getAddress() + "       ");
            System.out.println();
        }
    }

    public void generateShipment(Integer customerId, Integer orderId, String shipmentAddress, List<Integer> physicalItemLine) throws NullParameterException, BadParameterException {
        shipmentList.add(ShipmentFactory.shipmentBuilder(getLastShipmentId() + 1, customerId, orderId, shipmentAddress, physicalItemLine));
    }

    private Integer getLastShipmentId() {
        if (shipmentList.isEmpty()) {
            return 1;
        } else {
            Shipment lastShipment = shipmentList.get(shipmentList.size() - 1);
            return ((ShipmentImpl) lastShipment).getId();
        }
    }
}
