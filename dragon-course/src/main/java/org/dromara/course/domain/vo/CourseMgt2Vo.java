package org.dromara.course.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 课程mgt2 vo
 * 课程管理-阶段2 bo
 *
 * @author mumu
 * @date 2024/03/23
 */
@Data
public class CourseMgt2Vo implements Serializable {

    /**
     * UUID
     */
    private String  id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父级Id，大章节写"0"
     */
    private String parentid;

    /**
     * 层级，分为1、2级
     */
    private Long grade;

    private Long orderby;

    /**
     * 课程标识
     */
    private Long courseId;

    /**
     * 媒资id
     */
    private String mediaId;

    /**
     * 媒资名称
     */
    private String mediaName;

    /**
     * 是否支持试学或预览（试看）
     */
    @JsonProperty("isPreview")
    private boolean isPreview;

    private List<CourseMgt2Vo> chapter;


}
