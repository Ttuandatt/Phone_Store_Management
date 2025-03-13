package Components;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer extends DefaultTableCellRenderer {
    @Override
    public void setValue(Object value) {
        if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
            setText(""); // Không hiển thị text
        } else {
            setIcon(null);
            setText("No Image");
        }
    }
}
