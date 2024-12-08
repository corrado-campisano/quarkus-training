package off.itgoes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springweb/eurodollar")
public class EuroDollarController {

	@GetMapping("/conversion")
	public Conversion conversion(@RequestParam(value = "euro") float euro) {
		return new Conversion(euro * 1.19f, euro);
	}
	
	public static class Conversion {
		
		private float euro;
		private float dollar;
		
		public Conversion(float dollar, float euro) {
			this.euro = euro;
			this.dollar = dollar;
		}
						
		public float getEuro() {
			return euro;
		}
		
		public float getDollar() {
			return dollar;
		}
	}
}
