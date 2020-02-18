-- undo add_artist_id_to_album_table

alter table album
drop column artist_id;

delete from flyway_schema_history
where version = '20200212235418';