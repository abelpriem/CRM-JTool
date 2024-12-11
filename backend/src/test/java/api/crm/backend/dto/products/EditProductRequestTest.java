package api.crm.backend.dto.products;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EditProductRequestTest {

    @Test
    void testGettersAndSetters() {
        EditProductRequest editProductRequest = new EditProductRequest();

        editProductRequest.setName("Producto1");
        editProductRequest.setImage("producto.jpg");
        editProductRequest.setDescription("Descripción del producto");
        editProductRequest.setPrice(19.99);
        editProductRequest.setStock(43);

        assertEquals("Producto1", editProductRequest.getName());
        assertEquals("producto.jpg", editProductRequest.getImage());
        assertEquals("Descripción del producto", editProductRequest.getDescription());
        assertEquals(19.99, editProductRequest.getPrice());
        assertEquals(43, editProductRequest.getStock());
    }
}
