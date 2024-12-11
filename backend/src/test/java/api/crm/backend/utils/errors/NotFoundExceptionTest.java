package api.crm.backend.utils.errors;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NotFoundExceptionTest {
    @Test
    void testNotFoundExceptionMessage() {
        String message = "User not found";

        assertThatThrownBy(() -> {
            throw new NotFoundException(message);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage(message);
    }
}
