package org.dromara.common.redis.utils;

import org.dromara.common.core.constant.CacheNames;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class CourseHotUtils {

    private static final String COURSE_HOT_ID_LIST = "course_hot:id_list";
    private static final Duration COURSE_HOT_ID_LIST_EXPIRE_TIME = Duration.ofSeconds(60 * 10);
    //private static final String COURSE_HOT_HOMEPAGE_SET = "course_hot:homepage:id_set";

    /**
     * 根据id判断此课程是否为热门课程
     *
     * @param id 身份证件
     * @return boolean
     */
    public static boolean isHotExist(Long id){
        return RedisUtils.getCacheMapValue(CacheNames.COURSE_HOT, id.toString()) != null;
    }

    /**
     * 重新加载热id列表
     *
     * @return boolean
     */
    public static void reLoadHotIdList(){
        Set<String> keySet = RedisUtils.getCacheMapKeySet(CacheNames.COURSE_HOT);
        List<Long> ids = keySet.stream().map(Long::parseLong).sorted(Long::compareTo).collect(Collectors.toCollection(ArrayList::new));

        RedisUtils.deleteObject(COURSE_HOT_ID_LIST);
        RedisUtils.setCacheList(COURSE_HOT_ID_LIST, ids);
        RedisUtils.expire(COURSE_HOT_ID_LIST, COURSE_HOT_ID_LIST_EXPIRE_TIME);
    }

    /**
     * 获取热门id列表-升序
     * <BR/>
     * id的list也会被缓存。使用过期时间来更新。同时如果course_hot出现增删也会立即更新list。
     *
     *
     * @param isAsc 是否升序
     * @return {@link List}<{@link Long}>
     */
    public static List<Long> getHotIdList (){
        long time = RedisUtils.getTimeToLive(COURSE_HOT_ID_LIST);
        if (time > 0){
            List<Long> list = RedisUtils.getCacheList(COURSE_HOT_ID_LIST);
            //可能需要重新设置
            if (list.isEmpty()){
                time = -2;
            }else {
                return list;
            }
        }
        if (time <= 0){
            reLoadHotIdList();
        }
        return RedisUtils.getCacheList(COURSE_HOT_ID_LIST);
    }

    /**
     * 获取热门id页面列表
     *
     * @param pageNum  页面页码
     * @param pageSize 页面大小
     * @return {@link List}<{@link Long}>
     */
    public static List<Long> getHotIdPageList (Integer pageNum, Integer pageSize, boolean isAsc){
        List<Long> ids = getHotIdList();
        if (isAsc){
            //[start, end)
            int start = (pageNum-1) * pageSize;
            int end = Math.min(pageNum * pageSize, ids.size());
            return ids.subList(start, end);
        }else {
            int listSize = ids.size();
            //[start, end)
            int start = (pageNum - 1) * pageSize;
            int end = Math.min(pageNum * pageSize, ids.size());
            //倒序后end < start，并且是(end, start]
            start = listSize - start;
            end = listSize - end;
            List<Long> list = ids.subList(end + 1, start + 1);
            Collections.reverse(list);
            return list;

        }
    }

}
