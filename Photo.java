

import java.awt.Image;
import java.time.LocalDate;


public class Photo {
	Image photo;
	
	String photoDirectory; //  Directory without photo Name !
	String photoName;		// Photo name without Directory!
	String photoFormat;
	
	LocalDate dateAdded;
	
	Photo nextPhoto;
	Photo previousPhoto;
	
	Photo(){
		photoDirectory = "No Directory";
		photoName = "DefaultName";
		photoFormat = "jpg";
		
		dateAdded = LocalDate.now();		
	}
	public void renamePhoto(String newName){
		photoName = newName;
	}
	public void changePhoto(String newSource){
		;
	}
}
