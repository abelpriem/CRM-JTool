package api.crm.backend.dto.clients;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EditClientRequestTest {

    @Test
    void testSettersAndGetters() {
        EditClientRequest editClientRequest = new EditClientRequest();

        editClientRequest.setName("Example");
        editClientRequest.setEmail("example@email.com");
        editClientRequest.setCompany("Google");
        editClientRequest.setPhone("600112233");

        assertEquals("Example", editClientRequest.getName());
        assertEquals("example@email.com", editClientRequest.getEmail());
        assertEquals("Google", editClientRequest.getCompany());
        assertEquals("600112233", editClientRequest.getPhone());
    }
}
