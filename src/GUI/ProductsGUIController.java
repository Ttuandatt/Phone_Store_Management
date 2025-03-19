package GUI;
import java.io.File;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ProductsGUIController {
    @FXML
    private ImageView imageView;

    @FXML
    private TextField textFieldTimKiem;
    public void initialize() {
        File file = new File("src/img/delete.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
    @FXML
    void handlePressTimKiem(KeyEvent event) {
        System.out.println("checked");
    }

}
