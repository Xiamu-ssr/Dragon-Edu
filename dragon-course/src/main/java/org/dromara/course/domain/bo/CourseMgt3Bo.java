package org.dromara.course.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 课程管理-阶段2 bo
 *
 * @author mumu
 * @date 2024/03/23
 */
@Data
public class CourseMgt3Bo implements Serializable {
    Long courseId;
    String teacher;
}
