package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
	private String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=phonestore;encrypt=false;trustServerCertificate=true";
	private String username = "sa";
	private String password = "KVy@070303";
	private Connection con;

	// Hàm thiết lập kết nối tới CSDL
	public boolean openConnection() {
		boolean result = false;

		try {
			con = DriverManager.getConnection(dbUrl, username, password);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Hàm ngắt kết nối tới CSDL
	public void closeConnection() {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Hàm trả về đối tượng Connection
	public Connection getConnection() {
		return con;
	}
}
