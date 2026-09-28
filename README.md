# FitBuddy – mikroservisni sustav za praćenje treninga

**FitBuddy** je mikroservisni sustav razvijen za praćenje i organizaciju treninga. Korisniku omogućuje unos i pregled vježbi, kreiranje treninga, dodavanje vježbi sa serijama, ponavljanjima i težinama te praćenje napretka kroz vrijeme.

Sustav uključuje i **AI Coach** servis koji pruža dodatne funkcionalnosti poput generiranja personaliziranih prijedloga treninga, objašnjavanja pojedinih vježbi te izrade sažetka korisnikova napretka.

## Glavne funkcionalnosti

* unos i pregled vježbi
* pretraživanje vježbi prema mišićnoj skupini
* kreiranje i pregled treninga
* dodavanje vježbi u trening
* evidentiranje serija, ponavljanja i korištene težine
* pregled povijesti treninga
* praćenje napretka korisnika
* AI generiranje prijedloga plana treninga
* AI objašnjenje pojedine vježbe
* AI generiranje sažetka korisnikova napretka

## API resursi

Sustav obuhvaća sljedeće glavne resurse:

* **Exercises** – naziv, mišićna skupina, oprema i opis vježbe
* **Workouts** – treninzi korisnika
* **Workout Exercises** – vježbe unutar pojedinog treninga
* **Exercise Sets** – serije, ponavljanja i težine
* **Training Plans** – planovi treninga
* **Progress** – podaci potrebni za praćenje napretka

## Arhitektura sustava

Sustav je organiziran kao skup međusobno povezanih servisa i komponenti:

* **Spring Boot `workout-service`** – upravljanje vježbama, treninzima i podacima o napretku
* **Spring Boot `ai-coach-service`** – implementacija AI funkcionalnosti
* **MariaDB** – pohrana podataka aplikacije
* **Redis** – cacheiranje AI odgovora radi smanjenja nepotrebnih ponovljenih zahtjeva
* **HAProxy** – usmjeravanje HTTP zahtjeva prema odgovarajućem servisu
* **Prometheus** – prikupljanje i praćenje metrika
* **Grafana** – vizualizacija metrika i praćenje rada sustava
* **Docker Compose** – pokretanje i povezivanje svih komponenti sustava u Docker okruženju

## Tehnologije

* Java
* Spring Boot
* Spring Data JPA
* Spring AI
* MariaDB
* Redis
* HAProxy
* Prometheus
* Grafana
* Docker & Docker Compose
* REST API
