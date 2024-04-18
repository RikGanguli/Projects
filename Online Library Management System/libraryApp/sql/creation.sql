CREATE TABLE members (
	mid INT PRIMARY KEY,
	lastname VARCHAR(40),
	firstname VARCHAR(40),
	address VARCHAR(100),
	contact INT,
	dob DATE,
	email VARCHAR(100)
);

CREATE TABLE membership (
	doj DATE,
	mid INT,
	borrowcount INT,
	latefees DECIMAL,
	FOREIGN KEY(mid) REFERENCES members(mid),
	PRIMARY KEY(doj, mid)
);

CREATE TABLE loginAccess (
	aid INT,
	usertype VARCHAR(10), 
	pwd VARCHAR(20),
	PRIMARY KEY(usertype, aid)
);

CREATE TABLE memLogin (
	mid INT,
	usertype VARCHAR(10), 
	aid INT,
	FOREIGN KEY(mid) REFERENCES members(mid),
	PRIMARY KEY(usertype, aid)
);

CREATE TABLE documentType (
	dtid INT PRIMARY KEY,
	doctype VARCHAR(20)
);
	
CREATE TABLE documents (
	did INT PRIMARY KEY,
	title VARCHAR(100),
	publisher VARCHAR(40),
	publishyear INT,
	edition INT,
	issueno INT,
	issuedate DATE,
	editor VARCHAR(100),
	docname VARCHAR(100),
	dtid INT,
	FOREIGN KEY(dtid) REFERENCES documentType(dtid)
);

CREATE TABLE docGenre (
	did INT,
	genre VARCHAR(50),
	FOREIGN KEY(did) REFERENCES documents(did),
	PRIMARY KEY(did, genre)
);

CREATE TABLE docAuthor (
	did INT,
	author VARCHAR(100),
	FOREIGN KEY(did) REFERENCES documents(did),
	PRIMARY KEY(did, author)
);

CREATE TABLE record (
	recid INT PRIMARY KEY,
	total INT,
	issued INT,
	remaining INT GENERATED ALWAYS AS (total - issued) STORED
);

CREATE TABLE details (
	recid INT PRIMARY KEY,
	did INT,
	FOREIGN KEY(recid) REFERENCES record(recid),
	FOREIGN KEY(did) REFERENCES documents(did)
);

CREATE TABLE librarians (
	lid INT PRIMARY KEY,
	lastname VARCHAR(40),
	firstname VARCHAR(40),
	address VARCHAR(100),
	contact INT,
	doj DATE,
	dob DATE,
	email VARCHAR(100)
);


CREATE TABLE maintain (
	lid INT,
	did INT,
	FOREIGN KEY(lid) REFERENCES librarians(lid),
	FOREIGN KEY(did) REFERENCES documents(did),
	PRIMARY KEY(lid, did)
);
	
CREATE TABLE libLogin (
	lid INT,
	usertype VARCHAR(10), 
	aid INT,
	FOREIGN KEY(lid) REFERENCES librarians(lid),
	PRIMARY KEY(usertype, aid)
);