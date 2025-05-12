package Components;
import javax.swing.*;
import java.awt.*;


public class ShadowButton extends JButton{
	private int shadowSize = 3;

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
    
    public ShadowButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.BOTTOM);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Vẽ bóng (hình chữ nhật, không bo góc)
        g2.setColor(new Color(0, 0, 0, 60)); // Màu đen với độ trong suốt
        g2.fillRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize);

        // Vẽ nút (hình chữ nhật, không bo góc)
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize);

        // Vẽ viền (hình chữ nhật, không bo góc)
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize);

        g2.dispose();
        super.paintComponent(g);
    }

}
