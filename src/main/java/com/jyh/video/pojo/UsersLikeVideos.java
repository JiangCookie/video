package com.jyh.video.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name = "users_like_videos")
@Data
public class UsersLikeVideos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 视频
     */
    @Column(name = "video_id")
    private String videoId;


}