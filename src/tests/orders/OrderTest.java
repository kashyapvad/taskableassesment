package tests.orders;

import com.taskable.assessement.exceptions.BadParameterException;
import com.taskable.assessement.exceptions.NullParameterException;
import com.taskable.assessement.orders.Order;
import com.taskable.assessement.orders.OrderImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class OrderTest {

    @Test
    public void testOrderImpl() throws Exception {
        try {
            Order testOrder = (Order) new OrderImpl(null, 34f, 204, new HashMap<Integer, String>());
        } catch (NullParameterException e) {
            assertEquals("Null value passed in for ItemID", e.getMessage());
        }

        try {
            Order testOrder = (Order) new OrderImpl(-34, 34f, 204, new HashMap<Integer, String>());
        } catch (BadParameterException e) {
            assertEquals("Bad value passed in for ItemID: -34", e.getMessage());
        }

        Map<Integer, String> itemLine = new HashMap<>();
        itemLine.put(21342, "Video Club Membership");
        itemLine.put(21345, "Video");
        List<Integer> physicalItemIds = new ArrayList<>();
        physicalItemIds.add(21345);
        Order testOrder = (Order) new OrderImpl(34, 34f, 204, itemLine);

        assertEquals(physicalItemIds, testOrder.getPhysicalItemIds());
        assertEquals("Video Club Membership", testOrder.getType(21342));
        assertEquals("Video", testOrder.getType(21345));
        assertTrue(testOrder.isMembershipItem(21342));
        assertFalse(testOrder.isMembershipItem(21345));
    }
}
