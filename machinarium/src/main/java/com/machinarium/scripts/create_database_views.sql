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

;select * from see_user_results
;select * from see_user_orders
