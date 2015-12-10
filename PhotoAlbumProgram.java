

import java.io.IOException;

public class PhotoAlbumProgram {

	public static void main(String[] args) throws IOException {
		
		AlbumManager albumManager = new AlbumManager();
		
		Photo pancakes = new Photo(); pancakes.photoName = "pancakes";
		Photo waffles = new Photo();waffles.photoName = "waffles";
		
		Photo milk = new Photo(); milk.photoName = "milk";
		Photo sauvignon = new Photo(); sauvignon.photoName = "sauvignon";
		
		System.out.println("-- Photo Album --");
		System.out.println("These Are Your Photo Albums : ");
		albumManager.addAlbum("Food");
		albumManager.addAlbum("Drinks");
		albumManager.printAlbums();
		
		
		

		
	}

}
