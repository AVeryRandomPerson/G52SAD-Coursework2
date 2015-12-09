

public class AlbumManager {
	AlbumList allAlbums;
		
	AlbumManager(){
		allAlbums = new AlbumList();
	}
	
	
	public void printAlbums(){
		allAlbums.printAlbums();
	}
	public void addAlbum(String newAlbumName){
		allAlbums.addAlbum(newAlbumName);
	}
	public void removeLastAlbum(){
		final int DEAD = -1;
		
		int albumStatus = allAlbums.removeLastAlbum();
		if(albumStatus == DEAD){
			allAlbums = new AlbumList(); // Replaces album list with new one, with unassigned variables.
		}
	}
}
