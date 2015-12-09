

import java.time.LocalDate;

public class Album {
	PhotoManager photoManager;
	String albumName;
	
	LocalDate dateAdded;
	
	Album nextAlbum;
	Album previousAlbum;
	
	
	Album(){
		albumName = "DefaultName";
		dateAdded = LocalDate.now();
		photoManager = new PhotoManager();
	}
	Album(String albumName){
		this.albumName = albumName;
		dateAdded = LocalDate.now();
		photoManager = new PhotoManager();
	}
	
	public void printPhotoNames(){
		Photo currentPhoto = photoManager.allPhotos.headPhoto;
		while(currentPhoto != null){
			System.out.print("- "+currentPhoto.photoName+" -");
			currentPhoto = currentPhoto.nextPhoto;
		}
	}
	
	public void addPhotoToAlbum(Photo newPhoto){
		photoManager.addPhoto(newPhoto);
	}
	public void removeLastPhotoFromAlbum(){
		photoManager.removeLastPhoto();
	}
	public void removePhotoFromAlbum(Photo targetPhoto){
		photoManager.removeLastPhoto();
	}
	public void renameAlbum(String newName){
		albumName = newName;
	}
	
}
