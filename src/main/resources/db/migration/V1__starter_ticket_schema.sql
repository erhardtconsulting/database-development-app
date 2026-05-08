CREATE SCHEMA IF NOT EXISTS app_starter;

CREATE TABLE app_starter.tickets (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title TEXT,
    status TEXT,
    created_at TIMESTAMPTZ DEFAULT now()
);

INSERT INTO app_starter.tickets (title, status) VALUES
    ('Reporting-Abfrage pruefen', 'open'),
    ('VPN-Zugriff analysieren', 'waiting');

