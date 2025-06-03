package com.ino.app.mapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void selectBoardListCount() {
    }

    @Test
    void selectBoardList() {
    }

    @Test
    void insertBoard() {
    }

    @Test
    void insertAttach() {
    }

    @Test
    void selectBoardByNo() {
    }

    @Test
    void selectAttachByBoardNo() {
    }
}