create sequence if not exists users_seq start with 1 increment by 50;
create table if not exists users (
    id bigint not null,
    name varchar(255),
    role varchar(255),
    primary key (id)
);