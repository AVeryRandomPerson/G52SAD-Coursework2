package albumMaster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class AlbumManager {
	public AlbumList allAlbums;
	final static Charset ENCODING = StandardCharsets.UTF_8;
	AlbumManager(){
		allAlbums = new AlbumList();
	}
	
	
	public void printAlbums(){
		allAlbums.printAlbums();
	}
	public Album getSelectedAlbum(String albumName){
		Album currentAlbum = allAlbums.headAlbum;
		while(currentAlbum != null){
			if(currentAlbum.albumName.equals(albumName)){
				return currentAlbum;
			}
			currentAlbum = currentAlbum.nextAlbum;
		}
		Album deadAlbum = new Album(); 
		deadAlbum.albumName = "!\\DeadAlbum";
		return deadAlbum;
	}
	public void addAlbum(String newAlbumName) throws IOException{
		File newAlbum = new File("Data\\Albums\\"+newAlbumName+".txt");
		newAlbum.createNewFile();
			  Path albumPath = Paths.get("Data\\Albums\\"+newAlbumName+".txt");
		  	  try (BufferedWriter writer = Files.newBufferedWriter(albumPath,ENCODING,StandardOpenOption.APPEND))
		  	  { 
		  		  {
		  			  System.out.println("Initialized Album File ! for : "+newAlbumName);
		  			  writer.write("[ALL Photos]");
		  		  }
		  	  }	
		allAlbums.addAlbum(newAlbumName); 
	}
	public void addAlbum(String newAlbumName, Boolean initializeOnly) throws IOException{
		if(initializeOnly){
			addAlbum(newAlbumName);
		}
		else{
			allAlbums.addAlbum(newAlbumName); 
		}
	}
	public ArrayList<String> getAllAlbumNames(){
		Album currentAlbum = allAlbums.headAlbum;
		ArrayList<String> allAlbumNames = new ArrayList<String>();
		while(currentAlbum != null){
			allAlbumNames.add(currentAlbum.albumName);
			currentAlbum = currentAlbum.nextAlbum;
		}
		return allAlbumNames;
	}
	public void removeAlbumByName(String targetName) throws InterruptedException{
		final int DEAD = -1;
				
		int albumStatus = allAlbums.removeAlbumByName(targetName);
		if(albumStatus == DEAD){
			allAlbums = new AlbumList(); // Replaces album list with new one, with unassigned variables.
		}
		
	}
	public void removeLastAlbum() throws InterruptedException{
		final int DEAD = -1;
		
		int albumStatus = allAlbums.removeLastAlbum();
		if(albumStatus == DEAD){
			allAlbums = new AlbumList(); // Replaces album list with new one, with unassigned variables.
		}
	}
	public void removeFirstAlbum() throws InterruptedException{
		final int DEAD = -1;
		
		int albumStatus = allAlbums.removeFirstAlbum();
		if(albumStatus == DEAD){
			allAlbums = new AlbumList(); // Replaces album list with new one, with unassigned variables.
		}
	}
	public void renameAlbum(String targetName, String newName){
		Album targetAlbum = getSelectedAlbum(targetName);
		targetAlbum.albumName = newName;
	}
}
