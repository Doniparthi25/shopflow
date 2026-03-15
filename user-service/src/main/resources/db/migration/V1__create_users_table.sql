-- V1: Initial users table. NEVER MODIFY after applying.
CREATE TABLE IF NOT EXISTS users (
    id          UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'CUSTOMER',
    status      VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_users_email  ON users(email);
CREATE INDEX idx_users_status ON users(status);

ALTER TABLE users ADD CONSTRAINT chk_user_role
    CHECK (role IN ('CUSTOMER', 'ADMIN', 'SUPPORT'));

ALTER TABLE users ADD CONSTRAINT chk_user_status
    CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED'));