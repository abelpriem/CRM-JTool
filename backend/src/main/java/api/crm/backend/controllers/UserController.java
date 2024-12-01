package api.crm.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.UserResponseDTO;
import api.crm.backend.dto.auth.LoginRequest;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;
    JwtUtils jwtUtils;

    @PostMapping("/users/register")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            userService.create(registerRequest);

            Map<String, String> response = Map.of("message", "Usuario creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/auth")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Map<String, String> response = userService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<UserResponseDTO> user = userService.getUser(authorizationHeader);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/users/clients")
    public ResponseEntity<List<UserResponseDTO>> getClients(
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            List<UserResponseDTO> clients = userService.getClients(authorizationHeader);

            System.out.println("Clientes encontrados: " + clients.size());

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
