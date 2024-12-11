package api.crm.backend.dto.profile;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChangePasswordRequestTest {

    @Test
    void testGettersAndSetters() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();

        changePasswordRequest.setPassword("password1234");
        changePasswordRequest.setNewPassword("newPassword1234");
        changePasswordRequest.setConfirmPassword("newPassword1234");

        assertEquals("password1234", changePasswordRequest.getPassword());
        assertEquals("newPassword1234", changePasswordRequest.getNewPassword());
        assertEquals("newPassword1234", changePasswordRequest.getConfirmPassword());
    }
}
