package api.crm.backend.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderDetailTest {

    private User user;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(3L);
        user.setUsername("Example");
        user.setEmail("example@email.com");

        product = new Product();
        product.setProductId(2L);
        product.setName("Product1");
        product.setDescription("First product");
        product.setImage("product.img");
        product.setPrice(19.99);
        product.setStock(20);

        order = new Order();
        order.setOrderId(9L);
        order.setOrderDate(LocalDate.of(2024, 12, 11));
        order.setUser(user);
        order.setDetails(null);
        order.setTotal(31.0);

    }

    @Test
    void testGettersAndSetters() {
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderDetailId(1L);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(5);
        orderDetail.setUnitPrice(19.99);

        assertEquals(1L, orderDetail.getOrderDetailId());
        assertEquals("Example", orderDetail.getOrder().getUser().getUsername());
        assertEquals("example@email.com", orderDetail.getOrder().getUser().getEmail());
        assertEquals("Product1", orderDetail.getProduct().getName());
        assertEquals(20, orderDetail.getProduct().getStock());
        assertEquals(19.99, orderDetail.getUnitPrice());
        assertEquals(5, orderDetail.getQuantity());
    }
}
