/*package Database;

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
*/

package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
    public static final String DB_URL_GOC = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER01;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_HN  = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER03;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_DN  = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER04;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_HCM = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER05;databaseName=phonestore;integratedSecurity=true;encrypt=false";

    private static String dbUrl = DB_URL_GOC; // Mặc định là gốc
    private String username = "";
    private String password = "";
    private Connection con;

    // Đặt lại đường dẫn kết nối
    public static void setDbUrl(String url) {
        dbUrl = url;
    }

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

    public void closeConnection() {
        try {
            if (con != null)
                con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
}
