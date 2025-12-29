package klockimans;

import java.sql.*;

/**
 * Zarządzanie połączeniem z bazą danych SQL Server
 * Obsługuje Windows Authentication oraz SQL Authentication
 * Konfiguracja z pliku .env
 * @author Patrycja Rybak
 */
public class DatabaseManager {
    
    private Connection connection;
    
    // Wartości domyślne (jeśli brak .env)
    private static final String DEFAULT_SERVER = "NT-27.wwsi.edu.pl,1601";
    private static final String DEFAULT_DATABASE = "fullstack25_spod-351pr";
    private static final String DEFAULT_AUTH_TYPE = "SQL";
    
    /**
     * Konstruktor - nawiązuje połączenie z bazą danych
     * Konfiguracja z pliku .env
     */
    public DatabaseManager() throws SQLException {
        // Załaduj konfigurację z .env
        EnvLoader.load();
        
        String server = EnvLoader.get("DB_SERVER", DEFAULT_SERVER);
        String database = EnvLoader.get("DB_DATABASE", DEFAULT_DATABASE);
        String authType = EnvLoader.get("DB_AUTH_TYPE", DEFAULT_AUTH_TYPE);
        String username = EnvLoader.get("DB_USERNAME", "");
        String password = EnvLoader.get("DB_PASSWORD", "");
        
        String connectionUrl = buildConnectionUrl(server, database, authType, username, password);
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("✓ Połączono z bazą danych SQL Server");
            System.out.println("  Serwer: " + server);
            System.out.println("  Baza: " + database);
            System.out.println("  Uwierzytelnianie: " + authType);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Brak sterownika JDBC dla SQL Server: " + e.getMessage());
        }
    }
    
    /**
     * Buduje URL połączenia w zależności od typu uwierzytelniania
     */
    private String buildConnectionUrl(String server, String database, String authType, 
                                     String username, String password) {
        StringBuilder url = new StringBuilder();
        
        // Obsługa portu w adresie serwera (np. server,port lub server:port)
        String serverUrl = server;
        if (server.contains(",")) {
            // Format: hostname,port -> zmień na hostname:port dla JDBC
            serverUrl = server.replace(",", ":");
        }
        
        url.append("jdbc:sqlserver://").append(serverUrl);
        url.append(";databaseName=").append(database);
        url.append(";encrypt=true");
        url.append(";trustServerCertificate=true");
        url.append(";sslProtocol=TLSv1.2");
        url.append(";applicationName=KlockiMANS");
        
        if ("SQL".equalsIgnoreCase(authType)) {
            // SQL Authentication - wymaga username i password
            if (username.isEmpty() || password.isEmpty()) {
                System.err.println("⚠ UWAGA: SQL Authentication wymaga username i password w .env!");
                System.err.println("  Przełączam na Windows Authentication");
                url.append(";integratedSecurity=true");
            } else {
                url.append(";user=").append(username);
                url.append(";password=").append(password);
                System.out.println("  Użytkownik: " + username);
            }
        } else {
            // Windows Authentication (domyślnie)
            url.append(";integratedSecurity=true");
        }
        
        return url.toString();
    }
    
    /**
     * Tworzy tabelę do przechowywania wyników jeśli nie istnieje
     */
    public void createTableIfNotExists() throws SQLException {
        String createTableSQL = 
            "IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='KnapsackResults' AND xtype='U') " +
            "CREATE TABLE KnapsackResults (" +
            "    id INT IDENTITY(1,1) PRIMARY KEY," +
            "    algorithm_name NVARCHAR(100) NOT NULL," +
            "    execution_time_ms BIGINT NOT NULL," +
            "    waste_area FLOAT NOT NULL," +
            "    efficiency FLOAT NOT NULL," +
            "    blocks_count INT NOT NULL," +
            "    total_area FLOAT," +
            "    used_area FLOAT," +
            "    timestamp DATETIME DEFAULT GETDATE()" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabela KnapsackResults gotowa");
        }
    }
    
    /**
     * Zapisuje wynik algorytmu do bazy danych
     * Czas w mikrosekundach dla lepszej precyzji
     */
    public void saveResult(AlgorithmResult result) throws SQLException {
        String insertSQL = 
            "INSERT INTO KnapsackResults " +
            "(algorithm_name, execution_time_us, waste_area, efficiency, blocks_count, total_area, used_area, skipped_blocks) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, result.getAlgorithmName());
            pstmt.setLong(2, (long) result.getExecutionTimeMicros()); // mikrosekundy
            pstmt.setDouble(3, result.getWasteArea());
            pstmt.setDouble(4, result.getEfficiency());
            pstmt.setInt(5, result.getBlocks() != null ? result.getBlocks().size() : 0);
            pstmt.setDouble(6, result.getTotalArea());
            pstmt.setDouble(7, result.getUsedArea());
            pstmt.setInt(8, result.getSkippedBlocks());
            
            pstmt.executeUpdate();
            System.out.println("Zapisano wynik: " + result.getAlgorithmName());
        }
    }
    
    /**
     * Pobiera ostatnie wyniki z bazy danych
     */
    public String getLastResults(int count) throws SQLException {
        String query = 
            "SELECT TOP " + count + " * FROM KnapsackResults ORDER BY timestamp DESC";
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Ostatnie wyniki z bazy danych ===\n\n");
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                sb.append(String.format("ID: %d\n", rs.getInt("id")));
                sb.append(String.format("Algorytm: %s\n", rs.getString("algorithm_name")));
                sb.append(String.format("Czas: %d µs\n", rs.getLong("execution_time_us")));
                sb.append(String.format("Odpad: %.2f\n", rs.getDouble("waste_area")));
                sb.append(String.format("Efektywność: %.2f%%\n", rs.getDouble("efficiency")));
                sb.append(String.format("Liczba klocków: %d\n", rs.getInt("blocks_count")));
                sb.append(String.format("Pominięte: %d\n", rs.getInt("skipped_blocks")));
                sb.append(String.format("Data: %s\n", rs.getTimestamp("timestamp")));
                sb.append("---\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Pobiera statystyki dla wszystkich algorytmów
     */
    public String getAlgorithmStatistics() throws SQLException {
        String query = 
            "SELECT algorithm_name, " +
            "       COUNT(*) as run_count, " +
            "       AVG(execution_time_us) as avg_time, " +
            "       AVG(waste_area) as avg_waste, " +
            "       AVG(efficiency) as avg_efficiency, " +
            "       MIN(waste_area) as min_waste, " +
            "       MAX(efficiency) as max_efficiency " +
            "FROM KnapsackResults " +
            "GROUP BY algorithm_name " +
            "ORDER BY avg_waste ASC";
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Statystyki algorytmów ===\n\n");
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                sb.append(String.format("Algorytm: %s\n", rs.getString("algorithm_name")));
                sb.append(String.format("  Uruchomień: %d\n", rs.getInt("run_count")));
                sb.append(String.format("  Średni czas: %.2f µs\n", rs.getDouble("avg_time")));
                sb.append(String.format("  Średni odpad: %.2f\n", rs.getDouble("avg_waste")));
                sb.append(String.format("  Średnia efektywność: %.2f%%\n", rs.getDouble("avg_efficiency")));
                sb.append(String.format("  Min odpad: %.2f\n", rs.getDouble("min_waste")));
                sb.append(String.format("  Max efektywność: %.2f%%\n", rs.getDouble("max_efficiency")));
                sb.append("---\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Zamyka połączenie z bazą danych
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Zamknięto połączenie z bazą danych");
            }
        } catch (SQLException e) {
            System.err.println("Błąd przy zamykaniu połączenia: " + e.getMessage());
        }
    }
    
    /**
     * Testuje połączenie z bazą danych
     */
    public static boolean testConnection() {
        try {
            DatabaseManager db = new DatabaseManager();
            db.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Błąd połączenia: " + e.getMessage());
            return false;
        }
    }
}
