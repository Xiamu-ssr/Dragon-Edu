package org.dromara.media.domain.vo;

import org.dromara.media.domain.MediaFiles;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;



/**
 * media视图对象 media_files
 *
 * @author Xiamu
 * @date 2024-03-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MediaFiles.class)
public class MediaFilesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id,md5值
     */
    @ExcelProperty(value = "文件id,md5值")
    private String id;

    /**
     * 机构ID
     */
    @ExcelProperty(value = "机构ID")
    private Long companyId;

    /**
     * 原名,只真正的原来的，文件名
     */
    @ExcelProperty(value = "原名,只真正的原来的，文件名")
    private String originalName;

    /**
     * 文件后缀
     */
    @ExcelProperty(value = "文件后缀")
    private String fileSuffix;

    /**
     * 文件da
     */
    @ExcelProperty(value = "文件da")
    private Long size;

    /**
     * 在minio中的完整路径
     */
    @ExcelProperty(value = "在minio中的完整路径")
    private String path;

    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 审核状态
     */
    @ExcelProperty(value = "审核状态")
    private Integer auditStatus;

    /**
     * 审核意见
     */
    @ExcelProperty(value = "审核意见")
    private String auditMind;


}
