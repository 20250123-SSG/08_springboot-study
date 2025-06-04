INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$hgJxGCHnxleojEfbInWL8ePt6kQ2GWskThIsyLrASkxe1UG/w4SWC', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$OWF43VLT/I/9DTMAM.2ZpO377v4wTcdMRcVtDb0KlOxs07eej91KW', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$tcY3lIQ1/kuHCnJn2ot1neSEBHr5QCC.qvCdhrmbqMiwtFx8DExSK', '일반인2', 'USER');

COMMIT;

