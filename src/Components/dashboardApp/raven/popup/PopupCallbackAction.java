package Components.dashboardApp.raven.popup;

public interface PopupCallbackAction {

    public static final int CLOSE = -1;

    public void action(PopupController controller, int action);
}