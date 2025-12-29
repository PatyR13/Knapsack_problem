@echo off
REM ====================================================================
REM Skrypt uruchamiający projekt KlockiMANS
REM ====================================================================

echo.
echo ========================================
echo    PROJEKT UKLADANIE KLOCKOW
echo ========================================
echo.

REM Sprawdź czy jesteśmy w odpowiednim katalogu
if not exist "src\klockimans\GUI.java" (
    echo [BLAD] Nie znaleziono plikow projektu!
    echo Uruchom ten skrypt z katalogu glownego projektu.
    pause
    exit /b 1
)

echo [1/3] Sprawdzanie srodowiska...

REM Sprawdź Javę
java -version >nul 2>&1
if errorlevel 1 (
    echo [BLAD] Java nie jest zainstalowana lub nie jest w PATH!
    echo Zainstaluj Jave i sprobuj ponownie.
    pause
    exit /b 1
)
echo    [OK] Java znaleziona

echo.
echo [2/3] Kompilowanie projektu...

REM Sprawdź czy mamy ant (NetBeans używa ant)
if exist "build.xml" (
    echo    Uzywam Apache Ant...
    call ant clean
    call ant compile
    if errorlevel 1 (
        echo [BLAD] Kompilacja nie powiodla sie!
        echo Sprobuj skompilowac projekt w NetBeans.
        pause
        exit /b 1
    )
) else (
    echo    [INFO] Brak build.xml, otworz projekt w NetBeans
)

echo.
echo [3/3] Uruchamianie...
echo.
echo ========================================
echo    INSTRUKCJA SZYBKIEGO STARTU:
echo ========================================
echo 1. Kliknij "Generowanie" - losuj klocki
echo 2. Kliknij "Ukladanie" - wybierz algorytm
echo 3. Zobacz wyniki i zapisz do bazy
echo 4. Powtorz dla kazdego algorytmu
echo ========================================
echo.

REM Najlepiej uruchomić przez NetBeans
echo [UWAGA] Zalecane uruchomienie przez NetBeans IDE!
echo.
echo Opcje uruchomienia:
echo   A - Uruchom GUI (wymaga skompilowanego projektu)
echo   B - Uruchom tester konsolowy
echo   C - Otworz w NetBeans (polecane)
echo   X - Zakoncz
echo.

choice /C ABC /M "Wybierz opcje"

if errorlevel 3 goto netbeans
if errorlevel 2 goto tester
if errorlevel 1 goto gui

:gui
echo.
echo Uruchamianie GUI...
if exist "build\classes\klockimans\GUI.class" (
    java -cp build\classes;lib\* klockimans.GUI
) else (
    echo [BLAD] Projekt nie jest skompilowany!
    echo Otworz projekt w NetBeans i nacisnij F6.
)
pause
exit /b

:tester
echo.
echo Uruchamianie testera...
if exist "build\classes\klockimans\AlgorithmTester.class" (
    java -cp build\classes;lib\* klockimans.AlgorithmTester
) else (
    echo [BLAD] Projekt nie jest skompilowany!
    echo Otworz projekt w NetBeans i nacisnij F6.
)
pause
exit /b

:netbeans
echo.
echo Otwieranie NetBeans...
echo.
echo [INFO] Jesli masz NetBeans:
echo 1. File ^> Open Project
echo 2. Wybierz folder: %CD%
echo 3. Nacisnij F6 aby uruchomic
echo.
echo Jesli nie masz NetBeans:
echo Pobierz z: https://netbeans.apache.org/
echo.
pause
exit /b

:end
echo.
echo Dziekuje za uzywanie programu!
pause
