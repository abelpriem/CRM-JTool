package api.crm.backend.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.crm.backend.models.User;

public class UserResponseDTOTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();

        user.setUserId(1L);
        user.setUsername("Example");
        user.setEmail("example@email.com");
        user.setCompany("Google");
        user.setPhone("600112233");
    }

    @Test
    void testGettersAndSetters() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(user);

        userResponseDTO.setUserId(user.getUserId());
        userResponseDTO.setUsername("ExampleModify");
        userResponseDTO.setEmail("exampleModify@email.com");
        userResponseDTO.setCompany("Apple");
        userResponseDTO.setPhone("611998877");

        assertEquals(1L, userResponseDTO.getUserId());
        assertEquals("ExampleModify", userResponseDTO.getUsername());
        assertEquals("exampleModify@email.com", userResponseDTO.getEmail());
        assertEquals("Apple", userResponseDTO.getCompany());
        assertEquals("611998877", userResponseDTO.getPhone());
    }
}
