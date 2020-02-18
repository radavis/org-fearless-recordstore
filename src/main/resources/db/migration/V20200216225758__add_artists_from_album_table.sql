-- add_artists_from_album_table

insert into artist(name)
select distinct artist from album;