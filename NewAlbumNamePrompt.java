

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class NewAlbumNamePrompt extends Application {

	AlbumManager albumManager;
	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	
	NewAlbumNamePrompt(ListFileManager listFileController, AlbumManager albumManager,ListView<String> interfaceAlbumList){
        this.albumManager = albumManager;
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
		}
		
		public static void main(String args[]){
			launch(args);
		}

		@Override
		public void start(Stage albumNamerWindow) throws Exception{

			NewAlbumNameController albumNamingInterface = new NewAlbumNameController(listFileController, albumManager,interfaceAlbumList);
			Scene askNewAlbumNameScreen = new Scene(albumNamingInterface,200,120);
			albumNamerWindow.setTitle("New Album");
			albumNamerWindow.setScene(askNewAlbumNameScreen);
			albumNamerWindow.show();
		}
		


}

