package com.jyh.video.mapper;



import com.jyh.video.pojo.SearchRecords;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SearchRecordsMapper extends Mapper<SearchRecords> {
	
	public List<String> getHotWords();

	public void insertSearchRecord(String content);
}