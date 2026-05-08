# Guided Gaps fuer Block 2

Dieses Starterprojekt laeuft, ist aber fachlich absichtlich unvollstaendig. Die Luecken sind der Unterrichtsgegenstand.

## Aufgabe 1: Migration reviewen

Analysiere `V1__starter_ticket_schema.sql`.

- Welche Spalten koennen aktuell `NULL` sein?
- Welche fehlerhaften Statuswerte wuerde PostgreSQL akzeptieren?
- Welche Regeln fehlen in der Datenbank, obwohl die API bereits Eingaben validiert?
- Welche Regeln wuerdest du nicht in die Datenbank schreiben?

## Aufgabe 2: Datenbanklogik ergaenzen

Verbessere die Migration so, dass PostgreSQL mindestens Folgendes garantiert:

- ein Ticket hat immer einen Titel
- ein Ticket hat immer einen Status
- der Status ist auf wenige fachlich erlaubte Werte beschraenkt

Fuehre danach aus:

```bash
./mvnw -Pguided-gaps test
```

## Aufgabe 3: Service und Repository einordnen

Analysiere den Datenfluss fuer `GET /api/tickets?status=open`.

- Wo wird der HTTP-Parameter entgegengenommen?
- Welche Methode erzeugt die Datenbankabfrage?
- Welche Rolle hat der Service?
- Warum ist die Repository Method fuer diesen Fall noch lesbar?

