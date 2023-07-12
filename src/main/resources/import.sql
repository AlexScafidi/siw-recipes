insert into ingredient (id, name) values(nextval('ingredient_seq'), 'Pomodoro');
insert into ingredient (id, name) values(nextval('ingredient_seq'), 'Basilico');
insert into ingredient (id, name) values(nextval('ingredient_seq'), 'Insalata Iceberg');
insert into ingredient (id, name) values(nextval('ingredient_seq'), 'Aglio');
insert into ingredient (id, name) values(nextval('ingredient_seq'), 'Acciuga');


insert into category (id, name, description) values(nextval('category_seq'), 'Primi piatti', 'Descrizione primi piatti');
insert into category (id, name, description) values(nextval('category_seq'), 'Secondi piatti', 'Descrizione secondi piatti');
insert into category (id, name, description) values(nextval('category_seq'), 'Antipasti', 'Descrizione antipasti');
insert into category (id, name, description) values(nextval('category_seq'), 'Contorni', 'Descrizione contorni');
insert into category (id, name, description) values(nextval('category_seq'), 'Dolci', 'Descrizione dolci');


insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(nextval('ingredient_quantity_seq'), 'g', 1, 1);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(nextval('ingredient_quantity_seq'), 'g', 1, 51);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(nextval('ingredient_quantity_seq'), 'g', 1, 101);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(nextval('ingredient_quantity_seq'), 'g', 1, 151);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(nextval('ingredient_quantity_seq'), 'g', 1, 201);


insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 1', null, 1);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 2', null, 51);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 3', null, 101);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 4', null, 151);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), false, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 5', null, 201);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(nextval('recipe_seq'), false, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 6', null, 1);