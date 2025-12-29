# 🔐 BEZPIECZEŃSTWO PROJEKTU

## 📋 Przegląd

Projekt implementuje dobre praktyki bezpieczeństwa dla połączeń z bazą danych:

✅ **Zewnętrzna konfiguracja** - dane wrażliwe w `.env`  
✅ **Ignorowanie przez Git** - `.env` w `.gitignore`  
✅ **Szablon bez danych** - `.env.example` jako przykład  
✅ **Dwa tryby uwierzytelniania** - Windows Auth i SQL Auth  
✅ **Maskowanie haseł** - w logach i debugowaniu  

---

## 🔒 Pliki wrażliwe

### ⚠️ NIE COMMITUJ DO GIT:

```
.env                    # Twoje prawdziwe dane logowania
dane.txt               # Może zawierać dane testowe
*.log                  # Logi mogą zawierać wrażliwe informacje
```

### ✅ BEZPIECZNE DO COMMITOWANIA:

```
.env.example           # Szablon bez prawdziwych danych
src/**/*.java          # Kod źródłowy (bez hardcoded passwords!)
```

---

## 🛡️ Dobre praktyki

### 1. Zarządzanie hasłami

```properties
# ❌ ZŁE - słabe hasło
DB_PASSWORD=123456

# ❌ ZŁE - hasło w komentarzu
DB_PASSWORD=SuperSecretPass  # moje hasło do bazy

# ✅ DOBRE - silne hasło
DB_PASSWORD=Zx9!mK#pL2@qR5$Student2025
```

**Zasady silnego hasła:**
- Minimum 12 znaków
- Wielkie i małe litery
- Cyfry
- Znaki specjalne (!@#$%^&*)
- Bez popularnych słów lub dat

### 2. Przechowywanie .env

```bash
# ✅ DOBRE - lokalnie na dysku
C:\Projects\projekt\.env

# ❌ ZŁE - udostępnione lokalizacje
C:\Users\Public\.env
D:\DropBox\projekt\.env
\\network\share\.env

# ❌ ZŁE - repozytorium
git add .env        # NIGDY!
```

### 3. Udostępnianie dostępu

```
❌ Wysyłanie .env przez email
❌ Wklejanie .env na Discord/Slack
❌ Pokazywanie .env na screenach
❌ Commitowanie .env do GitHub

✅ Wysłanie .env.example z instrukcją
✅ Osobiste przekazanie hasła (offline)
✅ Użycie menedżera haseł zespołowego
✅ Każdy ma własne konto w bazie
```

---

## 🔍 Sprawdzenie bezpieczeństwa

### Checklist przed commitowaniem:

```bash
# 1. Sprawdź status git
git status
# .env NIE POWINIEN być na liście!

# 2. Sprawdź .gitignore
cat .gitignore | grep .env
# Powinno być: .env

# 3. Sprawdź historię
git log --all -- .env
# Powinno być puste (brak commitów)

# 4. Sprawdź staging area
git diff --cached
# .env NIE POWINIEN być tutaj!

# 5. Sprawdź wszystkie pliki
git ls-files | grep .env
# Powinno być puste (tylko .env.example jest OK)
```

---

## 🚨 Co zrobić gdy doszło do wycieku?

### Scenariusz 1: .env został commitowany do git

```bash
# KROK 1: Natychmiast zmień hasło w bazie danych!

# KROK 2: Usuń plik z historii git
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch .env" \
  --prune-empty --tag-name-filter cat -- --all

# KROK 3: Force push (jeśli to twoje repo)
git push origin --force --all

# KROK 4: Powiadom zespół
echo "UWAGA: Zmienione hasło do bazy!"
```

### Scenariusz 2: .env został wysłany przez email/chat

```
KROK 1: NATYCHMIAST zmień hasło!
KROK 2: Usuń wiadomość (jeśli możliwe)
KROK 3: Powiadom administratora bazy
KROK 4: Sprawdź logi dostępu do bazy
KROK 5: Zaktualizuj .env z nowym hasłem
```

### Scenariusz 3: Pokazany na prezentacji/screenie

```
KROK 1: Zmień hasło jak najszybciej
KROK 2: Poproś o usunięcie nagrania/zrzutu (jeśli było)
KROK 3: Monitoruj dostęp do bazy przez kilka dni
KROK 4: Rozważ zmianę username (jeśli możliwe)
```

---

## 🔐 Konfiguracja według poziomu bezpieczeństwa

### 🟢 Podstawowy (Studencki projekt)

```properties
# Windows Authentication - brak hasła
DB_AUTH_TYPE=WINDOWS
DB_SERVER=localhost\\SQLEXPRESS
DB_DATABASE=TestDB
```

**Dobre dla:**
- Lokalnej bazy danych
- Prototypów
- Nauki

### 🟡 Średni (Projekt zespołowy)

```properties
# SQL Authentication z hasłem
DB_AUTH_TYPE=SQL
DB_SERVER=shared-server\\INSTANCE
DB_DATABASE=TeamProject
DB_USERNAME=team_user_jan
DB_PASSWORD=SilneHaslo2025!
```

**Dobre dla:**
- Pracy zespołowej
- Współdzielonej bazy
- Projektów akademickich

### 🔴 Produkcyjny (Aplikacja rzeczywista)

```properties
# Silne hasło + dodatkowe zabezpieczenia
DB_AUTH_TYPE=SQL
DB_SERVER=prod-server.company.com
DB_DATABASE=ProductionDB
DB_USERNAME=app_service_user
DB_PASSWORD=Zx9!mK#pL2@qR5$vT7^nH4&

# + Dodatkowo:
# - Szyfrowany dysk
# - VPN do serwera
# - Rotacja haseł co 90 dni
# - Audyt dostępu
# - Backup .env
```

---

## 📚 Dodatkowe zabezpieczenia (opcjonalne)

### 1. Szyfrowanie .env

```bash
# Użyj gpg do zaszyfrowania
gpg -c .env
# Tworzy: .env.gpg (zaszyfrowany)

# Odszyfrowanie
gpg .env.gpg
# Wpisz hasło → dostaniesz .env
```

### 2. Zmienne środowiskowe systemowe

Zamiast .env, użyj zmiennych systemowych:

```bash
# Windows
setx DB_PASSWORD "TwojeHaslo"

# W kodzie:
String password = System.getenv("DB_PASSWORD");
```

### 3. Azure Key Vault / AWS Secrets Manager

Dla projektów komercyjnych rozważ cloud secrets management.

---

## 📖 Źródła i więcej informacji

- **OWASP Top 10:** https://owasp.org/www-project-top-ten/
- **Git Secrets:** https://github.com/awslabs/git-secrets
- **Microsoft Security:** https://docs.microsoft.com/security/

---

## ✅ Podsumowanie

**ZAWSZE:**
- ✅ Używaj `.env` dla wrażliwych danych
- ✅ Dodaj `.env` do `.gitignore`
- ✅ Używaj silnych haseł
- ✅ Sprawdzaj co commitujess

**NIGDY:**
- ❌ Nie commituj `.env` do git
- ❌ Nie udostępniaj `.env` publicznie
- ❌ Nie używaj słabych haseł
- ❌ Nie hardcoduj haseł w kodzie

---

**Bezpieczeństwo to proces, nie produkt!** 🔐

*Ostatnia aktualizacja: 2025-12-22*
