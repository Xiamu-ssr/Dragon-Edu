package org.dromara.es.controller;

import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.es.domain.CourseBase;
import org.dromara.es.domain.bo.CourseQueryBo;
import org.dromara.es.service.CourseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseOpenControllerTest {

    @Autowired
    CourseOpenController courseOpenController;

    @Test
    void homePageList() {
        CourseQueryBo bo = new CourseQueryBo();
        bo.setPageNum(1);
        bo.setPageSize(6);

        // 第一次调用
        EsPageInfo<CourseBase> result1 = courseOpenController.homePageList(bo);

        // 第二次调用（应该走caffeine）
        EsPageInfo<CourseBase> result2 = courseOpenController.homePageList(bo);

        // 第二次调用（应该走caffeine）
        EsPageInfo<CourseBase> result3 = courseOpenController.homePageList(bo);

    }
}
