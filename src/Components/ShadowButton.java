package Components;
import javax.swing.*;
import java.awt.*;


public class ShadowButton extends JButton{
	private int shadowSize = 5;

    public ShadowButton(String text, Icon icon) {
        super(text, icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
    }
    
    public ShadowButton( Icon icon) {
        super(icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Vẽ bóng
        g2.setColor(new Color(0, 0, 0, 80)); // Màu đen với độ trong suốt
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 10, 10);

        // Vẽ nút
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 10, 10);

        // Vẽ viền
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 10, 10);

        g2.dispose();
        super.paintComponent(g);
    }
}
