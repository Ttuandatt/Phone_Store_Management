
<<<<<<< HEAD:src/Components/dashboardApp/raven/popup/GlassPaneChild.java
package Components.dashboardApp.raven.popup;
=======
package GUI.dashboardApp.raven.popup;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/raven/popup/GlassPaneChild.java

import javax.swing.*;

public class GlassPaneChild extends JPanel {

    protected PopupController controller;
    protected PopupCallbackAction callbackAction;

    public int getRoundBorder() {
        return 0;
    }

    public void onPush() {

    }

    public void onPop() {

    }

    public void popupShow() {
    }

    protected PopupController createController() {
        return new PopupController() {

            @Override
            public void closePopup() {
                GlassPanePopup.closePopup(GlassPaneChild.this);
            }
        };
    }
}
