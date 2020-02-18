-- update_artist_ids_on_album_table

update album
inner join artist on album.artist = artist.name
set album.artist_id = artist.id;