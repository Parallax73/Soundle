/*CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       spotifyId VARCHAR(50),
                       streak INT,
                       score INT,
                       profile_imageurl VARCHAR(100),
                       role varchar(50) default 'USER',
                       creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE users_roles(
    id serial primary key,
    name varchar(100)

);

CREATE TABLE refresh_tokens (
                                token_id SERIAL PRIMARY KEY,
                                token VARCHAR(100),
                                expiration DATETIME,
                                user_id BIGINT unsigned,
                                FOREIGN KEY (user_id) REFERENCES users(id)
);
*/