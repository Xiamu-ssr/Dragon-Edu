package org.dromara.media.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MediaImageVo implements Serializable {
    String filePath;
    String md5;
}
