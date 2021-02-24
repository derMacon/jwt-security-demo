create table if not exists app_user
(
    user_id  serial primary key,
    username varchar(100) unique not null,
    password varchar(1000)       not null,
    role     varchar(100)        not null,
    email    varchar(100)        not null
);



