CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE auth.roles
(
    role_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(50)  NOT NULL,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE auth.users
(
    user_id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    document_number VARCHAR(20)  NOT NULL UNIQUE,
    phone           VARCHAR(15),
    role_id         UUID REFERENCES auth.roles (role_id),
    base_salary     NUMERIC(12, 2)
);
