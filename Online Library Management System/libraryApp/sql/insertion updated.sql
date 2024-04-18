INSERT INTO documenttype VALUES(1, 'Book');
INSERT INTO documenttype VALUES(2, 'Journal Article');
INSERT INTO documenttype VALUES(3, 'Magazine');
INSERT INTO documenttype VALUES(4, 'Thesis');
INSERT INTO documenttype VALUES(5, 'Techical Report');

SELECT * FROM documenttype;

INSERT INTO documents VALUES(1, 'Of Mice and Men', 'Puffin', 2001, 2, 5, '2008-04-12', 'John Douglas', null, 1);
INSERT INTO documents VALUES(2, 'The Big Bang', 'Sc.Edu', 2020, 1, 1, '2020-01-25', 'Paul Scott', 'Science Journal', 2);
INSERT INTO documents VALUES(3, 'The 4th Issue', 'Dunkin', 1956, 1, 4, '1956-09-05', 'Adam Dunn', 'Vogue', 3);

SELECT * FROM documents;

INSERT INTO docgenre VALUES(1, 'Fiction');
INSERT INTO docgenre VALUES(1, 'Tragedy');
INSERT INTO docgenre VALUES(2, 'Science');
INSERT INTO docgenre VALUES(3, 'Fashion');

ALTER TABLE members add duedate DATE;
ALTER TABLE members add returndate DATE;
ALTER TABLE members add bid INT;
ALTER TABLE members add rid INT;
ALTER TABLE members add did INT;
ALTER TABLE members add FOREIGN KEY(did) REFERENCES documents(did);

ALTER TABLE members add ontime BOOLEAN;

INSERT INTO members VALUES(1, 'Deshpande', 'Paddyesh', 'abcd', '1234', '2000-04-05', 'padyesh@padri.com', '2022-12-10', '2022-12-5', 2, 1, 1, 'YES');
INSERT INTO members VALUES(2, 'Ganguli', 'Rix', 'cdef', '5678', '2000-03-08', 'rix@rik.com', '2022-11-11', '2022-12-1', 1, 2, 2, 'NO');
INSERT INTO members VALUES(3, 'Bone', 'V', '401 E', '2468', '1999-10-01', 'vbone@bub.com', '2021-06-12', '2021-06-11', 3, 3, 3, 'YES');

SELECT * FROM members;

INSERT INTO librarians VALUES(1, 'Felix', 'Anthony', 'yadayada', '3344', '2015-08-15', '1998-11-30', 'anthonyf@g.com');
INSERT INTO librarians VALUES(2, 'DeWitt', 'Booker', 'hibijibi', '99999', '2019-12-25', '1975-05-02', 'dbooker@yuhu.com');
INSERT INTO librarians VALUES(3, 'Dawg', 'Jonty', 'sonuvabic', '420', '2021-01-01', '1994-10-10', 'jdawg@bic.com');

SELECT * FROM librarians;

ALTER TABLE loginaccess DROP COLUMN usertype;

INSERT INTO loginaccess VALUES(1, '123');
INSERT INTO loginaccess VALUES(2, '456');

ALTER TABLE liblogin DROP COLUMN usertype;
ALTER TABLE memlogin DROP COLUMN usertype;

DROP TABLE loginaccess;

CREATE TABLE loginAccess (
	aid INT,
	pwd VARCHAR(20),
	PRIMARY KEY(aid)
);

INSERT INTO loginaccess VALUES(1, '123');
INSERT INTO loginaccess VALUES(2, '456');

INSERT INTO liblogin VALUES(1, 2);
INSERT INTO memlogin VALUES(2, 1);

INSERT INTO record VALUES(1, 5, 3);
INSERT INTO record VALUES(2, 3, 3);
INSERT INTO record VALUES(3, 4, 1);

INSERT INTO details VALUES(1, 3);
INSERT INTO details VALUES(3, 2);
INSERT INTO details VALUES(2, 1);

INSERT INTO docauthor VALUES(1, 'Sajesh');
INSERT INTO docauthor VALUES(2, 'Rajesh');
INSERT INTO docauthor VALUES(1, 'Mukesh');

INSERT INTO maintain VALUES(1, 3);
INSERT INTO maintain VALUES(2, 1);
INSERT INTO maintain VALUES(3, 2);

INSERT INTO membership VALUES('2007-02-28', 1, 5, 5);
INSERT INTO membership VALUES('2010-11-03', 2, 10, 1);
INSERT INTO membership VALUES('1992-08-18', 3, 32, 12);
