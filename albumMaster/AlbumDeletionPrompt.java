package albumMaster;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AlbumDeletionPrompt extends Application {


	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	AlbumDeletionPrompt(ListFileManager listFileController,ListView<String> interfaceAlbumList){
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
		}
		
		public static void main(String args[]){
			launch(args);
		}

		@Override
		public void start(Stage albumDeletionWindow) throws Exception{
			DeleteAlbumController albumNamingInterface = new DeleteAlbumController(listFileController,interfaceAlbumList);
			Scene askNewAlbumNameScreen = new Scene(albumNamingInterface,330,140);
			Label targetNameLabel = albumNamingInterface.getTargetNameLabel();
			targetNameLabel.setText(MainInterfaceController.selectedAlbum);
			
			albumDeletionWindow.setTitle("Delete Album ? ");
			albumDeletionWindow.setScene(askNewAlbumNameScreen);
			albumDeletionWindow.show();
		}
}