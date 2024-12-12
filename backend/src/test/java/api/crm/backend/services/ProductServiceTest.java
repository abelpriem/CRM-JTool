package api.crm.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.products.EditProductRequest;
import api.crm.backend.models.Product;
import api.crm.backend.models.User;
import api.crm.backend.repository.ProductRepository;
import api.crm.backend.repository.UserRepository;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    private User mockUser;
    private List<Product> products;
    private EditProductRequest mockEditProductRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setUsername("ExampleUser");
        mockUser.setEmail("example-user@email.com");
        mockUser.setPassword("encryptedPassword123");
        mockUser.setActive(true);
        mockUser.setToken("valid-token");
        mockUser.setRol("admin");

        products = List.of(
                new Product(1L, "Product1", "product1.img", "Description1", 19.99, 20),
                new Product(2L, "Product2", "product2.img", "Description2", 29.99, 15));

        mockEditProductRequest = new EditProductRequest(
                "EditedProduct",
                "edited.img",
                "Edited description",
                25.99,
                10);
    }

    @Test
    void testGetAll_Success() {
        String token = "valid-token";
        String email = "example-user@email.com";

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAll("Bearer " + token);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jwtUtils).getEmailFromToken(token);
        verify(userRepository).findByEmail(email);
        verify(productRepository).findAll();
    }

    @Test
    void testEditProductById_Success() {
        String token = "valid-token";
        String email = "example-user@email.com";
        Long productId = 1L;

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(productId)).thenReturn(Optional.of(products.get(0)));
        when(productRepository.save(any(Product.class))).thenReturn(products.get(0));

        Product result = productService.editProductById("Bearer " + token, productId, mockEditProductRequest);

        assertNotNull(result);
        assertEquals(mockEditProductRequest.getName(), result.getName());
        verify(jwtUtils).getEmailFromToken(token);
        verify(userRepository).findByEmail(email);
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void testDeleteProductById_Success() {
        String token = "valid-token";
        String email = "example-user@email.com";
        Long productId = 1L;

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(productId)).thenReturn(Optional.of(products.get(0)));

        productService.deleteProductById("Bearer " + token, productId);

        verify(jwtUtils).getEmailFromToken(token);
        verify(userRepository).findByEmail(email);
        verify(productRepository).findById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testGetAll_UserNotFound() {
        String token = "invalid-token";
        String email = "nonexistent@email.com";

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getAll("Bearer " + token));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testEditProductById_Unauthorized() {
        String token = "valid-token";
        String email = "example-user@email.com";
        Long productId = 1L;

        mockUser.setRol("user");

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        assertThrows(ForbiddenException.class,
                () -> productService.editProductById("Bearer " + token, productId, mockEditProductRequest));
        verify(userRepository).findByEmail(email);
    }
}
