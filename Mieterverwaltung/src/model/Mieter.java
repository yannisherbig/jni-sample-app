package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Mieter {
	private final SimpleLongProperty mieterID;
	private final SimpleStringProperty name;
	private final SimpleStringProperty vorname;
	private final SimpleIntegerProperty alter;
	private final SimpleIntegerProperty telefonnummer;
	private final SimpleLongProperty mietobjektIDFK;
	
	public Mieter(long mieterID, String name, String vorname, int alter, int telefonnummer, long mietobjektIDFK) {
		super();
		this.mieterID = new SimpleLongProperty(mieterID);
		this.name = new SimpleStringProperty(name);
		this.vorname = new SimpleStringProperty(vorname);
		this.alter = new SimpleIntegerProperty(alter);
		this.telefonnummer = new SimpleIntegerProperty(telefonnummer);
		this.mietobjektIDFK = new SimpleLongProperty(mietobjektIDFK);
	}

	public Long getMieterID() {
		return mieterID.get();
	}

	public String getName() {
		return name.get();
	}

	public String getVorname() {
		return vorname.get();
	}

	public Integer getAlter() {
		return alter.get();
	}

	public Integer getTelefonnummer() {
		return telefonnummer.get();
	}

	public Long getMietobjektIDFK() {
		return mietobjektIDFK.get();
	}
	
}
