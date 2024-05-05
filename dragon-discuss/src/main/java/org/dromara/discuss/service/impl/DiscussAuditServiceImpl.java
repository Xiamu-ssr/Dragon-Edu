package org.dromara.discuss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.discuss.domain.Discuss;
import org.dromara.discuss.domain.bo.DiscussBo;
import org.dromara.discuss.domain.vo.DiscussVo;
import org.dromara.discuss.enums.DiscussStatusEnum;
import org.dromara.discuss.mapper.DiscussMapper;
import org.dromara.discuss.service.DiscussAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussAuditServiceImpl implements DiscussAuditService {

    @Autowired
    private DiscussMapper discussMapper;

    @Override
    public TableDataInfo<DiscussVo> queryPageList(DiscussBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Discuss> lqw = buildQueryWrapper(bo);
        Page<DiscussVo> result = discussMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    private LambdaQueryWrapper<Discuss> buildQueryWrapper(DiscussBo bo) {
        LambdaQueryWrapper<Discuss> lqw = Wrappers.lambdaQuery();
        lqw.eq(Discuss::getStatus, DiscussStatusEnum.APPLYBLOCK.getValue());
        //default, order by create time
        lqw.orderByDesc(Discuss::getCreateTime);
        return lqw;
    }
}
