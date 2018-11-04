package com.jyh.video.controller;


import com.github.pagehelper.util.StringUtil;
import com.jyh.video.common.utils.JSONResult;
import com.jyh.video.pojo.Bgm;
import com.jyh.video.service.BgmService;
import com.jyh.video.service.VideoService;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;


@RestController
@Api(value="视频相关业务的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {
	
	@Autowired
	private BgmService bgmService;
	

	
	@ApiOperation(value="上传视频", notes="上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false, 
				dataType="String", paramType="form")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public JSONResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String desc,
							 @ApiParam(value="短视频", required=true) MultipartFile file) throws Exception {
		
		//文件命名空间
		String fileSpace = "D:/video_dev";
		//保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/video";
		if(file != null){
			String fileName = file.getOriginalFilename();
			if(StringUtil.isNotEmpty(fileName)){
				//文件上传的最终保存路径
				String finalVideoPath = fileSpace + uploadPathDB + "/" +fileName;
				//设置数据库保存的路径
				uploadPathDB += ("/" + fileName);

				File outFile = new File(finalVideoPath);
				if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
					//创建父文件夹
					outFile.getParentFile().mkdirs();
				}

				file.transferTo(outFile);
			}else {
				return JSONResult.errorMsg("上传出错...");
			}
		}else {
			return JSONResult.errorMsg("上传视频为空...");
		}
		return JSONResult.ok();
	}
	

	
}
