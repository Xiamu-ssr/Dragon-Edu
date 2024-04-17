package org.dromara.common.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dromara.common.messagesdk.domain.MqMessageHistory;
import org.dromara.common.messagesdk.mapper.MqMessageHistoryMapper;
import org.dromara.common.messagesdk.service.MqMessageHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itcast
 */
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}
