

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewAlbumNameController extends AnchorPane {
	@FXML
	private TextField newNameField;
	
	@FXML
	public Button closeButton;
	
	AlbumManager albumManager;
	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	
	NewAlbumNameController(ListFileManager listFileController, AlbumManager albumManager,ListView<String> interfaceAlbumList){
        this.albumManager = albumManager;
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
        
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlbumNewNameFX.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	private boolean isValidName(String target){
	    CharSequence[] invalidCharacters = {"\\","|","<",">","?",":","*","\"","/" };
	    if(target.isEmpty())return false;
	    
	    // Check if any Invalid Characters
	    for(int i=0;i<9;i++){
    		if(target.contains(invalidCharacters[i])){
    			System.out.println("Invalid Characters!");
    			return false;
	    	}
    	}
	    
	    // Check if its (NOT) only Spaces. (Filtering Algorithm).
	    CharSequence space = " ";
    	CharSequence empty = "";
	    target = target.replace(space, empty);
	    if(target.isEmpty()){
	    	System.out.println("Empty Album Name.");
	    	return false;
	    }
	    
    	return true;
	}
	
	
	
	@FXML
	protected void createAlbum() throws IOException{
		Boolean validName = true;
		String newAlbumName = newNameField.getText();
	    Stage stage = (Stage) newNameField.getScene().getWindow();
	    
	    validName = isValidName(newAlbumName);
	    if(validName){
	    	albumManager.addAlbum(newAlbumName);
	    	listFileController.addPhotoToAlbumList(newAlbumName);
	    	interfaceAlbumList.setItems(listFileController.getAllAlbums(false));
	    	System.out.println("[New Album Name Interface] [Button Album Name] Album Successfully Added.");
	    	stage.close();
	    }
	    newNameField.clear();
	    System.out.println("[New Album Name Interface] [Button Album Name] Album Failed to be Added. Invalid FileName");
    	

	    
	    
	}
	
}
