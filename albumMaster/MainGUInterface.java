package albumMaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainGUInterface extends Application{

	Stage window = new Stage();
	Scene welcomeScreen, mainScreen;
		
	public static void main(String args[]){
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception{
		MainInterfaceController mainInterface = new MainInterfaceController();
		mainScreen = new Scene(mainInterface,960,720);
		window.setTitle("Album Master");
		window.setScene(mainScreen);
		window.setResizable(false);
		window.show();
	}
	


}