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

# Slots iniciales
INSERT INTO schedules (date, time, available) VALUES ('2025-01-25', '12:00:00', true);
INSERT INTO schedules (date, time, available) VALUES ('2025-01-25', '14:00:00', true);
INSERT INTO schedules (date, time, available) VALUES ('2025-01-25', '16:00:00', true);
INSERT INTO schedules (date, time, available) VALUES ('2025-01-25', '18:00:00', true);
INSERT INTO schedules (date, time, available) VALUES ('2025-01-25', '20:00:00', true);
