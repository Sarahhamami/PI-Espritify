package Controllers;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowImageController {

    @FXML
    private ImageView imageView;

    @FXML
    private Popup popup;

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
    public void closePopup(){
        popup.hide();
    }
    public void setImageView(String resourceName) {
        try {
            File file = new File(resourceName);
            InputStream inputStream = new FileInputStream(file);
            Image image = new Image(inputStream);
            imageView.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., file not found, failed to load image)
        }
    }
}
