package api.crm.backend.utils.errors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConflictExceptionTest {
    @Test

    void testConflictExceptionMessage() {
        String message = "El usuario no está activado";

        assertThatThrownBy(() -> {
            throw new ConflictException(message);
        }).isInstanceOf(ConflictException.class).hasMessage(message);
    }
}
