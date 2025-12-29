# ❓ FAQ - Najczęściej Zadawane Pytania

## 🚀 Instalacja i Uruchomienie

### Q: Jak uruchomić projekt?
**A:** Najprościej w NetBeans:
1. Skopiuj `.env.example` do `.env` (konfiguracja bazy danych)
2. File → Open Project
3. Wybierz folder projektu
4. Naciśnij F6
5. Jeśli jest błąd "brak sterownika" - zobacz następne pytanie

### Q: Gdzie pobrać sterownik JDBC?
**A:** 
- Link bezpośredni: https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc/12.4.2.jre11
- Pobierz plik: `mssql-jdbc-12.4.2.jre11.jar`
- Lub zobacz: `lib/README_JDBC.md`

### Q: Jak dodać .jar do projektu w NetBeans?
**A:**
1. Kliknij prawym na projekt → Properties
2. Libraries → Add JAR/Folder
3. Wybierz pobrany `mssql-jdbc-*.jar`
4. OK

### Q: Czy mogę uruchomić bez bazy danych?
**A:** TAK! Aplikacja działa bez bazy. Po uruchomieniu algorytmu po prostu kliknij "NIE" gdy pyta o zapis do BD.

---

## 🔧 Problemy Techniczne

### Q: "Brak sterownika JDBC" / "No suitable driver"
**A:** 
1. Pobierz sterownik (zobacz wyżej)
2. Dodaj do Libraries w projekcie
3. Restart NetBeans
4. Rebuild projekt (Clean and Build)

### Q: "Login failed" / "Błąd połączenia z bazą"
**A:** Sprawdź w pliku `.env`:
- Czy nazwa serwera jest poprawna?
- Czy nazwa bazy jest poprawna?
- Czy typ uwierzytelniania jest poprawny: `DB_AUTH_TYPE=WINDOWS` lub `SQL`?
- Jeśli używasz SQL Auth: sprawdź username i password
- Czy masz dostęp do tej bazy?
- Jeśli nie - aplikacja działa bez bazy!

### Q: Projekt się nie kompiluje
**A:**
1. Sprawdź czy masz Java 11 lub nowszą: `java -version`
2. NetBeans → Clean and Build (Shift+F11)
3. Sprawdź czy wszystkie pliki .java są w `src/klockimans/`
4. Restart NetBeans

### Q: "Cannot find symbol" przy kompilacji
**A:** 
- Prawdopodobnie brakuje któregoś pliku
- Sprawdź czy wszystkie pliki z `src/klockimans/` są w projekcie
- Clean and Build

---

## 🎮 Używanie Aplikacji

### Q: Jak przetestować algorytmy?
**A:**

**Opcja 1 - GUI (graficznie):**
1. Kliknij "Generowanie" - podaj liczbę klocków (1-100)
2. Kliknij "Układanie"
3. Wybierz algorytm LUB **"📊 PORÓWNAJ WSZYSTKIE"**
4. Zobacz wyniki
5. Zapisz do bazy (opcjonalnie)

**Opcja 2 - AlgorithmTester (konsola):**
1. Uruchom: `AlgorithmTester.java`
2. Podaj liczbę klocków (domyślnie 10)
3. Program automatycznie testuje wszystkie algorytmy
4. Wyświetla:
   - Szczegółowe wyniki każdego algorytmu
   - Czas w mikrosekundach (µs) dla szybkich algorytmów
   - Ranking końcowy z medalami 🥇🥈🥉
   - Liczba ułożonych i pominiętych klocków

**Polecenie uruchomienia (PowerShell):**
```powershell
chcp 65001; java '-Dfile.encoding=UTF-8' -cp "build/classes" klockimans.AlgorithmTester
```

### Q: Co robi "PORÓWNAJ WSZYSTKIE ALGORYTMY"?
**A:** Uruchamia wszystkie algorytmy na **tych samych klockach** i pokazuje:
- Tabelę porównawczą z medalami (🥇🥈🥉)
- Sortowanie: najlepsza efektywność, przy remisie najkrótszy czas
- Po zamknięciu okna klocki układają się wg zwycięskiego algorytmu
- Możliwość zapisu wszystkich wyników do bazy jednym kliknięciem

### Q: Dlaczego Brute Force nie działa?
**A:** Brute Force działa tylko dla **maksymalnie 10 klocków**. 
- Jeśli masz więcej, algorytm się nie uruchomi
- W porównaniu wszystkich algorytmów jest automatycznie pomijany

### Q: Jak zmienić liczbę generowanych klocków?
**A:** Dialog sam pyta o liczbę klocków (1-100). Nie trzeba modyfikować kodu!

### Q: Jak zmienić rozmiar klocków?
**A:** W tej samej metodzie zmień:
```java
Klocek k = new Klocek(
    (int)(Math.random()*100+100),  // szerokość: 100-200
    (int)(Math.random()*100+100)   // wysokość: 100-200
);
```

### Q: Jak zapisać klocki do pliku?
**A:**
- Kliknij "Zapis" → "Zapisz klocki do pliku"
- Zostanie utworzony plik `dane.txt`

### Q: Jak wczytać klocki z pliku?
**A:**
- Kliknij "Odczyt"
- Wczyta klocki z pliku `dane.txt`

## Bezpieczeństwo

### Q: Jak zabezpieczyć hasło do bazy danych?
**A:** 
- Używamy pliku `.env` który NIE jest commitowany do git
- Plik `.env` jest w `.gitignore` - git go ignoruje
- **NIGDY** nie udostępniaj pliku `.env`!
- Używaj silnych haseł (min. 12 znaków)

### Q: Co jeśli ktoś zobaczy mój plik .env?
**A:**
1. **NATYCHMIAST** zmień hasło w bazie danych
2. Zaktualizuj `.env` z nowym hasłem
3. Sprawdź czy `.env` nie został commitowany:
   ```bash
   git log --all -- .env
   ```
4. Jeśli był - skontaktuj się z administratorem bazy

### Q: Czy mogę używać tego samego hasła dla różnych projektów?
**A:** **NIE!** Każdy projekt powinien mieć:
- Osobny plik `.env`
- Osobne hasło (jeśli używasz SQL Auth)
- Osobne konto w bazie (najlepiej)

### Q: Windows Authentication czy SQL Authentication?
**A:** 
- **Windows Auth** :
  - ✅ Bezpieczniejsze (używa konta Windows)
  - ✅ Nie trzeba pamiętać hasła
  - ❌ Działa tylko na Windows
  
- **SQL Auth** :
  - ✅ Działa na wszystkich systemach
  - ✅ Możesz mieć dedykowane konto
  - ❌ Hasło w pliku (ryzyko)

---

## 📊 Baza Danych

### Q: Jak sprawdzić czy mam połączenie z bazą?
**A:** 

**Opcja 1 - Sprawdzenie automatyczne:**
- W konsoli NetBeans po uruchomieniu powinno być: "Połączono z bazą danych SQL Server"
- Jeśli nie ma - aplikacja działa lokalnie bez bazy

**Opcja 2 - DatabaseTester:**
1. Uruchom: `DatabaseTester.java`
2. Wybierz opcję 5: "Test połączenia"
3. Program sprawdzi połączenie i wyświetli status

**Polecenie uruchomienia (PowerShell):**
```powershell
chcp 65001; java '-Dfile.encoding=UTF-8' -cp "build/classes;lib/*" klockimans.DatabaseTester
```

**Menu DatabaseTester:**
- 1️⃣ Wyświetl ostatnie wyniki (10)
- 2️⃣ Wyświetl ostatnie wyniki (własna liczba)
- 3️⃣ Wyświetl statystyki algorytmów
- 4️⃣ Zapisz testowe wyniki
- 5️⃣ Test połączenia
- 0️⃣ Wyjście

### Q: Jak zobaczyć wyniki z bazy?
**A:**

**Opcja 1 - GUI:**
- Kliknij "Zapis" → "Pokaż ostatnie wyniki z BD"
- Lub: "Pokaż statystyki algorytmów"

**Opcja 2 - DatabaseTester (bardziej zaawansowane):**
- Uruchom `DatabaseTester.java`
- Opcja 1: Ostatnie 10 wyników
- Opcja 2: Dowolna liczba wyników
- Opcja 3: Statystyki wszystkich algorytmów (średnie, min, max)

### Q: Jak wyeksportować dane z bazy do Excela?
**A:**
1. Otwórz SQL Server Management Studio
2. Połącz się z bazą
3. Uruchom: `SELECT * FROM KnapsackResults`
4. Query → Results To → Results To File
5. Zapisz jako CSV
6. Otwórz w Excelu

### Q: Jak utworzyć tabelę ręcznie?
**A:**
- Uruchom skrypt `database_setup.sql` w SQL Server Management Studio
- Lub aplikacja tworzy ją automatycznie przy pierwszym uruchomieniu

### Q: Jak usunąć stare dane z bazy?
**A:** W SQL Server Management Studio:
```sql
DELETE FROM KnapsackResults 
WHERE CONVERT(DATE, timestamp) < '2025-12-22';
```

---

## 📈 Testy i Pomiary

### Q: Ile razy powinien uruchomić każdy algorytm?
**A:** 
- Minimum 3 razy
- Zalecane 5 razy
- Policz średnią z wyników

### Q: Czy wyniki mogą się różnić przy każdym uruchomieniu?
**A:** TAK, ponieważ:
- Klocki są generowane losowo
- Czas wykonania zależy od obciążenia komputera
- Dlatego wykonuj wielokrotne testy i licz średnie

### Q: Jak porównać algorytmy?
**A:**

**Opcja 1 - GUI (pojedynczo):**
1. Uruchom każdy algorytm 3-5 razy
2. Zapisz wszystkie wyniki do bazy
3. Kliknij "Zapis" → "Pokaż statystyki algorytmów"
4. Zobacz średnie wartości

**Opcja 2 - GUI (wszystkie naraz):**
1. Kliknij "📊 PORÓWNAJ WSZYSTKIE ALGORYTMY"
2. Program automatycznie testuje wszystkie na tych samych klockach
3. Zapisz wszystkie wyniki jednym kliknięciem

**Opcja 3 - AlgorithmTester (konsola, najszybsze):**
1. Uruchom `AlgorithmTester.java`
2. Podaj liczbę klocków
3. Program automatycznie:
   - Testuje wszystkie algorytmy
   - Wyświetla ranking z medalami
   - Pokazuje szczegółowe statystyki
4. Powtórz test 3-5 razy z różnymi liczbami klocków

**Przykład wyników z AlgorithmTester:**
```
🏆 RANKING ALGORYTMÓW 🏆
🥇 Sortowanie po wysokości    85,65%    1 ms    30    0
🥈 Best-Fit                    78,87%   25 ms    28    2
🥉 Zachłanny z obracaniem      71,95%    0 ms    26    4
```

### Q: Co to jest "odpad"?
**A:** 
- Odpad = Powierzchnia prostokąta ograniczającego - Suma powierzchni klocków
- Im mniejszy odpad, tym lepiej

### Q: Co to jest "efektywność"?
**A:**
- Efektywność = (Suma powierzchni klocków / Prostokąt ograniczający) × 100%
- Im wyższa efektywność, tym lepiej
- 100% = brak odpadu (idealnie)

---

## 🔬 Algorytmy

### Q: Który algorytm jest najlepszy?
**A:** Zależy od kryteriów:
- **Najlepsza jakość:** Brute Force (ale tylko ≤10 klocków)
- **Dobra jakość, szybko:** Best-Fit lub sortowanie
- **Najszybszy:** Zachłanny (bez sortowania)

### Q: Czym się różnią warianty sortowania?
**A:**
- **Po powierzchni:** Największe klocki jako pierwsze
- **Po wysokości:** Najwyższe klocki jako pierwsze (lepsze dla długich tafli)
- **Po szerokości:** Najszersze klocki jako pierwsze (lepsze dla szerokich tafli)

### Q: Jak działa Best-Fit?
**A:**
- Dla każdego klocka sprawdza wszystkie wolne miejsca
- Testuje obie orientacje (0° i 90°)
- Wybiera miejsce z najmniejszym lokalnym odpadem
- Wolniejszy ale skuteczniejszy

### Q: Dlaczego Brute Force jest taki wolny?
**A:**
- Sprawdza wszystkie permutacje: n!
- 10! = 3,628,800 kombinacji
- 15! = 1,307,674,368,000 - niemożliwe w rozsądnym czasie

---

## 📝 Sprawozdanie

### Q: Co powinno być w sprawozdaniu?
**A:**
- Opis algorytmów
- Tabele z wynikami (3-5 testów dla każdego)
- Wykresy porównawcze
- Analiza i wnioski

### Q: Jak zrobić wykresy?
**A:**
1. Wyeksportuj dane z bazy do CSV
2. Otwórz w Excelu
3. Insert → Chart
4. Wybierz Bar Chart lub Line Chart

### Q: Jakie wnioski napisać?
**A:**
- Który algorytm najlepszy dla jakiego przypadku
- Trade-off między czasem a jakością
- Porównanie teorii z praktyką
- Rekomendacje

---

## 🎓 Dodatkowe

### Q: Czy mogę modyfikować kod?
**A:** TAK! Możesz:
- Zmienić sposób generowania klocków
- Dodać własne algorytmy
- Zmienić interfejs
- Dodać kolorowanie klocków

### Q: Jak dodać własny algorytm?
**A:**
1. W `Algorytmy.java` dodaj nową metodę podobną do innych
2. Zwróć `AlgorithmResult`
3. W `GUI.java` dodaj nową opcję w liście algorytmów

### Q: Gdzie jest pełna dokumentacja?
**A:** Zobacz pliki:
- `README.md` - Quick Start
- `SPRAWOZDANIE.md` - Pełna dokumentacja
- `lib/README_JDBC.md` - Instalacja JDBC

### Q: Projekt nie działa w IntelliJ / Eclipse
**A:** 
- Projekt jest dla NetBeans (ma pliki `nbproject/`)
- W innych IDE musisz:
  1. Utworzyć nowy projekt
  2. Skopiować wszystkie pliki z `src/`
  3. Dodać sterownik JDBC do bibliotek
  4. Może być konieczne dostosowanie

---

## 🆘 Dalej nie działa?

1. **Sprawdź konsole** - często są tam szczegółowe komunikaty błędów
2. **Clean and Build** - NetBeans → Clean and Build Project
3. **Restart IDE** - czasem pomaga restart NetBeans
4. **Sprawdź Javę** - `java -version` w terminalu
5. **GitHub Issues** - jeśli to projekt publiczny

---

## 📞 Kontakt

Jeśli masz pytania związane z projektem akademickim:
- Zapytaj prowadzącego zajęcia
- Sprawdź dokumentację w plikach projektu
- Przeczytaj `SPRAWOZDANIE.md`

---

**Powodzenia z projektem! 🚀**
