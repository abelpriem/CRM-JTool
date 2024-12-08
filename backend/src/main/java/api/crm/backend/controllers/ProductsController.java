package api.crm.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.models.Product;
import api.crm.backend.services.ProductService;
import api.crm.backend.utils.errors.NotFoundException;

@RestController
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    ProductService productService;
    JwtUtils jwtUtils;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<Product> listProducts = productService.getAll(authorizationHeader);
            return ResponseEntity.ok(listProducts);
        } catch (NotFoundException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
