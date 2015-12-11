package albumMaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RenameAlbumPrompt extends Application {

	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	
	RenameAlbumPrompt(ListFileManager listFileController,ListView<String> interfaceAlbumList){
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
		}
		
		public static void main(String args[]){
			launch(args);
		}

		@Override
		public void start(Stage albumNamerWindow) throws Exception{

			RenameAlbumController albumNamingInterface = new RenameAlbumController(listFileController,interfaceAlbumList);
			Scene askNewAlbumNameScreen = new Scene(albumNamingInterface,200,120);
			albumNamerWindow.setTitle("Rename Album");
			albumNamerWindow.setScene(askNewAlbumNameScreen);
			albumNamerWindow.show();
		}
		


}

