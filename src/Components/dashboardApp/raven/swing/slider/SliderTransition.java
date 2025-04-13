/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
<<<<<<< HEAD:src/Components/dashboardApp/raven/swing/slider/SliderTransition.java
package Components.dashboardApp.raven.swing.slider;
=======
package GUI.dashboardApp.raven.swing.slider;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/raven/swing/slider/SliderTransition.java

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;


public abstract class SliderTransition {

    public abstract void renderImageOld(Component component, Graphics g, Image image, int width, int height, float animate);

    public abstract void renderImageNew(Component component, Graphics g, Image image, int width, int height, float animate);

    public boolean closeAfterAnimation() {
        return true;
    }

    public void render(Component component, Graphics g, Image imageOld, Image imageNew, int width, int height, float animate) {
        renderImageOld(component, g.create(), imageOld, width, height, animate);
        renderImageNew(component, g.create(), imageNew, width, height, animate);

    }
}