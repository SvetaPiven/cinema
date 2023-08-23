create table public.language
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table public.language
    owner to development;

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
create table public.category
(
    id   bigserial
        primary key,
    name varchar not null
);

alter table public.category
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

alter table public.l_films_category
    owner to development;