package com.easychat.redis;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.easychat.dto.SysSettingDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.mappers.InfoMapper;
import com.easychat.utils.StringTools;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 20241:23:28 PM
* @ClassName:RedisComponent.java

*/
@Component("redisComponent")
public class RedisComponent {

	@Autowired
	private  RedisUtils redisUtils;

	@Resource
	private RedisTemplate redisTemplate;

	@Resource
	private InfoMapper infomapper;

	private static final String USER_LOGIN_KEY_PREFIX = "user:login:";

	// 获取用户心跳防止客户端掉线
	public String getUserHeartBeat(String userId) {

		return (String) redisUtils.get(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
	}

	public void saveHeartBeat(String userId) {
		redisUtils.setex(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId, System.currentTimeMillis(),
				Constants.REDIS_KEY_EXPIRES_HEART_BEAT);
	}
	public void removeHeartBeats(String userId) {
		redisUtils.delete(Constants.REDIS_KEY_WS_USER_HEART_BEAT + userId);
	}

	public void saveTokenUserInfoDto(TokenUserInfoDto tokenUserInfoDto) {
		redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN + tokenUserInfoDto.getToken(), tokenUserInfoDto,
				Constants.REDIS_KEY_EXPIRE_DAY * 2);
		redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN_USERID + tokenUserInfoDto.getUserId(),
				tokenUserInfoDto.getToken(), Constants.REDIS_KEY_EXPIRE_DAY * 2);
		redisUtils.setex(Constants.REDIS_KEY_WS_USER_HEART_BEAT + tokenUserInfoDto.getUserId(),
				tokenUserInfoDto.getToken(), Constants.REDIS_KEY_EXPIRE_DAY * 2);
	}

	public TokenUserInfoDto getTokenUserInfoDto(String token) {
		TokenUserInfoDto tokenUserInfoDto = (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);
		return tokenUserInfoDto;

	}

	public SysSettingDto getSysSetting() {

		SysSettingDto sysSettingDto = (SysSettingDto) redisUtils.get(Constants.REDIS_KEY_SYS_SETTING);
		sysSettingDto = sysSettingDto == null ? new SysSettingDto() : sysSettingDto;
		return sysSettingDto;
	}

	public void saveSysSetting(SysSettingDto sysSettingDto) {
		redisUtils.set(Constants.REDIS_KEY_SYS_SETTING, sysSettingDto);
	}

	// 清空联系人
	public void cleanUserContact(String userId) {
		redisUtils.delete(Constants.REDIS_KEY_USER_CONTACT + userId);
	}
	//添加单个好友进好友列表缓存
	public void addUserContact(String userId, String contactId) {
		List<String> contactIdList =getUserContactList(userId);
		if (contactIdList.contains(contactId)) {
			return;
		}
		redisUtils.lpush(Constants.REDIS_KEY_USER_CONTACT + userId, contactId,Constants.REDIS_KEY_EXPIRE_DAY);		
}
	
	
	// 批量添加联系人
	public void addUserContactBatch(String userId, List<String> contactIdList) {
			redisUtils.lpushAll(Constants.REDIS_KEY_USER_CONTACT + userId,contactIdList, 
					Constants.REDIS_KEY_TOKEN_EXPIRES);
	}
	
	

	// 获取联系人id列表
	public List<String> getUserContactList(String userId) {
		try {
			return (List<String>) redisUtils.getQueueList(Constants.REDIS_KEY_USER_CONTACT + userId);
		} catch (Exception e) {
		 e.printStackTrace();
		 return null;
		}
		
	}
	public void cleanUserTokenByUserId(String userId) {
		String token =(String) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN+userId);
		if (StringTools.isEmpty(token)) {
			return;
		}
		redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN_USERID+token);
	}


}
