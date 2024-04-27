package org.dromara.media.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.media.domain.vo.MediaFilesVo;
import org.dromara.media.service.MediaAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 媒资审核
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/audit")
public class MediaAuditController {

    @Autowired
    MediaAuditService mediaAuditService;

    /**
     * 查询media列表
     */
    @GetMapping("/list")
    public TableDataInfo<MediaFilesVo> list(MediaFilesBo bo, PageQuery pageQuery) {
        return mediaAuditService.queryPageList(bo, pageQuery);
    }


    /**
     * media审核通过
     */
    @PutMapping("/pass/{id}")
    public R<Void> pass(@PathVariable String id) {
        Boolean b = mediaAuditService.pass(id);
        if (b){
            return R.ok("审核成功");
        }else {
            return R.fail("审核失败");
        }
    }

    /**
     * media审核不通过
     */
    @PutMapping("/reject")
    public R<Void> reject(@RequestBody JsonNode bo) {
        Boolean b = mediaAuditService.reject(bo);
        if (b){
            return R.ok("审核成功");
        }else {
            return R.fail("审核失败");
        }
    }

}
