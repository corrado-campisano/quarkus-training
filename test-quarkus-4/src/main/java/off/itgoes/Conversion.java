package off.itgoes;

public class Conversion {
	
	private float euro;
	private float dollar;
	
	public Conversion(float dollar, float euro) {
		this.euro = euro;
		this.dollar = dollar;
	}
	
	public void setEuro(float euro) { this.euro = euro;}
	public float getEuro(){ return euro;}
	
	public void setDollar(float dollar) { this.dollar = dollar;}
	public float getDollar() { return dollar; }
}