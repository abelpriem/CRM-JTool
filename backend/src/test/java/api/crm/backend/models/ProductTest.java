package api.crm.backend.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void testGettersAndSetters() {
        Product product = new Product();

        product.setProductId(3L);
        product.setName("Ejemplo");
        product.setImage("imagenEjemplo.png");
        product.setDescription("Producto de ejemplo");
        product.setPrice(21.99);
        product.setStock(48);

        assertEquals(3L, product.getProductId());
        assertEquals("Ejemplo", product.getName());
        assertEquals("imagenEjemplo.png", product.getImage());
        assertEquals("Producto de ejemplo", product.getDescription());
        assertEquals(21.99, product.getPrice());
        assertEquals(48, product.getStock());
    }

    @Test
    void testConstructorWithParameters() {
        Product product = new Product(1L, "Laptop", "laptop.jpg", "High performance laptop", 1500.00, 10);

        assertEquals(1L, product.getProductId());
        assertEquals("Laptop", product.getName());
        assertEquals("laptop.jpg", product.getImage());
        assertEquals("High performance laptop", product.getDescription());
        assertEquals(1500.00, product.getPrice());
        assertEquals(10, product.getStock());
    }

    @Test
    void testConstructorWithoutParameters() {
        Product product = new Product();

        assertNull(product.getProductId());
        assertNull(product.getName());
        assertNull(product.getDescription());
        assertNull(product.getImage());
        assertNull(product.getPrice());
        assertNull(product.getStock());
    }
}
