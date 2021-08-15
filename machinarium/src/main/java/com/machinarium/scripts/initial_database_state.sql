USE machinarium_database;

INSERT INTO item_categories (category_name)
VALUES
        ("PART"),
        ("CONNECTOR");


INSERT INTO item_types (type_name, item_category_id)
VALUES 
		("CHASSIS", 1),
        ("BODY", 1),
        ("ENGINE", 1),
        ("TRANSMISSION", 1),
        ("WHEELS", 1);
        


INSERT INTO connectors (connector_name, item_type_1_id, item_type_2_id)
VALUES 
		("Body Mount", 1, 2),
		("Transmission Mount", 1, 4),
        ("Suspension", 1, 5),
        ("Engine Bolts", 1, 3),
        ("Friction Plate", 3, 4),
        ("Differential", 4, 5);  
        

INSERT INTO items (item_name, type_id, weight, weight_support, aero_drag, horse_power, traction_unit)
VALUES
		("Carbon Fiber", 1, 50, 1200, null, null, null),
        ("Low Drag", 2, 300, null, 1, null, null),
        ("High Downforce", 2, 100, null, 3, null, null),
        ("2-JZ", 3, 600, null, null, 800, null),
        ("Rotary", 3, 300, null, null, 500,null),
        ("ZF 8-Speed", 4, 50, null, null, null, null),
        ("Soft Compound", 5, 300, null, null, null, 7),
        ("Hard Compound", 5, 100, null, null, null, 5);
        
INSERT INTO game_stages (stage_name)
VALUES
       ('in_lobby'),
       ('active')
       ('finish');
/*
select * from item_categories order by id asc;
select * from item_types order by id asc;
select * from connectors order by id asc;
select * from items order by id asc; 
*/
