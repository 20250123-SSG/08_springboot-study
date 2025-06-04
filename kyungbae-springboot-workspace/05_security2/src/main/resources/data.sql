INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$NV7RF7Mglitk0ZZ5a8qAP.MJVSw41PmBMvBbL8Frnib7S1Oxdw2PC', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$3hoj5KFVekE20fuzi9OHp.LQ.omgHPTY.sKJ9ApnmZAqkSWZechyS', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$LL/iI3QcRuTWZr2L/Cw5x.fpA6pDqO6WrY0gnxnLEUK72C4T7JKA.', '일반인2', 'USER');

COMMIT;