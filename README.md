# 🎯 Knapsack Problem / Problem Plecakowy 2D

[![Java](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![NetBeans](https://img.shields.io/badge/IDE-NetBeans-blue.svg)](https://netbeans.apache.org/)

## 📖 About the Project / O Projekcie

### 🇬🇧 English

**2D Bin Packing Optimization Solver** - An educational project implementing six different algorithms for arranging rectangular blocks on a sheet to minimize waste (unused space).

**Key Features:**
- 🧮 **7 Optimization Algorithms**: Basic greedy, Brute Force, Greedy with Rotation, Best-Fit, and 3 sorting strategies
- 📊 **Performance Comparison**: Compare all algorithms on the same dataset with ranking system
- 🖥️ **GUI with Visualization**: Interactive interface for testing and visualizing results
- 🗄️ **SQL Server Integration**: Store and analyze results in database
- 🧪 **Testing Tools**: Console-based algorithm tester and database tester
- 📈 **Detailed Metrics**: Execution time (µs), efficiency, waste area, skipped blocks

**Technologies:** Java, Swing, SQL Server, JDBC

**Educational Project** for Warsaw School of Computer Science (WWSI) - Advanced IDE and AI-Assisted Development course.

---

### 🇵🇱 Polski

**Solver optymalizacji układania elementów 2D** - Projekt edukacyjny implementujący sześć różnych algorytmów układania prostokątnych klocków na tafli w celu minimalizacji odpadu (niewykorzystanej przestrzeni).

**Główne funkcje:**
- 🧮 **7 Algorytmów Optymalizacji**: Podstawowy zachłanny, Brute Force, Zachłanny z obracaniem, Best-Fit oraz 3 strategie sortowania
- 📊 **Porównanie Wydajności**: Porównaj wszystkie algorytmy na tych samych danych z systemem rankingowym
- 🖥️ **GUI z Wizualizacją**: Interaktywny interfejs do testowania i wizualizacji wyników
- 🗄️ **Integracja z SQL Server**: Zapisywanie i analiza wyników w bazie danych
- 🧪 **Narzędzia Testowe**: Konsolowe testery algorytmów i bazy danych
- 📈 **Szczegółowe Metryki**: Czas wykonania (µs), efektywność, odpad, pominięte klocki

**Technologie:** Java, Swing, SQL Server, JDBC

**Projekt edukacyjny** dla Warszawskiej Wyższej Szkoły Informatyki (WWSI) - przedmiot Tworzenie aplikacji z wykorzystaniem IDE oraz wsparciem AI (TAZW).

---

## 📚 Documentation / Dokumentacja

- 📖 **[INDEX.md](INDEX.md)** - Complete project overview / Pełny spis treści projektu
- 📝 **[SPRAWOZDANIE.md](SPRAWOZDANIE.md)** - Technical report with results / Sprawozdanie techniczne z wynikami  
- ❓ **[FAQ.md](FAQ.md)** - Frequently asked questions / Najczęściej zadawane pytania
- 🔐 **[SECURITY.md](SECURITY.md)** - Security guidelines / Zasady bezpieczeństwa

---

## 🎯 QUICK START / SZYBKI START

## 🚀 Szybki Start

### 1. Zainstaluj sterownik JDBC (WAŻNE!)
```
1. Otwórz: lib/README_JDBC.md
2. Pobierz: mssql-jdbc-12.4.2.jre11.jar
3. W NetBeans: Projekt → Properties → Libraries → Add JAR
4. Wybierz pobrany plik .jar
```

### 2. Skonfiguruj połączenie z bazą danych
```
1. Skopiuj plik .env.example do .env
2. Edytuj .env - ustaw dane swojej bazy
3. Domyślnie: SQL Authentication (login + hasło)
4. Zobacz szczegóły: DATABASE_CONFIG.md
```

### 3. Uruchom aplikację GUI
```
1. Otwórz projekt w NetBeans
2. Uruchom: GUI.java (kliknij prawym → Run File)
   LUB naciśnij F6 aby uruchomić cały projekt
```

### 4. Testuj algorytmy
```
1. Kliknij "Generowanie" - podaj liczbę klocków (1-100)
2. Kliknij "Układanie" - wybierz algorytm LUB "📊 PORÓWNAJ WSZYSTKIE"
3. Zobacz wyniki - odpad, czas, efektywność, pominięte
4. Zapisz do bazy danych - kliknij "Tak" w dialogu
```

### 5. Porównaj wyniki
```
1. Kliknij "Zapis"
2. Wybierz "Pokaż statystyki algorytmów"
3. Zobacz które algorytmy są najlepsze!
```

---

## 📋 Dostępne Algorytmy

| # | Algorytm | Opis | Ograniczenia |
|---|----------|------|--------------|
| 0 | **Podstawowy** | Prosty zachłanny bez optymalizacji | Wersja bazowa |
| 1 | **Brute Force** | Wszystkie permutacje | ⚠️ Max 10 klocków |
| 2 | **Zachłanny z obracaniem** | Testuje 0° i 90° | - |
| 3 | **Sort. powierzchnia** | Największe pierwsze | - |
| 4 | **Sort. wysokość** | Najwyższe pierwsze | - |
| 5 | **Sort. szerokość** | Najszersze pierwsze | - |
| 6 | **Best-Fit** | Najlepsze dopasowanie | Wolniejszy |

---

## 🧪 Szybki Test (bez GUI)

### Test algorytmów:
Uruchom klasę testową:
```bash
AlgorithmTester.java
```
Funkcje:
- ⌨️ Pytanie o liczbę klocków (domyślnie 10)
- 🏆 Porównanie wszystkich algorytmów
- ⏱️ Czas w µs dla szybkich algorytmów (< 10ms)
- 📊 Ranking końcowy z medalami 🥇🥈🥉
- 📈 Szczegółowe statystyki każdego algorytmu

### Test bazy danych:
Uruchom tester bazy:
```bash
DatabaseTester.java
```
Funkcje:
- 📋 Wyświetlanie ostatnich wyników (10 lub dowolna liczba)
- 📊 Statystyki algorytmów (średnie czasy, odpad, efektywność)
- 💾 Zapisywanie testowych wyników
- 🔌 Test połączenia z bazą danych
- 🎯 Interaktywne menu - testuj bez ponownego uruchamiania

---

## 📊 Co zmierzyć do sprawozdania?

Dla każdego algorytmu zapisz:
- ⏱️ **Czas wykonania** (µs/ms/s - zależnie od algorytmu)
- 📦 **Odpad** (niewykorzystana przestrzeń)
- 📈 **Efektywność** (% wykorzystania)
- 🚫 **Pominięte klocki** (te które nie zmieściły się w tafli)

**Tip:** Użyj funkcji **"📊 PORÓWNAJ WSZYSTKIE"** aby przetestować wszystkie algorytmy na tych samych klockach!

---

## 🖥️ INTERFEJS UŻYTKOWNIKA (GUI)

### Przyciski:

1. **Generowanie** - Generuje klocki o wymiarach 100-199 × 100-199 px
   - Możliwość wyboru liczby klocków (1-100)
   - Opcje: nowe/dodaj do istniejących/wyczyść
2. **Układanie** - Wybór algorytmu z listy rozwijanej:
   - Algorytm podstawowy
   - Brute Force (max 10 elementów)
   - Zachłanny z obracaniem
   - Zachłanny - sortowanie po powierzchni
   - Zachłanny - sortowanie po wysokości
   - Zachłanny - sortowanie po szerokości
   - Best-Fit
   - **📊 PORÓWNAJ WSZYSTKIE ALGORYTMY**

3. **Zapis** - Menu z opcjami:
   - Zapisz klocki do pliku (dane.txt)
   - Pokaż ostatnie wyniki z BD
   - Pokaż statystyki algorytmów

4. **Odczyt** - Wczytuje klocki z pliku dane.txt

### Wyświetlanie wyników:
Po każdym uruchomieniu algorytmu pokazywany jest dialog z:
- Nazwa algorytmu
- Czas wykonania
- Liczba klocków
- Powierzchnia użyta / całkowita
- Odpad
- Efektywność
- Opcja zapisu do bazy danych

---

## 🗄️ Baza Danych

### Automatyczne tworzenie tabeli ✅
Aplikacja automatycznie tworzy tabelę `KnapsackResults` przy pierwszym uruchomieniu.

### Ręczne utworzenie (jeśli potrzebne):
```sql
USE fullstack25_spod-351pr;

CREATE TABLE KnapsackResults (
    id INT IDENTITY(1,1) PRIMARY KEY,
    algorithm_name NVARCHAR(100) NOT NULL,
    execution_time_us BIGINT NOT NULL,        -- czas w mikrosekundach (µs)
    waste_area FLOAT NOT NULL,
    efficiency FLOAT NOT NULL,
    blocks_count INT NOT NULL,
    total_area FLOAT,
    used_area FLOAT,
    skipped_blocks INT DEFAULT 0,             -- klocki pominięte/poza taflą
    timestamp DATETIME DEFAULT GETDATE()
);
```

### Przykładowe zapytania SQL:
```sql
-- Wszystkie wyniki
SELECT * FROM KnapsackResults ORDER BY timestamp DESC;

-- Najlepsze wyniki (najmniejszy odpad)
SELECT TOP 10 * FROM KnapsackResults 
ORDER BY waste_area ASC;

-- Średnie dla każdego algorytmu
SELECT 
    algorithm_name,
    COUNT(*) as runs,
    AVG(execution_time_us) as avg_time_us,     -- mikrosekundy
    AVG(waste_area) as avg_waste,
    AVG(efficiency) as avg_efficiency
FROM KnapsackResults
GROUP BY algorithm_name
ORDER BY avg_waste ASC;
```

---

## 🐛 Rozwiązywanie Problemów

💡 **Więcej rozwiązań problemów:** Zobacz [FAQ.md](FAQ.md) - szczegółowe odpowiedzi na najczęstsze pytania!

### ❌ "Brak sterownika JDBC"
**Rozwiązanie:** Zobacz `lib/README_JDBC.md` i dodaj plik .jar do bibliotek

### ❌ "Błąd połączenia z bazą danych"
**Opcja 1:** Sprawdź nazwę serwera i bazy w `DatabaseManager.java`
**Opcja 2:** Aplikacja działa bez bazy! Po prostu nie klikaj "Tak" przy zapisie wyników

### ❌ "Brute Force nie działa"
**Powód:** Masz więcej niż 10 klocków!
**Rozwiązanie:** Zmodyfikuj `jButton1ActionPerformed` aby generować mniej klocków

---

## 📁 Struktura Projektu

```
src/klockimans/
├── Klocek.java              ← Klasa elementu (rotate, copy, getArea)
├── Tafla.java               ← Klasa tafli
├── Algorytmy.java           ← ⭐ WSZYSTKIE ALGORYTMY ⭐
├── AlgorithmResult.java     ← Wyniki algorytmu (czas, odpad, efektywność)
├── DatabaseManager.java     ← Połączenie z SQL Server
├── EnvLoader.java           ← Ładowanie konfiguracji .env
├── GUI.java                 ← ⭐ Interfejs graficzny (main) ⭐
├── MyPanel.java             ← Panel rysowania
├── AlgorithmTester.java     ← 🧪 Tester algorytmów (interaktywny)
└── DatabaseTester.java      ← 🗄️ Tester bazy danych (interaktywny)
```

## 🎓 Przydatne Komendy SQL

### Eksport wyników do CSV (dla Excela):
```sql
-- W SQL Server Management Studio:
-- Query → Results To → Results To File
-- Następnie uruchom:
SELECT * FROM KnapsackResults;
```

### Czyszczenie danych testowych:
```sql
-- UWAGA: To usunie WSZYSTKIE wyniki!
DELETE FROM KnapsackResults;

-- Lub usuń tylko wybrane:
DELETE FROM KnapsackResults 
WHERE algorithm_name = 'Brute Force';
```

---

