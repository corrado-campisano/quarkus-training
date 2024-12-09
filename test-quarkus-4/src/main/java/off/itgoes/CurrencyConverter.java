package off.itgoes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/currencyService")
public class CurrencyConverter {

	private float euroDollarRatio = 1.19f;
	
	@GET
	@Path("euro/{euro}")
	@Produces(MediaType.APPLICATION_JSON)
	public float dollarToEuro(@PathParam(value = "euro") float euro) {
		Conversion conversion = new Conversion(euro * euroDollarRatio, euro);
		return  conversion.getDollar();
	}
	
	@GET
	@Path("dollar/{dollar}")
	@Produces(MediaType.APPLICATION_JSON)
	public float euroToDollar(@PathParam(value = "dollar") float dollar) {
		Conversion conversion = new Conversion(dollar, dollar / euroDollarRatio);
		return  conversion.getEuro();
	}
}
