CREATE TABLE IF NOT EXISTS system_health_check (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO system_health_check (status) VALUES ('HEALTHY');