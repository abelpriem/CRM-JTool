package api.crm.backend.utils.errors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CredentialsExceptionTest {
    @Test

    void testCredentialsExceptionMessage() {
        String message = "Credenciales incorrectas. Inténtelo de nuevo";

        assertThatThrownBy(() -> {
            throw new CredentialsException(message);
        }).isInstanceOf(CredentialsException.class).hasMessage(message);
    }
}
