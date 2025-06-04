INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$mZ6bnM08cxpZe01ByWUTqu7KXXUUQEHaW60ag8QaDKnTz3zEcgFGq', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$JrrvQ//.vkJqC1sawhaOPOBeWSLtFyMe3B2sJu/bJykhf9zc/7qQC', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$XsFSN0nHsNvejOu/id5pze1XqQxE0LbGm.fv117GKwVxzbP.2mXB2', '일반인2', 'USER');

COMMIT;
