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
}
