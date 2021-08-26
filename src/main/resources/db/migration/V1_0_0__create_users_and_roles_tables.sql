create table if not exists `roles` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role` varchar(50)
);

create table if not exists `users` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `login` varchar(50),
    `password` varchar(500),
    `role_id` int,
     FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);

insert into roles (role) values ('user'),('admin');
insert into users (login, password, role_id) values ('admin', '7d4bd6ad042984d1075a8f33a183d423da7c3ec51a7ba08f713673d6d8e07c', 2), ('user', '1310cfd4cb4cfd23547f873d74469dd0e8350d63b3e810f11d2d10ecc2', 1);

