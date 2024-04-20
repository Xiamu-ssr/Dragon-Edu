package org.dromara.media.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jdk.jfr.ContentType;
import org.apache.commons.codec.digest.DigestUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.entity.UploadResult;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.media.config.MinioProperties;
import org.dromara.media.domain.bo.MediaVideoMergeBo;
import org.dromara.media.domain.vo.MediaImageVo;
import org.dromara.media.enums.MediaStatusEnum;
import org.springframework.stereotype.Service;
import org.dromara.media.domain.bo.MediaFilesBo;
import org.dromara.media.domain.vo.MediaFilesVo;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.mapper.MediaFilesMapper;
import org.dromara.media.service.MediaFilesService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Collection;
import java.util.UUID;

/**
 * mediaService业务层处理
 *
 * @author Xiamu
 * @date 2024-03-24
 */
@RequiredArgsConstructor
@Service
public class MediaFilesServiceImpl implements MediaFilesService {

    private final MediaFilesMapper mediaFilesMapper;

    private final MinioProperties minioProperties;

    /**
     * 查询media
     */
    @Override
    public MediaFilesVo queryById(String id, Long companyId){
        return mediaFilesMapper.selectVoOne(new LambdaQueryWrapper<MediaFiles>()
            .eq(MediaFiles::getId, id)
            .eq(MediaFiles::getCompanyId, companyId)
        );
    }

    /**
     * 查询media列表
     */
    @Override
    public TableDataInfo<MediaFilesVo> queryPageList(MediaFilesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MediaFiles> lqw = buildQueryWrapper(bo);
        Page<MediaFilesVo> result = mediaFilesMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询media列表
     */
    @Override
    public List<MediaFilesVo> queryList(MediaFilesBo bo) {
        LambdaQueryWrapper<MediaFiles> lqw = buildQueryWrapper(bo);
        return mediaFilesMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MediaFiles> buildQueryWrapper(MediaFilesBo bo) {
        LambdaQueryWrapper<MediaFiles> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCompanyId() != null, MediaFiles::getCompanyId, bo.getCompanyId());
        lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), MediaFiles::getOriginalName, bo.getOriginalName());
        lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), MediaFiles::getFileSuffix, bo.getFileSuffix());
        lqw.eq(bo.getSize() != null, MediaFiles::getSize, bo.getSize());
        lqw.eq(StringUtils.isNotBlank(bo.getPath()), MediaFiles::getPath, bo.getPath());
        lqw.eq(bo.getAuditStatus()!=null, MediaFiles::getAuditStatus, bo.getAuditStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditMind()), MediaFiles::getAuditMind, bo.getAuditMind());
        return lqw;
    }

    /**
     * 新增media
     */
    @Override
    public Boolean insertByBo(MediaFilesBo bo) {
        MediaFiles add = MapstructUtils.convert(bo, MediaFiles.class);
        validEntityBeforeSave(add);
        boolean flag = mediaFilesMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改media
     */
    @Override
    public Boolean updateByBo(MediaFilesBo bo) {
        //媒资修改只能修改两个字段
        LambdaUpdateWrapper<MediaFiles> updateWrapper = new LambdaUpdateWrapper<MediaFiles>()
            .eq(MediaFiles::getId, bo.getId())
            .set(MediaFiles::getOriginalName, bo.getOriginalName())
            .set(MediaFiles::getRemark, bo.getRemark());
        return mediaFilesMapper.update(updateWrapper) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MediaFiles entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除media
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return mediaFilesMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean publish(List<String> ids) {
        LambdaUpdateWrapper<MediaFiles> updateWrapper = new LambdaUpdateWrapper<MediaFiles>()
            .in(MediaFiles::getId, ids)
            .set(MediaFiles::getAuditStatus, MediaStatusEnum.UNDER_REVIEW.getValue())
            ;
        return mediaFilesMapper.update(updateWrapper) > 0;
    }

    @Override
    public MediaImageVo uploadImage(MultipartFile file) {
        //上传至Minio
        String md5 = calculateMD5(file);
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        String path = getPathByDate(suffix, md5);
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.upload(file.getInputStream(), path, file.getContentType(), minioProperties.getFilesBucket());
            //uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        // 更新至mysql
        MediaFiles mediaFiles = new MediaFiles();
        mediaFiles.setId(md5);
        mediaFiles.setCompanyId(LoginHelper.getDeptId());
        mediaFiles.setOriginalName(originalfileName);
        mediaFiles.setFileSuffix(suffix);
        mediaFiles.setSize(file.getSize());
        mediaFiles.setPath(path);
        mediaFiles.setAuditStatus(MediaStatusEnum.UNREVIEWED.getValue());
        mediaFilesMapper.insertOrUpdate(mediaFiles);
        //return
        MediaImageVo mediaImageVo = new MediaImageVo();
        mediaImageVo.setFilePath(path);
        mediaImageVo.setMd5(md5);
        return mediaImageVo;
    }

    @Override
    public Boolean deleteImage(List<MediaImageVo> files) {
        //删除minio
        OssClient storage = OssFactory.instance();
        files.forEach(mediaImageVo -> {
            storage.delete(mediaImageVo.getFilePath(), minioProperties.getFilesBucket());
        });
        //删除mysql
        List<String> ids = files.stream().map(MediaImageVo::getMd5).toList();
        return mediaFilesMapper.deleteBatchIds(ids) == files.size();
    }

    /**
     * 分块文件上传
     * <br/>
     * 分块文件不存放mysql信息，同时文件名不含后缀，只有md5
     * @param file 文件
     * @param md5  md5
     * @return {@link Boolean}
     */
    @Override
    public Boolean handleChunkUpload(MultipartFile file, String md5) {
        //只上传至minio
        OssClient storage = OssFactory.instance();
        String path = getPathByMD5(md5, "");
        try {
            storage.upload(file.getInputStream(), path, file.getContentType(), minioProperties.getVideoBucket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean isChunkExist(String md5) {
        OssClient storage = OssFactory.instance();
        String path = getPathByMD5(md5, "");
        return storage.doesFileExist(minioProperties.getVideoBucket(), path);
    }

    @Override
    public Boolean mergeChunks(MediaVideoMergeBo bo) {
        OssClient storage = OssFactory.instance();
        String originalfileName = bo.getVideoName();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        //创建临时文件，用来存放合并文件
        String tmpDir = System.getProperty("java.io.tmpdir");
        String tmpFileName = UUID.randomUUID().toString() + ".tmp";
        File tmpFile = new File(tmpDir, tmpFileName);

        try(
            FileOutputStream fOut = new FileOutputStream(tmpFile);
        ) {
            //将分块文件以流的形式copy到临时文件
            List<String> chunksMd5 = bo.getChunksMd5();
            chunksMd5.forEach(chunkMd5 -> {
                String chunkPath = getPathByMD5(chunkMd5, "");
                InputStream chunkIn = storage.getObjectContent(minioProperties.getVideoBucket(), chunkPath);
                IoUtil.copy(chunkIn, fOut);
            });
            //合并文件上传到minio
            String videoMd5 = bo.getVideoMd5();
            String path = getPathByMD5(videoMd5, suffix);
            storage.upload(tmpFile, path, minioProperties.getVideoBucket());
            //删除分块文件
            chunksMd5.forEach(chunkMd5->{
                String chunkPath = getPathByMD5(chunkMd5, "");
                storage.delete(chunkPath, minioProperties.getVideoBucket());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (tmpFile.exists()){
                tmpFile.delete();
            }
        }
        //上传信息到mysql
        MediaFiles mediaFiles = new MediaFiles();
        mediaFiles.setId(bo.getVideoMd5());
        mediaFiles.setCompanyId(bo.getCompanyId());
        mediaFiles.setOriginalName(originalfileName);
        mediaFiles.setFileSuffix(suffix);
        mediaFiles.setSize(bo.getVideoSize());
        mediaFiles.setPath(getPathByMD5(bo.getVideoMd5(), suffix));
        mediaFiles.setRemark(bo.getRemark());
        mediaFiles.setAuditStatus(MediaStatusEnum.UNREVIEWED.getValue());
        return mediaFilesMapper.insert(mediaFiles) > 0;
    }

    /**
     * 计算md5
     *
     * @param file 文件
     * @return {@link String}
     * @throws Exception 例外
     */
    public static String calculateMD5(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            return DigestUtils.md5Hex(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按日期生成文件路径-uuid
     * <br/>
     * 比如 2024/03/24/uuid + suffix
     *
     * @param suffix 后缀
     * @return {@link String}
     */
    public String getPathByDate(String suffix) {
        // 生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        // 文件路径
        String path = DateUtils.datePath() + "/" + uuid;
        return path + suffix;
    }

    /**
     * 按日期生成文件路径-指定filename
     * <br/>
     * 比如 2024/03/24/filename + suffix
     *
     * @param suffix   后缀
     * @param fileName 文件名
     * @return {@link String}
     */
    public String getPathByDate(String suffix, String fileName) {
        // 文件路径
        String path = DateUtils.datePath() + "/" + fileName;
        return path + suffix;
    }

    /**
     * 通过md5生成文件路径
     * <br/>
     * 比如
     * md5 = 6c4acb01320a21ccdbec089f6a9b7ca3
     * <br/>
     * path = 6/c/md5 + suffix
     * @param prefix 前缀
     * @param suffix 后缀
     * @return {@link String}
     */
    public String getPathByMD5(String md5, String suffix) {
        // 文件路径
        String path = md5.charAt(0) + "/" + md5.charAt(1) + "/" + md5;
        return path + suffix;
    }
}
