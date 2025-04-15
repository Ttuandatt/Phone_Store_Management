package Components;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;

public class DateConverter {
	public static java.sql.Date convertToSQLDate(String dateString) {
        try {
            // Định dạng đầu vào và đầu ra
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Chuyển đổi sang định dạng chuẩn
            Date parsedDate = inputFormat.parse(dateString);
            String formattedDate = outputFormat.format(parsedDate);

            return java.sql.Date.valueOf(formattedDate);
        } catch (Exception e) {
            System.out.println("Lỗi chuyển đổi ngày: " + e.getMessage());
            return null;
        }
    }
}
