

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
		if(size == 0){
			System.out.println("Photo List is empty!");
			return -1;
		}
		else if(size == 1){
			selfDestruct();
		}
		else if(size == 2){
			headPhoto.nextPhoto = null;
			tailPhoto = headPhoto;
		}
		else{
			tailPhoto = tailPhoto.previousPhoto;
			tailPhoto.nextPhoto = null;
		}
		size--;
		return size;
	}

}
