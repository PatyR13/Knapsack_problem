package klockimans;

import java.util.ArrayList;

/**
 * Klasa przechowująca wyniki działania algorytmu
 * @author Patrycja Rybak (rozszerzenie projektu)
 */
public class AlgorithmResult {
    
    private String algorithmName;
    private long executionTimeNano; // czas w nanosekundach dla precyzji
    private double wasteArea;
    private double usedArea;
    private double totalArea;
    private double efficiency; // procent wykorzystania
    private ArrayList<Klocek> blocks;
    private int skippedBlocks; // liczba klocków które się nie zmieściły
    private int blockCount; // liczba klocków które w pełni zmieściły się w tafli
    
    public AlgorithmResult(String algorithmName) {
        this.algorithmName = algorithmName;
        this.blocks = new ArrayList<>();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    // Zwraca czas w ms (dla kompatybilności z bazą danych)
    public long getExecutionTimeMs() {
        return executionTimeNano / 1_000_000;
    }

    // Ustawia czas w ms (konwertuje na nano)
    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeNano = executionTimeMs * 1_000_000;
    }
    
    // Zwraca czas w mikrosekundach (µs) - dla precyzyjnego wyświetlania
    public double getExecutionTimeMicros() {
        return executionTimeNano / 1_000.0;
    }
    
    // Ustawia czas w nanosekundach (dla precyzji)
    public void setExecutionTimeNano(long nanos) {
        this.executionTimeNano = nanos;
    }
    
    // Zwraca sformatowany czas (auto-wybór jednostki)
    public String getFormattedTime() {
        if (executionTimeNano < 1_000) {
            return executionTimeNano + " ns";
        } else if (executionTimeNano < 1_000_000) {
            return String.format("%.2f µs", executionTimeNano / 1_000.0);
        } else if (executionTimeNano < 1_000_000_000) {
            return String.format("%.2f ms", executionTimeNano / 1_000_000.0);
        } else {
            return String.format("%.2f s", executionTimeNano / 1_000_000_000.0);
        }
    }

    public double getWasteArea() {
        return wasteArea;
    }

    public void setWasteArea(double wasteArea) {
        this.wasteArea = wasteArea;
    }

    public double getUsedArea() {
        return usedArea;
    }

    public void setUsedArea(double usedArea) {
        this.usedArea = usedArea;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public ArrayList<Klocek> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Klocek> blocks) {
        this.blocks = blocks;
    }

    public int getSkippedBlocks() {
        return skippedBlocks;
    }

    public void setSkippedBlocks(int skippedBlocks) {
        this.skippedBlocks = skippedBlocks;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(int blockCount) {
        this.blockCount = blockCount;
    }

    @Override
    public String toString() {
        return String.format("%s: Czas=%s, Odpad=%.2f, Efektywność=%.2f%%", 
                algorithmName, getFormattedTime(), wasteArea, efficiency);
    }
}
