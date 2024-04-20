package org.dromara.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.media.domain.vo.MediaFilesVo;
import org.dromara.media.enums.MediaStatusEnum;
import org.dromara.media.mapper.MediaFilesMapper;
import org.dromara.media.service.MediaAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaAuditServiceImpl implements MediaAuditService {

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Override
    public TableDataInfo<MediaFilesVo> queryPageList(MediaFilesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MediaFiles> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), MediaFiles::getOriginalName, bo.getOriginalName());
        lqw.eq(MediaFiles::getFileSuffix, ".mp4");
        lqw.eq(MediaFiles::getAuditStatus, MediaStatusEnum.UNDER_REVIEW.getValue());

        Page<MediaFilesVo> result = mediaFilesMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    @Override
    public Boolean pass(String id) {
        LambdaUpdateWrapper<MediaFiles> updateWrapper = new LambdaUpdateWrapper<MediaFiles>()
            .eq(MediaFiles::getId, id)
            .set(MediaFiles::getAuditStatus, MediaStatusEnum.REVIEW_PASSED.getValue())
            .set(MediaFiles::getAuditMind, "")
            ;
        return mediaFilesMapper.update(updateWrapper) > 0;
    }

    @Override
    public Boolean reject(JsonNode bo) {
        LambdaUpdateWrapper<MediaFiles> updateWrapper = new LambdaUpdateWrapper<MediaFiles>()
            .eq(MediaFiles::getId, bo.get("id").asText())
            .set(MediaFiles::getAuditStatus, MediaStatusEnum.REVIEW_FAILED.getValue())
            .set(MediaFiles::getAuditMind, bo.get("auditMind").asText());
        return mediaFilesMapper.update(updateWrapper) > 0;
    }
}
