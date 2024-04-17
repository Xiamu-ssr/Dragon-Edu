package org.dromara.media.domain.bo;

import org.dromara.media.domain.MediaFiles;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.io.Serializable;
/**
 * media业务对象 media_files
 *
 * @author Xiamu
 * @date 2024-03-30
 */
@Data
@AutoMapper(target = MediaFiles.class, reverseConvertGenerate = false)
public class MediaFilesBo implements Serializable {

    /**
     * 文件id,md5值
     */
    @NotBlank(message = "文件id,md5值不能为空", groups = { EditGroup.class })
    private String id;

    /**
     * 机构ID
     */
    private Long companyId;

    /**
     * 原名,只真正的原来的，文件名
     */
    @NotBlank(message = "原名,只真正的原来的，文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String originalName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 在minio中的完整路径
     */
    private String path;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
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
