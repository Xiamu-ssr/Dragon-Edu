package org.dromara.order.processors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.order.domain.CompanySalesDTO;
import org.dromara.order.domain.OrderStatistics;
import org.dromara.order.mapper.OrderMapper;
import org.dromara.order.mapper.OrderStatisticsMapper;
import org.dromara.order.service.SaleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    SaleDataService saleDataService;

    @Override
    @Transactional
    public ProcessResult process(TaskContext taskContext) throws Exception {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        //select data
        List<CompanySalesDTO> companySalesDTOS = orderMapper.queryStatistics();
        if (companySalesDTOS.isEmpty()){
            omsLogger.info("昨天{}没有新订单",LocalDate.now().minusDays(1));
            return new ProcessResult(true, taskContext + ": " + true);
        }
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
        //insert daily table
        //check if last day already handle
        boolean b1 = true;
        boolean exists = orderStatisticsMapper.exists(new LambdaQueryWrapper<OrderStatistics>()
            .eq(OrderStatistics::getCompanyId, statistics.get(0).getCompanyId())
            .eq(OrderStatistics::getStatDate, statistics.get(0).getStatDate())
        );
        if (exists){
            omsLogger.debug("昨天{}的order_statistics(每日销售情况表)数据已经更新，无需二次更新,sale_data表也一起忽略", LocalDate.now().minusDays(1));
            return new ProcessResult(true, taskContext + ": " + true);
        }else {
            b1 = orderStatisticsMapper.insertBatch(statistics);
            if (b1){
                omsLogger.debug("昨天{}的order_statistics(每日销售情况表)数据更新完成",LocalDate.now().minusDays(1));
            }else {
                omsLogger.error("昨天{}的order_statistics(每日销售情况表)数据更新失败",LocalDate.now().minusDays(1));
                throw new RuntimeException("order_statistics(每日销售情况表)数据更新失败");
                //return new ProcessResult(false, taskContext + ": " + true);
            }
        }
        //update total sale_data
        boolean b2 = true;
        for (OrderStatistics statistic : statistics) {
            b2 = saleDataService.pushSaleData(statistic.getCompanyId(), statistic.getSaleNum(), statistic.getSale());
            if (b2){
                omsLogger.debug("公司{}昨天{}的sale_data(销售总累计表)更新成功",statistic.getCompanyId(), LocalDate.now().minusDays(1));
            }else {
                omsLogger.error("公司{}昨天{}的sale_data(销售总累计表)更新失败",statistic.getCompanyId(), LocalDate.now().minusDays(1));
                throw new RuntimeException("sale_data(销售总累计表)更新失败");
                //return new ProcessResult(false, taskContext + ": " + true);
            }
        }

        if (b1 && b2){
            omsLogger.info("今天{}更新昨天{}的订单数据成功",LocalDate.now(),LocalDate.now().minusDays(1));
            return new ProcessResult(true, taskContext + ": " + true);
        }else {
            omsLogger.error("今天{}更新昨天{}的订单数据失败",LocalDate.now(),LocalDate.now().minusDays(1));
            return new ProcessResult(false, taskContext + ": " + true);
        }
    }
}
