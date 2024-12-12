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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.UserResponseDTO;
import api.crm.backend.dto.auth.LoginRequest;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.dto.clients.EditClientRequest;
import api.crm.backend.dto.clients.NewClientRequest;
import api.crm.backend.dto.profile.ChangePasswordRequest;
import api.crm.backend.services.UserService;
import api.crm.backend.utils.errors.ConflictException;
import api.crm.backend.utils.errors.CredentialsException;
import api.crm.backend.utils.errors.ForbiddenException;
import api.crm.backend.utils.errors.NotFoundException;
import io.jsonwebtoken.security.SecurityException;
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
        } catch (ConflictException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (CredentialsException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/users/auth")
    public ResponseEntity<Map<String, String>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Map<String, String> response = userService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (CredentialsException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (ConflictException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/users/active/{userId}")
    public ResponseEntity<Map<String, String>> activeUser(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            boolean isActive = userService.active(authorizationHeader, userId);

            if (isActive) {
                Map<String, String> response = Map.of("Usuario activo: ", "true");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Map<String, String> response = Map.of("Usuario activo: ", "false");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (ForbiddenException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
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
        } catch (NotFoundException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException securityError) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/users/change-password")
    public ResponseEntity<Map<String, String>> changeUserPassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.changePassword(authorizationHeader, changePasswordRequest);

            Map<String, String> response = Map.of("message", "Contraseña cambiada correctamente");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (SecurityException securityError) {
            Map<String, String> errorResponse = Map.of("message", securityError.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (CredentialsException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/users/clients/new-client")
    public ResponseEntity<Map<String, String>> createClient(@RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody NewClientRequest newClientRequest) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.createClient(authorizationHeader, newClientRequest);

            Map<String, String> response = Map.of("message", "Nuevo cliente creado correctamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotFoundException securityError) {
            Map<String, String> errorResponse = Map.of("message", securityError.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (SecurityException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (ForbiddenException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        } catch (ConflictException error) {
            Map<String, String> errorResponse = Map.of("message", error.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
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

            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (NotFoundException securityError) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/users/clients/{clientId}")
    public ResponseEntity<?> getSelectedClient(
            @RequestHeader("Authorization") String authorizationHeader, @PathVariable Long clientId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserResponseDTO selectedClient = userService.getClientById(authorizationHeader, clientId);

            return new ResponseEntity<>(selectedClient, HttpStatus.OK);
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

    @PatchMapping("/users/clients/edit/{clientId}")
    public ResponseEntity<Map<String, String>> editClient(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long clientId, @Valid @RequestBody EditClientRequest editClientRequest) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.editClientById(authorizationHeader, clientId, editClientRequest);

            Map<String, String> response = Map.of("message", "Cliente editado correctamente");
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

    @DeleteMapping("/users/clients/delete/{clientId}")
    public ResponseEntity<Map<String, String>> deleteClient(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long clientId) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            userService.deleteClientById(authorizationHeader, clientId);

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
