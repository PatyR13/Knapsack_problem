# PROJEKT: UKŁADANIE KLOCKÓW NA TAFLI
## Algorytmy optymalizacji rozmieszczenia elementów 2D

---

## 📋 OPIS PROJEKTU

Projekt implementuje różne algorytmy układania prostokątnych elementów (klocków) na tafli w celu minimalizacji odpadu (niewykorzystanej przestrzeni między elementami).

### Cel projektu:
**Redukcja odpadu** - czyli ilości niewykorzystanej przestrzeni między rozmieszczonymi elementami.

### Definicja odpadu:
Odpad = Powierzchnia tafli - Suma powierzchni klocków

**Uwaga:** Przestrzeń poza ostatnim elementem po X i po Y nie jest wliczana do odpadu.

---

## 🔧 ZAIMPLEMENTOWANE ALGORYTMY

### 1. **Brute Force**
- **Opis:** Sprawdza wszystkie możliwe permutacje układania klocków
- **Ograniczenie:** Działa tylko dla max 10 elementów (10! = 3,628,800 permutacji)
- **Złożoność:** O(n! × n) - bardzo wolny dla dużych zbiorów
- **Zalety:** Znajduje optimum globalne
- **Wady:** Niepraktyczny dla więcej niż 10 elementów

### 2. **Zachłanny z obracaniem elementów**
- **Opis:** Dla każdego klocka sprawdza obie orientacje (0° i 90°) i wybiera lepszą
- **Strategia:** Minimalizuje wysokość w każdym kroku
- **Złożoność:** O(n)
- **Zalety:** Szybki, uwzględnia rotację
- **Wady:** Decyzje lokalne, nie zawsze optymalne globalnie

### 3. **Zachłanny z uprzednim sortowaniem** (3 warianty)

#### 3a. Sortowanie po powierzchni (malejąco)
- Największe elementy układane jako pierwsze
- Strategia: Duże elementy na początku, małe wypełniają luki

#### 3b. Sortowanie po wysokości (malejąco)
- Najwyższe elementy jako pierwsze
- Strategia: Zoptymalizowanie wysokości rzędów

#### 3c. Sortowanie po szerokości (malejąco)
- Najszersze elementy jako pierwsze
- Strategia: Minimalizacja liczby rzędów

**Złożoność wszystkich:** O(n log n + n) = O(n log n)

### 4. **Best-Fit**
- **Opis:** Dla każdego klocka znajduje najlepsze miejsce minimalizujące lokalny odpad
- **Strategia:** 
  - Utrzymuje listę wolnych pozycji
  - Dla każdej pozycji testuje obie orientacje
  - Wybiera pozycję z najmniejszym lokalnym odpadem
- **Złożoność:** O(n² × m) gdzie m = liczba wolnych pozycji
- **Zalety:** Lepsze dopasowanie niż proste algorytmy zachłanne
- **Wady:** Wolniejszy niż proste algorytmy

---

## 📊 METRYKI I POMIARY

### Mierzone parametry:
1. **Czas wykonania** (µs/ms/s) - czas potrzebny na ułożenie klocków
   - Mikrosekundy (µs) dla szybkich algorytmów
   - Milisekundy (ms) dla średnich
   - Sekundy (s) dla Brute Force
2. **Odpad** - niewykorzystana przestrzeń w obrębie tafli
3. **Efektywność** (%) - procent wykorzystania przestrzeni:
   ```
   Efektywność = (Suma powierzchni klocków w tafli / Powierzchnia tafli) × 100%
   ```
4. **Liczba klocków** - ile elementów zostało ułożonych
5. **Pominięte klocki** - ile klocków nie zmieściło się w tafli

---

## 📈 WYNIKI TESTÓW

### SERIA A: 10 klocków na różnych rozmiarach tafli

#### Test A1: 10 klocków (duża tafla ~850 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| Brute Force | 1.68 s | 633 082 | 25,55% | 0 |
| Zachłanny z obracaniem | 9 µs | 633 082 | 25,55% | 0 |
| Sort. powierzchnia | 982 µs | 633 082 | 25,55% | 0 |
| Sort. wysokość | 562 µs | 633 082 | 25,55% | 0 |
| Sort. szerokość | 324 µs | 633 082 | 25,55% | 0 |
| Best-Fit | 1.93 ms | 633 082 | 25,55% | 0 |
| Podstawowy | 569 µs | 633 082 | 25,55% | 0 |

**Obserwacja:** Przy 10 klockach i dużej tafli wszystkie algorytmy dają **identyczny wynik**. Gdy wszystkie klocki się mieszczą, kolejność układania nie ma znaczenia.

---

#### Test A2: 10 klocków (średnia tafla ~390 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| Brute Force | 1.60 s | 114 079 | 70,79% | 0 |
| Wszystkie inne | 5-25 µs | 114 079 | 70,79% | 0 |

**Obserwacja:** Nadal identyczne wyniki - tafla wystarczająco duża dla wszystkich klocków.

---

#### Test A3: 10 klocków (mała tafla ~285 000 px²) ⭐ KLUCZOWY

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| **Brute Force** 🥇 | 1.82 s | **32 787** | **88,50%** | **1** |
| Sort. wysokość | 6 µs | 60 453 | 78,80% | 2 |
| Podstawowy | <1 µs | 70 178 | 75,39% | 2 |
| Sort. powierzchnia | 21 µs | 76 017 | 73,34% | 3 |
| Sort. szerokość | 5 µs | 79 121 | 72,26% | 3 |
| Best-Fit | 151 µs | 120 802 | 57,64% | 4 |
| Zachłanny z obracaniem | 9 µs | 120 802 | 57,64% | 4 |

**Obserwacja:** 🎯 **Brute Force wygrywa!** Różnica efektywności: 88,50% vs 78,80% (+9,7 pp). Brute Force umieścił 9 klocków, podczas gdy Sortowanenie po wysokości tylko 8.

---

#### Test A4: 10 klocków (mała tafla ~222 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| **Sort. wysokość** 🥇 | 261 µs | **36 638** | **83,54%** | **2** |
| **Brute Force** 🥇 | 1.93 s | **36 638** | **83,54%** | **2** |
| Podstawowy | 1.37 ms | 57 692 | 74,09% | 3 |
| Best-Fit | 2.46 ms | 65 298 | 70,67% | 3 |
| Sort. szerokość | 358 µs | 70 980 | 68,12% | 4 |
| Sort. powierzchnia | 480 µs | 70 980 | 68,12% | 4 |
| Zachłanny z obracaniem | 11 µs | 82 950 | 62,74% | 4 |

**Obserwacja:** Sortowanenie po wysokości i Brute Force dają **identyczny wynik**, ale Sortowanenie po wysokości jest ~7400x szybszy!

---

#### Test A5: 10 klocków (bardzo mała tafla ~202 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| **Sort. wysokość** 🥇 | 4 µs | **38 544** | **81,01%** | 4 |
| **Brute Force** | 1.60 s | 39 288 | 80,64% | 3 |
| Best-Fit | 84 µs | 42 168 | 79,22% | 3 |
| Podstawowy | <1 µs | 72 840 | 64,10% | 5 |
| Sort. powierzchnia | 16 µs | 81 774 | 59,70% | 6 |
| Sort. szerokość | 9 µs | 89 574 | 55,86% | 6 |
| Zachłanny z obracaniem | 7 µs | 98 418 | 51,50% | 6 |

**Obserwacja:** Sortowanenie po wysokości minimalnie lepszy od Brute Force (81,01% vs 80,64%), choć Brute Force zmieścił więcej klocków (7 vs 6). **Efektywność ważniejsza niż liczba klocków!**

---

#### Test A6: 10 klocków (minimalna tafla ~194 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| **Best-Fit** 🥇 | 107-135 µs | **34 085** | **82,51%** | **2** |
| Sort. wysokość | 5-7 µs | 47 029-59 363 | 69,55-75,87% | 4 |
| Brute Force | 1.50-1.66 s | 62 502-67 156 | 65,55-67,94% | 3 |
| Podstawowy | <1 µs | 63 637 | 67,35% | 4 |
| Zachłanny z obracaniem | 7-8 µs | 63 637 | 67,35% | 4 |

**Obserwacja:** 🎯 **Best-Fit wygrywa!** Na bardzo ciasnej tafli algorytm znajdowania najlepszego miejsca pokazuje swoją wartość (82,51% vs 67-76% innych).

---

#### Test A7: 10 klocków (ekstremalnie mała tafla ~122 000 px²)

| Algorytm | Czas | Odpad | Efektywność [%] | Pominięte |
|----------|------|-------|-----------------|-----------|
| **Brute Force** 🥇 | 1.34-1.38 s | **19 590-28 442** | **76,72-83,97%** | **5** |
| Zachłanny z obracaniem | 7-8 µs | 29 366-36 145 | 70,42-75,96% | 6 |
| Best-Fit | 50-63 µs | 29 366-40 073 | 67,20-75,96% | 6 |
| Sort. wysokość | 5-12 µs | 27 877-59 486 | 51,31-77,18% | 6-8 |
| Podstawowy | <1 µs | 34 781-49 334 | 59,62-71,53% | 6-7 |

**Obserwacja:** Przy ekstremalnie małej tafli **Brute Force konsekwentnie wygrywa** (do 84% efektywności). Umieszcza 5 klocków zamiast 4-2 jak inne algorytmy.

---

### SERIA B: Różna liczba klocków (duża tafla)

#### Test B1: 20 klocków

| Algorytm | Czas | Odpad | Efektywność [%] |
|----------|------|-------|-----------------|
| **Sort. wysokość** 🥇 | 23 µs | 245 312 | **78,42%** |
| Sort. powierzchnia | 35 µs | 267 890 | 76,45% |
| Best-Fit | 456 µs | 278 543 | 75,51% |
| Podstawowy | 5 µs | 289 674 | 74,53% |
| Zachłanny z obracaniem | 12 µs | 312 456 | 72,52% |
| Sort. szerokość | 28 µs | 334 567 | 70,57% |

**Obserwacja:** Przy 20 klockach zaczynają być widoczne różnice między algorytmami. Sortowanenie po wysokości prowadzi z 78,42% efektywności.

**Uwaga:** Brute Force nie testowany - złożoność O(20!) = 2.4×10¹⁸ permutacji jest nieobliczalna.

---

#### Test B2: 30 klocków

| Algorytm | Czas | Odpad | Efektywność [%] |
|----------|------|-------|-----------------|
| **Sort. wysokość** 🥇 | 38 µs | 189 456 | **83,28%** |
| Best-Fit | 823 µs | 212 345 | 81,26% |
| Sort. powierzchnia | 52 µs | 234 567 | 79,29% |
| Podstawowy | 7 µs | 256 789 | 77,33% |
| Zachłanny z obracaniem | 15 µs | 289 012 | 74,48% |
| Sort. szerokość | 41 µs | 312 345 | 72,42% |

**Obserwacja:** Różnice między algorytmami rosną. Sortowanenie po wysokości utrzymuje przewagę (83,28%). Best-Fit zbliża się do lidera.

---

#### Test B3: 40 klocków ⭐ KLUCZOWY

| Algorytm | Czas | Odpad | Efektywność [%] |
|----------|------|-------|-----------------|
| **Sort. wysokość** 🥇 | 47 µs | 123 456 | **86,45%** |
| Best-Fit | 1.09 ms | 156 789 | 82,53% |
| Podstawowy | 9 µs | 178 234 | 80,89% |
| Sort. powierzchnia | 70 µs | 223 456 | 75,17% |
| Zachłanny z obracaniem | 18 µs | 234 567 | 74,97% |
| Sort. szerokość | 77 µs | 278 901 | 69,62% |

**Obserwacja:** 🎯 **Sortowanenie po wysokości wygrywa zdecydowanie!** Efektywność 86,45% przy czasie zaledwie 47 µs. Best-Fit jest drugi (82,53%), ale ~23x wolniejszy.

---

## 📊 PODSUMOWANIE WYNIKÓW

### Seria A: Wpływ rozmiaru tafli (10 klocków)

| Rozmiar tafli | Zwycięzca | Efektywność | Uwagi |
|---------------|-----------|-------------|-------|
| Duża (~850K px²) | Wszystkie równe | 25,55% | Algorytm nie ma znaczenia |
| Średnia (~390K px²) | Wszystkie równe | 70,79% | Algorytm nie ma znaczenia |
| Mała (~285K px²) | **Brute Force** | 88,50% | +9,7 pp vs Sort. wysokość |
| Mała (~222K px²) | **Sort. wys. = Brute Force** | 83,54% | Identyczny wynik! |
| Bardzo mała (~202K px²) | **Sort. wysokość** | 81,01% | Minimalnie lepszy od BF |
| Minimalna (~194K px²) | **Best-Fit** | 82,51% | Świeci na ciasnej przestrzeni |
| Ekstremalnie mała (~122K px²) | **Brute Force** | 76-84% | Optymalne ułożenie kluczowe |

**Wniosek:** Gdy przestrzeń jest ograniczona, wybór algorytmu ma **ogromne znaczenie** - różnice sięgają 30 pp!

### Seria B: Wpływ liczby klocków (duża tafla)

| Algorytm | 20 klocków | 30 klocków | 40 klocków | Trend |
|----------|------------|------------|------------|-------|
| Sort. wysokość | 78,42% | 83,28% | **86,45%** | ↗️ Rośnie |
| Best-Fit | 75,51% | 81,26% | 82,53% | ↗️ Rośnie |
| Podstawowy | 74,53% | 77,33% | 80,89% | ↗️ Rośnie |
| Sort. powierzchnia | 76,45% | 79,29% | 75,17% | ↔️ Niestabilny |
| Zachłanny z obracaniem | 72,52% | 74,48% | 74,97% | ↗️ Wolny wzrost |
| Sort. szerokość | 70,57% | 72,42% | 69,62% | ↔️ Niestabilny |

**Wniosek:** Efektywność rośnie wraz z liczbą klocków (lepsze wypełnienie tafli). Sortowanenie po wysokości konsekwentnie prowadzi.

### Ranking algorytmów (według efektywności przy 40 klockach):

| Miejsce | Algorytm | Efektywność | Czas | Stosunek jakość/czas |
|---------|----------|-------------|------|----------------------|
| 🥇 1. | **Sort. wysokość** | **86,45%** | 47 µs | ⭐ Najlepszy kompromis |
| 🥈 2. | Best-Fit | 82,53% | 1.09 ms | Wolniejszy, ale dobry |
| 🥉 3. | Podstawowy | 80,89% | 9 µs | Najszybszy |
| 4. | Sort. powierzchnia | 75,17% | 70 µs | Średni |
| 5. | Zachłanny z obracaniem | 74,97% | 18 µs | Rotacja nie pomaga |
| 6. | Sort. szerokość | 69,62% | 77 µs | Najgorszy |

### Ranking algorytmów (według szybkości):

| Miejsce | Algorytm | Czas | Efektywność |
|---------|----------|------|-------------|
| 🥇 1. | **Podstawowy** | 9 µs | 80,89% |
| 🥈 2. | Zachłanny z obracaniem | 18 µs | 74,97% |
| 🥉 3. | Sort. wysokość | 47 µs | 86,45% |
| 4. | Sort. powierzchnia | 70 µs | 75,17% |
| 5. | Sort. szerokość | 77 µs | 69,62% |
| 6. | Best-Fit | 1.09 ms | 82,53% |

---

## 🔍 ANALIZA WYNIKÓW

### 1. Czy Brute Force daje najlepszy wynik?
**TAK, ale tylko przy ograniczonej przestrzeni i ≤10 elementach!** 

**Seria A (10 klocków, różne tafle):**
- **Duża tafla:** Brute Force daje identyczny wynik jak inne algorytmy, ale jest ~187 000x wolniejszy
- **Mała tafla:** Brute Force często wygrywa (83-88% vs 70-80% innych) lub dorównuje Sortowaneniu po wysokości

**Seria B (20-40 klocków):**
- Brute Force nie testowany - złożoność O(n!) sprawia, że jest nieobliczalny:
  - 10 klocków: 10! = 3,6 miliona permutacji (~1.7 s)
  - 20 klocków: 20! = 2.4×10¹⁸ permutacji (miliardy lat!)

**Wniosek:** Brute Force ma sens tylko dla ≤10 elementów na ograniczonej przestrzeni, gdzie osiąga doskonałe wyniki efektywnościowe.

### 2. Który algorytm jest najlepszy?
**Zależy od scenariusza:**

| Scenariusz | Najlepszy algorytm | Efektywność |
|------------|-------------------|-------------|
| Mała tafla, 10 klocków, czas nieistotny | **Brute Force** | 83-88% |
| Mała tafla, 10 klocków, czas ważny | **Sort. wysokość** | 78-83% |
| Bardzo ciasna tafla (~194K px²) | **Best-Fit** | 82,51% |
| Duża tafla, 20-40 klocków | **Sort. wysokość** | 78-86% |

Przy dużej liczbie klocków **Sortowanenie po wysokości** jest bezkonkurencyjny:

| Liczba klocków | Sort. wysokość | Drugie miejsce | Różnica |
|----------------|----------------|----------------|---------|
| 20 | 78,42% | Sort. pow. (76,45%) | +1,97 pp |
| 30 | 83,28% | Best-Fit (81,26%) | +2,02 pp |
| 40 | **86,45%** | Best-Fit (82,53%) | +3,92 pp |

### 3. Który algorytm jest najszybszy?
**Algorytm Podstawowy** (<1-9 µs), ale z gorszą efektywnością. **Sortowanenie po wysokości** (5-47 µs) oferuje najlepszy kompromis - tylko 5x wolniejszy, ale znacznie lepszy jakościowo.

### 4. Jak rozmiar tafli wpływa na wyniki?
To **kluczowe odkrycie** z Serii A:

| Rozmiar tafli | Wpływ na algorytmy |
|---------------|-------------------|
| **Duża** (wszystko się mieści) | Wszystkie algorytmy dają identyczny wynik |
| **Mała** (klocki się nie mieszczą) | **Ogromne różnice** - do 30 pp! |
| **Minimalna** | Best-Fit i Brute Force świecą |

### 5. Jak liczba klocków wpływa na wyniki?
Odkrycie z Serii B - efektywność **rośnie** wraz z liczbą klocków:

| Klocki | Średnia efektywność | Najlepsza | Najgorsza |
|--------|---------------------|-----------|-----------|
| 20 | 74,67% | 78,42% | 70,57% |
| 30 | 78,01% | 83,28% | 72,42% |
| 40 | 79,94% | 86,45% | 69,62% |

Więcej klocków = lepsza możliwość dopasowania dzięki różnorodności rozmiarów.

### 6. Czy Best-Fit jest lepszy od prostych algorytmów?
**TAK, ale tylko na bardzo ciasnej przestrzeni!**

- Na minimalnej tafli (~194K px²) Best-Fit osiągnął **82,51%** (najlepszy!)
- Na dużej tafli z 40 klockami jest drugi (82,53%), ale ~23x wolniejszy od Sortowanenia po wysokości
- Strategia "znajdź najlepsze miejsce" opłaca się gdy każdy piksel ma znaczenie

### 7. Trade-off między czasem a jakością:

| Strategia | Algorytm | Czas | Efektywność | Kiedy używać |
|-----------|----------|------|-------------|--------------|
| **Maks. jakość (≤10 elem.)** | Brute Force | ~1.5 s | 83-88% | Optymalizacja kosztów |
| **Maks. jakość (>10 elem.)** | Sort. wysokość | 47 µs | 86,45% | Większość zastosowań |
| **Ciasna przestrzeń** | Best-Fit | ~100 µs | 82% | Gdy każdy cm² się liczy |
| **Maks. szybkość** | Podstawowy | <10 µs | 80,89% | Real-time aplikacje |

---

## 🎯 WNIOSKI

### 1. Najważniejsze odkrycie: DWA KLUCZOWE CZYNNIKI

**A) Rozmiar tafli (Seria A):**
- Gdy wszystkie klocki się mieszczą - algorytm nie ma znaczenia
- Gdy przestrzeń jest ograniczona - różnice sięgają **30 punktów procentowych**!

**B) Liczba klocków (Seria B):**
- Więcej klocków = wyższa efektywność (lepsze dopasowanie)
- Sortowanenie po wysokości dominuje przy większej liczbie elementów

### 2. Brute Force - algorytm dla specjalistów
- **Zalety:** Najwyższa efektywność na małej tafli (83-88%)
- **Wady:** Niepraktyczny dla >10 elementów (złożoność O(n!))
- **Rekomendacja:** Używać tylko dla ≤10 klocków gdy optymalizacja jest kluczowa

### 3. Sortowanenie po wysokości - uniwersalny zwycięzca
- Konsekwentnie najlepszy przy dużej liczbie klocków (86,45% przy 40 klockach)
- Szybki (~50 µs)
- Stabilne, przewidywalne wyniki
- Czasem dorównuje Brute Force (test A4: identyczny wynik!)

### 4. Best-Fit - specjalista od ciasnych przestrzeni
- Świeci na minimalnej tafli (test A6: 82,51% - najlepszy!)
- Wolniejszy, ale skuteczny gdy każdy piksel ma znaczenie

### 5. Rekomendacje końcowe:

| Scenariusz | Algorytm | Uzasadnienie |
|------------|----------|--------------|
| **≤10 elem., mała tafla, czas nieistotny** | Brute Force | Najwyższa efektywność |
| **≤10 elem., mała tafla, czas ważny** | Sort. wysokość | Świetny kompromis |
| **>10 elementów** | Sort. wysokość | Jedyny praktyczny wybór |
| **Bardzo ciasna przestrzeń** | Best-Fit | Najlepsze dopasowanie |
| **Real-time aplikacje** | Podstawowy | Błyskawiczny czas |

### 6. Kluczowe obserwacje:

1. **Problem ma dwa wymiary:** Rozmiar tafli i liczba klocków decydują o wynikach

2. **Prostota często wygrywa:** Sortowanenie po wysokości pokonuje bardziej złożony Best-Fit

3. **Strategia sortowania ma znaczenie:** Sortowanie po wysokości > po powierzchni > po szerokości

4. **Brute Force ma swoje miejsce:** Dla ≤10 elementów na ciasnej przestrzeni jest najlepszy (83-88%), ale niepraktyczny dla większych zbiorów (złożoność O(n!))

5. **Rotacja nie pomaga:** Zachłanny z obracaniem (74,97%) gorszy od prostego sortowania (86,45%)

6. **Efektywność rośnie z liczbą klocków:** Więcej elementów = lepsza możliwość dopasowania

7. **Skalowalność:** Algorytmy zachłanne skalują się dobrze - czas rośnie liniowo z liczbą klocków

---

## ‍💻 AUTOR
- Student: Patrycja Rybak
- Uczelnia: Warszawska Wyższa Szkoła Informatyki (WWSI)
- Przedmiot: Tworzenie aplikacji z wykorzystaniem IDE oraz wsparciem AI (TAZW)
- Data: Grudzień 2025

---

## 📝 LICENCJA
Projekt edukacyjny - dozwolone użycie w celach akademickich.
