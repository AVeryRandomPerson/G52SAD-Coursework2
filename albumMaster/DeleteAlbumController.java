package albumMaster;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeleteAlbumController extends AnchorPane {

	@FXML
	private Button confirmButton;
		
	@FXML
	private Label targetNameLabel;
	
	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	AlbumManager albumManager;
	
	DeleteAlbumController(ListFileManager listFileController,ListView<String> interfaceAlbumList){
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
        albumManager = listFileController.albumManager;
        
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlbumPromptDeletionFX.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	public Label getTargetNameLabel(){
		return targetNameLabel;
	}
	
	@FXML
	protected void deleteAlbum() throws IOException, InterruptedException{
			Stage stage = (Stage) confirmButton.getScene().getWindow();
			String target = MainInterfaceController.selectedAlbum;
	    	listFileController.albumManager.removeAlbumByName(MainInterfaceController.selectedAlbum);
	    	listFileController.removeAlbumFromSystem(target);
	    	ArrayList<String> patchedList = listFileController.albumManager.getAllAlbumNames();
	    	patchedList.remove(target);
	    	ObservableList<String> newList = FXCollections.observableArrayList(patchedList);
	    	interfaceAlbumList.setItems(newList);

	    	
	    	stage.close();
	    	System.out.println("[Album Deletion Interface] [Button Confirm] Album Successfully Deleted.");
	}
	
}
