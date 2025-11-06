# Evidencija Klijenata Banke – Desktop Aplikacija (Java Swing)

## 1. Uvod
Ova aplikacija predstavlja jednostavan desktop sistem za evidenciju klijenata banke, izrađen u programskom jeziku Java uz korištenje Swing grafičkog interfejsa. Aplikacija omogućava dodavanje, pregled i brisanje klijenata, kao i prikaz osnovnih statističkih podataka o njima. Razvijena je u svrhu praktične demonstracije osnovnih principa objektno-orijentisanog programiranja, rada sa listama podataka, jednostavnog GUI dizajna i jediničnog testiranja (JUnit).

## 2. Tehnologije i alati korišteni u razvoju
| Komponenta / Tehnologija | Opis |
|--------------------------|------------------------------------------------|
| Java SE                  | Programski jezik korišten za implementaciju logike |
| Java Swing               | Biblioteka za izradu korisničkog interfejsa (GUI) |
| Eclipse IDE              | Razvojno okruženje korišteno za implementaciju aplikacije |
| JUnit 4                  | Biblioteka za implementaciju jediničnih testova |
| Apache Ant               | Alat za automatizaciju procesa izgradnje projekta |
| Git & GitHub             | Kontrola verzija i spremanje projekta u repozitorij |

## 3. Funkcionalnosti aplikacije
Aplikacija omogućava sljedeće funkcije:
- ✅ Dodavanje novog klijenta (ime, prezime, stanje računa)
- ✅ Brisanje postojećeg klijenta iz evidencije
- ✅ Pregled ukupnog broja klijenata
- ✅ Prikaz ukupnog zbira stanja svih računa
- ✅ Izračunavanje i prikaz prosječnog stanja računa
- ✅ Identifikacija klijenta sa najvećim / najmanjim stanjem
- ✅ Sortiranje klijenata abecedno (A→Z i Z→A)

## 4. Struktura aplikacije
src/
└─ banka.model/
└─ Klijent.java → Model / entitet klijenta
└─ banka/
└─ Main.java → Glavni GUI prozor i logika aplikacije
└─ banka.test/
└─ KlijentTest.java → JUnit jedinični testovi
lib/ → JUnit i Hamcrest biblioteke
build.xml → Ant build skripta


## 5. Pokretanje aplikacije
### 5.1. Pokretanje preko IDE okruženja (npr. Eclipse)
1. Otvoriti projekat u Eclipse-u
2. Pokrenuti klasu `Main.java` → `Run As > Java Application`

### 5.2. Pokretanje putem Ant skripte
U root direktorij projektu izvršiti:

ant clean
ant compile
ant run


## 6. Jedinično testiranje (JUnit)
Implementirani su testovi za:
- Konstruktor i getter metode klase `Klijent`
- Metodu za izračunavanje ukupnih podataka nad listom klijenata
- Identifikaciju klijenta sa najvećim iznosom

Pokretanje testova:
Run As → JUnit Test
ili putem Ant:

## 7. Primijenjena metodologija razvoja
U razvoju ove aplikacije korišten je **Iterativni pristup** (inkrementalni razvoj).  
Razvoj je podijeljen u manje faze:
1. Definisanje domenskog modela (klasa `Klijent`)
2. Razvoj GUI komponenata i osnovnih funkcija
3. Dodavanje naprednih funkcija (sortiranje i statistika)
4. Jedinično testiranje i ispravke
5. Automatizacija izgradnje i repozitorij na GitHub-u

Ovakav pristup omogućava postepeno poboljšavanje sistema i lakšu detekciju grešaka.

## 8. Zaključak
Kroz izradu ove aplikacije prikazana je primjena ključnih koncepata objektno-orijentisanog programiranja uz praktičnu implementaciju u vidu jednostavne bankarske evidencije. Integracija JUnit testiranja i Ant alata ukazuje na značaj automatizacije i provjere ispravnosti softvera. Konačno, korištenje Git-a i GitHub-a omogućilo je verzionisanje i profesionalno upravljanje izvornim kodom.

---
## Izgled korisničkog interfejsa

![GUI aplikacije](assets/gui.png)
