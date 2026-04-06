PRAGMA foreign_keys = ON;

BEGIN;

DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS film_user;
DROP TABLE IF EXISTS film_actor;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS actors;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS genres;

CREATE TABLE films (
    film_id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    director TEXT,
    runtime INT,
    release_date DATE,
    rating TEXT
);

INSERT OR IGNORE INTO films (title, director, runtime, release_date, rating)
VALUES 
('Taxi Driver', 'Martin Scorsese', 114, '1976-02-19', 'R'),
('Heat', 'Michael Mann', 170, '1995-12-15', 'R'),
('Scarface', 'Brian De Palma', 165, '1983-12-09', 'R'),
('The Wolf of Wall Street', 'Martin Scorsese', 180, '2013-12-25', 'R'),
('Django Unchained', 'Quentin Tarantino', 165, '2012-12-25', 'R'),
('Inception', 'Christopher Nolan', 148, '2010-07-16', 'PG-13');

CREATE TABLE actors (
    actor_id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_name TEXT,
    last_name TEXT,
    birthday DATE,
    nationality TEXT
);

INSERT OR IGNORE INTO actors (first_name, last_name, birthday, nationality)
VALUES
('Leonardo', 'DiCaprio', '1974-11-11', 'American'),
('Robert', 'De Niro', '1943-08-17', 'American'),
('Al', 'Pacino', '1940-04-25', 'American'),
('Samuel', 'Jackson', '1948-12-21', 'American'),
('Jonah', 'Hill', '1983-12-20', 'American'),
('Margot', 'Robbie', '1990-07-02', 'Australian'),
('Joseph', 'Gordon-Levitt', '1981-02-17', 'American');

CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE,
    password TEXT,
    email TEXT UNIQUE,
    subscription_id INT
);

CREATE TABLE genres (
    genre_id INTEGER PRIMARY KEY AUTOINCREMENT,
    genre_type TEXT
);

CREATE TABLE film_actor (
    film_id INT,
    actor_id INT,
    character TEXT, 
    actor_salary INT,
    PRIMARY KEY (film_id, actor_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (actor_id) REFERENCES actors(actor_id)
);

INSERT OR IGNORE INTO film_actor (film_id, actor_id, character, actor_salary)
VALUES
(1, 2, 'Travis Bickle', 35000),
(2, 2, 'Neil McCauley', 6000000),
(2, 3, 'Lt. Vincent Hanna', 6000000),
(3, 3, 'Tony Montana', 500000),
(4, 1, 'Jordan Belfort', 10000000),
(4, 5, 'Donnie Azoff', 60000),
(4, 6, 'Naomi Lapaglia', 347000),
(6, 7, 'Arthur', 5000000),
(6, 1, 'Don Cobb', 20000000),
(5, 1, 'Calvin J. Candie', 10000000),
(5, 5, 'Bag Head #2', NULL),
(5, 4, 'Stephen', 6000000);

CREATE TABLE film_user (
    film_id INT,
    user_id INT,
    watch_date DATE, 
    progress INT,
    user_rating TEXT,
    PRIMARY KEY (user_id, film_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE
);

CREATE TABLE film_genre (
    film_id INT,
    genre_id INT,
    is_primary_genre BOOLEAN,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(film_id),
    FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

COMMIT;
