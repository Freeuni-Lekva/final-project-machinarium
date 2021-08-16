USE machinarium_database;

INSERT INTO item_categories (category_name)
VALUES
        ('PART_CAT'),
        ('CONNECTOR_CAT');


INSERT INTO item_types (type_name, item_category_id)
VALUES 
		('CHASSIS', 1),
        ('BODY', 1),
        ('ENGINE', 1),
        ('TRANSMISSION', 1),
        ('WHEELS', 1),
        ('CONNECTOR', 2);


INSERT INTO connectors (connector_name, item_type_1_id, item_type_2_id)
VALUES 
		('Body Mount', 1, 2),
		('Transmission Mount', 1, 4),
        ('Suspension', 1, 5),
        ('Engine Bolts', 1, 3),
        ('Friction Plate', 3, 4),
        ('Differential', 4, 5);  
        

INSERT INTO items (item_name, type_id, weight, weight_support, aero_drag, horse_power, traction_unit)
VALUES
		('Carbon Fiber', 1, 50, 1200, null, null, null),
        ('Low Drag', 2, 300, null, 1, null, null),
        ('High Downforce', 2, 100, null, 3, null, null),
        ('2-JZ', 3, 600, null, null, 800, null),
        ('Rotary', 3, 300, null, null, 500,null),
        ('ZF 8-Speed', 4, 50, null, null, null, null),
        ('Soft Compound', 5, 300, null, null, null, 7),
        ('Hard Compound', 5, 100, null, null, null, 5);


INSERT INTO items (item_name, type_id, item_type_1_id, item_type_2_id)
VALUES
        ('Body Mount', 6, 1, 2),
        ('Transmission Mount', 6, 1, 4),
        ('Suspension', 6, 1, 5),
        ('Engine Bolts', 6, 1, 3),
        ('Friction Plate', 6, 3, 4),
        ('Differential', 6, 4, 5);


INSERT INTO game_stages (stage_name)
VALUES
       ('in_lobby'),
       ('active'),
       ('finish');

INSERT INTO rewards(id, reward_name)
VALUES
        (1, 'first_a'),
        (2, 'first_b'),
        (3, 'second_a'),
        (4, 'second_b'),
        (5, 'third_a'),
        (6, 'first_b');
INSERT INTO reward_item(reward_id, item_id, item_count)
VALUES
        (1, 9, 3),
        (1,	3,	2),
        (2,	10,	3),
        (2,	4,	2),
        (3,	13,	2),
        (3,	6,	1),
        (4,	14,	2),
        (4,	5,	1),
        (5,	11,	1),
        (6,	12,	1);

/*
select * from item_categories order by id asc;
select * from item_types order by id asc;
select * from connectors order by id asc;
select * from items order by id asc; 
*/
