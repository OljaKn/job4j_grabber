create table post(
id serial primary key,
"name" varchar(100),
"text" text, 
link varchar UNIQUE,
created timestamp
);