/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ThongKeDAO;
import DTO.ThongKeDTO;
import raven.chart.data.category.DefaultCategoryDataset;
import raven.chart.data.pie.DefaultPieDataset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThongKeBUS {

    private ThongKeDAO thongKeDAO;

    public ThongKeBUS() {
        thongKeDAO = new ThongKeDAO();
    }

    public DefaultPieDataset createProductCollectionData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        ArrayList<ThongKeDTO.PieChartData> data = thongKeDAO.getProductCollectionData();
        for (ThongKeDTO.PieChartData item : data) {
            dataset.addValue(item.getBrandName(), item.getValue());
        }
        return dataset;
    }

    public DefaultPieDataset createProductCostData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        ArrayList<ThongKeDTO.PieChartData> data = thongKeDAO.getProductCostData();
        for (ThongKeDTO.PieChartData item : data) {
            dataset.addValue(item.getBrandName(), item.getValue());
        }
        return dataset;
    }

    public DefaultPieDataset createProductProfitData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        ArrayList<ThongKeDTO.PieChartData> data = thongKeDAO.getProductProfitData();
        for (ThongKeDTO.PieChartData item : data) {
            dataset.addValue(item.getBrandName(), item.getValue());
        }
        return dataset;
    }

    public DefaultCategoryDataset createLineChartData() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy");

        // Get the dynamic date range from PHIEUXUAT
        String[] dateRange = thongKeDAO.getPhieuXuatDateRange();
        String startDate = dateRange[0]; // e.g., "2024-03-01"
        String endDate = dateRange[1];   // e.g., "2024-03-20"

        ArrayList<ThongKeDTO.LineChartData> data = thongKeDAO.getLineChartData(startDate, endDate);
        for (ThongKeDTO.LineChartData item : data) {
            String date = df.format(item.getDate());
            dataset.addValue(item.getIncome(), "Thu nhập", date);
            dataset.addValue(item.getCost(), "Chi phí", date);
            dataset.addValue(item.getProfit(), "Lợi nhuận", date);
        }
        System.out.println("Line Chart Dataset Column Count: " + dataset.getColumnCount());
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            System.out.println("Column " + i + ": " + dataset.getColumnKey(i));
        }
        return dataset;
    }

    public DefaultPieDataset createMonthlyIncomeData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        int year = 2024; // You can make this dynamic as well
        ArrayList<ThongKeDTO.BarChartData> data = thongKeDAO.getMonthlyIncomeData(year);
        for (ThongKeDTO.BarChartData item : data) {
            dataset.addValue(item.getMonthYear(), item.getValue());
        }
        return dataset;
    }

    public DefaultPieDataset createMonthlyCostData() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        int year = 2024; // You can make this dynamic as well
        ArrayList<ThongKeDTO.BarChartData> data = thongKeDAO.getMonthlyCostData(year);
        for (ThongKeDTO.BarChartData item : data) {
            dataset.addValue(item.getMonthYear(), item.getValue());
        }
        return dataset;
    }
}
