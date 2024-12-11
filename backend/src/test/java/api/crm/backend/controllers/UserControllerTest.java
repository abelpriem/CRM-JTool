package api.crm.backend.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.security.auth.login.CredentialException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import api.crm.backend.config.SecurityConfig;
import api.crm.backend.config.jwt.JwtUtils;
import api.crm.backend.dto.auth.RegisterRequest;
import api.crm.backend.models.User;
import api.crm.backend.services.UserService;
import api.crm.backend.utils.errors.ConflictException;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtils jwtUtils;

    @InjectMocks
    RegisterRequest mockRegisterRequest;

    @Test
    void testCreateUser_Success() throws Exception {
        mockRegisterRequest = new RegisterRequest("Example", "example@email.com", "password1234",
                "password1234");

        when(userService.create(mockRegisterRequest)).thenReturn(new User());

        mockMvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(
                        "{\"username\":\"Example\",\"email\":\"example@email.com\",\"password\":\"password1234\",\"confirmPassword\":\"password1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Usuario creado correctamente"));

        // verify(userService, times(1)).create(mockRegisterRequest);
    }

    @Test
    void testCreateUser_Conflict() throws Exception {
        mockRegisterRequest = new RegisterRequest("Example", "example@email.com", "password1234",
                "password1234");

        doThrow(new ConflictException("El nombre de usuario ya existe")).when(userService.create(mockRegisterRequest));

        mockMvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(
                        "{\"username\":\"Example\",\"email\":\"example@email.com\",\"password\":\"password1234\",\"confirmPassword\":\"password1234\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("El nombre de usuario ya existe"));

        // verify(userService, times(1)).create(mockRegisterRequest);
    }

    @Test
    void testCreateUser_Credentials() throws Exception {
        mockRegisterRequest = new RegisterRequest("Example", "example@email.com", "password1234",
                "password1235");

        doThrow(new CredentialException("Las contraseñas deben coincidir")).when(userService
                .create(mockRegisterRequest));

        mockMvc.perform(post("/api/users/register")
                .contentType("application/json")
                .content(
                        "{\"username\":\"Example\",\"email\":\"example@email.com\",\"password\":\"password1234\",\"confirmPassword\":\"password1235\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Las contraseñas deben coincidir"));

        // verify(userService, times(1)).create(mockRegisterRequest);
    }
}
