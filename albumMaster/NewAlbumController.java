package albumMaster;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewAlbumController extends AnchorPane {
	@FXML
	private TextField newNameField;	
	ListFileManager listFileController;
	ListView<String> interfaceAlbumList;
	AlbumManager albumManager;
	
	NewAlbumController(ListFileManager listFileController,ListView<String> interfaceAlbumList){
        this.listFileController = listFileController;
        this.interfaceAlbumList = interfaceAlbumList;
        albumManager = listFileController.albumManager;
        
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewAlbumFX.fxml"));
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
    	String processedChar;
    	processedChar = target.replace(space, empty);
	    if(processedChar.isEmpty()){
	    	System.out.println("Empty Album Name.");
	    	return false;
	    }
	    
	    // Check if an album of the same name Exists.
	    AlbumList allAlbums;
	    allAlbums = albumManager.allAlbums;
	    Album currentAlbum = allAlbums.headAlbum;
	    
	    while(currentAlbum != null){
	    	if(target.equals(currentAlbum.albumName)) {	
	    	System.out.println("File of Same Name Already Exists!");
	    	return false;
	    	}
	    	currentAlbum = currentAlbum.nextAlbum;
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
	    	listFileController.albumManager.addAlbum(newAlbumName);
	    	listFileController.addAlbumToSystem(newAlbumName);
	    	ArrayList<String> patchedList = listFileController.albumManager.getAllAlbumNames();
	    	ObservableList<String> newList = FXCollections.observableArrayList(patchedList);
	    	interfaceAlbumList.setItems(newList);
	    	System.out.println("[New Album Name Interface] [Button Album Name] Album Successfully Added.");
	    	stage.close();
	    }else{
	    	newNameField.clear();
		    System.out.println("[New Album Name Interface] [Button Album Name] Album Failed to be Added. Invalid FileName");	
	    }
	    
    	

	    
	    
	}
	
}
