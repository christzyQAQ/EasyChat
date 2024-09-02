package com.easychat.entity.constants;

import com.easychat.enums.UserContactTypeEnum;
import com.easychat.enums.UserStatusEnum;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:2024年7月16日下午2:51:39
* @ClassName:Constants.java

*/
public class Constants {
 
	 public static final  String REDIS_KEY_CHECK_CODE="easychat:checkcode:";
	 public static final  String REDIS_KEY_WS_USER_HEART_BEAT="easychat:ws:user:heartbeat:";	
	 public static final  String REDIS_KEY_WS_TOKEN="easychat:ws:token:";
	 public static final  String REDIS_KEY_WS_TOKEN_USERID="easychat:ws:token:user:";
	 public static final  String REDIS_KEY_USER_CONTACT="easychat:ws:user:contact:";
	 
	 
	 public static final  Integer REDIS_KEY_EXPIRES_HEART_BEAT = 6;
	 public static final  Integer REDIS_TIME_1MIN=60;
	 public static final  Integer REDIS_KEY_EXPIRE_DAY=REDIS_TIME_1MIN*60*24;
	 //token失效时间
	 public static final  Integer REDIS_KEY_TOKEN_EXPIRES=REDIS_KEY_EXPIRE_DAY*2;
	 public static final  Integer LENGTH_11=11;
	 public static final  Integer LENGTH_20=20;
	 public static final  String IMAGE_SUFFIX=".png";
	 public static final  String APP_EXE_SUFFIX=".exe";
	 public static final  String COVER_IMAGE_SUFFIX="_cover.png";
	 public static final  String APPLY_INFO_DEFAULT="我是";
	 
	 
	public static final String ROBOT_UID = UserContactTypeEnum.USER.getPrefix()+"Robot001";
	public static final String REDIS_KEY_SYS_SETTING = "easychat:syssetting:";
	public static final String FILE_FOLDER_FILE= "file/";
	public static final String FILE_FOLDER_AVATAR_NAME= "avatar/";
	
	public static final String APP_UPDATE_FOLDER= "app/";
	
//	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{8,18}$";

	public static final String APP_NAME="EasyChatSetup";
	
	public static final Long MILLISECONDS_3DAYS_AGO=3*24*60*60*1000L;
	
	public static final String[] IMAGE_SUFFIX_LIST= {".jpg",".png",".jpeg",".gif",".bmp",".webp"};
	
	public static final String[] VIDEO_SUFFIX_LIST= {".mp4",".avi",".rmvb",".mvk",".mov"};
	//表示文件1M大小的常量
	public static final Long FILE_SIZE_MB=1024*1024L;

	
	
}
