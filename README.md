# DB-2 Ticket System Starter

Dieses Projekt ist das kleine Spring-Boot-Starterprojekt für **Database Development**. Es ist bewusst kein fertiges Ticket-System. Die Anwendung zeigt die Layer-Struktur, läuft gegen PostgreSQL und enthält gezielte Lücken, damit die Datenbanklogik im Unterricht selbst entwickelt wird.

## Technischer Rahmen

- Spring Boot 4.0.5
- Java 24 als Compile-Standard
- Spring MVC, Spring Data JPA, Hibernate/JPA
- Flyway
- PostgreSQL
- Maven Wrapper
- Lombok für DTOs, Entities und einfache Datenklassen
- Swagger UI und OpenAPI über springdoc-openapi

## Code-Guidelines

- Verwende in diesem Kursprojekt **keine Java Records**.
- Verwende für einfache Datenklassen Lombok:
  - `@Value` für unveränderliche Antwort-DTOs und Value-Objekte
  - `@Data` für JPA Entities, Request-DTOs und mutable Framework-Modelle
- Nutze Maven als Build-Werkzeug; der Maven Wrapper ist Teil des Projekts.
- Schreibe modernen Java-24-Code. Nutze Streams dort, wo sie Lesbarkeit und Datenfluss verbessern, aber nicht als Selbstzweck.
- Datenbankregeln gehören nicht nur in Java-Validierung. Zentrale Invarianten müssen auch in PostgreSQL sichtbar sein.

## Start

Die DB-2-PostgreSQL-Umgebung muss laufen:

```bash
cd ../db-2/postgres
podman compose up -d
```

Danach kann die Anwendung gestartet werden:

```bash
./mvnw spring-boot:run
```

Die Anwendung verwendet die Datenbank `ticket_system` auf Port `5433`, schreibt aber in ein eigenes Schema `app_starter`. Dadurch bleibt das vollständige Unterrichtsschema aus `db-2/postgres` als Referenz erhalten.

## Guided Gaps

Die erste Migration ist absichtlich schwach. Sie lässt Dinge offen, die Studierende fachlich entscheiden sollen:

- Welche Felder braucht ein minimales Ticket wirklich?
- Welche Spalten dürfen nie `NULL` sein?
- Welche Statuswerte gehören als `CHECK` Constraint in die Datenbank?
- Welche Regeln gehören in PostgreSQL, welche in den Service-Layer?
- Welche Repository-Methode bleibt lesbar?

Normale Tests laufen mit:

```bash
./mvnw test
```

Ein Integrationstest mit Testcontainers startet eine frische PostgreSQL-Datenbank automatisch:

```bash
./mvnw -Ptestcontainers test
```

Ein Docker-Compose-Test gegen die lokale DB-2-PostgreSQL-Umgebung läuft mit:

```bash
cd ../db-2/postgres
podman compose up -d
cd ../../db-2-app
./mvnw -Pdocker-compose test
```

Die aktivierbare Engineering-Aufgabe läuft mit:

```bash
./mvnw -Pguided-gaps test
```

Dieser Test schlägt am Anfang erwartbar fehl. Er wird erst grün, wenn eine neue Migration datenbankseitige Regeln enthält.

Die Lösung wird nicht in `V1__starter_ticket_schema.sql` eingetragen. `V1` bleibt der dokumentierte Startzustand. Studierende schreiben stattdessen eine neue Migration, zum Beispiel:

```text
src/main/resources/db/migration/V2__enforce_ticket_rules.sql
```

Diese `V2` ergänzt Pflichtfelder und einen Status-Constraint für die Ticket-Tabelle.

## Teststrategie

- `./mvnw test`: schnelle Tests ohne Docker.
- `./mvnw -Ptestcontainers test`: echte PostgreSQL-Integration mit isolierter Testcontainers-Datenbank.
- `./mvnw -Pdocker-compose test`: Prüfung gegen die Kursdatenbank aus `db-2/postgres/docker-compose.yml`.
- `./mvnw -Pguided-gaps test`: bewusst fehlschlagende Engineering-Aufgabe für fehlende Datenbankregeln.

## Docker

Die Anwendung kann auch als Container gebaut und zusammen mit PostgreSQL gestartet werden:

```bash
podman compose up --build
```

Danach ist die API unter `http://localhost:8080/api/tickets` erreichbar.

## GitHub Actions und Container Registry

Das Repository enthält einen GitHub-Actions-Workflow für die App:

- Pull Requests führen `./mvnw test`, `./mvnw -Ptestcontainers test` und einen Docker-Build ohne Push aus.
- Pushes auf `main` führen dieselben Prüfungen aus und veröffentlichen das Image in der GitHub Container Registry.
- Das veröffentlichte Image heisst `ghcr.io/erhardtconsulting/database-development-app`.
- `latest` wird nur von `main` gesetzt; zusätzlich erhält jedes Image einen Versions- und Commit-Tag.

## Swagger und OpenAPI

Die API-Dokumentation ist in der laufenden Anwendung verfügbar:

- Root: `http://localhost:8080/` leitet auf Swagger UI weiter.
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## API für Block 2

```http
GET /api/tickets
GET /api/tickets?status=open
POST /api/tickets
```

Beispiel für `POST /api/tickets`:

```json
{
  "title": "Datenbankverbindung prüfen",
  "status": "open"
}
```
