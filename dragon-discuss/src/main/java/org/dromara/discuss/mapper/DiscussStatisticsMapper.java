package org.dromara.discuss.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.discuss.domain.DiscussStatistics;
import org.dromara.discuss.domain.vo.DiscussStatisticsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 评论统计，机构用Mapper接口
 *
 * @author LionLi
 * @date 2024-05-02
 */
public interface DiscussStatisticsMapper extends BaseMapperPlus<DiscussStatistics, DiscussStatisticsVo> {

    @Select("SELECT SUM(discuss_count) " +
        "FROM discuss_statistics " +
        "WHERE company_id = #{company_id}")
    Long getDiscussNum(@Param("companyId") Long company_id);

}
