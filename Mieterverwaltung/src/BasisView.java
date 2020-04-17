import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;

public class BasisView {
	private BasisModel basisModel;
	private BasisControl basisControl;
	
	//GUI-Elemente erzeugen
	private Pane pane = new Pane();
	private Button btnLeseAusDatei = new Button("Lese aus Datei");
	private Button btnHolen = new Button("Holen");
	TextField datenTextfield = new TextField();
	
	//Konstruktor der Klasse
	public BasisView (BasisModel basisModel, BasisControl basisControl, Stage stage) {
		this.basisModel = basisModel;
		this.basisControl = basisControl;
		
		//Scene für primaryStage erzeugen
		Scene scene = new Scene(this.pane, 400, 200);
		stage.setTitle("PS_Uebung1");
    	stage.setScene(scene);
    	this.basisControl = basisControl;
    	this.basisModel = basisModel;
    	
    	//initialisieren der Komponenten und Listener
    	initKomponenten();
    	initListener();
	} 
	
	//Komponenten Initialisieren
	private void initKomponenten() {
		datenTextfield.setLayoutX(30);
		datenTextfield.setLayoutY(100);
		
		btnHolen.setLayoutX(250);
		btnHolen.setLayoutY(60);
		btnHolen.setPrefWidth(120);
				
		btnLeseAusDatei.setLayoutX(250);
		btnLeseAusDatei.setLayoutY(100);
		btnLeseAusDatei.setPrefWidth(120);
		pane.getChildren().addAll(datenTextfield, btnLeseAusDatei, btnHolen);
	}
	
	//Eventlistener erzeugen (Lambda Methode)
	private void initListener() {
		btnLeseAusDatei.setOnAction((event) -> {
			datenTextfield.setText(Float.toString(basisControl.test()));
		});
	}

	public void displayGesamteMieteinnahmen(double einnahmen) {
		System.out.println("Gesamteinnahmen: " + Double.toString(einnahmen));
		datenTextfield.setText("Gesamteinnahmen: " + Double.toString(einnahmen));
	}
}