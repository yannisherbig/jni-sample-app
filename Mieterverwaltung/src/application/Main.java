package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import repository.DBSingletonConnection;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Main.fxml"));
		Parent root = (Parent) loader.load();
		MainController controller = (MainController) loader.getController();
		controller.setStageAndSetupListeners(primaryStage, root); 
	}
	
	@Override
	public void stop(){
	    try {
			DBSingletonConnection.getConnection().close();
		} catch (Exception e) { 
			System.out.println(e.getMessage()); 
		}
	}
	
	public static void main(String... args){
		//launch ruft start() auf
		launch(args);
	}

}