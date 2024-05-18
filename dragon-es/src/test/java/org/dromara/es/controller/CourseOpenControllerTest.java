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
    CourseService courseService;

    @Test
    void homePageList() {
        CourseQueryBo bo = new CourseQueryBo();
        bo.setPageNum(1);
        bo.setPageSize(6);

        // 第一次调用
        EsPageInfo<CourseBase> result1 = courseService.homePageList(bo);

        // 第二次调用（应该走缓存）
        EsPageInfo<CourseBase> result2 = courseService.homePageList(bo);

        // 检查两次调用返回结果是否相同
        Assertions.assertEquals(result1, result2);

        // 检查日志中是否只出现一次执行标识
    }
}
