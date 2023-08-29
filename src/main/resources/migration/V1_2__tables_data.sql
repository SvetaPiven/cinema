insert into public.language (id, name)
values  (1, 'eng'),
        (2, 'fr'),
        (3, 'gr'),
        (4, 'ru'),
        (5, 'br'),
        (6, 'ita'),
        (7, 'ukr');

insert into public.film (id, title, language_id)
values  (1, 'Mr.nobody', 1),
        (2, 'Gostia iz bugushego', 4),
        (3, 'Indiana Jones', 1),
        (4, 'La vita e bella', 6),
        (5, 'Malena', 6),
        (6, 'Amélie', 2),
        (7, 'Belle Maman', 2),
        (8, 'Die Tür', 3),
        (9, 'Ludzi na baloce', 5),
        (10, 'Rusalka', 4);

insert into public.category (id, name)
values  (1, 'Comedy'),
        (2, 'Сartoons'),
        (3, 'Horror'),
        (5, 'Thrillers'),
        (6, 'Melodrama'),
        (7, 'Detectives'),
        (4, 'Science fiction'),
        (8, 'Action'),
        (9, 'Adventure'),
        (10, 'Drama');

insert into public.l_films_category (films_id, category_id)
values  (1, 6),
        (1, 4),
        (2, 4),
        (3, 8),
        (3, 9),
        (4, 10),
        (4, 6),
        (5, 6),
        (6, 6),
        (6, 1),
        (7, 1),
        (8, 10),
        (9, 3),
        (10, 10),
        (10, 6);
