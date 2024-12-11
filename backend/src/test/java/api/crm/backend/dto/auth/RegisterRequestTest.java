package api.crm.backend.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RegisterRequestTest {

    @Test
    void testGettersAndSetters() {
        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setUsername("Example");
        registerRequest.setEmail("example@email.com");
        registerRequest.setPassword("password1234");
        registerRequest.setRepeatPassword("password1234");

        assertEquals("Example", registerRequest.getUsername());
        assertEquals("example@email.com", registerRequest.getEmail());
        assertEquals("password1234", registerRequest.getPassword());
        assertEquals("password1234", registerRequest.getRepeatPassword());
    }
}
