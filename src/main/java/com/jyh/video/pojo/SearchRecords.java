package com.jyh.video.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "search_records")
public class SearchRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 搜索的内容
     */
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取搜索的内容
     *
     * @return content - 搜索的内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置搜索的内容
     *
     * @param content 搜索的内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}