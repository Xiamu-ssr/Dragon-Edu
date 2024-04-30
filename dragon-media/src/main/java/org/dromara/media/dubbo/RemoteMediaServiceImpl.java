package org.dromara.media.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.media.api.RemoteMediaService;
import org.dromara.media.domain.MediaFiles;
import org.dromara.media.mapper.MediaFilesMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DubboService
public class RemoteMediaServiceImpl implements RemoteMediaService {

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Override
    public Map<String, String> selectMediaNames(List<String> ids) {
        List<MediaFiles> mediaFiles = mediaFilesMapper.selectList(new LambdaQueryWrapper<MediaFiles>()
            .select(MediaFiles::getId, MediaFiles::getOriginalName)
            .in(MediaFiles::getId, ids)
        );

        return mediaFiles.stream().collect(Collectors.toMap(
            MediaFiles::getId,
            MediaFiles::getOriginalName
        ));
    }
}
