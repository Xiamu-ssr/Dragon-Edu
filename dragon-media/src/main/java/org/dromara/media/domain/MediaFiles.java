package org.dromara.media.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * media对象 media_files
 *
 * @author Xiamu
 * @date 2024-03-30
 */
@Data
@TableName("media_files")
public class MediaFiles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id,md5值
     */
    @TableId(value = "id")
    private String id;

    /**
     * 机构ID
     */
    private Long companyId;

    /**
     * 原名,只真正的原来的，文件名
     */
    private String originalName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件da
     */
    private Long size;

    /**
     * 在minio中的完整路径
     */
    private String path;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 审核意见
     */
    private String auditMind;


}

