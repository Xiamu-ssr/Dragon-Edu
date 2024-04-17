package org.dromara.media.service;

import org.dromara.media.domain.MediaFiles;
import org.dromara.media.domain.bo.MediaVideoMergeBo;
import org.dromara.media.domain.vo.MediaFilesVo;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.media.domain.vo.MediaImageVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * mediaService接口
 *
 * @author Xiamu
 * @date 2024-03-24
 */
public interface MediaFilesService {

    /**
     * 查询media
     */
    MediaFilesVo queryById(String id);

    /**
     * 查询media列表
     */
    TableDataInfo<MediaFilesVo> queryPageList(MediaFilesBo bo, PageQuery pageQuery);

    /**
     * 查询media列表
     */
    List<MediaFilesVo> queryList(MediaFilesBo bo);

    /**
     * 新增media
     */
    Boolean insertByBo(MediaFilesBo bo);

    /**
     * 修改media
     */
    Boolean updateByBo(MediaFilesBo bo);

    /**
     * 校验并批量删除media信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);


    /**
     * 发布media-提交审核
     */
    Boolean publish(List<String> ids);

    MediaImageVo uploadImage(MultipartFile file);

    Boolean deleteImage(List<MediaImageVo> files);

    /**
     * 上传文件分块
     *
     * @param file 文件
     * @param md5  md5
     * @return {@link Boolean}
     */
    Boolean handleChunkUpload(MultipartFile file, String md5);

    /**
     * 分块文件是否存在
     * <br/>
     * 查询minio而非mysql
     * @param md5 md5
     * @return {@link Boolean}
     */
    Boolean isChunkExist(String md5);

    /**
     * 合并分块文件
     * <br/>
     * 同时insert到mysql
     *
     * @param bo bo
     * @return {@link Boolean}
     */
    Boolean mergeChunks(MediaVideoMergeBo bo);
}
