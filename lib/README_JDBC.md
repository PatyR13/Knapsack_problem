# INSTRUKCJA INSTALACJI STEROWNIKA JDBC DLA SQL SERVER

## Krok 1: Pobierz sterownik JDBC
Pobierz sterownik Microsoft JDBC Driver for SQL Server:
https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server

LUB użyj Maven Central:
https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc

Zalecana wersja: mssql-jdbc-12.4.2.jre11.jar lub nowsza

## Krok 2: Dodaj sterownik do projektu

### Opcja A: NetBeans (prawdopodobnie używasz)
1. Pobierz plik mssql-jdbc-*.jar
2. Skopiuj go do folderu `lib/` w tym projekcie
3. W NetBeans: 
   - Kliknij prawym na projekt -> Properties
   - Wybierz "Libraries"
   - Kliknij "Add JAR/Folder"
   - Wybierz pobrany plik .jar z folderu lib/
   - Kliknij OK

### Opcja B: Ręczne dodanie do classpath
W pliku nbproject/project.properties dodaj linię:
```
javac.classpath=lib/mssql-jdbc-12.4.2.jre11.jar
```

## Krok 3: Zweryfikuj połączenie
Po dodaniu sterownika uruchom aplikację i sprawdź czy:
- Pojawia się komunikat "Połączono z bazą danych SQL Server" w konsoli
- Brak błędu "Brak sterownika JDBC"

## Konfiguracja bazy danych
Projekt jest skonfigurowany dla:
- Server: NT-27\WWSI2014
- Database: fullstack25_spod-351pr
- Uwierzytelnianie: Windows Authentication (Integrated Security)

Jeśli masz inne dane, zmień je w pliku:
src/klockimans/DatabaseManager.java

## Rozwiązywanie problemów

### Problem: "No suitable driver found"
- Upewnij się, że sterownik JDBC jest dodany do bibliotek projektu
- Sprawdź czy plik .jar jest w folderze lib/

### Problem: "Login failed"
- Sprawdź czy masz dostęp do bazy danych
- Sprawdź czy nazwa serwera i bazy są poprawne
- Jeśli używasz SQL Authentication zamiast Windows, zmień connection string

### Problem: "This driver is not configured for integrated authentication"
- Pobierz pełną wersję sterownika (nie jre8, ale jre11)
- Upewnij się, że masz zainstalowane biblioteki uwierzytelniające Windows
