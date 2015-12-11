package albumMaster;

public class PhotoManager {
	PhotoList allPhotos;
	
	PhotoManager(){
		allPhotos = new PhotoList();
	}
	
	public void printPhotos(){
		Photo currentPhoto = allPhotos.headPhoto;
		while(currentPhoto!= null){
			System.out.println(currentPhoto.photoName);
			currentPhoto = currentPhoto.nextPhoto;
		}
	}

	public Photo getSelectedPhoto(String photoName){
		Photo scannedPhoto = allPhotos.headPhoto;
		while(scannedPhoto != null){
			if(photoName.equals(scannedPhoto.photoName)){
				return scannedPhoto;
			}
			scannedPhoto = scannedPhoto.nextPhoto;
		}
		Photo deadPhoto = new Photo("!\\NoName!");
		return deadPhoto;
		
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

	public void removePhotoByName(String targetName) throws InterruptedException{
		final int DEAD = -1;
				
		int photoStatus = allPhotos.removePhotoByName(targetName);
		if(photoStatus == DEAD){
			allPhotos = new PhotoList(); // Replaces album list with new one, with unassigned variables.
		}
		
	}

	public void removeFirstPhoto() throws InterruptedException{
		final int DEAD = -1;
		
		int photoStatus = allPhotos.removeFirstPhoto();
		if(photoStatus == DEAD){
			allPhotos = new PhotoList(); // Replaces album list with new one, with unassigned variables.
		}
	}


}
