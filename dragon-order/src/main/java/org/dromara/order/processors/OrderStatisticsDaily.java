package org.dromara.order.processors;

import lombok.extern.slf4j.Slf4j;
import org.dromara.order.domain.CompanySalesDTO;
import org.dromara.order.domain.OrderStatistics;
import org.dromara.order.mapper.OrderMapper;
import org.dromara.order.mapper.OrderStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OrderStatisticsDaily implements BasicProcessor {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderStatisticsMapper orderStatisticsMapper;

    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        //select data
        List<CompanySalesDTO> companySalesDTOS = orderMapper.queryStatistics();
        //build insert Data
        ArrayList<OrderStatistics> statistics = new ArrayList<>();
        for (CompanySalesDTO companySalesDTO : companySalesDTOS) {
            OrderStatistics os = new OrderStatistics();
            os.setCompanyId(companySalesDTO.getCompanyId());
            os.setSaleNum(Long.valueOf(companySalesDTO.getOrderCount()));
            os.setSale(companySalesDTO.getTotalSales());
            //today calculate last day data, so need -1
            os.setStatDate(LocalDate.now().minusDays(1));
            statistics.add(os);
        }
        //insert
        boolean b = orderStatisticsMapper.insertBatch(statistics);
        if (b){
            omsLogger.info("今天{}更新昨天{}的订单数据成功",LocalDate.now(),LocalDate.now().minusDays(1));
            return new ProcessResult(true, taskContext + ": " + true);
        }else {
            omsLogger.error("今天{}更新昨天{}的订单数据失败",LocalDate.now(),LocalDate.now().minusDays(1));
            return new ProcessResult(false, taskContext + ": " + true);
        }
    }
}
