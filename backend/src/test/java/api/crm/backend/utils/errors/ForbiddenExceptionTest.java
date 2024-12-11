package api.crm.backend.utils.errors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ForbiddenExceptionTest {
    @Test

    void testForbiddenExceptionTest() {
        String message = "Usuario no autorizado. Solo los ADMIN pueden realizar esta acción";

        assertThatThrownBy(() -> {
            throw new ForbiddenException(message);
        }).isInstanceOf(ForbiddenException.class).hasMessage(message);
    }
}
