CREATE TABLE users (
                       id SERIAL,
                       username varchar(250) NOT NULL UNIQUE,
                       email varchar(250) NOT NULL UNIQUE,
                       password varchar(250) NOT NULL,
                       profile_imageUrl varchar(250),
                       spotifyId varchar(250) unique,
                       role varchar(250) NOT NULL,
                       streak int,
                       score int
);