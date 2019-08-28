INSERT INTO recipes(id, name, servings)
VALUES
(1, 'Pasta carbonara', 3),
(2, 'Lentejas', 2),
(3, 'Pollo al grill', 4);

INSERT INTO steps(description, duration, recipe_id)
VALUES
('Cook the pasta', 7, 1),
('Fry the bacon', 5, 1);
