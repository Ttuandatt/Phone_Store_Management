package GUI;

import javax.swing.JPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SanPhamGUI extends JPanel {
    private Parent root;

    public SanPhamGUI() {
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