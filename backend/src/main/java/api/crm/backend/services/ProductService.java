package api.crm.backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.models.Product;
import api.crm.backend.models.User;
import api.crm.backend.repository.ProductRepository;
import api.crm.backend.repository.UserRepository;
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
}
