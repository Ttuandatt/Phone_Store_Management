package GUI;

import javax.swing.JPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.swing.JPanel;

public class ThongTinCaNhanGUI extends JPanel {
    private Parent root;

    public ThongTinCaNhanGUI() {
        // Không load FXML trong constructor
    }

    public Parent getContent() throws Exception {
        if (root == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonalInformationGUI.fxml"));
            root = loader.load();
            PersonalInformationController controller = loader.getController();
            controller.initialize(); // Khởi tạo dữ liệu
        }
        return root;
    }
}