package org.dromara.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.messagesdk.enums.MessageTypeEnum;
import org.dromara.common.messagesdk.service.MqMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.course.domain.CourseBase;
import org.dromara.course.domain.bo.CourseBaseBo;
import org.dromara.course.domain.vo.CourseBaseVo;
import org.dromara.course.enums.CourseStatusEnum;
import org.dromara.course.mapper.CourseBaseMapper;
import org.dromara.course.service.CourseAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseAuditServiceImpl implements CourseAuditService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private MqMessageService mqMessageService;

    @Override
    public TableDataInfo<CourseBaseVo> queryPageList(CourseBaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CourseBase> lqw = buildQueryWrapper(bo);
        Page<CourseBaseVo> result = courseBaseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<CourseBase> buildQueryWrapper(CourseBaseBo bo) {
        LambdaQueryWrapper<CourseBase> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, CourseBase::getCompanyId, bo.getCompanyId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CourseBase::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getMt()), CourseBase::getMt, bo.getMt());
        lqw.eq(StringUtils.isNotBlank(bo.getSt()), CourseBase::getSt, bo.getSt());
        lqw.eq(StringUtils.isNotBlank(bo.getPic()), CourseBase::getPic, bo.getPic());
        lqw.eq(CourseBase::getStatus, CourseStatusEnum.UNDER_REVIEW.getValue());
        lqw.eq(StringUtils.isNotBlank(bo.getMind()), CourseBase::getMind, bo.getMind());
        return lqw;
    }

    @Override
    public Boolean pass(Long id) {
        boolean b = courseBaseMapper.update(new LambdaUpdateWrapper<CourseBase>()
            .eq(CourseBase::getId, id)
            .set(CourseBase::getStatus, CourseStatusEnum.REVIEW_PASSED.getValue())
            .set(CourseBase::getMind, "")
        ) > 0;
        if (!b){
            return false;
        }
        //add message into "mq_message" to let power-job finish the follow-up
        return mqMessageService.addMessage(MessageTypeEnum.COURSE_PUBLISH.getValue(), id.toString(), null, null) != null;
    }

    @Override
    public Boolean reject(JsonNode bo) {
        return courseBaseMapper.update(new LambdaUpdateWrapper<CourseBase>()
            .eq(CourseBase::getId, bo.get("id").asLong())
            .set(CourseBase::getStatus, CourseStatusEnum.REVIEW_FAILED.getValue())
            .set(CourseBase::getMind, bo.get("mind").asText())
        ) > 0;
    }
}
