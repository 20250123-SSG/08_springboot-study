USE bootdb;

DROP TABLE IF EXISTS tbl_board;
CREATE TABLE IF NOT EXISTS tbl_board
(
    board_id  INT AUTO_INCREMENT,
    title     VARCHAR(100) NOT NULL,
    content  VARCHAR(100),
    create_dt DATETIME DEFAULT NOW(),
    CONSTRAINT pk_board PRIMARY KEY(board_id)
) Engine=InnoDB;

ALTER TABLE tbl_board AUTO_INCREMENT = 1001;