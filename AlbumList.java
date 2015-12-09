

public class AlbumList {
	int size =0;
	Album headAlbum;
	Album tailAlbum;
	
	AlbumList(){}
	
	private void selfDestruct(){ // Points everything to NULL
		size = 0;
		headAlbum = null;
		tailAlbum = null;
	}
	private String getAlbumDate(Album targetAlbum){
		String dateRepresentation;
			dateRepresentation = targetAlbum.dateAdded.getDayOfMonth() + "-" + targetAlbum.dateAdded.getMonthValue() + "-" + targetAlbum.dateAdded.getYear();
		return dateRepresentation;
	}
	
	
	public void printAlbums(){
		Album currentAlbum = headAlbum;
		System.out.println("Albums You Have : ");
		for(int i=0;i<size;i++){
			System.out.printf("["+i+"] " + currentAlbum.albumName + " ---> " + getAlbumDate(currentAlbum) + "\n");
			currentAlbum = currentAlbum.nextAlbum;
		}
	}
	public void addAlbum(String newAlbumName){
		Album newAlbum = new Album(newAlbumName);
		if(size == 0){
			headAlbum = newAlbum;
			tailAlbum = newAlbum;
		}
		else if(size == 1){
			tailAlbum = newAlbum;
			
			headAlbum.nextAlbum = tailAlbum;
			tailAlbum.previousAlbum = headAlbum;
		}
		else{
			newAlbum.previousAlbum = tailAlbum;
			tailAlbum.nextAlbum = newAlbum;
			
			tailAlbum = newAlbum;
		}
		size++;
	}	
	public int removeLastAlbum(){
		if(size < 1){
			System.out.println("Album is empty!");
			return -1;
		}
		else if(size == 1){
			selfDestruct();
		}
		else if(size == 2){
			headAlbum.nextAlbum = null;
			tailAlbum = headAlbum;
		}
		else{
			tailAlbum = tailAlbum.previousAlbum;
			tailAlbum.nextAlbum = null;
		}
		size--;
		return size;
	}

	
	
}
