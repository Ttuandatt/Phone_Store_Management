
package Components.dashboardApp.components;

import Components.dashboardApp.raven.menu.FormManager;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
<<<<<<< HEAD:src/Components/dashboardApp/components/MainForm.java
import Components.dashboardApp.raven.swing.slider.PanelSlider;
import Components.dashboardApp.raven.swing.slider.SimpleTransition;
import Components.dashboardApp.raven.swing.slider.SliderTransition;
=======
import GUI.dashboardApp.raven.swing.slider.PanelSlider;
import GUI.dashboardApp.raven.swing.slider.SimpleTransition;
import GUI.dashboardApp.raven.swing.slider.SliderTransition;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/components/MainForm.java


public class MainForm extends JPanel {

    private final boolean undecorated;

    public MainForm(boolean undecorated) {
        this.undecorated = undecorated;
        init();
    }

    private void init() {
        if (undecorated) {
            putClientProperty(FlatClientProperties.STYLE, ""
                    + "border:5,5,5,5;"
                    + "arc:30");
        }
        setLayout(new MigLayout("wrap,fillx", "[fill]", ""));
        header = createHeader();
        panelSlider = new PanelSlider();
        JScrollPane scroll = new JScrollPane(panelSlider);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,0,0,0");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "trackArc:999;"
                + "width:10");
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(header);
        add(scroll);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new MigLayout("insets 3"));
        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:null");

        cmdUndo = createButton(new FlatSVGIcon("raven/icon/undo.svg"));
        cmdRedo = createButton(new FlatSVGIcon("raven/icon/redo.svg"));
        cmdRefresh = createButton(new FlatSVGIcon("raven/icon/refresh.svg"));
        
        cmdUndo.addActionListener(e -> {
            FormManager.undo();
        });
        cmdRedo.addActionListener(e -> {
            FormManager.redo();
        });
        cmdRefresh.addActionListener(e -> {
            FormManager.refresh();
        });

        panel.add(cmdUndo);
        panel.add(cmdRedo);
        panel.add(cmdRefresh);
        return panel;
    }

    private JButton createButton(Icon icon) {
        JButton button = new JButton(icon);
        button.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Button.toolbar.background;"
                + "arc:10;"
                + "margin:3,3,3,3;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
        return button;
    }

    public void showForm(Component component, SliderTransition transition) {
        checkButton();
        panelSlider.addSlide(component, transition);
        revalidate();
    }

    public void showForm(Component component) {
        showForm(component, SimpleTransition.getDefaultTransition(false));
    }

    public void setForm(Component component) {
        checkButton();
        panelSlider.addSlide(component, null);
    }

    private void checkButton() {
        cmdUndo.setEnabled(FormManager.getForms().isUndoAble());
        cmdRedo.setEnabled(FormManager.getForms().isRedoAble());
        cmdRefresh.setEnabled(FormManager.getForms().getCurrent() != null);
    }

    private JPanel header;
    private JButton cmdUndo;
    private JButton cmdRedo;
    private JButton cmdRefresh;
    private PanelSlider panelSlider;
}
