CREATE DATABASE IF NOT EXISTS restaurant_reservations;
USE restaurant_reservations;

CREATE TABLE IF NOT EXISTS clients
(
    client_id    INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    phone_number VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS schedules
(
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    date        DATE NOT NULL,
    time        TIME NOT NULL,
    available   BOOLEAN default TRUE,
    UNIQUE (date, time)
);

CREATE TABLE IF NOT EXISTS reservations
(
    reservation_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    client_id      int                not null,
    schedule_id    int                NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients (client_id),
    FOREIGN KEY (schedule_id) REFERENCES schedules (schedule_id),
    UNIQUE (schedule_id)
);

# Creamos el cliente y los slots de pruebas

INSERT INTO schedules (date, time, available)
VALUES ('2025-01-19', '14:00', TRUE);

INSERT INTO schedules (date, time, available)
VALUES ('2025-01-20', '14:00', TRUE);

INSERT into clients (name, email, phone_number)
VALUES ('Thaizir', 'thaizire@protonmail.com', '1234567890');

# 1. Verificar los horarios disponibles para un día específico

SELECT schedule_id, time, available
FROM schedules
WHERE date = '2025-01-19'
  AND available = TRUE
ORDER BY time;

# 2. Crear una nueva reservación para un cliente.

START TRANSACTION;

INSERT INTO reservations (client_id, schedule_id)
VALUES (1, 1);

UPDATE schedules
SET available = FALSE
WHERE schedule_id = 1;

COMMIT;

# 3. Actualizar una reservación existente.

START TRANSACTION;

SELECT r.reservation_id, s.schedule_id, s.available
FROM reservations r
         JOIN schedules s ON s.schedule_id = r.schedule_id
WHERE r.reservation_id = 1
    FOR
UPDATE;

UPDATE reservations
SET schedule_id = 2
WHERE reservation_id = 1;

UPDATE schedules
SET available = TRUE
WHERE schedule_id = 1;

UPDATE schedules
SET available = FALSE
WHERE schedule_id = 2;

COMMIT;

# 4. Eliminar una reservación existente.

START TRANSACTION;

UPDATE schedules
SET available = TRUE
WHERE schedule_id = 2;

DELETE
FROM reservations
WHERE reservation_id = 1;

COMMIT;