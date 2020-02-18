-- undo create_table_artist

drop table artist;

delete from flyway_schema_history
where version = '20200212232326';