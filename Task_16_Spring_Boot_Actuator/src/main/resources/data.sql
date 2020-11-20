insert into AUTHORS (id, name) values (1, 'King')
insert into AUTHORS (id, name) values (2, 'Kipling')

insert into GENRES (id, name) values (1, 'Thriller')
insert into GENRES (id, name) values (2, 'Drama')

insert into BOOKS (id, name, author_id, genre_id) values (1, 'It', 1, 2)
insert into BOOKS (id, name, author_id, genre_id) values (2, 'Maugli', 2, 1)

insert into COMMENTS (id, comment, book_id) values (1, 'Nice book', 1)
insert into COMMENTS (id, comment, book_id) values (2, 'glad to read', 1)

insert into COMMENTS (id, comment, book_id) values (3, 'worst book in the world', 2)
insert into COMMENTS (id, comment, book_id) values (4, 'waste time', 2)
