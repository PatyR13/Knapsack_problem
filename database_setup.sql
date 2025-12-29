-- ====================================================================
-- SKRYPT SQL DLA PROJEKTU UKŁADANIE KLOCKÓW
-- Baza danych: fullstack25_spod-351pr
-- Serwer: NT-27\WWSI2014
-- ====================================================================

USE fullstack25_spod-351pr;
GO

-- ====================================================================
-- 1. TWORZENIE TABELI KnapsackResults
-- ====================================================================

-- Usuń tabelę jeśli istnieje (OPCJONALNE - tylko podczas developmentu)
-- IF EXISTS (SELECT * FROM sysobjects WHERE name='KnapsackResults' AND xtype='U')
-- DROP TABLE KnapsackResults;
-- GO

-- Utwórz tabelę
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='KnapsackResults' AND xtype='U')
BEGIN
    CREATE TABLE KnapsackResults (
        id INT IDENTITY(1,1) PRIMARY KEY,
        algorithm_name NVARCHAR(100) NOT NULL,
        execution_time_us BIGINT NOT NULL,          -- czas w mikrosekundach (µs)
        waste_area FLOAT NOT NULL,
        efficiency FLOAT NOT NULL,
        blocks_count INT NOT NULL,
        total_area FLOAT NULL,
        used_area FLOAT NULL,
        skipped_blocks INT DEFAULT 0,               -- klocki pominięte/poza taflą
        timestamp DATETIME DEFAULT GETDATE()
    );
    
    PRINT 'Tabela KnapsackResults została utworzona.';
END
ELSE
BEGIN
    PRINT 'Tabela KnapsackResults już istnieje.';
END
GO

-- ====================================================================
-- 2. PRZYKŁADOWE ZAPYTANIA
-- ====================================================================

-- Wyświetl wszystkie wyniki
SELECT * FROM KnapsackResults 
ORDER BY timestamp DESC;
GO

-- Wyświetl ostatnie 10 wyników
SELECT TOP 10 * FROM KnapsackResults 
ORDER BY timestamp DESC;
GO

-- Statystyki dla każdego algorytmu
SELECT 
    algorithm_name as 'Algorytm',
    COUNT(*) as 'Liczba uruchomień',
    AVG(execution_time_us) as 'Średni czas [µs]',
    AVG(waste_area) as 'Średni odpad',
    AVG(efficiency) as 'Średnia efektywność [%]',
    MIN(waste_area) as 'Min odpad',
    MAX(efficiency) as 'Max efektywność [%]',
    AVG(skipped_blocks) as 'Śr. pominięte'
FROM KnapsackResults
GROUP BY algorithm_name
ORDER BY AVG(waste_area) ASC;
GO

-- Najlepsze wyniki (najmniejszy odpad)
SELECT TOP 10 
    algorithm_name,
    waste_area,
    efficiency,
    execution_time_us,
    blocks_count,
    skipped_blocks,
    timestamp
FROM KnapsackResults
ORDER BY waste_area ASC, efficiency DESC;
GO

-- Najszybsze wykonanie dla każdego algorytmu
SELECT 
    algorithm_name,
    MIN(execution_time_us) as 'Najszybszy czas [µs]',
    AVG(execution_time_us) as 'Średni czas [µs]'
FROM KnapsackResults
GROUP BY algorithm_name
ORDER BY MIN(execution_time_us) ASC;
GO

-- ====================================================================
-- 3. ZAAWANSOWANE ANALIZY
-- ====================================================================

-- Porównanie algorytmów - tabela przestawna
SELECT 
    'Średni odpad' as Metryka,
    MAX(CASE WHEN algorithm_name LIKE '%Brute Force%' THEN waste_area END) as 'Brute Force',
    MAX(CASE WHEN algorithm_name LIKE '%obracaniem%' THEN waste_area END) as 'Z obracaniem',
    MAX(CASE WHEN algorithm_name LIKE '%powierzchni%' THEN waste_area END) as 'Sort. powierzchnia',
    MAX(CASE WHEN algorithm_name LIKE '%wysokości%' THEN waste_area END) as 'Sort. wysokość',
    MAX(CASE WHEN algorithm_name LIKE '%szerokości%' THEN waste_area END) as 'Sort. szerokość',
    MAX(CASE WHEN algorithm_name LIKE '%Best-Fit%' THEN waste_area END) as 'Best-Fit'
FROM (
    SELECT algorithm_name, AVG(waste_area) as waste_area
    FROM KnapsackResults
    GROUP BY algorithm_name
) as subquery;
GO

-- Trendy w czasie (jak zmienia się jakość)
SELECT 
    algorithm_name,
    CONVERT(DATE, timestamp) as data,
    AVG(waste_area) as avg_waste,
    AVG(efficiency) as avg_efficiency,
    COUNT(*) as runs
FROM KnapsackResults
GROUP BY algorithm_name, CONVERT(DATE, timestamp)
ORDER BY data DESC, algorithm_name;
GO

-- ====================================================================
-- 4. UTILITY - CZYSZCZENIE DANYCH
-- ====================================================================

-- Usuń wyniki testowe (starsze niż dzisiaj)
-- UWAGA: Odkomentuj tylko jeśli chcesz usunąć!
/*
DELETE FROM KnapsackResults 
WHERE CONVERT(DATE, timestamp) < CONVERT(DATE, GETDATE());
GO
*/

-- Usuń wyniki konkretnego algorytmu
-- UWAGA: Odkomentuj tylko jeśli chcesz usunąć!
/*
DELETE FROM KnapsackResults 
WHERE algorithm_name = 'Brute Force';
GO
*/

-- Usuń WSZYSTKIE wyniki
-- UWAGA: To usunie WSZYSTKO! Używaj ostrożnie!
/*
TRUNCATE TABLE KnapsackResults;
GO
*/

-- ====================================================================
-- 5. EKSPORT DO SPRAWOZDANIA
-- ====================================================================

-- Dane do tabeli w sprawozdaniu
PRINT 'Kopiuj te wyniki do sprawozdania:';
PRINT '==========================================';
GO

SELECT 
    algorithm_name as 'Algorytm',
    blocks_count as 'Liczba klocków',
    ROUND(AVG(execution_time_us), 2) as 'Czas [µs]',
    ROUND(AVG(waste_area), 2) as 'Odpad',
    ROUND(AVG(efficiency), 2) as 'Efektywność [%]',
    ROUND(AVG(CAST(skipped_blocks AS FLOAT)), 0) as 'Pominięte'
FROM KnapsackResults
GROUP BY algorithm_name, blocks_count
ORDER BY blocks_count, AVG(waste_area) ASC;
GO

-- ====================================================================
-- KONIEC SKRYPTU
-- ====================================================================

PRINT 'Skrypt wykonany pomyślnie!';
GO
