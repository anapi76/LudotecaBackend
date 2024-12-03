INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO customer(name) VALUES ('Cliente 1');
INSERT INTO customer(name) VALUES ('Cliente 2');
INSERT INTO customer(name) VALUES ('Cliente 3');
INSERT INTO customer(name) VALUES ('Cliente 4');
INSERT INTO customer(name) VALUES ('Cliente 5');

INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (2,2,'2024-12-01','2024-12-15');
INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (1,1,'2024-12-03','2024-12-17');
INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (3,3,'2024-11-28','2024-12-12');
INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (4,4,'2024-12-02','2024-12-16');
INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (5,5,'2024-11-30','2024-12-14');
INSERT INTO loan(game_id,customer_id,loan_date,return_date) VALUES (6,1,'2024-11-30','2024-12-14');