package albumMaster;

import java.time.LocalDate;


public class Photo {
	String photoDirectory; 
	String photoName;		// Photo name without Directory!
		
	LocalDate dateAdded;
	
	Photo nextPhoto;
	Photo previousPhoto;
	
	Photo(String photoFileName){
		photoDirectory = "Data/Library/"+photoFileName;
		photoName = photoFileName;
		
		dateAdded = LocalDate.now();		
	}
	public void renamePhoto(String newName){
		photoName = newName;
	}
	public void changePhoto(String newSource){
		;
	}
}
