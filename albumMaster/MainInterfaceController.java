package albumMaster;

import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainInterfaceController extends BorderPane{
	public static String selectedAlbum;
	public static String selectedLibraryPhoto;
	public static String selectedAlbumPhoto;
	
	FileChooser pictureSelector = new FileChooser();
	AlbumManager albumManager = new AlbumManager();
	ListFileManager listFileController = new ListFileManager(albumManager);
	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageView.fxml"));
	private ObservableList<String> liveAlbumList;
	private ObservableList<String> liveLibraryList;
	private ObservableList<String> liveAlbumPhotos;
	
	@FXML
	protected ListView<String> interfaceAlbumList;
	@FXML
	protected ImageView imageDisplayer;	
	@FXML
	protected ListView<String> interfacePhotoLibrary;
	@FXML
	protected ListView<String> albumPhotoList;
	@FXML
	protected Label selectedAlbumLabel;
	@FXML
	protected Label dateAddedLabel;
	@FXML
	protected Label imageSelectedLabel;
	@FXML
	protected Label waterMarkText;
	@FXML
	protected Pane imageContainerBG; 
	@FXML
	protected Button addAlbumButton;
	
	
	MainInterfaceController(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageView.fxml"));
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
			listFileController.saveAllAlbumPhotosToSystem();
			
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
    
    @FXML
    protected void acquireAlbum() throws IOException{
    	selectedAlbum = interfaceAlbumList.getSelectionModel().getSelectedItem();
    	if(selectedAlbum != null )selectedAlbumLabel.setText(selectedAlbum);
    	
    	albumManager.getSelectedAlbum(selectedAlbum).photoManager.printPhotos();
	    liveAlbumPhotos = listFileController.getPhotosListOfAlbum(selectedAlbum);
		updateLivePhotoList();
		updateMainDisplay();
		
		waterMarkText.setText("");
    	imageContainerBG.setStyle("-fx-background-color: #BCCDCF;");
		
    }
    @FXML
    protected void acquireLibraryPhoto(){
    	selectedLibraryPhoto = interfacePhotoLibrary.getSelectionModel().getSelectedItem();
    	Image updateDisplay = new Image("file:Data/Library/"+selectedLibraryPhoto);
    	imageDisplayer.setImage(updateDisplay);
    	
    	waterMarkText.setText("");
    	imageContainerBG.setStyle("-fx-background-color: #BCCDCF;");
    }    
    @FXML
    protected void acquireAlbumPhoto(){
    	selectedAlbumPhoto = albumPhotoList.getSelectionModel().getSelectedItem();
    	Image updateDisplay = new Image("file:Data/Library/"+selectedAlbumPhoto);
    	imageDisplayer.setImage(updateDisplay);    	
    	PhotoManager selectedManager = albumManager.getSelectedAlbum(selectedAlbum).photoManager;
    	selectedManager.getSelectedPhoto(selectedAlbumPhoto);
    	imageSelectedLabel.setText(selectedAlbumPhoto);
    	dateAddedLabel.setText(selectedManager.getSelectedPhoto(selectedAlbumPhoto).dateAdded.toString());
    	
    	waterMarkText.setText("");
    	imageContainerBG.setStyle("-fx-background-color: #BCCDCF;");
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
    protected void showPictureList(){
    	System.out.println("Displaying Photo List");
    }    
    @FXML
    protected void addAlbumPrompt() throws Exception{
    	System.out.println("[Main Interface] [Button Add Album] Attempting to Add Album.");
    	Stage newAlbumWindow = new Stage();
    	NewAlbumPrompt newAlbumAsker = new NewAlbumPrompt(listFileController,interfaceAlbumList);
    	newAlbumAsker.start(newAlbumWindow);
    }
    @FXML
    protected void addPhotoPrompt() throws IOException{
    	File selectedFile = pictureSelector.showOpenDialog(null);

    	if(selectedFile != null)
    	{
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
    }    
    @FXML
    protected void removePhotoPrompt() throws IOException, InterruptedException{
    	pictureSelector.setInitialDirectory(new File("Data\\Library"));
    	File selectedFile = pictureSelector.showOpenDialog(null);
    	if(selectedFile != null)
    	{
    		liveLibraryList.remove(selectedFile.getName());
    		selectedFile.delete();
    		
        	listFileController.refreshLibraryList(false);
        	interfacePhotoLibrary.setItems(liveLibraryList);
        	
        	Album currentAlbum = listFileController.albumManager.allAlbums.headAlbum;
        	while(currentAlbum != null){
        		removePhotoFromAlbum(currentAlbum.albumName,selectedFile.getName());
        		
        		currentAlbum = currentAlbum.nextAlbum;
        	}
    	}
    }    
    
    private boolean isValidPhotoName(PhotoManager photoManager){
    	Photo currentPhoto = photoManager.allPhotos.headPhoto;
    	while(currentPhoto != null){
    		if(selectedLibraryPhoto.equals(currentPhoto.photoName)){
    			System.out.println(currentPhoto.photoName + "VS " + selectedLibraryPhoto);
    			return false;
    		}
    		currentPhoto = currentPhoto.nextPhoto;
    	}
    	return true;
    }
    private void updateLivePhotoList() throws IOException{
    	
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
    }
    private void updateMainDisplay() throws IOException{
    	Image updateViewer = new Image("file:Library/"+selectedLibraryPhoto);
		imageDisplayer.setImage(updateViewer);   	
    }
    
    
    @FXML
    protected void addPhotoToAlbum() throws IOException{
    	Album targetAlbum = albumManager.getSelectedAlbum(selectedAlbum);
    	
    	if(targetAlbum.albumName.equals("!\\DeadAlbum")){
    		System.out.println("Something is wrong! - > Unable to acquire album selected in Album List Data.");
    	}
    	else if(selectedLibraryPhoto == null){
    		System.out.println("No Image Selected");
    	}
    	else{
    		System.out.println("[Main Interface] [Button Add Photo] Attempting to Add Photo.");
    		PhotoManager photoManager = targetAlbum.photoManager;
    		Photo newPhoto = new Photo(selectedLibraryPhoto);
    		if(isValidPhotoName(photoManager)){
    			listFileController.addPhotoToAlbum(selectedAlbum, selectedLibraryPhoto);
    	    	liveAlbumPhotos = listFileController.getPhotosListOfAlbum(selectedAlbum);
    			updateLivePhotoList();
    			updateMainDisplay();
    			photoManager.addPhoto(newPhoto);
    			System.out.println("[Main Interface] [Button Add Photo->Album] Added Photo to Album.");
    		}
    		else{
    			System.out.println("[Main Interface] [Button Add Photo->Album] FAILED to Add Photo to Album.");
    		}
    	}
    }
    @FXML
    protected void removePhotoFromAlbum() throws InterruptedException, IOException{
    	Album targetAlbum = albumManager.getSelectedAlbum(selectedAlbum);
    	
    	if(targetAlbum.albumName.equals("!\\DeadAlbum")){
    		System.out.println("Something is wrong! - > Unable to acquire album selected in Album List Data.");
    	}
    	else if(selectedAlbum == null){
    		System.out.println("No Album Selected");
    	}
    	else if(selectedAlbumPhoto == null){
    		System.out.println("No Photo Selected");
    	}
    	else{
    		PhotoManager photoManager = targetAlbum.photoManager;
    		photoManager.removePhotoByName(selectedAlbumPhoto);
        	listFileController.deletePhotoFromAlbum(selectedAlbum, selectedAlbumPhoto);
    		liveAlbumPhotos = listFileController.getPhotosListOfAlbum(selectedAlbum); 
    		updateLivePhotoList();
    	}

    }
    public void removePhotoFromAlbum(String targetAlbumName, String targetName) throws InterruptedException, IOException{
    	Album targetAlbum = albumManager.getSelectedAlbum(targetAlbumName);
    	
    	if(targetAlbum.albumName.equals("!\\DeadAlbum")){
    		System.out.println("Something is wrong! - > Unable to acquire album selected in Album List Data.");
    	}
    	else if(selectedAlbum == null){
    		System.out.println("No Album Selected");
    	}
    	else if(selectedAlbumPhoto == null){
    		System.out.println("No Photo Selected");
    	}
    	else{
    		PhotoManager photoManager = targetAlbum.photoManager;
    		photoManager.removePhotoByName(targetName);
        	listFileController.deletePhotoFromAlbum(targetAlbumName, targetName);
    		liveAlbumPhotos = listFileController.getPhotosListOfAlbum(targetAlbumName); 
    		updateLivePhotoList();
    	}

    }
    
    @FXML 
    protected void renameAlbumPrompt() throws Exception{
    	if(selectedAlbum != null) {
    	System.out.println("[Main Interface] [Button Add Album] Attempting to Rename Album.");
    	Stage albumNamerWindow = new Stage();
    	RenameAlbumPrompt newNameAsker = new RenameAlbumPrompt(listFileController,interfaceAlbumList);
    	newNameAsker.start(albumNamerWindow);
    	}
    }
    @FXML
    protected void refreshLibrary() throws IOException{
    	
    	liveLibraryList = listFileController.refreshLibraryList(true);
    	interfacePhotoLibrary.setItems(liveLibraryList);
    }
    
}
