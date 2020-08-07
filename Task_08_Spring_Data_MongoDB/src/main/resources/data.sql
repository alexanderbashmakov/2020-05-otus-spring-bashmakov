insert into AUTHORS (id, name) values (1, 'King')
insert into AUTHORS (id, name) values (2, 'Kipling')

insert into GENRES (id, name) values (1, 'Thriller')
insert into GENRES (id, name) values (2, 'Drama')

insert into BOOKS (id, name, author_id, genre_id) values (1, 'It', 1, 2)
insert into BOOKS (id, name, author_id, genre_id) values (2, 'Maugli', 2, 1)
