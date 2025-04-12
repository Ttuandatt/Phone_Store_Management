
<<<<<<< HEAD:src/Components/dashboardApp/raven/popup/PopupController.java
package Components.dashboardApp.raven.popup;
=======
package GUI.dashboardApp.raven.popup;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/raven/popup/PopupController.java

public abstract class PopupController {

    private boolean consume;

    public boolean getConsume() {
        return consume;
    }

    public void consume() {
        consume = true;
    }

    public abstract void closePopup();
}
