package org.dromara.common.messagesdk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dromara.common.messagesdk.domain.MqMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Mapper
public interface MqMessageMapper extends BaseMapper<MqMessage> {

    /**
     * 按碎片索引选择列表
     * <br/>
     * max execute count = 3
     *
     * @param shardTotal  总计
     * @param shardindex  碎片索引
     * @param messageType 消息类型
     * @param count       计数
     * @return {@link List}<{@link MqMessage}>
     */
    @Select("SELECT t.* FROM mq_message t " +
        "WHERE t.id % #{shardTotal} = #{shardindex} " +
        "and t.state='0' " +
        "and t.message_type=#{messageType} " +
        "and t.execute_num < 3 "+
        "limit #{count}")
    List<MqMessage> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardindex") int shardindex, @Param("messageType") String messageType,@Param("count") int count);

}
