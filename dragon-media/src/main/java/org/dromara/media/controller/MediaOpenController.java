package org.dromara.media.controller;

import org.dromara.common.core.domain.R;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.mapper.MediaFilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开发端点
 *
 * @author mumu
 * @date 2024/04/24
 */
@RestController
@RequestMapping("/open")
public class MediaOpenController {
    @Autowired
    MediaFilesMapper mediaFilesMapper;


    /**
     * 获取章节视频播放地址
     * <br/>
     * 仅用于试看
     *
     * @param id 身份证件
     */
    //：todo
    @GetMapping("/chapter/video/{id}")
    public R<String> getChapterVideo(@PathVariable String id){
        MediaFiles mediaFiles = mediaFilesMapper.selectById(id);
        if (mediaFiles != null){
            return R.ok("",mediaFiles.getPath());
        }else {
            return R.fail("视频不存在");
        }
    }


}
