package api.crm.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.auth.LoginRequest;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.models.User;
import api.crm.backend.repository.UserRepository;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    private RegisterRequest mockRegisterRequest;
    private LoginRequest mockLoginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockRegisterRequest = new RegisterRequest(
                "john_doe",
                "john.doe@example.com",
                "password123",
                "password123");

        mockLoginRequest = new LoginRequest("john.doe@example.com", "password123");

    }

    @Test
    void testCreateUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword123");

        User mockUser = new User();
        mockUser.setUsername(mockRegisterRequest.getUsername());
        mockUser.setEmail(mockRegisterRequest.getEmail());
        mockUser.setPassword("encryptedPassword123");
        mockUser.setActive(false);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = userService.create(mockRegisterRequest);
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("john_doe", createdUser.getUsername());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertFalse(createdUser.getActive());
    }

    @Test
    void testLoginUser() {
        User mockUser = new User();
        mockUser.setUsername("john_doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword("encryptedPassword123");
        mockUser.setRol("user");
        mockUser.setActive(true);

        // Simular la generación de un token JWT
        String generatedToken = "jwtGeneratedToken123";
        when(userRepository.findByEmail(mockLoginRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(mockLoginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(jwtUtils.generateToken(mockUser.getEmail())).thenReturn(generatedToken);

        // Simular el guardado del token en la base de datos
        mockUser.setToken(generatedToken);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Llamar al método login
        Map<String, String> response = userService.login(mockLoginRequest);

        // Verificar que los métodos del repositorio y jwtUtil fueron llamados
        // correctamente
        verify(userRepository, times(1)).findByEmail(mockLoginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(mockLoginRequest.getPassword(), mockUser.getPassword());
        verify(jwtUtils, times(1)).generateToken(mockUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

        // Verificar el contenido del mapa devuelto
        assertEquals("john_doe", response.get("username"));
        assertEquals("jwtGeneratedToken123", response.get("token"));
        assertEquals("user", response.get("rol"));
    }
}
