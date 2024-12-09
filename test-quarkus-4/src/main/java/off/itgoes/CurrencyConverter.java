package off.itgoes;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currencyService")
public class CurrencyConverter {

	private float euroDollarRatio = 1.19f;

	@GET
	@Path("euro/{euro}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE1")
	public float dollarToEuro(@PathParam(value = "euro") float euro) {
		Conversion conversion = new Conversion(euro * euroDollarRatio, euro);
		return conversion.getDollar();
	}

	@GET
	@Path("dollar/{dollar}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE2")
	public float euroToDollar(@PathParam(value = "dollar") float dollar) {
		Conversion conversion = new Conversion(dollar, dollar / euroDollarRatio);
		return conversion.getEuro();
	}

	// questa dovrebbe stare in un suo login-controller, visto che produce un JWT
	@GET
	@Path("{username}/{password}/jwt")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getJWT(@PathParam(value = "username") String username,
			@PathParam(value = "username") String password) {
		String jwt = null;
		
		try {
			HashMap<String, String> rolesMap = new HashMap<>();
			rolesMap.put("role 1", "ROLE1");
			rolesMap.put("role 2", "ROLE2");
			rolesMap.put("role 3", "ROLE3");
			
			List<String> groups = new ArrayList<>();
			groups.add("Group 1");
			
			jwt = generateJwt(username, password, groups, rolesMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok(jwt).build();
	}

	// queste non devono certo stare dentro il controller, che al max le usa!
	
	@ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "my-issuer-name")
	String jwtIssuer;

	private PrivateKey loadPrivateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] keyfile = CurrencyConverter.class.getClassLoader().getResourceAsStream("privatekey.pem").readAllBytes();

		String key = new String(keyfile, 0, keyfile.length).replaceAll("-----BEGIN (.*)-----", "")
				.replaceAll("-----END (.*)-----", "").replaceAll("\r\n", "").replaceAll("\n", "").trim();

		return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
	}

	private String generateJwt(String username, String password, List<String> groups, Map<String, String> roles)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		
		HashMap<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("username", username);
		claimsMap.put("password", password);
		
		JwtClaimsBuilder claims = Jwt.claims(claimsMap).subject(username + " " + password).claim("roleMAppings", roles)
				.claim("groups", groups).issuer(jwtIssuer).issuedAt(Instant.now().toEpochMilli())
				.expiresAt(Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli());
		
		PrivateKey privatekey = loadPrivateKey();
		
		return claims.jws().sign(privatekey);
	}

}
