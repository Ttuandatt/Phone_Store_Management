
package GUI.dashboardApp.raven.chart.simple;

import raven.chart.data.category.DefaultCategoryDataset;

public class SimpleDataLineChart extends DefaultCategoryDataset<String, String> {

    public SimpleDataLineChart() {
        init();
    }

    private void init() {
        addValue(400, "Thu nhập", "Jun 7, 2023");
        addValue(250, "Thu nhập", "Jun 8, 2023");
        addValue(500, "Thu nhập", "Jun 9, 2023");
        addValue(300, "Thu nhập", "Jun 10, 2023");
        addValue(1000, "Thu nhập", "Jun 11, 2023");
        addValue(650, "Thu nhập", "Jun 12, 2023");
        addValue(410, "Thu nhập", "Jun 13, 2023");

        addValue(50, "Chi phí", "Jun 8, 2023");
        addValue(80, "Chi phí", "Jun 9, 2023");
        addValue(400, "Chi phí", "Jun 10, 2023");
        addValue(200, "Chi phí", "Jun 11, 2023");
    }
}
