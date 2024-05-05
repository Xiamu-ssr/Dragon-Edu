package org.dromara.discuss.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.common.core.constant.HttpStatus;
import org.dromara.common.core.domain.R;
import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.mapper.DiscussStatisticsMapper;
import org.dromara.es.api.RemoteESIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;
import tech.powerjob.worker.log.OmsLogger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UpdateStarToES implements BasicProcessor {

    @DubboReference
    RemoteESIndexService remoteESIndexService;
    @Autowired
    DiscussStatisticsMapper discussStatisticsMapper;

    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {
        OmsLogger omsLogger = taskContext.getOmsLogger();
        //select all course Star
        List<DiscussStatistics> statistics = discussStatisticsMapper.selectList();
        Map<Long, BigDecimal> courseStars = statistics.stream().collect(
            Collectors.toMap(
                DiscussStatistics::getCourseId,
                DiscussStatistics::getStar,
                (existingValue, newValue) -> newValue)
        );
        //update ES
        R<String> res = remoteESIndexService.updateStarBatch(courseStars);
        if (res.getCode() == HttpStatus.SUCCESS){
            omsLogger.info(res.getMsg());
            return new ProcessResult(true, taskContext + ": " + true);
        }else{
            omsLogger.error(res.getMsg());
            return new ProcessResult(false, taskContext + ": " + false);
        }
    }
}
