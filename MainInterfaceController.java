

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainInterfaceController extends BorderPane{
	FileChooser pictureSelector = new FileChooser();
	AlbumManager albumManager = new AlbumManager();
	ListFileManager listFileController = new ListFileManager(albumManager);
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageView.fxml"));
	ObservableList<String> liveAlbumList;
	
	@FXML
	private ListView<String> interfaceAlbumList;
	
	MainInterfaceController(){
        
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        pictureSelector.setTitle("Choose photo for your album.");
        pictureSelector.getExtensionFilters().addAll(
        		new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("All Files", "*.*")
        		);
        initialize();
        
        
	}
	
	public void initialize(){
		try {
			liveAlbumList = listFileController.getAllAlbums(false);
			interfaceAlbumList.setItems(liveAlbumList);
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
    @FXML
    protected void addAlbum() throws Exception{
    	System.out.println("[Main Interface] [Button Add Album] Attempting to Add Album.");
    	Stage albumNamerWindow = new Stage();
    	
    	NewAlbumNamePrompt newNameAsker = new NewAlbumNamePrompt(listFileController, albumManager,interfaceAlbumList);
    	newNameAsker.start(albumNamerWindow);
  	
    }
    @FXML
    protected void deleteAlbum(){
    	albumManager.removeLastAlbum();
    	System.out.println("Album Deleted");
    }
    @FXML
    protected void renameAlbum(){
    	System.out.println("Album Renamed");
    }
    @FXML
    protected void showPictureList(){
    	System.out.println("Displaying Photo List");
    }    
    
    @FXML
    protected void addPhoto(){
    	File selectedFile = pictureSelector.showOpenDialog(null);
    	if(selectedFile != null)System.out.println(selectedFile.getPath());
    	System.out.println("Added Photo");
    }    
    @FXML
    protected void removePhoto(){
    	System.out.println("Removed Photo");
    }    

}
