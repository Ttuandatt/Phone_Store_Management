package Database;

public class DBConfig {
    public static final String DB_URL_GOC = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER01;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_HN  = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER03;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_DN  = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER04;databaseName=phonestore;integratedSecurity=true;encrypt=false";
    public static final String DB_URL_HCM = "jdbc:sqlserver://DAMIAN\\MSSQLSERVER05;databaseName=phonestore;integratedSecurity=true;encrypt=false";

    public static String currentDbUrl = DB_URL_GOC; // Giá trị mặc định có thể thay đổi
}
