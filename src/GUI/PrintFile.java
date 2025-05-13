package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.print.*;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Vector;

public class PrintFile {

    // Thông tin kết nối CSDL
    static String dbUrl = "jdbc:mysql://localhost:3306/phonestore";
    static String username = "root";
    static String password = "1234";

   

    public static JTable getTableNhaCungCapFromDatabase() {
        String sql = "SELECT * FROM nhacungcap";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
        public static JTable getTableChamCongFromDatabase() {
        String sql = "SELECT * FROM BangChamCong";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
        public static JTable getTableBangLuongFromDatabase() {
        String sql = "SELECT * FROM BangLuong";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
        public static JTable getTableDonXinNghiFromDatabase() {
        String sql = "SELECT * FROM donxinnghi";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
        public static JTable getTableKhachHangFromDatabase() {
        String sql = "SELECT * FROM khachhang";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
             public static JTable getTableNhanVienFromDatabase() {
        String sql = "SELECT * FROM nhanvien";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);
            return table;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }   
        
        
        
//
             
             
             
             
             
        
          public static void printTableNhanVien(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH NHÂN VIÊN"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    }  
                    public static void printTableChamCong(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH CHẤM CÔNG"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    } 
                    public static void printTableBangLuong(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH BẢNG LƯƠNG"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    }  
                    public static void printTableDonXinNghi(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH ĐƠN XIN NGHỈ"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    }    
    public static void printTableNhaCungCap(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH NHÀ CUNG CẤP"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    }
        public static void printTableKhachHang(JTable table) {
        try {
            boolean complete = table.print(
                JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("DANH SÁCH NHÀ KHÁCH HÀNG"),
                new MessageFormat("Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Xem trước dữ liệu", JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null, "In thành công!");

            } else {
                JOptionPane.showMessageDialog(null, "Đã hủy in.");
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi in: " + e.getMessage());
        }
    }
    
    
    
    
}
