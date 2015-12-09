

public class PhotoManager {
	PhotoList allPhotos;
	
	PhotoManager(){
		allPhotos = new PhotoList();
	}
	
	
	
	public void addPhoto(Photo newPhoto){
		allPhotos.addPhoto(newPhoto);
	}
	public void removeLastPhoto(){
		final int DEAD = -1;
		
		int photosStatus = allPhotos.removeLastPhoto();
		if(photosStatus == DEAD){
			allPhotos = new PhotoList(); // Replaces photo list with new one, with unassigned variables.
		}
	}
}
