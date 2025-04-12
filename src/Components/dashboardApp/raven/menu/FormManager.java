package Components.dashboardApp.raven.menu;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
<<<<<<< HEAD:src/Components/dashboardApp/raven/menu/FormManager.java
import Components.dashboardApp.components.MainForm;
import Components.dashboardApp.components.SimpleForm;
import Components.dashboardApp.raven.swing.slider.PanelSlider;
import Components.dashboardApp.raven.swing.slider.SimpleTransition;
import Components.dashboardApp.utils.UndoRedo;
=======
import GUI.dashboardApp.components.MainForm;
import GUI.dashboardApp.components.SimpleForm;
import GUI.dashboardApp.raven.swing.slider.PanelSlider;
import GUI.dashboardApp.raven.swing.slider.SimpleTransition;
import GUI.dashboardApp.utils.UndoRedo;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/raven/menu/FormManager.java

public class FormManager {

    private static FormManager instance;
    private final JFrame frame;

    private final UndoRedo<SimpleForm> forms = new UndoRedo<>();

    private boolean menuShowing = true;
    private final PanelSlider panelSlider;
    private final MainForm mainForm;
    private final boolean undecorated;

    public static void install(JFrame frame, boolean undecorated) {
        instance = new FormManager(frame, undecorated);
    }

    private FormManager(JFrame frame, boolean undecorated) {
        this.frame = frame;
        panelSlider = new PanelSlider();
        mainForm = new MainForm(undecorated);
        this.undecorated = undecorated;
    }

    public static void showForm(SimpleForm component) {
        if (isNewFormAble()) {
            instance.forms.add(component);
            if (instance.menuShowing == true) {
                instance.menuShowing = false;
                instance.mainForm.setForm(component);
                instance.panelSlider.addSlide(instance.mainForm, SimpleTransition.getDefaultTransition(true));
            } else {
                instance.mainForm.showForm(component);
            }
            instance.forms.getCurrent().formInitAndOpen();
        }
    }

    public static void undo() {
        if (isNewFormAble()) {
            if (!instance.menuShowing && instance.forms.isUndoAble()) {
                instance.mainForm.showForm(instance.forms.undo(), SimpleTransition.getDefaultTransition(true));
                instance.forms.getCurrent().formOpen();
            }
        }
    }

    public static void redo() {
        if (isNewFormAble()) {
            if (!instance.menuShowing && instance.forms.isRedoAble()) {
                instance.mainForm.showForm(instance.forms.redo());
                instance.forms.getCurrent().formOpen();
            }
        }
    }

    public static void refresh() {
        if (!instance.menuShowing) {
            instance.forms.getCurrent().formRefresh();
        }
    }

    public static UndoRedo<SimpleForm> getForms() {
        return instance.forms;
    }

    public static boolean isNewFormAble() {
        return instance.forms.getCurrent() == null || instance.forms.getCurrent().formClose();
    }

    public static void updateTempFormUI() {
        for (SimpleForm f : instance.forms) {
            SwingUtilities.updateComponentTreeUI(f);
        }
    }
}
