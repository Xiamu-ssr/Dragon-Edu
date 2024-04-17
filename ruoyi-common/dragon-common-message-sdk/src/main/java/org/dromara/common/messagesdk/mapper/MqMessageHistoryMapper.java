package org.dromara.common.messagesdk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.common.messagesdk.domain.MqMessageHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface MqMessageHistoryMapper extends BaseMapper<MqMessageHistory> {

}
