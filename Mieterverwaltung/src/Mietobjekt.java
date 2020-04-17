
public class Mietobjekt {
	
	private long mietobjektID;
	private int flaecheInQuadratmetern;
	private double monatsmieteInEuro;
	private int baujahr;
	private String lage;
	
	public Mietobjekt(int flaecheInQuadratmetern, double monatsmieteInEuro, int baujahr, String lage, long mietobjektID) {
		super();
		this.flaecheInQuadratmetern = flaecheInQuadratmetern;
		this.monatsmieteInEuro = monatsmieteInEuro;
		this.baujahr = baujahr;
		this.lage = lage;
		this.mietobjektID = mietobjektID;
	}
	
	public int getFlaecheInQuadratmetern() {
		return flaecheInQuadratmetern;
	}
	public void setFlaecheInQuadratmetern(int flaecheInQuadratmetern) {
		this.flaecheInQuadratmetern = flaecheInQuadratmetern;
	}
	public double getMonatsmieteInEuro() {
		return monatsmieteInEuro;
	}
	public void setMonatsmieteInEuro(double monatsmieteInEuro) {
		this.monatsmieteInEuro = monatsmieteInEuro;
	}
	public int getBaujahr() {
		return baujahr;
	}
	public void setBaujahr(int baujahr) {
		this.baujahr = baujahr;
	}
	public String getLage() {
		return lage;
	}
	public void setLage(String lage) {
		this.lage = lage;
	}
	public long getMietobjektID() {
		return mietobjektID;
	}
	
}
