package com.jyh.video.service;


import com.jyh.video.common.utils.PagedResult;
import com.jyh.video.pojo.Videos;

import java.util.List;

/**
 * @author JYH
 * 2018/10/31 8:21
 */

public interface VideoService {

    /**
     * 保存视频
     * @param videos
     */
    public Integer saveVideo(Videos videos);

    /**
     * @Description: 修改视频的封面
     */
    public void updateVideo(String videoId, String coverPath);


    /**
     * @Description: 分页查询视频列表
     */
    public PagedResult getAllVideos(Videos videos,Integer isSaveRecord, Integer page, Integer pagesize);


    /**
     * @Description: 查询我喜欢的视频列表
     */
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 查询我关注的人的视频列表
     */
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 获取热搜词列表
     */
    public List<String> getHotWords();

    /**
     * @Description: 用户喜欢/点赞视频
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户不喜欢/取消点赞视频
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);



    /**
     * @Description: 留言分页
     */
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}


