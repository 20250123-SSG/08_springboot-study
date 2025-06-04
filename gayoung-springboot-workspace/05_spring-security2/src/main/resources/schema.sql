USE bootdb;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE IF NOT EXISTS tbl_user
(
    user_no       INT AUTO_INCREMENT,
    user_id       VARCHAR(100) NOT NULL UNIQUE,
    user_pwd      VARCHAR(100),
    user_name     VARCHAR(100),
    user_role     VARCHAR(100) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_no)
    ) Engine=InnoDB;