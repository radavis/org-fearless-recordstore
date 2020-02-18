-- add_artist_id_to_album_table

alter table album
add column artist_id bigint unsigned;