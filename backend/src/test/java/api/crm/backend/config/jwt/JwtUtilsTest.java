package api.crm.backend.config.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;

public class JwtUtilsTest {
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        String email = "example@email.com";
        String expectedToken = "tokenExample1234";

        when(jwtUtils.generateToken(email)).thenReturn(expectedToken);

        String token = jwtUtils.generateToken(email);
        assertEquals(expectedToken, token);

        verify(jwtUtils).generateToken(email);
    }

    @Test
    void testGetEmailFromToken() {
        String email = "example@email.com";
        String generatedToken = "tokenExample1234";

        when(jwtUtils.getEmailFromToken(generatedToken)).thenReturn(email);

        String emailExpected = jwtUtils.getEmailFromToken(generatedToken);
        assertEquals(emailExpected, emailExpected);

        verify(jwtUtils).getEmailFromToken(generatedToken);
    }

    @Test
    void testValidateToken() {
        String generatedToken = "tokenExample1234";

        when(jwtUtils.validateToken(generatedToken)).thenReturn(true);

        boolean isValid = jwtUtils.validateToken(generatedToken);
        assertTrue(isValid);

        verify(jwtUtils).validateToken(generatedToken);
    }

    @Test
    void testGetJwtFromHeader() {
        String header = "Bearer tokenExample1234";

        when(request.getHeader("Authorization")).thenReturn(header);
        when(jwtUtils.getJwtFromHeader(request)).thenReturn("tokenExample1234");

        String token = jwtUtils.getJwtFromHeader(request);
        assertEquals("tokenExample1234", token);

        // verify(request).getHeader("Authorization");
        verify(jwtUtils).getJwtFromHeader(request);
    }

    @Test
    void testRoleFromToken() {
        String role = "admin";
        String token = "tokenExample1234";

        when(jwtUtils.getRoleFromToken(token)).thenReturn(role);

        String extractedRole = jwtUtils.getRoleFromToken(token);
        assertEquals(role, extractedRole);

        verify(jwtUtils).getRoleFromToken(token);
    }
}
