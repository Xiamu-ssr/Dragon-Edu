package org.dromara.learn.mapper;

import org.apache.ibatis.annotations.Select;
import org.dromara.learn.domain.ClassSchedule;
import org.dromara.learn.domain.vo.ClassScheduleVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 课程表Mapper接口
 *
 * @author LionLi
 * @date 2024-04-27
 */
public interface ClassScheduleMapper extends BaseMapperPlus<ClassSchedule, ClassScheduleVo> {

    @Select("select sum(learning_time) from class_schedule where user_id = #{userId}")
    Long sumLearningTime(Long userId);

}
