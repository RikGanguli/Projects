SELECT * FROM loginaccess;
SELECT * FROM memlogin;
SELECT * FROM liblogin;
select * from members;
select * from membership;
select * from librarians;
select * from members inner join membership using (mid) where mid = 1;