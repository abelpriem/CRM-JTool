package api.crm.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.auth.LoginRequest;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.dto.profile.ChangePasswordRequest;
import api.crm.backend.models.User;
import api.crm.backend.repository.UserRepository;
import api.crm.backend.utils.errors.ConflictException;
import api.crm.backend.utils.errors.CredentialsException;
import api.crm.backend.utils.errors.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser_Success() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");
        registerRequest.setRepeatPassword("password");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        User createdUser = userService.create(registerRequest);

        assertEquals("testuser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_UsernameConflict() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.create(registerRequest));
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setActive(true);
        mockUser.setRol("user");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtils.generateToken("test@example.com")).thenReturn("mockToken");

        Map<String, String> loginResponse = userService.login(loginRequest);

        assertEquals("mockToken", loginResponse.get("token"));
        assertEquals("user", loginResponse.get("rol"));
        verify(userRepository).save(mockUser);
    }

    @Test
    void testLogin_UserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    void testChangePassword_Success() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");
        changePasswordRequest.setConfirmPassword("newPassword");

        String token = "mockToken";
        String email = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("encodedOldPassword");
        mockUser.setToken(token);

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        User updatedUser = userService.changePassword("Bearer " + token, changePasswordRequest);

        assertEquals("encodedNewPassword", updatedUser.getPassword());
        verify(userRepository).save(mockUser);
    }

    @Test
    void testChangePassword_InvalidOldPassword() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setPassword("invalidOldPassword");
        changePasswordRequest.setNewPassword("newPassword");
        changePasswordRequest.setConfirmPassword("newPassword");

        String token = "mockToken";
        String email = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(email);
        mockUser.setPassword("encodedOldPassword");
        mockUser.setToken(token);

        when(jwtUtils.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("invalidOldPassword", "encodedOldPassword")).thenReturn(false);

        assertThrows(CredentialsException.class,
                () -> userService.changePassword("Bearer " + token, changePasswordRequest));
    }
}
