create table users(
    id serial auto_increment,
    username varchar(50) unique not null,
    password varchar(100) not null,
    spotifyId varchar(50),
    streak int,
    score int,
    profile_imageurl varchar(100),
    role ENUM('USER', 'ADMIN') default 'USER' not null,
    creation_date datetime default CURRENT_TIMESTAMP
);
