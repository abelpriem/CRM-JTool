package api.crm.backend.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.repository.UserRepository;
import api.crm.backend.services.UserService;

class UserTest {

    @Mock
    private UserRepository userRepository; // Simulamos el repositorio

    @InjectMocks
    private UserService userService; // El servicio que estamos probando
    private RegisterRequest mockRegisterRequest;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockRegisterRequest = new RegisterRequest("john_doe", "john.doe@example.com", "password123", "password123");
    }

    @Test
    void testCreateUser() {
        // Simular el comportamiento de la encriptación con bcrypt
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword123");

        // Crear un objeto User simulado
        User mockUser = new User();

        mockUser.setUsername(mockRegisterRequest.getUsername());
        mockUser.setEmail(mockRegisterRequest.getEmail());
        mockUser.setPassword("encryptedPassword123"); // Contraseña cifrada
        mockUser.setActive(false);

        // Simular el comportamiento del repositorio para cuando se guarda un nuevo
        // usuario
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Llamar al método del servicio
        User createdUser = userService.create(mockRegisterRequest);

        // Verificar que el repositorio fue llamado correctamente
        verify(userRepository, times(1)).save(any(User.class));

        // Verificar que el objeto devuelto es el esperado
        assertEquals("john_doe", createdUser.getUsername());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertFalse(createdUser.getActive());
    }
}
