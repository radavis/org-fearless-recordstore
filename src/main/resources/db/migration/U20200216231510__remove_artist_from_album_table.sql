-- undo remove_artist_from_album_table

alter table album
add column artist varchar(255);

delete from flyway_schema_history
where version = '20200216231510';