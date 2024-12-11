package api.crm.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.products.EditProductRequest;
import api.crm.backend.models.Product;
import api.crm.backend.services.ProductService;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;
import jakarta.validation.Valid;

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

    @PatchMapping("/products/edit/{productId}")
    public ResponseEntity<Map<String, String>> editProduct(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long productId, @Valid @RequestBody EditProductRequest editProductRequest) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            productService.editProductById(authorizationHeader, productId, editProductRequest);

            Map<String, String> response = Map.of("message", "Producto editado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (SecurityException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (ForbiddenException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/products/delete/{productId}")
    public ResponseEntity<Map<String, String>> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long productId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            productService.deleteProductById(authorizationHeader, productId);

            Map<String, String> response = Map.of("message", "Cliente eliminado correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (SecurityException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (ForbiddenException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
    }
}
