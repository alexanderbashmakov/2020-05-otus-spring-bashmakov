insert into AUTHORS (id, name) values (1, 'TestAuthor1')
insert into AUTHORS (id, name) values (2, 'TestAuthor2')

insert into GENRES (id, name) values (3, 'TestGenre1')
insert into GENRES (id, name) values (4, 'TestGenre2')

insert into BOOKS (id, name, author_id, genre_id) values (3, 'TestBook', 1, 3)