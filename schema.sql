-- schema.sql
-- Aunque Hibernate / JPA en Spring Boot generará las tablas automáticamente gracias a 'spring.jpa.hibernate.ddl-auto=update',
-- este script es útil como referencia o para un entorno de producción con bases de datos pre-creadas.

CREATE TABLE residentes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    casa_apto VARCHAR(50),
    telefono VARCHAR(20),
    rol VARCHAR(20) NOT NULL, -- ADMIN, RESIDENTE, PORTERO
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE visitas (
    id SERIAL PRIMARY KEY,
    residente_id INT NOT NULL REFERENCES residentes(id),
    nombre_visitante VARCHAR(100) NOT NULL,
    fecha_llegada TIMESTAMP NOT NULL,
    whatsapp VARCHAR(20) NOT NULL,
    duracion_estimada INT,
    tipo_visita VARCHAR(50),
    estado VARCHAR(20) DEFAULT 'PENDIENTE', -- PENDIENTE, ACTIVA, CERRADA
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE qr_tokens (
    id SERIAL PRIMARY KEY,
    visita_id INT NOT NULL REFERENCES visitas(id),
    token VARCHAR(255) NOT NULL UNIQUE,
    qr_imagen_base64 TEXT,
    fecha_expiracion TIMESTAMP NOT NULL,
    usado BOOLEAN DEFAULT FALSE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accesos (
    id SERIAL PRIMARY KEY,
    visita_id INT NOT NULL REFERENCES visitas(id),
    portero_id INT NOT NULL REFERENCES residentes(id),
    fecha_entrada TIMESTAMP,
    fecha_salida TIMESTAMP,
    estado VARCHAR(20), -- ENTRADA, SALIDA
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notificaciones (
    id SERIAL PRIMARY KEY,
    residente_id INT NOT NULL REFERENCES residentes(id),
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    leido BOOLEAN DEFAULT FALSE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
