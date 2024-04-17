package org.dromara.media.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MediaVideoMergeBo implements Serializable {
    List<String> chunksMd5;
    String videoMd5;
    String videoName;
    Long videoSize;
    Long companyId;
    String remark;
}
