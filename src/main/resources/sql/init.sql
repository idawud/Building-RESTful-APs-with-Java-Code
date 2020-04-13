CREATE TABLE todo (  id SERIAL PRIMARY KEY, message TEXT NOT NULL);
commit;
INSERT INTO todo(message) VALUES('Hello todo 1');
INSERT INTO todo(message) VALUES('Hello todo 2');
commit;
select * from todo;
select (id, message) from todo where id = 1;

UPDATE todo SET message = 'New hello todo 1' WHERE id = 1;
commit;

DELETE FROM todo WHERE id = 2;
commit;