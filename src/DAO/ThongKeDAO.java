/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ThongKeDTO;
import Database.JDBCConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongKeDAO {

    private Connection conn;

    public ThongKeDAO() {
        JDBCConnection jdbcConnection = new JDBCConnection();
        if (jdbcConnection.openConnection()) {
            conn = jdbcConnection.getConnection();
        } else {
            throw new RuntimeException("Cannot connect to phonestore database.");
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // New method to get the date range from PHIEUXUAT
    public String[] getPhieuXuatDateRange() {
        String[] dateRange = new String[2]; // [startDate, endDate]
        String query = "SELECT MIN(ngayTao) as minDate, MAX(ngayTao) as maxDate FROM PHIEUXUAT";
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

        try ( PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Date minDate = rs.getDate("minDate");
                Date maxDate = rs.getDate("maxDate");
                if (minDate != null && maxDate != null) {
                    dateRange[0] = dbFormat.format(minDate);
                    dateRange[1] = dbFormat.format(maxDate);
                } else {
                    // Fallback if no data exists
                    Calendar cal = Calendar.getInstance();
                    dateRange[1] = dbFormat.format(cal.getTime()); // Today
                    cal.add(Calendar.MONTH, -1);
                    dateRange[0] = dbFormat.format(cal.getTime()); // 30 days ago
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Fallback in case of error
            Calendar cal = Calendar.getInstance();
            dateRange[1] = dbFormat.format(cal.getTime());
            cal.add(Calendar.MONTH, -1);
            dateRange[0] = dbFormat.format(cal.getTime());
        }
        return dateRange;
    }

    // Existing methods (unchanged)
    public ArrayList<ThongKeDTO.PieChartData> getProductCollectionData() {
        ArrayList<ThongKeDTO.PieChartData> data = new ArrayList<>();
        String query = "SELECT t.tenTH, SUM(kp.soLuong) as totalQuantity "
                + "FROM THUONGHIEU t "
                + "JOIN SANPHAM sp ON t.maTH = sp.maTH "
                + "JOIN PBSP pb ON sp.maSP = pb.maSP "
                + "JOIN KHO_PBSP kp ON pb.maPBSP = kp.maPBSP "
                + "GROUP BY t.tenTH";
        try ( PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String brand = rs.getString("tenTH");
                double quantity = rs.getDouble("totalQuantity");
                data.add(new ThongKeDTO.PieChartData(brand, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ThongKeDTO.PieChartData> getProductCostData() {
        ArrayList<ThongKeDTO.PieChartData> data = new ArrayList<>();
        String query = "SELECT t.tenTH, SUM(ct.giaNhap * ct.soLuong) as totalCost "
                + "FROM THUONGHIEU t "
                + "JOIN SANPHAM sp ON t.maTH = sp.maTH "
                + "JOIN PBSP pb ON sp.maSP = pb.maSP "
                + "JOIN CTPN ct ON pb.maPBSP = ct.maPBSP "
                + "GROUP BY t.tenTH";
        try ( PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String brand = rs.getString("tenTH");
                double cost = rs.getDouble("totalCost");
                data.add(new ThongKeDTO.PieChartData(brand, cost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ThongKeDTO.PieChartData> getProductProfitData() {
        ArrayList<ThongKeDTO.PieChartData> data = new ArrayList<>();
        String query = "SELECT t.tenTH, SUM((ctpx.giaXuat - ctpn.giaNhap) * ctpx.soLuong) as totalProfit "
                + "FROM THUONGHIEU t "
                + "JOIN SANPHAM sp ON t.maTH = sp.maTH "
                + "JOIN PBSP pb ON sp.maSP = pb.maSP "
                + "JOIN CTPX ctpx ON pb.maPBSP = ctpx.maPBSP "
                + "JOIN CTPN ctpn ON pb.maPBSP = ctpn.maPBSP "
                + "GROUP BY t.tenTH";
        try ( PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String brand = rs.getString("tenTH");
                double profit = rs.getDouble("totalProfit");
                data.add(new ThongKeDTO.PieChartData(brand, profit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ThongKeDTO.LineChartData> getLineChartData(String startDate, String endDate) {
        ArrayList<ThongKeDTO.LineChartData> data = new ArrayList<>();
        String query = "SELECT px.ngayTao, "
                + "SUM(ctpx.giaXuat * ctpx.soLuong) as income, "
                + "SUM(ctpn.giaNhap * ctpx.soLuong) as cost, "
                + "SUM((ctpx.giaXuat - ctpn.giaNhap) * ctpx.soLuong) as profit "
                + "FROM PHIEUXUAT px "
                + "JOIN CTPX ctpx ON px.maPX = ctpx.maPX "
                + "JOIN PBSP pb ON ctpx.maPBSP = pb.maPBSP "
                + "JOIN CTPN ctpn ON pb.maPBSP = ctpn.maPBSP "
                + "WHERE px.ngayTao BETWEEN ? AND ? "
                + "GROUP BY px.ngayTao";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("ngayTao");
                double income = rs.getDouble("income");
                double cost = rs.getDouble("cost");
                double profit = rs.getDouble("profit");
                data.add(new ThongKeDTO.LineChartData(date, income, cost, profit));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ThongKeDTO.BarChartData> getMonthlyIncomeData(int year) {
        ArrayList<ThongKeDTO.BarChartData> data = new ArrayList<>();
        String query = "SELECT MONTH(px.ngayTao) as month, SUM(ctpx.giaXuat * ctpx.soLuong) as income "
                + "FROM PHIEUXUAT px "
                + "JOIN CTPX ctpx ON px.maPX = ctpx.maPX "
                + "WHERE YEAR(px.ngayTao) = ? "
                + "GROUP BY MONTH(px.ngayTao)";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double income = rs.getDouble("income");
                String monthYear = "Month " + month;
                data.add(new ThongKeDTO.BarChartData(monthYear, income));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ThongKeDTO.BarChartData> getMonthlyCostData(int year) {
        ArrayList<ThongKeDTO.BarChartData> data = new ArrayList<>();
        String query = "SELECT MONTH(pn.ngayTao) as month, SUM(ctpn.giaNhap * ctpn.soLuong) as cost "
                + "FROM PHIEUNHAP pn "
                + "JOIN CTPN ctpn ON pn.maPN = ctpn.maPN "
                + "WHERE YEAR(pn.ngayTao) = ? "
                + "GROUP BY MONTH(pn.ngayTao)";
        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double cost = rs.getDouble("cost");
                String monthYear = "Month " + month;
                data.add(new ThongKeDTO.BarChartData(monthYear, cost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
