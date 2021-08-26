create table if not exists `products` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50),
    `price` int,
    `description` varchar(500),
    `date` date
);

insert into products (name, price, description, date) values ('Apple', 45 , 'Green apple', '2021-08-12'), ('Milk', 70 , 'Higt quality milk', '2021-08-12'),('Banana', 30 , 'Banana from Africa', '2021-08-12'), ('Bread', 10 , 'White bread', '2021-08-12'), ('Honey', 145 , 'Honey from valley', '2021-08-12'), ('Salmon', 260 , 'High quality salmon', '2021-08-12');
