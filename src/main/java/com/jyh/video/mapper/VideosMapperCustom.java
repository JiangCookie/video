package com.jyh.video.mapper;


import com.jyh.video.pojo.Videos;
import com.jyh.video.pojo.vo.VideosVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideosMapperCustom extends Mapper<Videos> {

    public List<VideosVO> queryAllVideos();


}