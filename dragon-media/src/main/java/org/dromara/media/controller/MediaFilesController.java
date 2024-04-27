package org.dromara.media.controller;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.media.domain.vo.MediaImageVo;
import org.dromara.media.domain.bo.MediaVideoMergeBo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.media.domain.vo.MediaFilesVo;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.media.service.MediaFilesService;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 媒资文件服务
 * 前端访问路由地址为:/media/media
 *
 * @author Xiamu
 * @date 2024-03-24
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaFilesController extends BaseController {

    private final MediaFilesService mediaFilesService;

    /**
     * 查询media列表
     */
    @GetMapping("/list")
    public TableDataInfo<MediaFilesVo> list(MediaFilesBo bo, PageQuery pageQuery) {
        bo.setCompanyId(LoginHelper.getDeptId());
        return mediaFilesService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出media列表
     */
    @Log(title = "media", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MediaFilesBo bo, HttpServletResponse response) {
        List<MediaFilesVo> list = mediaFilesService.queryList(bo);
        ExcelUtil.exportExcel(list, "media", MediaFilesVo.class, response);
    }

    /**
     * 获取media详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    public R<MediaFilesVo> getInfo(@NotNull(message = "主键不能为空")
                                   @PathVariable String id) {
        Long companyId = LoginHelper.getDeptId();
        return R.ok(mediaFilesService.queryById(id, companyId));
    }

    /**
     * 新增media
     */
    @Log(title = "media", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MediaFilesBo bo) {
        return toAjax(mediaFilesService.insertByBo(bo));
    }

    /**
     * 修改media
     */
    @Log(title = "media", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MediaFilesBo bo) {
        bo.setCompanyId(LoginHelper.getDeptId());
        return toAjax(mediaFilesService.updateByBo(bo));
    }

    /**
     * 删除media
     *
     * @param ids 主键串
     */
    @Log(title = "media", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(mediaFilesService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 发布media-提交审核
     *
     * @param ids 主键串
     */
    @Log(title = "media", businessType = BusinessType.UPDATE)
    @PutMapping("/publish")
    public R<Void> publish(@RequestBody List<String> ids) {
        Boolean b = mediaFilesService.publish(ids);
        if (b){
            return R.ok("发布成功");
        }else {
            return R.fail("发布失败");
        }
    }


    @Log(title = "普通文件上传", businessType = BusinessType.INSERT)
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<MediaImageVo> uploadImage(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return R.fail("上传文件不能为空");
        }
        return R.ok(mediaFilesService.uploadImage(file));
    }

    /**
     * 普通文件删除
     *
     * @param files 文件夹
     * @return {@link R}<{@link Void}>
     */
    @Log(title = "普通文件删除", businessType = BusinessType.DELETE)
    @PostMapping("/image/delete")
    public R<Void> deleteImage(@RequestBody List<MediaImageVo> files) {
        Boolean b = mediaFilesService.deleteImage(files);
        if (b){
            return R.ok("删除成功");
        }else {
            return R.fail("删除失败");
        }
    }


    @Log(title = "视频分块文件上传")
    @PostMapping(value = "/video/chunk")
    public R<String> handleChunkUpload(
        @RequestParam("file") MultipartFile file,
        @RequestParam("md5") String md5,
        @RequestParam("filename") String filename,
        @RequestParam("chunkIndex") int chunkIndex,
        @RequestParam("totalChunks") int totalChunks) {
        if (ObjectUtil.isNull(file)) {
            return R.fail("上传文件不能为空");
        }
        Boolean b = mediaFilesService.handleChunkUpload(file, md5);
        if (b){
            return R.ok();
        }else {
            return R.fail();
        }
    }

    @Log(title = "分块文件是否已经存在")
    @GetMapping(value = "/video/chunk")
    public R<Boolean> isChunkExist(@RequestParam("md5") String md5) {
        return R.ok(mediaFilesService.isChunkExist(md5));
    }

    @Log(title = "合并视频文件")
    @PostMapping(value = "/video/chunk/merge")
    public R<Boolean> mergeChunks(@RequestBody MediaVideoMergeBo bo) {
        bo.setCompanyId(LoginHelper.getDeptId());
        Boolean b = mediaFilesService.mergeChunks(bo);
        if (b){
            return R.ok();
        }else {
            return R.fail();
        }
    }

}
