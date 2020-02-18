-- undo create_table_album

drop table album;

delete from flyway_schema_history
where version = '20200130185833';