# Projekt: Układanie Klocków na Tafli
## Algorytmy optymalizacji 2D bin packing

Projekt realizowany w ramach studiów podyplomowych WWSI - przedmiot TAZW (Tworzenie aplikacji z wykorzystaniem IDE oraz wsparciem AI).

---

## 🚀 Szybki start

1. **Przeczytaj** [README.md](README.md) - instrukcje uruchomienia i instalacji
2. **Zainstaluj** sterownik JDBC - [lib/README_JDBC.md](lib/README_JDBC.md)
3. **Uruchom** projekt w NetBeans (F6) lub przez `run.bat`
4. **Testuj** algorytmy przez interfejs graficzny

Problemy? Zobacz [FAQ.md](FAQ.md)

---

## 📚 Dokumentacja

| Plik | Opis |
|------|------|
| [README.md](README.md) | Quick start, instrukcje techniczne, konfiguracja |
| [SPRAWOZDANIE.md](SPRAWOZDANIE.md) | Analiza algorytmów, wyniki testów, wnioski |
| [FAQ.md](FAQ.md) | Najczęstsze problemy i rozwiązania |

---

## 🧮 Algorytmy

Projekt implementuje 6 algorytmów układania prostokątnych elementów:

1. **Brute Force** - wszystkie permutacje (max 10 elementów)
2. **Zachłanny z obracaniem** - testuje orientacje 0° i 90°
3. **Sortowanenie po powierzchni** - największe elementy pierwsze
4. **Sortowanenie po wysokości** - najwyższe elementy pierwsze
5. **Sortowanenie po szerokości** - najszersze elementy pierwsze
6. **Best-Fit** - inteligentne dopasowanie pozycji

Szczegóły: [SPRAWOZDANIE.md](SPRAWOZDANIE.md)

---

## 📂 Struktura projektu

```
src/klockimans/
├── Algorytmy.java           # 6 algorytmów układania
├── GUI.java                 # Interfejs graficzny
├── DatabaseManager.java     # Obsługa SQL Server
├── AlgorithmResult.java     # Wyniki algorytmu
├── Klocek.java              # Model elementu
├── Tafla.java               # Model tafli
├── AlgorithmTester.java     # 🧪 Tester algorytmów (konsola)
└── DatabaseTester.java      # 🗄️ Tester bazy danych (interaktywny)
```

### 🧪 Narzędzia testowe

**AlgorithmTester.java** - Konsolowy test algorytmów:
- Interaktywne pytanie o liczbę klocków
- Automatyczne testowanie wszystkich algorytmów
- Ranking z medalami 🥇🥈🥉
- Czas w µs dla szybkich algorytmów
- Szczegółowe statystyki (ułożone/pominięte klocki)

**DatabaseTester.java** - Interaktywny test bazy danych:
- Wyświetlanie ostatnich wyników
- Statystyki algorytmów (średnie, min, max)
- Test połączenia z bazą
- Zapisywanie testowych wyników
- Menu z wyborem opcji

---

## 🎓 Dla studentów realizujących podobne zadanie

### Co ten projekt zawiera:

- ✅ 6 działających algorytmów bin packing 2D
- ✅ Interfejs graficzny do testowania
- ✅ Integracja z bazą danych (SQL Server)
- ✅ Automatyczne pomiary czasu i efektywności
- ✅ Porównanie algorytmów na różnych zestawach danych
- ✅ Pełna dokumentacja i analiza wyników

### Przydatne do nauki:

- Algorytmy zachłanne (greedy)
- Algorytmy sortowania
- Optymalizacja kombinatoryczna
- Analiza złożoności obliczeniowej
- Testowanie i porównywanie algorytmów
- Wizualizacja wyników
- Integracja z bazą danych

---

## 🛠️ Technologie

- Java 11+
- NetBeans IDE
- SQL Server + JDBC Driver
- Swing (GUI)
- Git

---

## 📊 Wyniki

Szczegółowe wyniki testów, analizy i wnioski znajdziesz w [SPRAWOZDANIE.md](SPRAWOZDANIE.md).

Najważniejsze odkrycia:
- **Sort. wysokość** - najlepszy kompromis jakość/czas (86% efektywności)
- **Brute Force** - najwyższa jakość dla ≤10 elementów (83-88%)
- **Best-Fit** - specjalista od ciasnych przestrzeni (82%)
- Rozmiar tafli i liczba klocków to kluczowe czynniki

---

## 📝 Licencja

Projekt edukacyjny - dozwolone użycie w celach akademickich.

**Autor:** Patrycja Rybak  
**Uczelnia:** WWSI  
**Rok:** 2025
