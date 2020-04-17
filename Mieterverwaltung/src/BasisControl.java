import java.sql.SQLException;

import javafx.stage.Stage;

//BasisControl dient der Steuerung der Application 
public class BasisControl {
	
	static {
		System.loadLibrary("mieterverwaltung");
	}
	
	private BasisModel basisModel;
	private BasisView basisView;
	
	private native float berechneKautionsKosten();
	private native double holeStromzaehlerStandInKWh();
	private native double holeWasserverbrauchInKubikM();
	private native void berechneMieteinnahmenGesamtInEuro(int[] mietenArr, String callbackMethodName);
	
	private void gesamteMieteinnahmenCallback(double einnahmen) {
		this.basisView.displayGesamteMieteinnahmen(einnahmen);
	}
	
	
	public BasisControl(Stage stage) {
		this.basisModel = BasisModel.getInstance();
		this.basisView = new BasisView(basisModel, this, stage);
		
		//Anzeigen der Application:
		stage.show();
		System.out.println(test());
		System.out.println(holeStromzaehlerStandInKWh());
		berechneMieteinnahmenGesamtInEuro(new int[] {3,2,1}, "gesamteMieteinnahmenCallback");
		System.out.println("Who's quicker?");
		CRUDRepository repo = new CRUDRepository();
		repo.createAllTables();
		try {
			//repo.insertIntoMieter("Hans", "Meier", 23, 324242, 1);
			repo.selectAlleMieterTest();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public float test() {
		return berechneKautionsKosten();
	}
	
}