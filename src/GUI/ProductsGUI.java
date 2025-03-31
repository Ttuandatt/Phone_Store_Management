package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.swing.JPanel;

public class ProductsGUI extends JPanel{
    private Parent root;

    public ProductsGUI() {
        // Không load FXML trong constructor
    }

    public Parent getContent() throws Exception {
        if (root == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductsGUI.fxml"));
            root = loader.load();
            ProductsGUIController controller = loader.getController();
            controller.initialize(); // Khởi tạo dữ liệu
        }
        return root;
    }
}