INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$s.TIyxSLltwn6958aSqwEOEkIgxvxTPLoEwPB.Bj/Fzz76msvU/Z.', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$BE3MTznQ6VwAPdCsb0KaCureeD9p08YaHAUTIUIso50UxlJGuWvRi', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$QZj5RkZ70X.aehfC6mLf3OYQeSlh0gmIzv/1EFKFGj4qiBMQnpyYi', '일반인2', 'USER');

COMMIT;