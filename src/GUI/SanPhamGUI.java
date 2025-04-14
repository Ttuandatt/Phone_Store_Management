package GUI;

import javax.swing.JPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.swing.JPanel;

<<<<<<< HEAD:src/GUI/ProductsGUI.java
public class ProductsGUI extends JPanel{
=======
public class SanPhamGUI extends JPanel {
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/SanPhamGUI.java
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