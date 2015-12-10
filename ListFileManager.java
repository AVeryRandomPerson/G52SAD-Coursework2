

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
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListFileManager {
	
	AlbumManager albumManager;
	ListFileManager(AlbumManager albumManager){
		this.albumManager = albumManager;
	}
	
	int i;
	int j;
	
	  final static String BUFFER_TEMP_FILE = "TempFile_Buffer.txt";
	  final static String ALBUM_LISTS ="AlbumLists.txt";
	  final static Charset ENCODING = StandardCharsets.UTF_8;
	  
	  String scanningValue;
	  Path albumListPath = Paths.get(ALBUM_LISTS);
	  Path bufferFilePath = Paths.get(BUFFER_TEMP_FILE);
	  
	  File bufferFile = new File(BUFFER_TEMP_FILE);
	  
	  
	  
	  public ObservableList<String> getAllAlbums(Boolean copyToAlbumManager) throws IOException{	    
		  	
		  	ArrayList<String> albumList = new ArrayList<String>();
		    try (Scanner scanner =  new Scanner(albumListPath, ENCODING.name())){
		    	if(scanner.hasNextLine()) {
		    		scanningValue = scanner.nextLine();
		    		if(scanningValue.equals("[All Albums]") && scanner.hasNextLine()){
		  		      while (scanner.hasNextLine())
				      {
			    		  scanningValue = scanner.nextLine();
			    		  albumList.add(scanningValue);
			    		  if(copyToAlbumManager) albumManager.addAlbum(scanningValue);
				      }   
		    		}
		    	}   
		    }
		    ObservableList<String> liveAlbumList = FXCollections.observableArrayList(albumList);
		    return liveAlbumList;
	  }	  
	  
	  
	  public void addPhotoToAlbumList(String newAlbumName) throws IOException{
		  try (Scanner scanner =  new Scanner(albumListPath, ENCODING.name())){
		      while (scanner.hasNextLine()){
		    	  scanner.nextLine();
		      }     
	    	  try (BufferedWriter writer = Files.newBufferedWriter(albumListPath,ENCODING,StandardOpenOption.APPEND))
	    	  { 
	    		  {
	    			  writer.newLine();
	    			  writer.write(newAlbumName);
	    		  }
	    	  }
		  }

	  }	  
}
