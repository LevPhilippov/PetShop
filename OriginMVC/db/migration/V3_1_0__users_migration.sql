CREATE TABLE IF NOT EXISTS roles(
                                     id bigserial NOT NULL,
                                     role VARCHAR(20) NOT NULL
);

INSERT INTO roles (id,role) VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN'),(3,'ROLE_MANAGER');

CREATE TABLE IF NOT EXISTS users(
                                     id bigserial NOT NULL,
                                     first_name VARCHAR(50),
                                     last_name VARCHAR(50),
                                     email VARCHAR(50),
                                     phone VARCHAR(20) NOT NULL,
                                     address VARCHAR(100),
                                     password VARCHAR NOT NULL,
                                     version INT,
                                     created_at TIMESTAMP,
                                     updated_at TIMESTAMP,
                                     PRIMARY KEY(id)
);

INSERT INTO users (id,first_name, last_name, email, phone, address, password)
values (0,'Richard','Hendrix', 'piedpiper@info.org','88005553535','Silicon valley. House of Elrich Backhmann', '$2a$12$55STic.eTRox2lagMmUI9ebtdbEOzUPVR2kTKMuqDJ3gWgQBOvswu');

CREATE TABLE IF NOT EXISTS users_roles(
                                    user_id BIGINT NOT NULL,
                                    role_id BIGINT NOT NULL
);

INSERT INTO users_roles (user_id, role_id)  values (0,2);
