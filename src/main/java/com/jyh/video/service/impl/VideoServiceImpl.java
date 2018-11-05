package com.jyh.video.service.impl;

import com.jyh.video.mapper.VideosMapper;
import com.jyh.video.pojo.Videos;
import com.jyh.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JYH
 * 2018/11/4 8:59
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Integer saveVideo(Videos videos) {
//        int m = videosMapper.insertSelective(videos);


      return videosMapper.saveReturnPK(videos);
    }

    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(Integer.valueOf(videoId));
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }
}
