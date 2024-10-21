DROP TABLE IF EXISTS Tarjeta;
CREATE TABLE IF NOT EXISTS Tarjeta (
    id UUID PRIMARY KEY,
    numero VARCHAR(16) NOT NULL,
    nombreTitular VARCHAR NOT NULL,
    clienteID BIGINT NOT NULL,
    fechaCaducidad VARCHAR NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    isDeleted BOOLEAN NOT NULL DEFAULT FALSE
    );

