package api.crm.backend.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import api.crm.backend.config.SecurityConfig;
import api.crm.backend.dto.products.EditProductRequest;
import api.crm.backend.models.Product;
import api.crm.backend.services.ProductService;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;

@WebMvcTest(ProductsController.class)
@Import(SecurityConfig.class)
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    EditProductRequest mockEditProductRequest;

    private final String AUTH_TOKEN = "Bearer valid-token";

    @Test
    void testGetProducts_Success() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Product1", "product1.img", "Description1", 19.99, 29),
                new Product(1L, "Product2", "product2.img", "Description2", 15.99, 14));

        when(productService.getAll(AUTH_TOKEN)).thenReturn(products);

        mockMvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN))
                .andExpect(status().isOk())
                // .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Product1"))
                // .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Product2"));

        verify(productService, times(1)).getAll(AUTH_TOKEN);
    }

    @Test
    void testGetProducts_Unauthorized() throws Exception {
        doThrow(new SecurityException("El token enviado no coincide con el almacenado para el usuario"))
                .when(productService)
                .editProductById(eq("Bearer invalid-token"), eq(1L), any(EditProductRequest.class));

        mockMvc.perform(patch("/api/products/edit/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\":\"ProductChanged\",\"image\":\"productChanged.img\",\"description\":\"DescriptionChanged\",\"price\":\"19\",\"stock\":\"20\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(
                        jsonPath("$.message").value("El token enviado no coincide con el almacenado para el usuario"));
    }

    @Test
    void testEditProduct_Success() throws Exception {
        mockEditProductRequest = new EditProductRequest("ProductChanged", "productChanged.img", "DescriptionChanged",
                19.99, 20);

        when(productService.editProductById(eq(AUTH_TOKEN), eq(1L), any(EditProductRequest.class)))
                .thenReturn(new Product());

        mockMvc.perform(patch("/api/products/edit/1")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\":\"ProductChanged\",\"image\":\"productChanged.img\",\"description\":\"DescriptionChanged\",\"price\":\"19.99\",\"stock\":\"20\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Producto editado correctamente"));

        ArgumentCaptor<EditProductRequest> captor = ArgumentCaptor.forClass(EditProductRequest.class);
        verify(productService, times(1)).editProductById(eq(AUTH_TOKEN), eq(1L), captor.capture());

        EditProductRequest capturedRequest = captor.getValue();
        assertEquals("ProductChanged", capturedRequest.getName());
        assertEquals("DescriptionChanged", capturedRequest.getDescription());
    }

    @Test
    void testEditProduct_NotFound() throws Exception {
        doThrow(new NotFoundException("Producto no encontrado. Vuelva a intentarlo"))
                .when(productService).editProductById(eq(AUTH_TOKEN), eq(1L), any(EditProductRequest.class));

        mockMvc.perform(patch("/api/products/edit/1")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"name\":\"ProductChanged\",\"image\":\"productChanged.img\",\"description\":\"DescriptionChanged\",\"price\":\"19\",\"stock\":\"20\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Producto no encontrado. Vuelva a intentarlo"));
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        doNothing().when(productService).deleteProductById(AUTH_TOKEN, 1L);

        mockMvc.perform(delete("/api/products/delete/1")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Producto eliminado correctamente"));

        verify(productService, times(1)).deleteProductById(AUTH_TOKEN, 1L);
    }

    @Test
    void testDeleteProduct_NotFound() throws Exception {
        doThrow(new NotFoundException("Producto no encontrado. Vuelva a intentarlo"))
                .when(productService).deleteProductById(AUTH_TOKEN, 1L);

        mockMvc.perform(delete("/api/products/delete/1")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Producto no encontrado. Vuelva a intentarlo"));
    }

    @Test
    void testDeleteProduct_Forbidden() throws Exception {
        doThrow(new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción"))
                .when(productService).deleteProductById(AUTH_TOKEN, 1L);

        mockMvc.perform(delete("/api/products/delete/1")
                .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción"));
    }
}
