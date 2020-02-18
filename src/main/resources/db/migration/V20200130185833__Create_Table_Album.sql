-- create_table_album

create table album (
  id serial,
  artist varchar(255) not null,
  title varchar(255) not null,
  year smallint
);
