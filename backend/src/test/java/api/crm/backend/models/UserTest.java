package api.crm.backend.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();

        user.setUsername("Personaje");
        user.setEmail("personaje@email.com");
        user.setPassword("ejemplo1234");
        user.setCompany("Google");
        user.setPhone("611223344");
        user.setRol("user");
        user.setToken("tokenabc123");
        user.setActive(true);

        assertEquals("Personaje", user.getUsername());
        assertEquals("personaje@email.com", user.getEmail());
        assertEquals("ejemplo1234", user.getPassword());
        assertEquals("Google", user.getCompany());
        assertEquals("611223344", user.getPhone());
        assertEquals("user", user.getRol());
        assertEquals("tokenabc123", user.getToken());
        assertTrue(user.getActive());
    }

    @Test
    void testConstructorWithoutParameters() {
        User user = new User();

        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getCompany());
        assertNull(user.getPhone());
        assertNull(user.getRol());
        assertNull(user.getToken());
        assertNull(user.getActive());
    }

}
