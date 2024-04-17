package org.dromara.es.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("搜索引擎模块测试")
class CourseControllerTest {

    @Autowired
    CourseController courseController;

    @Test
    void createIndex() {
        courseController.createIndex();
    }

    @Test
    void insert() {
    }
}
