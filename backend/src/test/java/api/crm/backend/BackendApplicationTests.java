package api.crm.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		try {
			BackendApplication.main(new String[] {});
		} catch (Exception e) {
			assert false : "Exception: " + e.getMessage();
		}
	}

}
