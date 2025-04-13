package Components.dashboardApp.raven.chart;

<<<<<<< HEAD:src/Components/dashboardApp/raven/chart/ChartLegendRenderer.java
=======
package GUI.dashboardApp.raven.chart;
>>>>>>> 8fde77a9811b746ce56d98bc66e56fc5b581c1ff:src/GUI/dashboardApp/raven/chart/ChartLegendRenderer.java

import javax.swing.*;
import java.awt.*;

public class ChartLegendRenderer {

    public ChartLegendRenderer() {
    }

    public Component getLegendComponent(Object legend, int index) {
        return new JLabel(legend.toString());
    }
}
