/* view for user statistics full information */
CREATE OR REPLACE VIEW see_user_results AS 
	SELECT s.user_id, u.user_name, s.first_count, s.second_count, s.third_count, s.lose_count
	FROM user_statistics s
    LEFT JOIN users u 
    ON s.user_id = u.id;

/* view for user orders full information */
CREATE OR REPLACE VIEW see_user_orders AS
	SELECT u.id user_id, u.user_name user_name, o.id order_id, 			
            ordi.item_id item_id, i.item_name, ordi.item_amount, ordi.source_destination, o.order_status, o.order_date
    FROM user_order ord
    LEFT JOIN users u
    ON ord.user_id = u.id
    LEFT JOIN orders o
    ON ord.order_id = o.id
    LEFT JOIN order_items ordi
    ON o.id = ordi.order_id
    LEFT JOIN items i
    ON ordi.item_id = i.id;

/* view for user cars full information */
CREATE OR REPLACE VIEW see_user_cars AS
	SELECT *
    FROM users u
    LEFT JOIN user_garage ug
    ON u.id = ug.user_id
    LEFT JOIN garage g
    ON ug.garage_id = g.id
    LEFT JOIN garage_car gc
    ON g.id = gc.garage_id
    LEFT JOIN cars c
    ON gc.car_id = c.id;
    
/* view for user spare items full information */    
CREATE OR REPLACE VIEW see_user_items AS
	SELECT u.id user_id, u.user_name, ug.garage_id, 
			gi.item_id, i.item_name, gi.item_count
    FROM users u
    LEFT JOIN user_garage ug
    ON u.id = ug.user_id
    LEFT JOIN garage g
    ON ug.garage_id = g.id
    LEFT JOIN garage_item gi
    ON g.id = gi.garage_id
    LEFT JOIN items i
    ON gi.item_id = i.id;
    
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
    LEFT JOIN rewards r1
    ON gr.reward1_id = r1.id
    LEFT JOIN rewards r2
    ON gr.reward2_id = r2.id
    LEFT JOIN rewards r3
    ON gr.reward3_id = r3.id;
    
 /*   
;select * from see_user_results
;select * from see_user_orders
;select * from see_user_items
;select * from see_game_results
*/
