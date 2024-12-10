package api.crm.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.products.EditProductResponse;
import api.crm.backend.models.Product;
import api.crm.backend.models.User;
import api.crm.backend.repository.ProductRepository;
import api.crm.backend.repository.UserRepository;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public List<Product> getAll(String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        List<Product> result = productRepository.findAll();

        return result;
    }

    public Product editProductById(String authorizationHeader, Long productId,
            EditProductResponse editProductResponse) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        Product productToEdit = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado. Vuelva a intentarlo"));

        productToEdit.setName(editProductResponse.getName());
        productToEdit.setImage(editProductResponse.getImage());
        productToEdit.setDescription(editProductResponse.getDescription());
        productToEdit.setPrice(editProductResponse.getPrice());
        productToEdit.setStock(editProductResponse.getStock());

        return productRepository.save(productToEdit);
    }

    public void deleteProductById(String authorizationHeader, Long productId) {
        String token = authorizationHeader.substring(7);
        String emailToken = jwtUtils.getEmailFromToken(token);

        User userRequest = userRepository.findByEmail(emailToken)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado. Inténtelo de nuevo"));

        if (!userRequest.getToken().equals(token)) {
            throw new SecurityException("El token enviado no coincide con el almacenado para el usuario");
        }

        if (!"admin".equals(userRequest.getRol())) {
            throw new ForbiddenException("Usuario no autorizado. Solo los ADMIN pueden realizar esta acción");
        }

        Product productToDelete = productRepository.findById(productId)
                .orElseThrow(
                        () -> new NotFoundException("Producto no encontrado. Vuelva a intentarlo"));

        productRepository.deleteById(productToDelete.getProductId());
    }
}
