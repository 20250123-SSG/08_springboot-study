INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$j7I5Si9zB7CF06GpHfXPLu5vuLw2jLAiIzTEPxvkaYFm9MOC3KLTy', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$QZCbiI37lw25Key/3tKVY.j1oiWhORiYIz4LU56HrBgg98kx50hfW', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$lQ7WlzImNd8/slnlj6EPGOgwxAw9fJa9cwkTsMGBv4nfOvLsr4mcO', '일반인2', 'USER');

COMMIT;

