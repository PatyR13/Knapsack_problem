package klockimans;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Klasa testowa do porównania wszystkich algorytmów
 * @author Patrycja Rybak
 */
public class AlgorithmTester {
    
    public static void main(String[] args) {
        // Ustaw kodowanie UTF-8 dla polskich znaków
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Nie można ustawić UTF-8: " + e.getMessage());
        }
        
        System.out.println("=== TEST ALGORYTMÓW UKŁADANIA KLOCKÓW ===\n");
        
        // Pytaj użytkownika o liczbę klocków
        int blockCount = 10;
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ile klocków chcesz wygenerować? (domyślnie 10): ");
            try {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    blockCount = Integer.parseInt(input);
                    if (blockCount < 1) {
                        System.out.println("Liczba musi być większa od 0. Używam domyślnej wartości: 10");
                        blockCount = 10;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa liczba. Używam domyślnej wartości: 10");
                blockCount = 10;
            }
        }
        System.out.println();
        
        // Generuj losowe klocki
        ArrayList<Klocek> klocki = generateRandomBlocks(blockCount);
        
        System.out.println("Wygenerowano " + klocki.size() + " klocków:");
        for (int i = 0; i < klocki.size(); i++) {
            Klocek k = klocki.get(i);
            System.out.printf("  %d. %dx%d (powierzchnia: %d)\n", 
                i+1, k.getWidth(), k.getHeight(), k.getArea());
        }
        System.out.println();
        
        // Tafla testowa
        Tafla tafla = new Tafla(1000, 800);
        System.out.println("Tafla: " + tafla.getWidth() + "x" + tafla.getHeight());
        System.out.println("Powierzchnia tafli: " + (tafla.getWidth() * tafla.getHeight()));
        System.out.println("=" .repeat(80));
        System.out.println();
        
        // Przechowuj wyniki do rankingu
        ArrayList<RankingEntry> ranking = new ArrayList<>();
        
        // Testuj wszystkie algorytmy
        ranking.add(testAlgorithm("Brute Force", 
            Algorytmy.bruteForcePacking(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        ranking.add(testAlgorithm("Zachłanny z obracaniem", 
            Algorytmy.greedyWithRotation(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        ranking.add(testAlgorithm("Sortowanie po powierzchni", 
            Algorytmy.greedySortedByArea(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        ranking.add(testAlgorithm("Sortowanie po wysokości", 
            Algorytmy.greedySortedByHeight(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        ranking.add(testAlgorithm("Sortowanie po szerokości", 
            Algorytmy.greedySortedByWidth(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        ranking.add(testAlgorithm("Best-Fit", 
            Algorytmy.bestFitPacking(tafla, copyBlocks(klocki)), tafla, klocki.size()));
        
        System.out.println("=" .repeat(80));
        
        // Wyświetl ranking
        displayRanking(ranking);
        
        System.out.println("\n✅ Test zakończony!");
        System.out.println("\n💡 Uruchom GUI aby zobaczyć wizualizację i zapisać wyniki do bazy.");
    }
    
    private static RankingEntry testAlgorithm(String name, AlgorithmResult result, Tafla tafla, int totalBlocks) {
        System.out.println("📊 " + name);
        System.out.println("-" .repeat(80));
        
        if (result.getBlocks() == null || result.getBlocks().isEmpty()) {
            System.out.println("⚠️  Algorytm nie zwrócił wyników (prawdopodobnie za dużo elementów)\n");
            return new RankingEntry(name, 0, 0, 0, 0, totalBlocks);
        }
        
        // Oblicz statystyki
        double usedArea = 0;
        
        for (Klocek k : result.getBlocks()) {
            usedArea += k.getWidth() * k.getHeight();
        }
        
        double totalArea = tafla.getWidth() * tafla.getHeight();
        double efficiency = (usedArea / totalArea) * 100.0;
        int skippedBlocks = totalBlocks - result.getBlocks().size();
        
        result.setUsedArea(usedArea);
        result.setTotalArea(totalArea);
        result.setEfficiency(efficiency);
        
        // Wyświetl wyniki
        long timeMs = result.getExecutionTimeMs();
        double timeMicros = result.getExecutionTimeMicros();
        
        if (timeMs < 10) {
            // Dla szybkich algorytmów pokazuj również mikrosekundy
            System.out.printf("  ⏱️  Czas wykonania: %d ms (%.0f μs)\n", timeMs, timeMicros);
        } else {
            System.out.printf("  ⏱️  Czas wykonania: %d ms\n", timeMs);
        }
        System.out.printf("  📦 Liczba ułożonych klocków: %d\n", result.getBlocks().size());
        System.out.printf("  ❌ Liczba pominiętych klocków: %d\n", skippedBlocks);
        System.out.printf("  📐 Powierzchnia tafli: %.0f\n", totalArea);
        System.out.printf("  ✅ Powierzchnia użyta: %.0f\n", usedArea);
        System.out.printf("  🗑️  Odpad: %.0f\n", result.getWasteArea());
        System.out.printf("  📈 Efektywność: %.2f%%\n", efficiency);
        System.out.println();
        
        return new RankingEntry(name, efficiency, result.getExecutionTimeMs(), 
                               result.getBlocks().size(), skippedBlocks, totalBlocks);
    }
    
    private static void displayRanking(ArrayList<RankingEntry> ranking) {
        System.out.println("\n🏆 RANKING ALGORYTMÓW 🏆");
        System.out.println("=" .repeat(80));
        
        // Sortuj po efektywności (malejąco), potem po czasie (rosnąco)
        ranking.sort(Comparator.comparingDouble(RankingEntry::getEfficiency)
                              .reversed()
                              .thenComparingLong(RankingEntry::getTime));
        
        System.out.println(String.format("%-35s %12s %10s %8s %10s", 
            "Algorytm", "Efektywność", "Czas [ms]", "Ułożone", "Pominięte"));
        System.out.println("-" .repeat(80));
        
        int position = 1;
        for (RankingEntry entry : ranking) {
            String medal = switch (position) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> "  ";
            };
            
            System.out.println(String.format("%s %-33s %11.2f%% %9d %8d %10d", 
                medal,
                entry.getName(),
                entry.getEfficiency(),
                entry.getTime(),
                entry.getPlacedBlocks(),
                entry.getSkippedBlocks()));
            position++;
        }
        
        System.out.println("=" .repeat(80));
    }
    
    // Klasa pomocnicza do przechowywania danych rankingu
    private static class RankingEntry {
        private final String name;
        private final double efficiency;
        private final long time;
        private final int placedBlocks;
        private final int skippedBlocks;
        
        public RankingEntry(String name, double efficiency, long time, 
                          int placedBlocks, int skippedBlocks, int totalBlocks) {
            this.name = name;
            this.efficiency = efficiency;
            this.time = time;
            this.placedBlocks = placedBlocks;
            this.skippedBlocks = skippedBlocks;
        }
        
        public String getName() { return name; }
        public double getEfficiency() { return efficiency; }
        public long getTime() { return time; }
        public int getPlacedBlocks() { return placedBlocks; }
        public int getSkippedBlocks() { return skippedBlocks; }
    }
    
    private static ArrayList<Klocek> generateRandomBlocks(int count) {
        ArrayList<Klocek> blocks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int width = (int)(Math.random() * 100 + 100);  // 100-200
            int height = (int)(Math.random() * 100 + 100); // 100-200
            blocks.add(new Klocek(width, height));
        }
        return blocks;
    }
    
    private static ArrayList<Klocek> copyBlocks(ArrayList<Klocek> original) {
        ArrayList<Klocek> copy = new ArrayList<>();
        for (Klocek k : original) {
            copy.add(k.copy());
        }
        return copy;
    }
}
