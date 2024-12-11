package api.crm.backend.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RegisterRequestTest {
    @Test

    void testGettersAndSetters() {
        RegisterRequest registerRequest = new RegisterRequest();

        // Probar setter y luego obtener el valor con getter
        registerRequest.setUsername("Example");
        assertEquals("Example", registerRequest.getUsername());

        registerRequest.setEmail("example@email.com");
        assertEquals("example@email.com", registerRequest.getEmail());

        registerRequest.setPassword("password1234");
        assertEquals("password1234", registerRequest.getPassword());

        registerRequest.setRepeatPassword("password1234");
        assertEquals("password1234", registerRequest.getRepeatPassword());
    }
}
