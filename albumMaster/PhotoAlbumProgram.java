package albumMaster;

import java.io.IOException;

public class PhotoAlbumProgram {

	public static void main(String[] args) throws IOException {
		
		AlbumManager albumManager = new AlbumManager();
		
		Photo pancakes = new Photo("pancakes"); pancakes.photoName = "pancakes";
		Photo waffles = new Photo("waffles");waffles.photoName = "waffles";
		
		Photo milk = new Photo("milk"); milk.photoName = "milk";
		Photo sauvignon = new Photo("sauvignon"); sauvignon.photoName = "sauvignon";
		
		System.out.println("-- Photo Album --");
		System.out.println("These Are Your Photo Albums : ");
		albumManager.addAlbum("a");
		albumManager.addAlbum("b");
		albumManager.addAlbum("c");
		albumManager.addAlbum("d");
		albumManager.addAlbum("f");
		albumManager.addAlbum("g");
		try {
			albumManager.removeLastAlbum();
			albumManager.removeLastAlbum();
			albumManager.removeLastAlbum();
		} catch (InterruptedException e) {e.printStackTrace();}

		albumManager.printAlbums();
		
		
		

		
	}

}
