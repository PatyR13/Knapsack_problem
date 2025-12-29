/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package klockimans;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author pfigat
 */
public class Algorytmy {
    
  public static void metoda1(Tafla t,ArrayList<Klocek>  tabKlocek  )
    {
    
        int sumx = 0;
        
        for (Klocek klocek : tabKlocek) {
        
             klocek.setPosx(sumx);
             klocek.setPosy(0);
             
             sumx+= klocek.getWidth();
          
        }
    
    
    
    }
    
    public static void metoda2(Tafla t,ArrayList<Klocek>  tabKlocek  )
    {
        int sumx = 0;
        int sumy=0;
        for (Klocek klocek : tabKlocek) {
        
            if(sumx+klocek.getWidth()>t.getWidth())
            {
            sumy+= klocek.getHeight();
            sumx=0;
            }
            
             klocek.setPosx(sumx);
             klocek.setPosy(sumy);
             
             sumx+= klocek.getWidth();
          
        }
 
    }
    
      public static void podstawowy(Tafla t,ArrayList<Klocek>  tabKlocek  )
    {
        int sumx = 0;
        int sumy=0;
        int Maxh =0;
        
        for (Klocek klocek : tabKlocek) {
 
            if(sumx+klocek.getWidth()>t.getWidth())
            {
            sumy+= Maxh;
            sumx=0;
            Maxh=0;
            }
              if(Maxh<klocek.getHeight())
              Maxh= klocek.getHeight();
             klocek.setPosx(sumx);
             klocek.setPosy(sumy);
             
             sumx+= klocek.getWidth();
          
        }
 
    }
    
    /**
     * Metoda podstawowa z kontrolą granic tafli
     * Zwraca listę klocków które się zmieściły i liczbę pominiętych
     */
    public static int podstawowyWithBounds(Tafla t, ArrayList<Klocek> tabKlocek, ArrayList<Klocek> arranged) {
        int sumx = 0;
        int sumy = 0;
        int maxH = 0;
        int skipped = 0;
        
        for (Klocek klocek : tabKlocek) {
            // Sprawdź czy trzeba przejść do nowego rzędu
            if (sumx + klocek.getWidth() > t.getWidth()) {
                sumy += maxH;
                sumx = 0;
                maxH = 0;
            }
            
            // Sprawdź czy klocek mieści się w wysokości tafli
            if (sumy + klocek.getHeight() > t.getHeight()) {
                skipped++;
                continue; // Pomiń ten klocek
            }
            
            if (maxH < klocek.getHeight()) {
                maxH = klocek.getHeight();
            }
            
            klocek.setPosx(sumx);
            klocek.setPosy(sumy);
            arranged.add(klocek);
            
            sumx += klocek.getWidth();
        }
        
        return skipped;
    }
    
    // ============== NOWE ALGORYTMY ==============
    
    /**
     * Oblicza odpad (przestrzeń między klockami)
     * Odpad = (prostokąt ograniczający - suma powierzchni klocków)
     */
    public static double calculateWaste(ArrayList<Klocek> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            return 0;
        }
        
        // Znajdź prostokąt ograniczający
        int maxX = 0;
        int maxY = 0;
        
        for (Klocek k : blocks) {
            int rightEdge = k.getPosx() + k.getWidth();
            int bottomEdge = k.getPosy() + k.getHeight();
            
            if (rightEdge > maxX) maxX = rightEdge;
            if (bottomEdge > maxY) maxY = bottomEdge;
        }
        
        // Oblicz powierzchnię prostokąta ograniczającego
        double boundingArea = maxX * maxY;
        
        // Oblicz sumę powierzchni klocków
        double blocksArea = 0;
        for (Klocek k : blocks) {
            blocksArea += k.getWidth() * k.getHeight();
        }
        
        // Odpad = różnica
        return boundingArea - blocksArea;
    }
    
    /**
     * ALGORYTM 1: Brute Force - sprawdza wszystkie permutacje
     * Działa tylko dla małej liczby elementów (max 10)
     */
    public static AlgorithmResult bruteForcePacking(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Brute Force");
        
        if (blocks.size() > 10) {
            result.setExecutionTimeNano(System.nanoTime() - startTime);
            result.setWasteArea(Double.MAX_VALUE);
            return result; // Za dużo elementów
        }
        
        ArrayList<Klocek> bestArrangement = null;
        double minWaste = Double.MAX_VALUE;
        
        // Generuj wszystkie permutacje
        ArrayList<ArrayList<Klocek>> permutations = generatePermutations(blocks);
        
        int bestSkipped = Integer.MAX_VALUE;
        
        for (ArrayList<Klocek> perm : permutations) {
            // Dla każdej permutacji wypróbuj układ z kontrolą granic
            ArrayList<Klocek> sorted = new ArrayList<>();
            for (Klocek k : perm) {
                sorted.add(k.copy());
            }
            
            ArrayList<Klocek> arranged = new ArrayList<>();
            int skipped = podstawowyWithBounds(tafla, sorted, arranged);
            
            // Oblicz odpad
            double waste = calculateWaste(arranged);
            
            // Sprawdź czy to najlepsze rozwiązanie (najpierw więcej klocków, potem mniejszy odpad)
            boolean isBetter = (skipped < bestSkipped) || 
                               (skipped == bestSkipped && waste < minWaste);
            
            if (isBetter) {
                minWaste = waste;
                bestArrangement = arranged;
                bestSkipped = skipped;
            }
        }
        
        result.setBlocks(bestArrangement);
        result.setSkippedBlocks(bestSkipped);
        result.setWasteArea(minWaste);
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * Generuje wszystkie permutacje listy klocków
     */
    private static ArrayList<ArrayList<Klocek>> generatePermutations(ArrayList<Klocek> blocks) {
        ArrayList<ArrayList<Klocek>> result = new ArrayList<>();
        permute(blocks, 0, result);
        return result;
    }
    
    private static void permute(ArrayList<Klocek> arr, int k, ArrayList<ArrayList<Klocek>> result) {
        if (k == arr.size()) {
            ArrayList<Klocek> copy = new ArrayList<>();
            for (Klocek klocek : arr) {
                copy.add(klocek.copy());
            }
            result.add(copy);
        } else {
            for (int i = k; i < arr.size(); i++) {
                Collections.swap(arr, i, k);
                permute(arr, k + 1, result);
                Collections.swap(arr, k, i);
            }
        }
    }
    
    /**
     * ALGORYTM 2: Zachłanny z obracaniem elementów
     * Dla każdego klocka próbuje obu orientacji i wybiera lepszą
     */
    public static AlgorithmResult greedyWithRotation(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Zachłanny z obracaniem");
        
        ArrayList<Klocek> arranged = new ArrayList<>();
        int skipped = 0;
        
        int sumx = 0;
        int sumy = 0;
        int maxHeight = 0;
        
        for (Klocek original : blocks) {
            // Skopiuj klocek
            Klocek k1 = original.copy();
            Klocek k2 = original.copy();
            k2.rotate();
            
            // Sprawdź obie orientacje
            boolean useRotated = false;
            
            // Jeśli klocek nie mieści się w bieżącym rzędzie
            if (sumx + k1.getWidth() > tafla.getWidth()) {
                // Sprawdź która orientacja jest lepsza dla nowego rzędu
                if (k2.getWidth() <= tafla.getWidth() && k2.getHeight() < k1.getHeight()) {
                    useRotated = true;
                }
            } else {
                // Klocek mieści się - wybierz niższą orientację
                if (k2.getHeight() < k1.getHeight() && sumx + k2.getWidth() <= tafla.getWidth()) {
                    useRotated = true;
                }
            }
            
            Klocek toPlace = useRotated ? k2 : k1;
            
            // Sprawdź czy trzeba przejść do nowego rzędu
            if (sumx + toPlace.getWidth() > tafla.getWidth()) {
                sumy += maxHeight;
                sumx = 0;
                maxHeight = 0;
            }
            
            // Sprawdź czy klocek mieści się w wysokości tafli
            if (sumy + toPlace.getHeight() > tafla.getHeight()) {
                skipped++;
                continue; // Pomiń ten klocek
            }
            
            // Ustaw pozycję
            toPlace.setPosx(sumx);
            toPlace.setPosy(sumy);
            
            // Aktualizuj pozycje
            sumx += toPlace.getWidth();
            if (toPlace.getHeight() > maxHeight) {
                maxHeight = toPlace.getHeight();
            }
            
            arranged.add(toPlace);
        }
        
        result.setBlocks(arranged);
        result.setSkippedBlocks(skipped);
        result.setWasteArea(calculateWaste(arranged));
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * ALGORYTM 3a: Zachłanny z sortowaniem po powierzchni (malejąco)
     */
    public static AlgorithmResult greedySortedByArea(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Zachłanny - sortowanie po powierzchni");
        
        // Skopiuj i posortuj
        ArrayList<Klocek> sorted = new ArrayList<>();
        for (Klocek k : blocks) {
            sorted.add(k.copy());
        }
        sorted.sort((a, b) -> Integer.compare(b.getArea(), a.getArea()));
        
        // Ułóż posortowane klocki z kontrolą granic
        ArrayList<Klocek> arranged = new ArrayList<>();
        int skipped = podstawowyWithBounds(tafla, sorted, arranged);
        
        result.setBlocks(arranged);
        result.setSkippedBlocks(skipped);
        result.setWasteArea(calculateWaste(arranged));
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * ALGORYTM 3b: Zachłanny z sortowaniem po wysokości (malejąco)
     */
    public static AlgorithmResult greedySortedByHeight(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Zachłanny - sortowanie po wysokości");
        
        ArrayList<Klocek> sorted = new ArrayList<>();
        for (Klocek k : blocks) {
            sorted.add(k.copy());
        }
        sorted.sort((a, b) -> Integer.compare(b.getHeight(), a.getHeight()));
        
        // Ułóż posortowane klocki z kontrolą granic
        ArrayList<Klocek> arranged = new ArrayList<>();
        int skipped = podstawowyWithBounds(tafla, sorted, arranged);
        
        result.setBlocks(arranged);
        result.setSkippedBlocks(skipped);
        result.setWasteArea(calculateWaste(arranged));
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * ALGORYTM 3c: Zachłanny z sortowaniem po szerokości (malejąco)
     */
    public static AlgorithmResult greedySortedByWidth(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Zachłanny - sortowanie po szerokości");
        
        ArrayList<Klocek> sorted = new ArrayList<>();
        for (Klocek k : blocks) {
            sorted.add(k.copy());
        }
        sorted.sort((a, b) -> Integer.compare(b.getWidth(), a.getWidth()));
        
        // Ułóż posortowane klocki z kontrolą granic
        ArrayList<Klocek> arranged = new ArrayList<>();
        int skipped = podstawowyWithBounds(tafla, sorted, arranged);
        
        result.setBlocks(arranged);
        result.setSkippedBlocks(skipped);
        result.setWasteArea(calculateWaste(arranged));
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * ALGORYTM 4: Best-Fit
     * Dla każdego klocka znajduje najlepsze miejsce (minimalizujące odpad)
     */
    public static AlgorithmResult bestFitPacking(Tafla tafla, ArrayList<Klocek> blocks) {
        long startTime = System.nanoTime();
        AlgorithmResult result = new AlgorithmResult("Best-Fit");
        
        ArrayList<Klocek> arranged = new ArrayList<>();
        ArrayList<Position> freePositions = new ArrayList<>();
        int skipped = 0;
        
        // Początkowa pozycja
        freePositions.add(new Position(0, 0));
        
        for (Klocek original : blocks) {
            Klocek best = null;
            Position bestPos = null;
            double minLocalWaste = Double.MAX_VALUE;
            
            // Sprawdź wszystkie wolne pozycje i obie orientacje
            for (Position pos : freePositions) {
                for (int rotation = 0; rotation < 2; rotation++) {
                    Klocek k = original.copy();
                    if (rotation == 1) k.rotate();
                    
                    k.setPosx(pos.x);
                    k.setPosy(pos.y);
                    
                    // Sprawdź czy mieści się w tafli
                    if (k.getPosx() + k.getWidth() <= tafla.getWidth() &&
                        k.getPosy() + k.getHeight() <= tafla.getHeight()) {
                        
                        // Sprawdź kolizje z istniejącymi klockami
                        if (!hasCollision(k, arranged)) {
                            // Oblicz lokalny odpad (przestrzeń wokół klocka)
                            double localWaste = calculateLocalWaste(k, arranged, tafla);
                            
                            if (localWaste < minLocalWaste) {
                                minLocalWaste = localWaste;
                                best = k;
                                bestPos = pos;
                            }
                        }
                    }
                }
            }
            
            if (best != null) {
                arranged.add(best);
                freePositions.remove(bestPos);
                
                // Dodaj nowe wolne pozycje
                freePositions.add(new Position(best.getPosx() + best.getWidth(), best.getPosy()));
                freePositions.add(new Position(best.getPosx(), best.getPosy() + best.getHeight()));
                
                // Usuń pozycje poza taflą lub zajęte
                freePositions.removeIf(p -> 
                    p.x >= tafla.getWidth() || 
                    p.y >= tafla.getHeight() ||
                    isPositionOccupied(p, arranged)
                );
            } else {
                skipped++;
            }
        }
        
        result.setBlocks(arranged);
        result.setSkippedBlocks(skipped);
        result.setWasteArea(calculateWaste(arranged));
        result.setExecutionTimeNano(System.nanoTime() - startTime);
        
        return result;
    }
    
    /**
     * Sprawdza czy klocek koliduje z innymi
     */
    private static boolean hasCollision(Klocek k, ArrayList<Klocek> placed) {
        for (Klocek other : placed) {
            if (rectanglesOverlap(k, other)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Sprawdza czy dwa prostokąty nachodzą na siebie
     */
    private static boolean rectanglesOverlap(Klocek a, Klocek b) {
        return !(a.getPosx() + a.getWidth() <= b.getPosx() ||
                 b.getPosx() + b.getWidth() <= a.getPosx() ||
                 a.getPosy() + a.getHeight() <= b.getPosy() ||
                 b.getPosy() + b.getHeight() <= a.getPosy());
    }
    
    /**
     * Oblicza lokalny odpad wokół klocka
     */
    private static double calculateLocalWaste(Klocek k, ArrayList<Klocek> placed, Tafla tafla) {
        // Prosta heurystyka: odległość od najbliższych klocków
        double waste = 0;
        
        // Sprawdź przestrzeń po prawej
        int rightSpace = tafla.getWidth() - (k.getPosx() + k.getWidth());
        
        // Sprawdź czy jest jakiś klocek po prawej w tym samym rzędzie
        boolean hasRightNeighbor = false;
        for (Klocek other : placed) {
            if (other.getPosx() >= k.getPosx() + k.getWidth() &&
                other.getPosy() < k.getPosy() + k.getHeight() &&
                other.getPosy() + other.getHeight() > k.getPosy()) {
                hasRightNeighbor = true;
                break;
            }
        }
        
        if (!hasRightNeighbor) {
            waste += rightSpace * k.getHeight() * 0.5; // Waga 0.5 dla przestrzeni końcowej
        }
        
        return waste;
    }
    
    /**
     * Sprawdza czy pozycja jest zajęta
     */
    private static boolean isPositionOccupied(Position pos, ArrayList<Klocek> placed) {
        for (Klocek k : placed) {
            if (pos.x >= k.getPosx() && pos.x < k.getPosx() + k.getWidth() &&
                pos.y >= k.getPosy() && pos.y < k.getPosy() + k.getHeight()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Klasa pomocnicza reprezentująca pozycję
     */
    static class Position {
        int x, y;
        
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
}
