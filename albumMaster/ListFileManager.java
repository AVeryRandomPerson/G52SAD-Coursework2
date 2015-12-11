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
	  final static String ALBUM_LISTS ="Data\\AlbumLists.txt";
	  final static String LIBRARY_LIST ="Data\\LibraryList.txt";
	  final static Charset ENCODING = StandardCharsets.UTF_8;
	  
	  
	  String scanningValue;
	  Path albumListPath = Paths.get(ALBUM_LISTS);
	  
	  private File createBufferFile(String bufferPath) throws IOException{
		  String buffer_temp_file = "TempFile_Buffer.txt";
		  File bufferFile = new File(buffer_temp_file);
		  bufferFile.createNewFile();
		  return bufferFile;
	  }
	  public ObservableList<String> getAllAlbums(Boolean copyToList) throws IOException{	 
		  	ArrayList<String> albumList = new ArrayList<String>();
		    try (Scanner scanner =  new Scanner(albumListPath, ENCODING.name())){
		    	if(scanner.hasNextLine()) {
		    		scanningValue = scanner.nextLine();
		    		if(scanningValue.equals("[All Albums]") && scanner.hasNextLine()){
		  		      while (scanner.hasNextLine())
				      {
			    		  scanningValue = scanner.nextLine();
			    		  albumList.add(scanningValue);
			    		  if(copyToList) albumManager.addAlbum(scanningValue,false);
				      }   
		    		}
		    	}   
		    }
		    ObservableList<String> liveAlbumList = FXCollections.observableArrayList(albumList);
		    return liveAlbumList;
	  }	  
	  
	  
	  public void addAlbumToSystem(String newAlbumName) throws IOException{
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
			albumManager.printAlbums();
	  }	
	  public void saveAllAlbumPhotosToSystem() throws IOException{
		  AlbumList allAlbums = albumManager.allAlbums;
		  Album currentAlbum = allAlbums.headAlbum;
		  while(currentAlbum != null){
			  saveAlbumPhotosToSystem(currentAlbum);
			  currentAlbum = currentAlbum.nextAlbum;
		  }
	  }
	  public void saveAlbumPhotosToSystem(Album targetAlbum) throws IOException{
		  String albumName = targetAlbum.albumName;
		  PhotoManager photoManager = targetAlbum.photoManager;
		  Path albumFilePath = Paths.get("Data\\Albums\\"+albumName+".txt");
		  try (Scanner scanner =  new Scanner(albumFilePath, ENCODING.name())){
	    	  scanningValue = scanner.nextLine();
	    	  if(scanningValue.equals("[ALL Photos]")){
	    		  System.out.println("Successfully Found File!");
	    	  }
			  while (scanner.hasNextLine()){
				  	  scanningValue = scanner.nextLine();
				  	  Photo addedImage = new Photo(scanningValue);
			    	  photoManager.allPhotos.addPhoto(addedImage);      	  
		      }        
		  }
	  }
	  public void removeAlbumFromSystem(String target) throws IOException{
		  File bufferFile = createBufferFile("TempFile_Buffer.txt");
		  Path bufferFilePath = Paths.get("TempFile_Buffer.txt");
		  AlbumList allAlbums = albumManager.allAlbums;
		  Album currentAlbum = allAlbums.headAlbum;
	  	  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
    	  { 
    		  {
    			  writer.write("[All Albums]");
    		  }
    	  }
		  while(currentAlbum != null){
			  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
	    	  { 
	    		  {
	    			  writer.newLine();
	    			  writer.write(currentAlbum.albumName);
	    			  
	    		  }
	    	  }	
			  currentAlbum = currentAlbum.nextAlbum;
		  }
			File albumListFile = new File(ALBUM_LISTS);
			albumListFile.delete();
			bufferFile.renameTo(albumListFile);
			albumManager.printAlbums();
		  
			File targetAlbumFile = new File("Data\\Albums\\"+target+".txt");
			targetAlbumFile.delete();
	  }
	  public ObservableList<String> refreshLibraryList(boolean copyToList) throws IOException{
		  ArrayList<String> resultList = new ArrayList<String>();
		  File bufferFile = createBufferFile("TempFile_Buffer.txt");
		  Path bufferFilePath = Paths.get("TempFile_Buffer.txt");
	  	  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
    	  { 
    		  {
    			  writer.write("[Library List]");
    			  writer.newLine();
    		  }
    	  }
	  	  File libraryFolder = new File("Data\\Library");
	  	  File[] allFiles = libraryFolder.listFiles();
	  	  
	  	  for(int i=0;i<allFiles.length;i++){
			  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
	    	  { 
	    		  {
	    			  writer.write(allFiles[i].getName());
	    			  writer.newLine();
	    			  if(copyToList)resultList.add(allFiles[i].getName());
	    		  }
	    	  }	 
	  	  }
			File libraryList = new File(LIBRARY_LIST);
			libraryList.delete();
			bufferFile.renameTo(libraryList);
			return FXCollections.observableArrayList(resultList);
	  }
	  public void addPhotoToAlbum(String albumName, String photoName) throws IOException{
		  Path albumFilePath = Paths.get("Data\\Albums\\"+albumName+".txt");
		  try (Scanner scanner =  new Scanner(albumFilePath, ENCODING.name())){
		      while (scanner.hasNextLine()){
		    	  scanner.nextLine();
		      }     
	    	  try (BufferedWriter writer = Files.newBufferedWriter(albumFilePath,ENCODING,StandardOpenOption.APPEND))
	    	  { 
	    		  {
	    			  writer.newLine();
	    			  writer.write(photoName);
	    		  }
	    	  }
		  }	  
	  }
	  public void deletePhotoFromAlbum(String albumName, String photoName) throws IOException{
		  Path albumFilePath = Paths.get("Data\\Albums\\"+albumName+".txt");
		  File bufferFile = createBufferFile("TempFile_Buffer.txt");
		  Path bufferFilePath = Paths.get("TempFile_Buffer.txt");
		  
		  
	  	  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
    	  { 
    		  {
    			  writer.write("[ALL Photos]");
    		  }
    	  }
		  
		  try (Scanner scanner =  new Scanner(albumFilePath, ENCODING.name())){
		  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND)){
		    	  while (scanner.hasNextLine()){	  
			    	  { 
			    		  { 
			    			  scanningValue = scanner.nextLine();
			    			  boolean ignore = (scanningValue.equals(photoName) || scanningValue.equals("[ALL Photos]")); 
			    			  if(!ignore){
			    			  writer.newLine();
			    			  writer.write(scanningValue);
			    			  }
			    		  }
			    	  }
		    	  }
    	  }
		  }
		  File albumFile = new File("Data\\Albums\\"+albumName+".txt");
		  albumFile.delete();
		  bufferFile.renameTo(albumFile); 
	  }	  
	  public ObservableList<String> getPhotosListOfAlbum(String albumName) throws IOException{
		  Path albumFilePath = Paths.get("Data\\Albums\\"+albumName+".txt");
		  ArrayList<String> albumPhotoList = new ArrayList<String>();
		  try (Scanner scanner =  new Scanner(albumFilePath, ENCODING.name())){
		      while (scanner.hasNextLine()){
		    	  scanningValue = scanner.nextLine();
		    	  if(scanningValue.equals("[ALL Photos]")){
		    		  System.out.println("Successfully Found File!");
		    	  }
		    	  else{
			    	  albumPhotoList.add(scanningValue);
			    	  System.out.println("Added to List : " + scanningValue);
		    	  }
		      }   
		  }
		  return FXCollections.observableArrayList(albumPhotoList);
	  }
	  public void renameAlbumToSystem(String targetAlbum, String newName) throws IOException{
		  File albumDataFile = new File("Data\\Albums\\"+targetAlbum+".txt");
		  File newAlbumDataFile = new File("Data\\Albums\\"+newName+".txt");
		  File albumListFile = new File(ALBUM_LISTS);

		  File bufferFile = createBufferFile("TempFile_Buffer.txt");
		  Path bufferFilePath = Paths.get("TempFile_Buffer.txt");

	  	  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND))
    	  { 
    		  {
    			  writer.write("[All Albums]");
    		  }
    	  }
		  try (Scanner scanner =  new Scanner(albumListPath, ENCODING.name())){
		  try (BufferedWriter writer = Files.newBufferedWriter(bufferFilePath,ENCODING,StandardOpenOption.APPEND)){
		    	  while (scanner.hasNextLine()){	  
			    	  { 
			    		  { 
			    			  scanningValue = scanner.nextLine(); 
			    			  if(scanningValue.equals("[All Albums]")){
			    				  //Ignore
			    				  System.out.println("Found File to Rename!");
			    			  }
			    			  else if(scanningValue.equals(targetAlbum)){
			    				  writer.newLine();
			    				  writer.write(newName);
			    			  }
			    			  else{
			    				  writer.newLine();
			    				  writer.write(scanningValue);
			    			  }
			    		  }
			    	  }
		    	  }
    	  }
		  }
		  albumListFile.delete();
		  bufferFile.renameTo(albumListFile); 
		  albumDataFile.renameTo(newAlbumDataFile);

		  //albumManager.renameAlbum(targetAlbum, newName);
	  }
}
