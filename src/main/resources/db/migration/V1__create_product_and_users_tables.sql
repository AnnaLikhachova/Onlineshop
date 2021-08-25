create table if not exists `products` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50),
    `price` int,
    `description` varchar(500),
    `date` date
);

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


insert into products (name, price, description, date) values ('Apple', 45 , 'Green apple', '2021-08-12'), ('Milk', 70 , 'Higt quality milk', '2021-08-12'),('Banana', 30 , 'Banana from Africa', '2021-08-12'), ('Bread', 10 , 'White bread', '2021-08-12'), ('Honey', 145 , 'Honey from valley', '2021-08-12'), ('Salmon', 260 , 'High quality salmon', '2021-08-12');
insert into roles (role) values ('user'),('admin');
insert into users (login, password, role_id) values ('admin', '7d4bd6ad042984d1075a8f33a183d423da7c3ec51a7ba08f713673d6d8e07c', 2), ('user', '1310cfd4cb4cfd23547f873d74469dd0e8350d63b3e810f11d2d10ecc2', 1);

