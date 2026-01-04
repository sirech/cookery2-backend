DELETE FROM ingredients;
DELETE FROM steps;
DELETE FROM recipes;

INSERT INTO recipes(id, name, servings)
VALUES
(1, 'Pasta carbonara', 3),
(2, 'Lentejas', 2),
(3, 'Pollo al grill', 4);

ALTER TABLE recipes ALTER COLUMN id RESTART WITH 4;

INSERT INTO steps(description, duration, recipe_id)
VALUES
('Cook the pasta', 7, 1),
('Fry the bacon', 5, 1);

INSERT INTO ingredients(name, quantity, unit, recipe_id)
VALUES
('pasta', 300, 'gr', 1),
('egg', 2, 'unit', 1),
('guanciale', 150, 'gr', 1);
