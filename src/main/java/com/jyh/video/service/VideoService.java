package com.jyh.video.service;


import com.jyh.video.pojo.Videos;

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
}
