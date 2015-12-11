package albumMaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class NewAlbumPrompt extends Application {

	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	
	NewAlbumPrompt(ListFileManager listFileController,ListView<String> interfaceAlbumList){
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
		}
		
		public static void main(String args[]){
			launch(args);
		}

		@Override
		public void start(Stage albumNamerWindow) throws Exception{

			NewAlbumController albumNamingInterface = new NewAlbumController(listFileController,interfaceAlbumList);
			Scene askNewAlbumNameScreen = new Scene(albumNamingInterface,200,120);
			albumNamerWindow.setTitle("New Album");
			albumNamerWindow.setScene(askNewAlbumNameScreen);
			albumNamerWindow.show();
		}
		


}

