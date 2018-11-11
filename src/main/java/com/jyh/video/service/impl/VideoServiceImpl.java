package com.jyh.video.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyh.video.common.utils.PagedResult;
import com.jyh.video.mapper.*;

import com.jyh.video.pojo.SearchRecords;
import com.jyh.video.pojo.UsersLikeVideos;
import com.jyh.video.pojo.Videos;
import com.jyh.video.pojo.vo.VideosVO;
import com.jyh.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author JYH
 * 2018/11/4 8:59
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Integer saveVideo(Videos videos) {
      return videosMapper.saveReturnPK(videos);
    }

    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(Integer.valueOf(videoId));
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public PagedResult getAllVideos(Videos videos,Integer isSaveRecord, Integer page, Integer pagesize) {


        //保存热搜词
        String content = videos.getVideoDesc();
        if(isSaveRecord != null && isSaveRecord == 1){
//            SearchRecords searchRecords = new SearchRecords();
//            searchRecords.setContent(content);
//            searchRecordsMapper.insert(searchRecords);
//            searchRecordsMapper.insertSelective(searchRecords);
            searchRecordsMapper.insertSearchRecord(content);
        }




        PageHelper.startPage(page,pagesize);
        Videos videos1 = new Videos();
        videos1.setVideoDesc(content);
        List<VideosVO> list =  videosMapperCustom.queryAllVideos(videos1);
        PageInfo<VideosVO> pageInfo = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageInfo.getTotal());
        return pagedResult;
    }

    /**
     * 查询所有热搜词
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<String> getHotWords() {

        return searchRecordsMapper.getHotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {

        // 1. 保存用户和视频的喜欢点赞关联关系表
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.insert(usersLikeVideos);

        // 2. 视频喜欢数量累加
        videosMapperCustom.addVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(userId);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {

        // 1. 删除用户和视频的喜欢点赞关联关系表
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(example);

        // 2. 视频喜欢数量累减
        videosMapperCustom.reduceVideoLikeCount(videoId);

        // 3. 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(videoCreaterId);
    }

    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        return null;
    }
}































