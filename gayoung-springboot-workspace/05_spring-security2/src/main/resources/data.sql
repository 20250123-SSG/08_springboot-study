INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('admin', '$2a$10$cgfX2D81/c6ZxJ8zXybd4.y72.E0E2sAgzCvW0p0UbNLw57t3p9IC', '관리자', 'ADMIN');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user01', '$2a$10$dAxqv2lcCg7KpyxeZc6AiOAfCP/7AYQnS5to82jV6hnXJaapW2IBa', '일반인1', 'USER');

INSERT INTO tbl_user(user_id, user_pwd, user_name, user_role)
VALUES ('user02', '$2a$10$Osd/xhJVexR0pvr3m1JpkOZ6FjC3h9/cKaa4chFHIkdd0rj8kiZaq', '일반인2', 'USER');

COMMIT;