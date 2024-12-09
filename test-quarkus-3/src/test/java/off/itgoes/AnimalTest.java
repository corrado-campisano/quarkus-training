package off.itgoes;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;

@QuarkusTest
class AnimalTest {

	@Test
	void testListAllAnimals() {
		//@formatter:off
		given()
				.when().get("/animals")
				.then()
				.statusCode(200)
				.body(
						containsString("Tiger"),
						containsString("Lion"),
						containsString("Dog")
				);
		given()
				.when().delete("/animals/1")
				.then()
				.statusCode(204);
		given()
				.when().get("/animals")
				.then()
				.statusCode(200)
				.body(
						not(containsString("Tiger")),
						containsString("Lion"),
						containsString("Dog")
				);
		given()
				.when().post("/animals/name/Cat/type/Domestic")
				.then()
				.statusCode(200)
				.body(containsString("Cat"))
				.body("id", notNullValue())
				.extract().body().jsonPath().getString("id");
		given()
				.when().get("/animals")
				.then()
				.statusCode(200)
				.body(
						not(containsString("Tiger")),
						containsString("Lion"),
						containsString("Dog"),
						containsString("Cat")
				);
		//@formatter:on
	}
}