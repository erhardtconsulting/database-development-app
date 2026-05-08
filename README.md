# DB-2 Ticket System Starter

Dieses Projekt ist das kleine Spring-Boot-Starterprojekt fuer **Database Development**. Es ist bewusst kein fertiges Ticket-System. Die Anwendung zeigt die Layer-Struktur, laeuft gegen PostgreSQL und enthaelt gezielte Luecken, damit die Datenbanklogik im Unterricht selbst entwickelt wird.

## Technischer Rahmen

- Spring Boot 4.0.5
- Java 24 als Compile-Standard
- Spring MVC, Spring Data JPA, Hibernate/JPA
- Flyway
- PostgreSQL
- Maven Wrapper
- Lombok fuer DTOs, Entities und einfache Datenklassen

## Code-Guidelines

- Verwende in diesem Kursprojekt **keine Java Records**.
- Verwende fuer einfache Datenklassen Lombok:
  - `@Value` fuer unveraenderliche Antwort-DTOs und Value-Objekte
  - `@Data` fuer JPA Entities, Request-DTOs und mutable Framework-Modelle
- Nutze Maven als Build-Werkzeug; der Maven Wrapper ist Teil des Projekts.
- Schreibe modernen Java-24-Code. Nutze Streams dort, wo sie Lesbarkeit und Datenfluss verbessern, aber nicht als Selbstzweck.
- Datenbankregeln gehoeren nicht nur in Java-Validierung. Zentrale Invarianten muessen auch in PostgreSQL sichtbar sein.

## Start

Die DB-2-PostgreSQL-Umgebung muss laufen:

```bash
cd ../../postgres
podman compose up -d
```

Danach kann die Anwendung gestartet werden:

```bash
./mvnw spring-boot:run
```

Die Anwendung verwendet die Datenbank `ticket_system` auf Port `5433`, schreibt aber in ein eigenes Schema `app_starter`. Dadurch bleibt das vollstaendige Unterrichtsschema aus `db-2/postgres` als Referenz erhalten.

## Guided Gaps

Die erste Migration ist absichtlich schwach. Sie laesst Dinge offen, die Studierende fachlich entscheiden sollen:

- Welche Felder braucht ein minimales Ticket wirklich?
- Welche Spalten duerfen nie `NULL` sein?
- Welche Statuswerte gehoeren als `CHECK` Constraint in die Datenbank?
- Welche Regeln gehoeren in PostgreSQL, welche in den Service-Layer?
- Welche Repository-Methode bleibt lesbar?

Normale Tests laufen mit:

```bash
./mvnw test
```

Ein Integrationstest mit Testcontainers startet eine frische PostgreSQL-Datenbank automatisch:

```bash
./mvnw -Ptestcontainers test
```

Ein Docker-Compose-Test gegen die lokale DB-2-PostgreSQL-Umgebung laeuft mit:

```bash
cd ../../postgres
podman compose up -d
cd ../app/ticket-system-java
./mvnw -Pdocker-compose test
```

Die aktivierbare Engineering-Aufgabe laeuft mit:

```bash
./mvnw -Pguided-gaps test
```

Dieser Test schlaegt am Anfang erwartbar fehl. Er wird erst gruen, wenn die Migration datenbankseitige Regeln enthaelt.

## Teststrategie

- `./mvnw test`: schnelle Tests ohne Docker.
- `./mvnw -Ptestcontainers test`: echte PostgreSQL-Integration mit isolierter Testcontainers-Datenbank.
- `./mvnw -Pdocker-compose test`: Pruefung gegen die Kursdatenbank aus `db-2/postgres/docker-compose.yml`.
- `./mvnw -Pguided-gaps test`: bewusst fehlschlagende Engineering-Aufgabe fuer fehlende Datenbankregeln.

## Docker

Die Anwendung kann auch als Container gebaut und zusammen mit PostgreSQL gestartet werden:

```bash
podman compose up --build
```

Danach ist die API unter `http://localhost:8080/api/tickets` erreichbar.

## API fuer Block 2

```http
GET /api/tickets
GET /api/tickets?status=open
POST /api/tickets
```

Beispiel fuer `POST /api/tickets`:

```json
{
  "title": "Datenbankverbindung pruefen",
  "status": "open"
}
```
