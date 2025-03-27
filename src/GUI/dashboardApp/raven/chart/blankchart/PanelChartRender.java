/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.dashboardApp.raven.chart.blankchart;

import javax.swing.*;
import java.awt.*;

public class PanelChartRender extends JPanel {

    private final ChartRender chartRender;

    public PanelChartRender(ChartRender chartRender) {
        this.chartRender = chartRender;
        init();
    }

    private void init() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        chartRender.render(this, g.create());
    }
}