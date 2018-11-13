package com.jyh.video.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name = "users_fans")
@Data
public class UsersFans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 粉丝
     */
    @Column(name = "fan_id")
    private String fanId;


}