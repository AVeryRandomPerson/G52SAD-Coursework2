package albumMaster;

public class PhotoList {
	int size = 0;
	Photo headPhoto;
	Photo tailPhoto;
	
	PhotoList(){}
	private void selfDestruct(){
		size = 0;
		headPhoto = null;
		tailPhoto = null;
	}
	
	
	public void addPhoto(Photo newPhoto){
		if(size == 0){
			headPhoto = newPhoto;
			tailPhoto = newPhoto;
		}
		else if(size == 1){
			tailPhoto = newPhoto;
			
			headPhoto.nextPhoto = tailPhoto;
			tailPhoto.previousPhoto = headPhoto;
		}
		else{
			newPhoto.previousPhoto = tailPhoto;
			tailPhoto.nextPhoto = newPhoto;
			
			tailPhoto = newPhoto;
		}
		size++;
	}	
	public int removeLastPhoto(){
		if(size < 1){
			System.out.println("Album is empty!");
			return -1;
		}
		else if(size == 1){
			System.out.println("Album destroyed");
		}
		else if(size == 2){
			headPhoto.nextPhoto = null;
			tailPhoto = headPhoto;
			System.out.println("ALBUM DELETED : HEAD["+headPhoto.photoName+"] and TAIL ["+tailPhoto.photoName+"]");
		}
		else{
			Photo exiledAlbum = tailPhoto;
			tailPhoto = tailPhoto.previousPhoto;
			exiledAlbum.previousPhoto = null;
			tailPhoto.nextPhoto = null;
			System.out.println("ALBUM DELETED : HEAD["+headPhoto.photoName+"] and TAIL ["+tailPhoto.photoName+"]");
		}
		size--;
		return size;
	}
	public int removeFirstPhoto(){
		if(size < 1){
			System.out.println("Album is empty!");
			return -1;
		}
		else if(size == 1){
			selfDestruct();
		}
		else if(size == 2){
			tailPhoto = headPhoto;
			headPhoto.nextPhoto = null;
		}
		else{
			Photo exiledAlbum = headPhoto;
			
			headPhoto = headPhoto.nextPhoto;
			exiledAlbum.nextPhoto = null;
			headPhoto.previousPhoto = null;
		}
		size--;
		return size;
	}
	public int removePhotoByName(String targetName){
		Photo currentPhoto = headPhoto;
		while(currentPhoto != null){
			if(currentPhoto.photoName.equals(targetName)){
				if(currentPhoto == tailPhoto){
					System.out.println("Deleting TAIL");
					if(size < 1){
						System.out.println("Album is empty!");
						return -1;
					}
					else if(size == 1){
						selfDestruct();
					}
					else if(size == 2){
						headPhoto.previousPhoto = null;
						headPhoto.nextPhoto = null;
						tailPhoto = headPhoto;
						System.out.println("PHOTO DELETED : HEAD["+headPhoto.photoName+"] and TAIL ["+tailPhoto.photoName+"]");
					}
					else{
						Photo exiledAlbum = tailPhoto;
						tailPhoto = tailPhoto.previousPhoto;
						exiledAlbum.previousPhoto = null;
						tailPhoto.nextPhoto = null;
						System.out.println("PHOTO DELETED : HEAD["+headPhoto.photoName+"] and TAIL ["+tailPhoto.photoName+"]");
					}
				}
				else if(currentPhoto == headPhoto) {
					System.out.println("Deleting HEAD");
					if(size < 1){
						System.out.println("Album is empty!");
						return -1;
					}
					else if(size == 1){
						selfDestruct();
					}
					else if(size == 2){
						tailPhoto.previousPhoto = null;
						tailPhoto.nextPhoto = null;
						headPhoto = tailPhoto;
					}
					else{
						Photo exiledAlbum = headPhoto;
						
						headPhoto = headPhoto.nextPhoto;
						exiledAlbum.nextPhoto = null;
						headPhoto.previousPhoto = null;
					}
					break;
				}
				else{
					System.out.println("Deleting Body");
					Photo currentsPreviousAlbum = currentPhoto.previousPhoto;
					Photo currentsNextAlbum = currentPhoto.nextPhoto;
					
					currentsPreviousAlbum.nextPhoto = currentsNextAlbum;
					currentsNextAlbum.previousPhoto = currentsPreviousAlbum;
					System.out.println("PHOTO DELETED : HEAD["+headPhoto.nextPhoto+"] and TAIL ["+tailPhoto.photoName+"]");
				}	
			}
			System.out.println(currentPhoto.photoName + "Scanned  !");
			currentPhoto = currentPhoto.nextPhoto;
		}
		size--;
		return size;
	}
}
