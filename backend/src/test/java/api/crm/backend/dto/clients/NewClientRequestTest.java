package api.crm.backend.dto.clients;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NewClientRequestTest {

    @Test
    void testSettersAndGetters() {
        NewClientRequest newClientRequest = new NewClientRequest();

        newClientRequest.setName("Example");
        newClientRequest.setEmail("example@email.com");
        newClientRequest.setCompany("Google");
        newClientRequest.setPhone("600112233");

        assertEquals("Example", newClientRequest.getName());
        assertEquals("example@email.com", newClientRequest.getEmail());
        assertEquals("Google", newClientRequest.getCompany());
        assertEquals("600112233", newClientRequest.getPhone());
    }
}
