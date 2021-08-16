CREATE DATABASE IF NOT EXISTS machinarium_database;
USE machinarium_database;

/* DROP TABLES */

DROP TABLE IF EXISTS user_order;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_game;
DROP TABLE IF EXISTS game_results;
DROP TABLE IF EXISTS reward_item;
DROP TABLE IF EXISTS rewards;
DROP TABLE IF EXISTS user_statistics;
DROP TABLE IF EXISTS garage_item;
DROP TABLE IF EXISTS garage_car;
DROP TABLE IF EXISTS car_parts;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS user_garage;
DROP TABLE IF EXISTS fusion_tools;
DROP TABLE IF EXISTS connectors;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS item_types;
DROP TABLE IF EXISTS item_categories;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS garages;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS game_stages;

/* CREATE TABLES */

CREATE TABLE users(
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_name VARCHAR(64) NOT NULL,
                      user_password CHAR(64) NOT NULL,
                      mail VARCHAR(64),
                      CONSTRAINT user_name_unique_constraint UNIQUE(user_name),
                      CONSTRAINT mail_unique_constraint UNIQUE(mail)
);


CREATE TABLE garages(
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       garage_name VARCHAR(64) UNIQUE
);

CREATE TABLE game_stages(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        stage_name VARCHAR(64)
);
CREATE TABLE games(
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      game_name VARCHAR(64) UNIQUE,
                      game_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                      game_stage_id INT,
                      user_host_id INT,
                      FOREIGN KEY (game_stage_id) REFERENCES game_stages(id) ON DELETE CASCADE,
                      FOREIGN KEY (user_host_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE item_categories(
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                category_name VARCHAR(64) UNIQUE
);


CREATE TABLE item_types(
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           type_name VARCHAR(64),
                           item_category_id INT NOT NULL,
                           connector_id 	INT,
                           fusion_tool_id 	INT,
                           FOREIGN KEY (item_category_id) REFERENCES item_categories(id) ON DELETE CASCADE
);


CREATE TABLE items(
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      item_name VARCHAR(64) NOT NULL,
                      type_id	INT NOT NULL,
                      weight	INT,
                      weight_support INT,
                      aero_drag INT,
                      horse_power INT,
                      traction_unit INT,
                      FOREIGN KEY (type_id) REFERENCES item_types(id) ON DELETE CASCADE
);


CREATE TABLE connectors(
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          connector_name VARCHAR(64),
                          item_type_1_id INT NOT NULL,
                          item_type_2_id INT NOT NULL,
                          FOREIGN KEY (item_type_1_id) REFERENCES item_types(id) ON DELETE CASCADE,
                          FOREIGN KEY (item_type_2_id) REFERENCES item_types(id) ON DELETE CASCADE
);
ALTER TABLE connectors AUTO_INCREMENT=1001;

CREATE TABLE fusion_tools(
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             fusion_tool_name	VARCHAR(64),
                             connector_type INT,
                             FOREIGN KEY (connector_type) REFERENCES connectors(id) ON DELETE CASCADE
);


CREATE TABLE garage_item(
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            garage_id INT,
                            item_id INT,
                            item_count INT,
                            CONSTRAINT garage_item_unique UNIQUE (garage_id, item_id),
                            FOREIGN KEY (garage_id) REFERENCES garages(id) ON DELETE CASCADE,
                            FOREIGN KEY (item_id) 	REFERENCES items(id) ON DELETE CASCADE
);


CREATE TABLE user_garage(
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            user_id INT,
                            garage_id INT,
                            CONSTRAINT user_garage_unique UNIQUE (user_id, garage_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (garage_id) REFERENCES garages(id) ON DELETE CASCADE
);


CREATE TABLE cars(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     car_name VARCHAR(64) NOT NULL
);


CREATE TABLE car_parts(
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    car_id INT,
                    item_id INT,
                    connector_id INT,
                    FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE,
                    FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
                    FOREIGN KEY (connector_id) REFERENCES connectors(id) ON DELETE CASCADE
);


CREATE TABLE garage_car(
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           garage_id INT,
                           car_id INT,
                           FOREIGN KEY (garage_id) REFERENCES garages(id) ON DELETE CASCADE,
                           FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);


CREATE TABLE user_statistics(
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                user_id INT UNIQUE,
                                first_count INT DEFAULT 0,
                                second_count INT DEFAULT 0,
                                third_count INT DEFAULT 0,
                                lose_count INT DEFAULT 0,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE rewards(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        reward_name VARCHAR(64)
);


CREATE TABLE reward_item(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        reward_id INT,
                        item_id INT,
                        item_count INT,
                        FOREIGN KEY (reward_id)REFERENCES rewards(id) ON DELETE CASCADE,
                        FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);

CREATE TABLE game_results(
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             game_id INT UNIQUE,
                             first_place_id INT,
                             reward1_id INT,
                             second_place_id INT,
                             reward2_id INT,
                             third_place_id INT,
                             reward3_id INT,
                             FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
                             FOREIGN KEY (first_place_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (reward1_id) REFERENCES rewards(id) ON DELETE CASCADE,
                             FOREIGN KEY (second_place_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (reward2_id) REFERENCES rewards(id) ON DELETE CASCADE,
                             FOREIGN KEY (third_place_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (reward3_id) REFERENCES rewards(id) ON DELETE CASCADE
);

CREATE TABLE user_game(
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          user_id INT,
                          game_id INT,
                          car_id INT,
                          CONSTRAINT user_game_unique UNIQUE (user_id, game_id),
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
                          FOREIGN KEY (car_id)  REFERENCES cars(id)  ON DELETE CASCADE
);

CREATE TABLE orders(
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       order_status VARCHAR(16),
                       order_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_item(
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            order_id INT,
                            item_id INT,
                            item_amount INT,
                            source_destination INT,
                            FOREIGN KEY (order_id)REFERENCES orders(id) ON DELETE CASCADE,
                            FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);

CREATE TABLE user_order(
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           user_id INT,
                           order_id INT UNIQUE,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                           FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);
