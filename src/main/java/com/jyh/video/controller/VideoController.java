package com.jyh.video.controller;


import com.github.pagehelper.util.StringUtil;
import com.jyh.video.common.enums.VideoStatusEnum;
import com.jyh.video.common.utils.FetchVideoCover;
import com.jyh.video.common.utils.JSONResult;
import com.jyh.video.common.utils.MergeVideoMp3;
import com.jyh.video.common.utils.PagedResult;
import com.jyh.video.pojo.Bgm;
import com.jyh.video.pojo.Users;
import com.jyh.video.pojo.Videos;
import com.jyh.video.pojo.vo.UsersVO;
import com.jyh.video.service.BgmService;
import com.jyh.video.service.UserService;
import com.jyh.video.service.VideoService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


@RestController
@Api(value="视频相关业务的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {
	
	@Autowired
	private BgmService bgmService;

	@Autowired
	private UserService userService;
	
	@Autowired
    private VideoService videoService;
	
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
//		String fileSpace = "D:/video_dev";
		//保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";


        String finalVideoPath = null;
		if(file != null){
			String fileName = file.getOriginalFilename();

            //获取视频名前缀
            String[]  arrayFilenameItem =  fileName.split("\\.");
            String fileNamePrefix = "";
            for (int i = 0 ; i < arrayFilenameItem.length-1 ; i ++) {
                fileNamePrefix += arrayFilenameItem[i];
            }

			if(StringUtil.isNotEmpty(fileName)){
				//文件上传的最终保存路径
				finalVideoPath = FILE_SPACE  + uploadPathDB + "/" +fileName;
				//设置数据库保存的路径
				uploadPathDB += ("/" + fileName);
                coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";

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

        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if(StringUtil.isNotEmpty(bgmId)){

            //获取mp3的相对路径
            Bgm bgm = bgmService.queryBgmById(bgmId);

            //获取mp3的绝对路径
            String mp3InputPath = FILE_SPACE +  bgm.getPath();

            //合并音频
            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);

            //视频原有路径
            String videoInputPath = finalVideoPath;

            //合并后视频的新名字
            String newname = UUID.randomUUID().toString() + ".mp4";
            //合并后视频的相对路径
            String newFinalVideoPath = "/" + userId + "/video" + "/" + newname;
            String videoOutputPath = FILE_SPACE + newFinalVideoPath;

            tool.convertor(videoInputPath,mp3InputPath,videoSeconds,videoOutputPath);
        }

        // 对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        videoInfo.getCover(finalVideoPath, FILE_SPACE + coverPathDB);

        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float)videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());

        Integer id =videoService.saveVideo(video);

		return JSONResult.ok(id);
	}


    @ApiOperation(value="上传封面", notes="上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })
    @PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
	public JSONResult upLoadCover(String userId,String videoId,@ApiParam(value = "视频封面", required = true) MultipartFile file) throws IOException {
	    if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(videoId)){
	        return JSONResult.errorMsg("视频主键id和用户id不能为空...");
        }

        //保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String finalVideoPath = null;
        if(file != null){
            String fileName = file.getOriginalFilename();
            if(StringUtil.isNotEmpty(fileName)){
                finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                // 设置数据库保存的路径
                uploadPathDB += ("/" + fileName);

                File outFile = new File(finalVideoPath);
                if(outFile.getParentFile() != null || outFile.isDirectory() ){

                }

                file.transferTo(outFile);
            }else {
                return JSONResult.errorMsg("上传出错...");
            }
        }else {
            return JSONResult.errorMsg("上传视频为空...");
        }

        videoService.updateVideo(videoId, uploadPathDB);

        return JSONResult.ok();
    }


    /**
     * 分页和搜索查询视频列表
     * isSaveRecord   1 - 需要保存
     *                0 - 不需要保存 ，或者为空的时候
     * @param videos
     * @param isSaveRecord
     * @param page
     * @return
     */
    @PostMapping("/showAllVideo")
    public JSONResult showAllVideo(@RequestBody Videos videos,Integer isSaveRecord, Integer page){
	    if(page == null){
	        page = 1;
        }

        PagedResult pagedResult = videoService.getAllVideos(videos,isSaveRecord,page,PAGE_SIZE);

	    return JSONResult.ok(pagedResult);
    }



    @PostMapping("/hot")
    public JSONResult hot(){
        return JSONResult.ok(videoService.getHotWords());
    }

    @PostMapping(value="/userLike")
    public JSONResult userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }

    @PostMapping(value="/userUnLike")
    public JSONResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return JSONResult.ok();
    }



}





























