package api.crm.backend.dto.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    @Test
    void testGettersAndSetters() {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("example@email.com");
        loginRequest.setPassword("password1234");

        assertEquals("example@email.com", loginRequest.getEmail());
        assertEquals("password1234", loginRequest.getPassword());
    }
}
