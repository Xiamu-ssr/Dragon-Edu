package org.dromara.order.processors;

import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

/**
 * 定时刷新公司的统计分析数据
 *
 * @author mumu
 * @date 2024/05/06
 */
@Component
public class CompanyStatistics implements BasicProcessor {
    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        //:todo

        return null;
    }
}
