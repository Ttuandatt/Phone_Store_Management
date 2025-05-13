package GUI;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Database.JDBCConnection;

public class excelExporter {
	private String dbUrl = JDBCConnection.getDatabaseUrl();
	private String username = "";
	private String password = "";
    public static void main(String[] args) {
      
    }
    
    public void excelExporterSanPham() {
    
        
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM sanpham";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SanPham");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
           try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/sanpham_data.xlsx")) {
    // Write workbook to file
    workbook.write(fileOut);
    System.out.println("Excel file exported successfully.");
} catch (IOException e) {
    System.err.println("Error exporting Excel file: " + e.getMessage());
}

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void excelExporterNhaCungCap(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM nhacungcap";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("NhaCungCap");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/nhacungcap_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
    public void excelExporterPhieuNhap(){
      try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM phieunhap";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("PhieuNhap");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/phieunhap_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }
    public void excelExporterPhieuXuat(){
      try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM phieuxuat";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("PhieuXuat");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/phieuxuat_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     
     }
    public void excelExporterNhanVien(){
  try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM nhanvien";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("NhanVien");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/nhanvien_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    
    }
    
    public void excelExporterKhoHang(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM khohang";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("KhoHang");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/khohang_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
    public void excelExporterTaiKhoan(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM taikhoan";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("TaiKhoan");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/taikhoan_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
    
        public void excelExporterKhachHang(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM khachhang";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("khachhang");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/khachhang_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
     public void excelExporterDonXinNghi(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM donxinnghi";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("donxinnghi");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/donxinnghi_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
          public void excelExporterBangChamCong(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM BangChamCong";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("BangChamCong");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/BangChamCong_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
           public void excelExporterBangLuong(){
     try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String sql = "SELECT * FROM BangLuong";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("BangLuong");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Write data rows
            int rowNum = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        row.createCell(i - 1).setCellValue(value.toString());
                    } else {
                        row.createCell(i - 1).setCellValue("");
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write workbook to file
            try (FileOutputStream fileOut = new FileOutputStream("./src/xcl_files/BangLuong_data.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                System.err.println("Error exporting Excel file: " + e.getMessage());
            }

            // Close workbook
            workbook.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
     }
}