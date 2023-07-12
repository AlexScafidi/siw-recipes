insert into ingredient (id, name) values(1, 'Pomodoro');
insert into ingredient (id, name) values(2, 'Basilico');
insert into ingredient (id, name) values(3, 'Insalata Iceberg');
insert into ingredient (id, name) values(4, 'Aglio');
insert into ingredient (id, name) values(5, 'Acciuga');


insert into category (id, name, description) values(1, 'Primi piatti', 'Descrizione primi piatti');
insert into category (id, name, description) values(2, 'Secondi piatti', 'Descrizione secondi piatti');
insert into category (id, name, description) values(3, 'Antipasti', 'Descrizione antipasti');
insert into category (id, name, description) values(4, 'Contorni', 'Descrizione contorni');
insert into category (id, name, description) values(5, 'Dolci', 'Descrizione dolci');


insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(1, 'g', 1, 1);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(2, 'g', 1, 2);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(3, 'g', 1, 3);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(4, 'g', 1, 4);
insert into ingredient_quantity (id, unit_of_measure, quantity, ingredient_id) values(5, 'g', 1, 5);


insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(1, true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 1', null, 1);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(2, true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 2', null, 2);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(3, true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 3', null, 3);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(4, true, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 4', null, 4);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(5, false, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 5', null, 5);
insert into recipe (id, is_new, preparation_text, presentation_text, title, author_id, category_id) values(6, false, 'testo di preparazione ricetta', 'testo di presentazione ricetta', 'Titolo ricetta 6', null, 1);