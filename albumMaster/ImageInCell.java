package albumMaster;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

public class ImageInCell extends ListCell<String> {
    Image image;
    String bgImageName;
	ImageInCell(String imageName){
		bgImageName = imageName;
		image = new Image("file:Library/"+imageName);
		
		this.setWidth(96);
		this.setHeight(96);	 
		BackgroundImage bgImage = new BackgroundImage(image, null, null, null, null);
		//BackgroundImage bgImage = new BackgroundImage(image,0,0,BackgroundPosition.CENTER,BackgroundSize.AUTO);
		Background backgound = new Background(bgImage);

		this.setBackground(backgound);
	}
	   public void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);
	        if (empty) {
	        	Image image = new Image("file:Library/"+bgImageName);
	    		
	    		this.setWidth(96);
	    		this.setHeight(96);	 
	    		BackgroundImage bgImage = new BackgroundImage(image, null, null, null, null);
	    		//BackgroundImage bgImage = new BackgroundImage(image,0,0,BackgroundPosition.CENTER,BackgroundSize.AUTO);
	    		Background backgound = new Background(bgImage);

	    		this.setBackground(backgound);
	            setGraphic(null);
	        } else {
	        	Image image = new Image("file:Library/"+bgImageName);
	    		
	    		this.setWidth(96);
	    		this.setHeight(96);	 
	    		BackgroundImage bgImage = new BackgroundImage(image, null, null, null, null);
	    		//BackgroundImage bgImage = new BackgroundImage(image,0,0,BackgroundPosition.CENTER,BackgroundSize.AUTO);
	    		Background backgound = new Background(bgImage);
	    		ImageView imView = new ImageView();
	    		imView.setImage(image);
	    		this.setBackground(backgound);
	            setGraphic(imView);
	        }
	    }
    
}
