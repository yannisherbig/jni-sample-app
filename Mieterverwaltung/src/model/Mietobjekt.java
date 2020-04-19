package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mietobjekt {
	
	private final SimpleLongProperty mietobjektID;
	private final SimpleIntegerProperty flaecheInQuadratmetern;
	private final SimpleDoubleProperty monatsmieteInEuro;
	private final SimpleIntegerProperty baujahr;
	private final SimpleStringProperty lage;
	
	public Mietobjekt(long mietobjektID, int flaecheInQuadratmetern, 
			double monatsmieteInEuro, int baujahr, String lage) {
		super();
		this.mietobjektID = new SimpleLongProperty(mietobjektID);
		this.flaecheInQuadratmetern = new SimpleIntegerProperty(flaecheInQuadratmetern);
		this.monatsmieteInEuro = new SimpleDoubleProperty(monatsmieteInEuro);
		this.baujahr = new SimpleIntegerProperty(baujahr);
		this.lage = new SimpleStringProperty(lage);
	}
	
	public Long getMietobjektID() {
		return mietobjektID.get();
	}
	
	public Integer getFlaecheInQuadratmetern() {
		return flaecheInQuadratmetern.get();
	}
	
	public Double getMonatsmieteInEuro() {
		return monatsmieteInEuro.get();
	}
	
	public Integer getBaujahr() {
		return baujahr.get();
	}
	
	public String getLage() {
		return lage.get();
	}
	
}
