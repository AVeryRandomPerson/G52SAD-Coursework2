package albumMaster;

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
		if(size==0){
			System.out.println("Nullified");
		}
		else{
			Album currentAlbum = headAlbum;
			System.out.println("Albums You Have : ");
			for(int i=0;i<size;i++){
				if(currentAlbum == null){System.out.println("Something is wrong"); break;}
				System.out.printf("["+i+"] " + currentAlbum.albumName + " ---> " + getAlbumDate(currentAlbum) + "\n");
				currentAlbum = currentAlbum.nextAlbum;
			}
			System.out.println("Size of ALBUM : " + size);
			System.out.println("ALBUM STATUS : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
		}
	}
	public void addAlbum(String newAlbumName){
		Album newAlbum = new Album(newAlbumName);
		if(size == 0){
			headAlbum = newAlbum;
			tailAlbum = newAlbum;
			System.out.println("ALBUM CREATED : HEAD AND TAIL SAME : s = 1");
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
		System.out.println("ALBUM CREATED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
		size++;
	}	
	public int removeLastAlbum() throws InterruptedException{
		if(size < 1){
			System.out.println("Album is empty!");
			return -1;
		}
		else if(size == 1){
			System.out.println("Album destroyed");
		}
		else if(size == 2){
			headAlbum.nextAlbum = null;
			tailAlbum = headAlbum;
			System.out.println("ALBUM DELETED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
		}
		else{
			Album exiledAlbum = tailAlbum;
			tailAlbum = tailAlbum.previousAlbum;
			exiledAlbum.previousAlbum = null;
			tailAlbum.nextAlbum = null;
			System.out.println("ALBUM DELETED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
		}
		size--;
		return size;
	}
	public int removeFirstAlbum() throws InterruptedException{
		if(size < 1){
			System.out.println("Album is empty!");
			return -1;
		}
		else if(size == 1){
			selfDestruct();
		}
		else if(size == 2){
			tailAlbum = headAlbum;
			headAlbum.nextAlbum = null;
		}
		else{
			Album exiledAlbum = headAlbum;
			
			headAlbum = headAlbum.nextAlbum;
			exiledAlbum.nextAlbum = null;
			headAlbum.previousAlbum = null;
		}
		size--;
		return size;
	}
	public int removeAlbumByName(String targetName) throws InterruptedException{
		Album currentAlbum = headAlbum;
		while(currentAlbum != null){
			if(currentAlbum.albumName.equals(targetName)){
				if(currentAlbum == tailAlbum){
					if(size < 1){
						System.out.println("Album is empty!");
						return -1;
					}
					else if(size == 1){
						selfDestruct();
					}
					else if(size == 2){
						headAlbum.previousAlbum = null;
						headAlbum.nextAlbum = null;
						tailAlbum = headAlbum;
						System.out.println("ALBUM DELETED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
					}
					else{
						Album exiledAlbum = tailAlbum;
						tailAlbum = tailAlbum.previousAlbum;
						exiledAlbum.previousAlbum = null;
						tailAlbum.nextAlbum = null;
						System.out.println("ALBUM DELETED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
					}
				}
				else if(currentAlbum == headAlbum) {
					if(size < 1){
						System.out.println("Album is empty!");
						return -1;
					}
					else if(size == 1){
						selfDestruct();
					}
					else if(size == 2){
						tailAlbum.previousAlbum = null;
						tailAlbum.nextAlbum = null;
						headAlbum = tailAlbum;
					}
					else{
						Album exiledAlbum = headAlbum;
						
						headAlbum = headAlbum.nextAlbum;
						exiledAlbum.nextAlbum = null;
						headAlbum.previousAlbum = null;
					}
					break;
				}
				else{
					Album currentsPreviousAlbum = currentAlbum.previousAlbum;
					Album currentsNextAlbum = currentAlbum.nextAlbum;
					
					currentsPreviousAlbum.nextAlbum = currentsNextAlbum;
					currentsNextAlbum.previousAlbum = currentsPreviousAlbum;
					System.out.println("ALBUM DELETED : HEAD["+headAlbum.albumName+"] and TAIL ["+tailAlbum.albumName+"]");
				}	
			}
			currentAlbum = currentAlbum.nextAlbum;
		}
		size--;
		return size;
	}
}
