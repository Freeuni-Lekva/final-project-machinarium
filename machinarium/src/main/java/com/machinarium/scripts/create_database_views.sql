USE machinarium_database;


/* view for user statistics full information */
CREATE OR REPLACE VIEW see_user_results AS
SELECT s.user_id, u.user_name, s.first_count, s.second_count, s.third_count, s.lose_count
FROM user_statistics s
         LEFT JOIN users u
                   ON s.user_id = u.id;

/* view for user rewards full information */
CREATE OR REPLACE VIEW see_user_rewards AS
SELECT     gr.game_id game_id, gr.id user_id, u1.user_name,
           gr.reward1_id reward_id, r1.reward_name
FROM game_results gr
         LEFT JOIN users u1
                   ON gr.first_place_id = u1.id
         LEFT JOIN rewards r1
                   ON gr.reward1_id = r1.id
UNION
SELECT     gr.game_id game_id, gr.id user_id, u2.user_name,
           gr.reward2_id reward_id, r2.reward_name
FROM game_results gr
         LEFT JOIN users u2
                   ON gr.second_place_id = u2.id
         LEFT JOIN rewards r2
                   ON gr.reward2_id = r2.id
UNION
SELECT     gr.game_id game_id, gr.id user_id, u3.user_name,
           gr.reward3_id reward_id, r3.reward_name
FROM game_results gr
         LEFT JOIN users u3
                   ON gr.third_place_id = u3.id
         LEFT JOIN rewards r3
                   ON gr.reward3_id = r3.id;

/* view for user orders full information */
CREATE OR REPLACE VIEW see_user_orders AS
SELECT u.id user_id, u.user_name user_name, o.id order_id,
       ordi.item_id item_id, i.item_name, ordi.item_amount, ordi.source_destination, o.order_status, o.order_date
FROM user_order ord
         LEFT JOIN users u
                   ON ord.user_id = u.id
         LEFT JOIN orders o
                   ON ord.order_id = o.id
         LEFT JOIN order_item ordi
                   ON o.id = ordi.order_id
         LEFT JOIN items i
                   ON ordi.item_id = i.id;

/* view for user cars full information */
CREATE OR REPLACE VIEW see_user_cars AS
SELECT u.id user_id, ug.garage_id garage_id, c.id car_id, c.car_name
FROM users u
         LEFT JOIN user_garage ug
                   ON u.id = ug.user_id
         LEFT JOIN garages g
                   ON ug.garage_id = g.id
         LEFT JOIN garage_car gc
                   ON g.id = gc.garage_id
         LEFT JOIN cars c
                   ON gc.car_id = c.id;

/* view for car parts full information */
CREATE OR REPLACE VIEW see_car_parts AS
SELECT c.id car_id, c.car_name car_name, i.id item_id,
       i.item_name, i.type_id, it.type_name,
       it.item_category_id, itc.category_name,
       i.weight, i.weight_support,
       i.aero_drag, i.horse_power, i.traction_unit,
       i.item_type_1_id, it1.type_name it1_type_name,
       it1.item_category_id it1_item_category_id,
       itc1.category_name it1_category_name,
       i.item_type_2_id, it1.type_name it2_type_name,
       it2.item_category_id it2_item_category_id,
       itc2.category_name it2_category_name
FROM car_parts cp
         LEFT JOIN cars c
                   ON cp.car_id = c.id
         LEFT JOIN items i
                   ON cp.item_id = i.id
         LEFT JOIN item_types it
                   ON i.type_id = it.id
         LEFT JOIN item_categories itc
                   ON it.item_category_id = itc.id
         LEFT JOIN item_types it1
                   ON i.item_type_1_id = it1.id
         LEFT JOIN item_categories itc1
                   ON it1.item_category_id = itc1.id
         LEFT JOIN item_types it2
                   ON i.item_type_2_id = it2.id
         LEFT JOIN item_categories itc2
                   ON it2.item_category_id = itc2.id;

/* view for user spare items full information */
CREATE OR REPLACE VIEW see_user_items AS
SELECT u.id user_id, u.user_name, ug.garage_id,
       gi.item_id, i.item_name, i.type_id, it.type_name, gi.item_count
FROM users u
         LEFT JOIN user_garage ug
                   ON u.id = ug.user_id
         LEFT JOIN garages g
                   ON ug.garage_id = g.id
         LEFT JOIN garage_item gi
                   ON g.id = gi.garage_id
         LEFT JOIN items i
                   ON gi.item_id = i.id
         LEFT JOIN item_types it
                   ON i.type_id = it.id;

/* view for game full results */
CREATE OR REPLACE VIEW see_game_results AS
SELECT gr.game_id, u1.id first_uid, u1.user_name first_user_name,
       r1.item_id reward1_id, r1.item_count reward1_item_count,
       u2.id second_uid, u2.user_name second_user_name,
       r2.item_id reward2_id, r2.item_count reward2_item_count,
       u3.id third_uid, u3.user_name third_user_name,
       r3.item_id reward3_id, r3.item_count reward3_item_count
FROM game_results gr
         LEFT JOIN users u1
                   ON gr.first_place_id = u1.id
         LEFT JOIN users u2
                   ON gr.second_place_id = u2.id
         LEFT JOIN users u3
                   ON gr.third_place_id = u3.id
         LEFT JOIN games g
                   ON gr.game_id = g.id
         LEFT JOIN reward_item r1
                   ON gr.reward1_id = r1.reward_id
         LEFT JOIN reward_item r2
                   ON gr.reward2_id = r2.reward_id
         LEFT JOIN reward_item r3
                   ON gr.reward3_id = r3.reward_id;

/* view for full information about users and games */
CREATE OR REPLACE VIEW see_user_game AS
SELECT u.id user_id, u.user_name user_name,
       g.id game_id, g.game_name game_name,
       g.game_date game_date, host.id user_host_id,
       host.user_name user_host_name,
       g.game_stage_id game_stage_id,
       gs.stage_name stage_name,
       c.id car_id, c.car_name car_name
FROM user_game ug
         LEFT JOIN users u
                   ON ug.user_id = u.id
         LEFT JOIN games g
                   ON ug.game_id = g.id
         LEFT JOIN cars c
                   ON ug.car_id = c.id
         LEFT JOIN users host
                   ON g.user_host_id = host.id
         LEFT JOIN game_stages gs
                   ON g.game_stage_id = gs.id;

/* view for games full information */
CREATE OR REPLACE VIEW see_games AS
SELECT g.id game_id, g.game_name game_name,
       g.game_date game_date, gs.id game_stage_id,
       gs.stage_name stage_name, u.id user_host_id,
       u.user_name user_host_name
FROM games g
         LEFT JOIN game_stages gs
                   ON g.game_stage_id = gs.id
         LEFT JOIN users u
                   ON g.user_host_id = u.id;

/* view for items full information */
CREATE OR REPLACE VIEW see_items AS
SELECT i.id item_id, i.item_name, it.id type_id,
       it.type_name type_name, it.item_category_id,
       ic.category_name, i.weight, i.weight_support,
       i.aero_drag, i.horse_power, i.traction_unit,
       i.item_type_1_id, it1.type_name it1_type_name,
       it1.item_category_id it1_item_category_id,
       itc1.category_name it1_category_name,
       i.item_type_2_id, it1.type_name it2_type_name,
       it2.item_category_id it2_item_category_id,
       itc2.category_name it2_category_name
FROM items i
         JOIN item_types it
              ON i.type_id = it.id
         JOIN item_categories ic
              ON it.item_category_id = ic.id
         LEFT JOIN item_types it1
                   ON i.item_type_1_id = it1.id
         LEFT JOIN item_categories itc1
                   ON it1.item_category_id = itc1.id
         LEFT JOIN item_types it2
                   ON i.item_type_2_id = it2.id
         LEFT JOIN item_categories itc2
                   ON it2.item_category_id = itc2.id;
/*
;select * from see_user_results
;select * from see_user_rewards
;select * from see_user_orders
;select * from see_user_items
;select * from see_car_parts
;select * from see_game_results
;
*/
