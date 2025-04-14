/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

public class ThongKeDTO {

    // For Pie Charts (Product Collection, Cost, Profit by Brand)
    public static class PieChartData {

        private String brandName;
        private double value;

        public PieChartData(String brandName, double value) {
            this.brandName = brandName;
            this.value = value;
        }

        public String getBrandName() {
            return brandName;
        }

        public double getValue() {
            return value;
        }
    }

    // For Line Chart (Income, Cost, Profit over time)
    public static class LineChartData {

        private Date date;
        private double income;
        private double cost;
        private double profit;

        public LineChartData(Date date, double income, double cost, double profit) {
            this.date = date;
            this.income = income;
            this.cost = cost;
            this.profit = profit;
        }

        public Date getDate() {
            return date;
        }

        public double getIncome() {
            return income;
        }

        public double getCost() {
            return cost;
        }

        public double getProfit() {
            return profit;
        }
    }

    // For Bar Charts (Monthly Income and Cost)
    public static class BarChartData {

        private String monthYear;
        private double value;

        public BarChartData(String monthYear, double value) {
            this.monthYear = monthYear;
            this.value = value;
        }

        public String getMonthYear() {
            return monthYear;
        }

        public double getValue() {
            return value;
        }
    }
}
