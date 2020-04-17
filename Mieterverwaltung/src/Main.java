import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		new BasisControl(stage);
	}
	
	@Override
	public void stop(){
	    try {
			DBSingletonConnection.getConnection().close();
		} catch (Exception e) { 
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}
	}
	
	public static void main(String... args){
		//launch ruft start() auf
		launch(args);
	}

}