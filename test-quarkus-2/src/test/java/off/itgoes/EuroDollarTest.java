package off.itgoes;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class EuroDollarTest {

	@Test
	public void testEuroDollarEndpoint() {
		
		//@formatter:off
		RestAssured
			.given()
			.when()
				.get("/springweb/eurodollar/conversion?euro=2.5")
			.then()
				.statusCode(200)
			.and()
				.body(CoreMatchers.is("{\"dollar\":2.9750001,\"euro\":2.5}"));
		//@formatter:on
	}
}
