-- create_table_artist

create table artist (
  id serial,
  name varchar(255) not null,
  location varchar(255),
  year smallint
);