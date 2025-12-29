package klockimans;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Tester funkcji pobierania danych z bazy danych
 * @author Patrycja Rybak
 */
public class DatabaseTester {
    
    public static void main(String[] args) {
        // Ustaw kodowanie UTF-8 dla polskich znaków
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Nie można ustawić UTF-8: " + e.getMessage());
        }
        
        System.out.println("=== TEST FUNKCJI BAZY DANYCH ===\n");
        
        try (Scanner scanner = new Scanner(System.in)) {
            // Połącz z bazą danych
            DatabaseManager db = new DatabaseManager();
            System.out.println();
            
            // Upewnij się, że tabela istnieje
            db.createTableIfNotExists();
            System.out.println();
            
            boolean running = true;
            while (running) {
                System.out.println("=" .repeat(80));
                System.out.println("MENU:");
                System.out.println("  1. Wyświetl ostatnie wyniki (10)");
                System.out.println("  2. Wyświetl ostatnie wyniki (własna liczba)");
                System.out.println("  3. Wyświetl statystyki algorytmów");
                System.out.println("  4. Zapisz testowe wyniki");
                System.out.println("  5. Test połączenia");
                System.out.println("  0. Wyjście");
                System.out.println("=" .repeat(80));
                System.out.print("Wybierz opcję: ");
                
                String choice = scanner.nextLine().trim();
                System.out.println();
                
                switch (choice) {
                    case "1" -> {
                        // Ostatnie 10 wyników
                        System.out.println(db.getLastResults(10));
                    }
                    
                    case "2" -> {
                        // Własna liczba wyników
                        System.out.print("Ile wyników pobrać? ");
                        try {
                            int count = Integer.parseInt(scanner.nextLine().trim());
                            System.out.println();
                            System.out.println(db.getLastResults(count));
                        } catch (NumberFormatException e) {
                            System.out.println("❌ Nieprawidłowa liczba\n");
                        }
                    }
                    
                    case "3" -> {
                        // Statystyki algorytmów
                        System.out.println(db.getAlgorithmStatistics());
                    }
                    
                    case "4" -> {
                        // Zapisz testowe wyniki
                        saveTestResults(db);
                    }
                    
                    case "5" -> {
                        // Test połączenia
                        System.out.println("Testowanie połączenia...");
                        boolean connected = DatabaseManager.testConnection();
                        if (connected) {
                            System.out.println("✅ Połączenie działa poprawnie\n");
                        } else {
                            System.out.println("❌ Błąd połączenia\n");
                        }
                    }
                    
                    case "0" -> {
                        // Wyjście
                        System.out.println("Zamykanie...");
                        running = false;
                    }
                    
                    default -> System.out.println("❌ Nieprawidłowa opcja\n");
                }
            }
            
            // Zamknij połączenie
            db.close();
            System.out.println("\n✅ Test zakończony!");
            
        } catch (SQLException e) {
            System.err.println("❌ Błąd bazy danych: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Zapisuje przykładowe wyniki testowe do bazy
     */
    private static void saveTestResults(DatabaseManager db) {
        System.out.println("Zapisywanie testowych wyników...\n");
        
        // Tafla testowa
        Tafla tafla = new Tafla(1000, 800);
        
        // Test 1: Zachłanny
        AlgorithmResult result1 = new AlgorithmResult("Test - Zachłanny");
        result1.setExecutionTimeNano(500_000); // 500 μs
        result1.setTotalArea(tafla.getWidth() * tafla.getHeight());
        result1.setUsedArea(600_000);
        result1.setWasteArea(200_000);
        result1.setEfficiency(75.0);
        result1.setSkippedBlocks(2);
        
        // Test 2: Best-Fit
        AlgorithmResult result2 = new AlgorithmResult("Test - Best-Fit");
        result2.setExecutionTimeNano(2_000_000); // 2000 μs
        result2.setTotalArea(tafla.getWidth() * tafla.getHeight());
        result2.setUsedArea(650_000);
        result2.setWasteArea(150_000);
        result2.setEfficiency(81.25);
        result2.setSkippedBlocks(1);
        
        try {
            db.saveResult(result1);
            db.saveResult(result2);
            System.out.println("✅ Zapisano 2 testowe wyniki\n");
        } catch (SQLException e) {
            System.err.println("❌ Błąd zapisu: " + e.getMessage() + "\n");
        }
    }
}
