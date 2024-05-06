package org.dromara.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    OrderStatisticsService orderStatisticsService;

    @Test
    void queryRange() {
    }

    @Test
    void queryThisWeekSaleNum() {
        Long l = orderStatisticsService.queryThisWeekSaleNum(1782032703936839681L);
        System.out.println(l);
    }

    @Test
    void queryLastWeekSaleNum() {
        Long l = orderStatisticsService.queryLastWeekSaleNum(1782032703936839681L);
        System.out.println(l);
    }

    @Test
    void queryLastXWeekSaleNum() {
    }

    @Test
    void queryThisWeekSale() {
        BigDecimal sale = orderStatisticsService.queryThisWeekSale(1782032703936839681L);
        System.out.println(sale);
    }

    @Test
    void queryLastWeekSale() {
        BigDecimal sale = orderStatisticsService.queryLastWeekSale(1782032703936839681L);
        System.out.println(sale);
    }

    @Test
    void queryLastXWeekSale() {
        BigDecimal sale = orderStatisticsService.queryLastXWeekSale(1782032703936839681L, 2);
        System.out.println(sale);
    }
}
