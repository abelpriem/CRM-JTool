package api.crm.backend.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderTest {

    private User user;
    private Order order;
    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(3L);
        user.setUsername("Example");
        user.setEmail("example@email.com");

        orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(1L);
        orderDetail.setQuantity(2);
        orderDetail.setUnitPrice(15.5);
        orderDetail.setOrder(order);
    }

    @Test
    void testGettersAndSetters() {
        order = new Order();

        order.setOrderId(1L);
        order.setUser(user);
        order.setOrderDate(LocalDate.of(2024, 12, 11));
        order.setDetails(List.of(orderDetail));
        order.setTotal(31.0);

        assertEquals(1L, order.getOrderId());
        assertEquals(LocalDate.of(2024, 12, 11), order.getOrderDate());
        assertEquals("Example", order.getUser().getUsername());
        assertEquals("example@email.com", order.getUser().getEmail());
        assertEquals(31.0, order.getTotal());
        assertEquals(1, order.getDetails().size());
    }

    @Test
    void testConstructorWithParameters() {
        Order order = new Order(1L, LocalDate.of(2024, 12, 11), List.of(orderDetail), 31.0);
        order.setUser(user);

        assertEquals(1L, order.getOrderId());
        assertEquals(LocalDate.of(2024, 12, 11), order.getOrderDate());
        assertEquals("Example", order.getUser().getUsername());
        assertEquals("example@email.com", order.getUser().getEmail());
        assertEquals(31.0, order.getTotal());
        assertEquals(1, order.getDetails().size());
    }

    @Test
    void testConstructorWithoutParameters() {
        Order order = new Order();

        assertNull(order.getOrderId());
        assertNull(order.getOrderDate());
        assertNull(order.getUser());
        assertNull(order.getDetails());
        assertNull(order.getUser());
    }
}
