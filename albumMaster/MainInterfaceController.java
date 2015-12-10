package albumMaster;

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainInterfaceController extends BorderPane{
	public static String selectedAlbum;
	public static String selectedLibraryPhoto;
	
	FileChooser pictureSelector = new FileChooser();
	AlbumManager albumManager = new AlbumManager();
	ListFileManager listFileController = new ListFileManager(albumManager);
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageView.fxml"));
	ObservableList<String> liveAlbumList;
	ObservableList<String> liveLibraryList;
	ObservableList<String> liveAlbumPhotos;
	
	@FXML
	private ListView<String> interfaceAlbumList;
	@FXML
	private ImageView imageDisplayer;	
	@FXML
	private ListView<String> interfacePhotoLibrary;
	@FXML
	private ListView<String> albumPhotoList;
	@FXML
	private Label selectedAlbumLabel;

	
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
	}
	
	public void initialize(){
		try {
			liveAlbumList = listFileController.getAllAlbums(true);
			liveLibraryList = listFileController.refreshLibraryList(true);
			interfaceAlbumList.setItems(liveAlbumList);
			interfacePhotoLibrary.setItems(liveLibraryList);
			
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
    @FXML
    protected void addAlbumPrompt() throws Exception{
    	System.out.println("[Main Interface] [Button Add Album] Attempting to Add Album.");
    	Stage albumNamerWindow = new Stage();
    	NewAlbumNamePrompt newNameAsker = new NewAlbumNamePrompt(listFileController,interfaceAlbumList);
    	newNameAsker.start(albumNamerWindow);
    }
    @FXML
    protected void acquireAlbum(){
    	selectedAlbum = interfaceAlbumList.getSelectionModel().getSelectedItem();
    	if(selectedAlbum != null )selectedAlbumLabel.setText(selectedAlbum);
    }
    @FXML
    protected void acquireLibraryPhoto(){
    	selectedLibraryPhoto = interfacePhotoLibrary.getSelectionModel().getSelectedItem();
    	Image updateDisplay = new Image("file:Data/Library/"+selectedLibraryPhoto);
    	imageDisplayer.setImage(updateDisplay);
    }    
    
    
    @FXML
    protected void deleteAlbumPrompt() throws Exception{
    	if(selectedAlbum != null) {
        	System.out.println("[Main Interface] [Button Delete Album] Attempting to Delete Album.");
        	Stage albumDeletion = new Stage();
        	AlbumDeletionPrompt albumDeletionPrompter = new AlbumDeletionPrompt(listFileController,interfaceAlbumList);
        	albumDeletionPrompter.start(albumDeletion);
    	}
    }
    @FXML
    protected void renameAlbumPrompt(){
    	System.out.println("Album Renamed");
    }
    @FXML
    protected void showPictureList(){
    	System.out.println("Displaying Photo List");
    }    
    
    @FXML
    protected void addPhotoPrompt() throws IOException{
    	File selectedFile = pictureSelector.showOpenDialog(null);
    	File newFile = new File("Data/Library/"+selectedFile.getName());
    	selectedFile.renameTo(newFile);
    	System.out.println(selectedFile.getName());
    	System.out.println(newFile.getPath());
    	Image displayImage = new Image("file:Data/Library/"+selectedFile.getName());
    	liveLibraryList.add(selectedFile.getName());
    	
    	interfacePhotoLibrary.setItems(liveLibraryList);
    	
    	imageDisplayer.setImage(displayImage);
    	listFileController.refreshLibraryList(false);
    }    
    @FXML
    protected void removePhotoPrompt(){
    	System.out.println("Removed Photo");
    }    
    @FXML
    protected void addPhotoToAlbum() throws IOException{
    	Album targetAlbum = albumManager.getSelectedAlbum(selectedAlbum);
    	
    	if(targetAlbum.albumName.equals("!\\DeadAlbum")){
    		System.out.println("Something is wrong! - > Unable to acquire album selected in Album List Data.");
    	}
    	else{
    		System.out.println("[Main Interface] [Button Add Photo] Attempting to Add Photo.");
    		PhotoManager photoManager = targetAlbum.photoManager;
    		Photo newPhoto = new Photo(selectedLibraryPhoto);
    		System.out.println("Selected Album is : " + selectedAlbum);
    		listFileController.addPhotoToAlbum(selectedAlbum, selectedLibraryPhoto);
    		
    		liveAlbumPhotos = listFileController.getPhotosListOfAlbum(selectedAlbum);
    		albumPhotoList.setItems(liveAlbumPhotos);  	
    		albumPhotoList.setCellFactory(listView -> new ListCell<String>() {
                private final ImageView imageView = new ImageView();
                {
                    imageView.setFitHeight(96);
                    imageView.setFitWidth(96);
                    imageView.setPreserveRatio(true);
                }
                @Override
                public void updateItem(String str, boolean empty) {
                    super.updateItem(str, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                    	System.out.println("updated!");
                        imageView.setImage(new Image("file:Data/Library/"+str));
                        if(imageView == null) System.out.println("no Image!");
                        setGraphic(imageView);
                    }
                }
            });

    		photoManager.addPhoto(newPhoto);
    		Image updateViewer = new Image("file:Library/"+selectedLibraryPhoto);
    		imageDisplayer.setImage(updateViewer);
    		
    		System.out.println("[Main Interface] [Button Add Photo->Album] Added Photo to Album.");
    	}
    }
    @FXML
    protected void removePhotoFromAlbum(){
    	Album targetAlbum = albumManager.getSelectedAlbum(selectedAlbum);
    	
    	if(targetAlbum.albumName.equals("!\\DeadAlbum")){
    		System.out.println("Something is wrong! - > Unable to acquire album selected in Album List Data.");
    	}
    	else{
    		PhotoManager photoManager = targetAlbum.photoManager;
    		System.out.println("Selected Album is : " + selectedAlbum);
    	}
    		
    	
    }

}
