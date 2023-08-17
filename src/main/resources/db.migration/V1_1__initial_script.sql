create table if not exist public.language
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table public.language
    owner to development;

create table if not exist public.film
(
    id          bigserial
        primary key,
    title       varchar not null,
    language_id bigserial
        constraint film_language_id_fk
            references public.language
);

alter table if not exist public.film
    owner to development;
create table public.category
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table if not exist public.category
    owner to development;

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

alter table if not exist public.l_films_category
    owner to development;