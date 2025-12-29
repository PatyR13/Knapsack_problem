package klockimans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Prosta klasa do ładowania zmiennych środowiskowych z pliku .env
 * @author Patrycja Rybak
 */
public class EnvLoader {
    
    private static Map<String, String> envVars = new HashMap<>();
    private static boolean loaded = false;
    
    /**
     * Ładuje zmienne z pliku .env
     */
    public static void load() {
        if (loaded) return; // Załaduj tylko raz
        
        String envFile = ".env";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(envFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Pomiń puste linie i komentarze
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                // Parsuj linię KEY=VALUE
                int separatorIndex = line.indexOf('=');
                if (separatorIndex > 0) {
                    String key = line.substring(0, separatorIndex).trim();
                    String value = line.substring(separatorIndex + 1).trim();
                    
                    // Usuń cudzysłowy jeśli są
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    
                    envVars.put(key, value);
                }
            }
            loaded = true;
            System.out.println("✓ Załadowano konfigurację z pliku .env");
        } catch (IOException e) {
            System.err.println("⚠ Nie znaleziono pliku .env - używam wartości domyślnych");
            System.err.println("  Utwórz plik .env na podstawie .env.example");
        }
    }
    
    /**
     * Pobiera wartość zmiennej środowiskowej
     * @param key Klucz
     * @return Wartość lub null jeśli nie istnieje
     */
    public static String get(String key) {
        if (!loaded) load();
        return envVars.get(key);
    }
    
    /**
     * Pobiera wartość zmiennej środowiskowej z wartością domyślną
     * @param key Klucz
     * @param defaultValue Wartość domyślna
     * @return Wartość lub defaultValue jeśli nie istnieje
     */
    public static String get(String key, String defaultValue) {
        if (!loaded) load();
        return envVars.getOrDefault(key, defaultValue);
    }
    
    /**
     * Sprawdza czy klucz istnieje
     */
    public static boolean has(String key) {
        if (!loaded) load();
        return envVars.containsKey(key);
    }
    
    /**
     * Wyświetla wszystkie załadowane zmienne (dla debugowania)
     */
    public static void debug() {
        if (!loaded) load();
        System.out.println("=== Zmienne środowiskowe z .env ===");
        envVars.forEach((key, value) -> {
            // Maskuj hasła
            if (key.toLowerCase().contains("password")) {
                System.out.println(key + " = ********");
            } else {
                System.out.println(key + " = " + value);
            }
        });
    }
}
