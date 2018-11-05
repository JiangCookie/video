package com.jyh.video.mapper;



import com.jyh.video.pojo.Videos;
import tk.mybatis.mapper.common.Mapper;

public interface VideosMapper extends Mapper<Videos> {

    /**
     * 保存用户，返回主键
     */
    int saveReturnPK(Videos videos);


}