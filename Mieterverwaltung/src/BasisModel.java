import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



//BasisModel dient als Schnittstelle zwischen Anwendung und "Daten" (Datenbank, Files etc.)
//hier: singleton prinzip
public final class BasisModel {
	
	private static BasisModel instance;
	
	//Konstruktor der Klasse
	public BasisModel() {
		
	}

	//R�ckgabe der eigenen Instanz (falls keine existiert, dann erstelle eine und gebe diese zur�ck)
	//this. nicht m�glich, da vom Typ static 
	public static BasisModel getInstance() {
		if(instance == null) {
			instance = new BasisModel();
		}
		return instance;
	}
}