package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

public class Alert {

    private Popup popup;
    @FXML
    private Label CustomMsg;

    @FXML
    private Label CustomTitle;

    @FXML
    private ImageView imageView;

    @FXML
    private Button closePopup;
    public void setPopup(Popup popup) {
        this.popup = popup;
    }

    public void setImageView(String imageUrl) {
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        imageView.setImage(image);
    }

    @FXML
    private void closePopup() {
        popup.hide();
    }

    public void setCustomMsg(String customMsg) {
        this.CustomMsg.setText(customMsg);
    }
    public void setCustomTitle(String customTitle) {
        this.CustomTitle.setText(customTitle);
    }

    public void setClosePopupStyle(String styleClass) {
        this.closePopup.getStyleClass().clear();
        closePopup.getStyleClass().add(styleClass);

    }
}
