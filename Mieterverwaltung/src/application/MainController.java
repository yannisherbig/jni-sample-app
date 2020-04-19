package application;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Mieter;
import model.Mietobjekt;
import repository.CRUDRepository;

//BasisControl dient der Steuerung der Application 
public class MainController implements Initializable {
	
	static {
		System.loadLibrary("mieterverwaltung");
	}
	
	private CRUDRepository repo;
		
	@FXML
	private TableView<Mieter> mieterTabelle;
	@FXML private TableColumn<Mieter, Long> mieterID;
	@FXML private TableColumn<Mieter, String> name;
	@FXML private TableColumn<Mieter, String> vorname;
	@FXML private TableColumn<Mieter, Integer> alter;
	@FXML private TableColumn<Mieter, Integer> telefonnummer;
	@FXML private TableColumn<Mieter, Long> mietobjektIDFK;
	
	@FXML
	private TableView<Mietobjekt> mietobjektTabelle;	
	@FXML private TableColumn<Mietobjekt, Long> mietobjektID;
	@FXML private TableColumn<Mietobjekt, Integer> flaecheInQuadratmetern;
	@FXML private TableColumn<Mietobjekt, Double> monatsmieteInEuro;
	@FXML private TableColumn<Mietobjekt, Integer> baujahr;
	@FXML private TableColumn<Mietobjekt, String> lage;
	
	@FXML private ComboBox<Long> mietobjektIDCB;
	
	@FXML private LineChart<Number, Number> lineChart;
	
	@FXML private TextField flaecheInQuadratmeternTextField;
	@FXML private TextField monatsmieteInEuroTextField;
	@FXML private TextField baujahrTextField;
	@FXML private TextField lageTextField;
		
	@FXML private TextField nameTextField;
	@FXML private TextField vornameTextField;
	@FXML private TextField alterTextField;
	@FXML private TextField telefonnummerTextField;
	@FXML private TextField mietobjektIDTextField;
	
	@FXML private Button saveMieterButton;
	@FXML private Button saveMietobjektButton;
	@FXML private Button deleteMieterButton;
	@FXML private Button deleteMietobjektButton;
	
	@FXML private Button berechneGesamteMieteinnahmenButton;
	@FXML private Label gesamteMieteinnahmenLabel;
	
	@FXML private Label softwareLabel;
	@FXML private Label softwareLabelCopy;
	@FXML private Label neueMieterLabel;
	@FXML private Label neueMietobjekteLabel;
	
	public ObservableList<Mieter> mieterListe;
	public ObservableList<Mietobjekt> mietobjektListe;
	
	private AtomicInteger tick = new AtomicInteger(0);  // Counter, representing the time in the X-Axis of LineChart
	
	@FXML
	public void processNewMieter(ActionEvent event) {
		try {
			String name = nameTextField.getText();
			String vorname = vornameTextField.getText();
			int alter = Integer.parseInt(alterTextField.getText()); 
			int telefonnummer = Integer.parseInt(telefonnummerTextField.getText());
			long mietobjektID = Long.parseLong(mietobjektIDTextField.getText());
			if(name.equals("") || vorname.equals("") || alter < 0 || alter > 150)
				throw new Exception("Ungültige Eingaben");
			Mietobjekt m = repo.findMietobjektByID(mietobjektID);
			if(m == null) 
				throw new Exception("Das angegebene Mietobjekt existiert nicht!");
			long generatedMieterID = repo.insertIntoMieter(name, vorname, alter, telefonnummer, mietobjektID);
			
			// Nativer Aufruf:
			Mieter neuerMieter = erstelleMieterObjekt(generatedMieterID, name, vorname, alter, telefonnummer, mietobjektID);
			mieterListe.add(neuerMieter);
		} catch (Exception e) {
			new Alert(AlertType.WARNING, 
                    "Mieter-Erstellung fehlgeschlagen: " + e.getMessage(), 
                    ButtonType.OK).show();
		}
	}
	
	@FXML
	public void processNewMietobjekt(ActionEvent event) {
		try {
			int flaecheInQuadratmetern = Integer.parseInt(flaecheInQuadratmeternTextField.getText());
			double monatsmieteInEuro = Double.parseDouble(monatsmieteInEuroTextField.getText());
			int baujahr = Integer.parseInt(baujahrTextField.getText()); 
			String lage = lageTextField.getText();
			if(lage.equals("") || flaecheInQuadratmetern < 0 || baujahr < 0)
				throw new Exception("Ungültige Eingaben");
			long generatedMietobjektID = repo.insertIntoMietobjekt(flaecheInQuadratmetern, monatsmieteInEuro, baujahr, lage);
			
			// Nativer Aufruf mit Callback-Funktion:
			erstelleMietobjektObjekt(generatedMietobjektID, flaecheInQuadratmetern, monatsmieteInEuro, baujahr, lage, "afterMietobjektCreationCallback");
			System.out.println("Who's quicker?");
		} catch (Exception e) {
			new Alert(AlertType.WARNING, "Mietobjekt-Erstellung fehlgeschlagen: " + e.getMessage(), ButtonType.OK).show();
			System.out.println(e.getMessage());
		}
	}
	
	// Callback-Method
	private void afterMietobjektCreationCallback(Mietobjekt m) {
		mietobjektListe.add(m);
	}
	
	@FXML
	public void handleMietobjektDelete(ActionEvent event) {
		Mietobjekt m = mietobjektTabelle.getSelectionModel().getSelectedItem();
		try {
			repo.deleteMietobjekt(m.getMietobjektID());
			mietobjektTabelle.getItems().removeAll(m);
		} catch (SQLException e) {
			new Alert(AlertType.WARNING, 
					"Das ausgewählte Mietobjekt, konnte nicht gelöscht werden: " + e.getMessage(), ButtonType.OK).show();
		}
	}
	
	@FXML
	public void handleMieterDelete(ActionEvent event) {
		Mieter m = mieterTabelle.getSelectionModel().getSelectedItem();
		try {
			repo.deleteMieter(m.getMieterID());
			mieterTabelle.getItems().removeAll(m);
		} catch (SQLException e) {
			new Alert(AlertType.WARNING, 
					"Der ausgewählte Mieter, konnte nicht gelöscht werden: " + e.getMessage(), ButtonType.OK);
		}
	}
	
	@FXML
	public void handleGesamteMieteinnahnenBerechnung(ActionEvent event) {
		List<Double> mieteinnahmenListe = new ArrayList<>();
		for(Mieter mieter : this.mieterListe) {
			long mietobjektID = mieter.getMietobjektIDFK();
			Optional<Mietobjekt> mietobjektOptional = mietobjektListe.stream().filter(m -> m.getMietobjektID() == mietobjektID).findFirst();
			Mietobjekt mietobjekt = mietobjektOptional.orElseGet(null);
			if(mietobjekt != null)
				mieteinnahmenListe.add(mietobjekt.getMonatsmieteInEuro());
		}
		berechneMieteinnahmenGesamtInEuro(mieteinnahmenListe.stream().mapToDouble(i -> i).toArray(), "gesamteMieteinnahmenCallback");
		System.out.println("Who's quicker?"); // Dies sollte nach vor der Ausgabe des Ergebnis des native-Aufruf in der Konsole erscheinen
	}
	
	// Callback-Method
	private void gesamteMieteinnahmenCallback(double einnahmen) {
		System.out.println("Einnahmen: " + einnahmen);
		gesamteMieteinnahmenLabel.setText(Double.toString(einnahmen) + "€");
	}
	
	@SuppressWarnings("unchecked")
	public void onComboBoxChange() {
		lineChart.getData().clear();
		XYChart.Series<Number, Number> stromverbrauchSeries = new XYChart.Series<>();
		stromverbrauchSeries.setName("Stromverbrauch");
		XYChart.Series<Number, Number> wasserverbrauchSeries = new XYChart.Series<>();
		wasserverbrauchSeries.setName("Wasserverbrauch");
		lineChart.getData().addAll(stromverbrauchSeries, wasserverbrauchSeries);
		Thread updateThread = new Thread(() -> {
	      while (true) {
	        try {
	          Thread.sleep(1000);
	          Platform.runLater(() -> stromverbrauchSeries.getData()
	        		  .add(new XYChart.Data<>(tick.incrementAndGet(), holeAktuellenStromverbrauchInKWh())));
	          Platform.runLater(() -> wasserverbrauchSeries.getData()
	        		  .add(new XYChart.Data<>(tick.incrementAndGet(), holeAktuellenWasserverbrauchInKubikM())));
	        } catch (InterruptedException e) {
	          throw new RuntimeException(e);
	        }
	      }
	    });
	    updateThread.setDaemon(true);
	    updateThread.start();
	}
	
	
	private native float berechneKautionsKosten();
	
	private native double holeAktuellenStromverbrauchInKWh();
	
	private native double holeAktuellenWasserverbrauchInKubikM();
	
	private native void berechneMieteinnahmenGesamtInEuro(double[] mietenArr, String callbackMethodName);
	
	private native Mieter erstelleMieterObjekt(long mieterID, String name, String vorname, int alter, int telefonnummer, long mietobjektIDFK);
	
	private native void erstelleMietobjektObjekt(long mietobjektID, int flaecheInQuadratmetern, 
			double monatsmieteInEuro, int baujahr, String lage, String callbackMethodName);
	
	
	public MainController() {
		this.repo = CRUDRepository.getInstance();
		System.out.println("Kautionskosten: " + berechneKautionsKosten());
	}
	
	public float test() {
		return berechneKautionsKosten();
	}
	
	public void setStageAndSetupListeners(Stage primaryStage, Parent root) {
		Scene scene = new Scene (root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Mieterverwaltung");
		primaryStage.setScene(scene);
		primaryStage.show();  // Anzeigen der Application
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		repo.createAllTables();
		initTables();
		mietobjektIDCB.setItems(mietobjektListe.stream().mapToLong(mo -> mo.getMietobjektID())
				.boxed().sorted().collect(Collectors.toCollection(FXCollections::observableArrayList)));
	}
	
	private void initTables() {
		try {
			mietobjektListe = repo.selectAlleMietobjekte();
			mietobjektID.setCellValueFactory(new PropertyValueFactory<Mietobjekt, Long>("mietobjektID"));
			flaecheInQuadratmetern.setCellValueFactory(new PropertyValueFactory<Mietobjekt, Integer>("flaecheInQuadratmetern"));
			monatsmieteInEuro.setCellValueFactory(new PropertyValueFactory<Mietobjekt, Double>("monatsmieteInEuro"));
			baujahr.setCellValueFactory(new PropertyValueFactory<Mietobjekt, Integer>("baujahr"));
			lage.setCellValueFactory(new PropertyValueFactory<Mietobjekt, String>("lage"));
			mietobjektTabelle.setItems(mietobjektListe);
			
			mieterListe = repo.selectAlleMieter();
			mieterID.setCellValueFactory(new PropertyValueFactory<Mieter, Long>("mieterID"));
			name.setCellValueFactory(new PropertyValueFactory<Mieter, String>("name"));
			vorname.setCellValueFactory(new PropertyValueFactory<Mieter, String>("vorname"));
			alter.setCellValueFactory(new PropertyValueFactory<Mieter, Integer>("alter"));
			telefonnummer.setCellValueFactory(new PropertyValueFactory<Mieter, Integer>("telefonnummer"));
			mietobjektIDFK.setCellValueFactory(new PropertyValueFactory<Mieter, Long>("mietobjektIDFK"));
			mieterTabelle.setItems(mieterListe);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
}