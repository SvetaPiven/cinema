create table public.language
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table public.language
    owner to development;

insert into public.language (name)
values  ('eng'),
        ('fr'),
        ('gr'),
        ('ru'),
        ('br'),
        ('ita'),
        ('ukr');

create table public.film
(
    id          bigserial
        primary key,
    title       varchar not null,
    language_id bigserial
        constraint film_language_id_fk
            references public.language
);

alter table public.film
    owner to development;

insert into public.film (title, language_id)
values  ('Mr.nobody', 1),
        ('Gostia iz bugushego', 4),
        ('Indiana Jones', 1),
        ('La vita e bella', 6),
        ('Malena', 6),
        ('Amélie', 2),
        ('Belle Maman', 2),
        ('Die Tür', 3),
        ('Ludzi na baloce', 5),
        ('Rusalka', 4);

create table public.category
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table public.category
    owner to development;

insert into public.category (name)
values  ('Comedy'),
        ('Сartoons'),
        ('Horror'),
        ('Thrillers'),
        ('Melodrama'),
        ('Detectives'),
        ('Science fiction'),
        ('Action'),
        ('Adventure'),
        ('Drama');

create table public.l_films_category
(
    films_id    bigint not null
        constraint l_films_category_film_id_fk
            references public.film,
    category_id bigint not null
        constraint l_films_category_category_id_fk
            references public.category,
    constraint l_films_category_pk
        primary key (films_id, category_id)
);

alter table public.l_films_category
    owner to development;


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
        (6, 1);