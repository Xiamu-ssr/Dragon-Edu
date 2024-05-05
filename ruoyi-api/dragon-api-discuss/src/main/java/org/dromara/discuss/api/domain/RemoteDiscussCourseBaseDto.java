package org.dromara.discuss.api.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 课程管理对象 course_base
 *
 * @author Xiamu
 * @date 2024-04-12
 */
@Data
public class RemoteDiscussCourseBaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 机构ID
     */
    private Long companyId;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程图片
     */
    private String pic;
}




