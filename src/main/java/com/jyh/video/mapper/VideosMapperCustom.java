package com.jyh.video.mapper;


import com.jyh.video.pojo.Videos;
import com.jyh.video.pojo.vo.VideosVO;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideosMapperCustom extends Mapper<Videos> {

    public List<VideosVO> queryAllVideos(Videos video1);

    /**
     * @Description: 对视频喜欢的数量进行累加
     */
    public void addVideoLikeCount(String videoId);

    /**
     * @Description: 对视频喜欢的数量进行累减
     */
    public void reduceVideoLikeCount(String videoId);
}