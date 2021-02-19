create table if not exists app_user
(
    user_id  serial primary key,
    username varchar(500) unique not null,
    password varchar(5000)       not null
);



