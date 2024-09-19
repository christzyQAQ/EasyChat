package com.easychat.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.enums.BeautyAccountStatusEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.enums.UserStatusEnum;
import com.easychat.exception.BusinessException;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.Info;
import com.easychat.entity.po.InfoBeauty;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.ContactApplyService;
import com.easychat.service.ContactService;
import com.easychat.service.InfoService;
import com.easychat.service.SessionUserService;
import com.easychat.utils.StringTools;
import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.InfoBeautyMapper;
import com.easychat.mappers.InfoMapper;
import com.easychat.redis.RedisComponent;
import com.easychat.redis.RedisUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户信息ServiceImpl
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */

@Service
public class InfoServiceImpl implements InfoService {
	
	
	final Logger log = LoggerFactory.getLogger(InfoServiceImpl.class);	

	@Autowired
	private InfoMapper<Info, InfoQuery> infoMapper;
	
	@Resource
	private ContactMapper contactMapper;

	@Autowired
	private InfoBeautyMapper<InfoBeauty,InfoQuery> infoBeautyMapper;
	
	@Autowired 	
	private AppConfig appConfig;
	
	@Autowired
	private RedisComponent redisComponent;
	
	@Resource
	private RedisUtils redisUtils;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Resource
	private ContactService contactService;
	@Resource
	private ContactApplyService contactApplyService;
	
	@Resource
	private SessionUserService sessionUserService;
	
	
	
	
	/**
	 * 根据条件查询列表
	 */
	public List<Info> findListByParam(InfoQuery query) {
		return this.infoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(InfoQuery query) {
		return this.infoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Info> findListByPage(InfoQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<Info> list = this.findListByParam(query);
		PaginationResultVO<Info> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Info bean) {
		return this.infoMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<Info> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<Info> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.infoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserId查询
	 */
	public Info selectByUserId(String userId) {
		return this.infoMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	public Integer updateByUserId(Info bean, String userId) {
		return this.infoMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	public Integer deleteByUserId(String userId) {
		return this.infoMapper.deleteByUserId(userId);
	}

	/**
	 * 根据Email查询
	 */
	public Info selectByEmail(String email) {
		return this.infoMapper.selectByEmail(email);
	}

	/**
	 * 根据Email更新
	 */
	public Integer updateByEmail(Info bean, String email) {
		return this.infoMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	public Integer deleteByEmail(String email) {
		return this.infoMapper.deleteByEmail(email);
			
	}

	
	
	@Override
	@Transactional(rollbackFor=Exception.class) 	
	public  void register(String email, String nickName, String password) {
		Map<String, Object >  result =new HashMap<>(); 
		Info info =this.infoMapper.selectByEmail(email);
		if (null!=info) {
			throw new BusinessException("账号或邮箱已经存在");
		}
		
			String userId =StringTools.getUserId();
			InfoBeauty beautyAccout =this.infoBeautyMapper.selectByEmail(email);
			boolean UseBeautyAccount =null!=beautyAccout&&BeautyAccountStatusEnum.NO_USE.getStatus().equals(beautyAccout.getStatus());
			if(UseBeautyAccount) {
			  userId=UserContactTypeEnum.USER.getPrefix()+beautyAccout.getUserId();
			}
			Date curDate=new Date();
	        long milliseconds = curDate.getTime();	        
	        // 将毫秒数转换为 Date 对象
	        Date dateFromMilliseconds = new Date(milliseconds);
			info =new Info();
			info.setUserId(userId);
			info.setEmail(email);
			info.setNickName(nickName);
			//密码使用MD5加密
			info.setPassword(StringTools.encodeMd5(password));
			info.setCreateTime(curDate);
			info.setStatus(UserStatusEnum.ENABLE.getStatus());
			info.setLastOffTime(dateFromMilliseconds);			
			log.info("准备插入信息: {}", info);
	        
	        int rowsAffected = this.infoMapper.insert(info);
	        
	        log.info("插入结果, 受影响的行数: {}", rowsAffected);
	        
	        if (rowsAffected == 0) {
	            log.error("插入数据失败");
	            throw new RuntimeException("数据插入失败");
	            }
			if(UseBeautyAccount) {
				InfoBeauty updateBeauty =new InfoBeauty();
				updateBeauty.setStatus(BeautyAccountStatusEnum.USEED.getStatus());
				this.infoBeautyMapper.updateByUserId(updateBeauty, beautyAccout.getUserId());
				
			}			
			//创建机器人好友	并添加会话消息
			contactApplyService.addContact4Robot(userId);
			//添加自己为好友
			contactApplyService.addContact(userId, userId, userId, UserContactTypeEnum.USER.getType(), null);
			
			
		}
	
	@Transactional(rollbackFor = Exception.class)
	public TokenUserInfoDto login(String email,String password) 
	{
		Map<String, Object> result =new HashMap<>();
		Info info=this.infoMapper.selectByEmail(email);
//		if (null!=info||info.getPassword() == null ||!info.getPassword().equals(StringTools.encodeMd5(password))) {
//			log.debug("数据库中的密码: {}", info.getPassword());
//			log.debug("加密后的输入密码: {}", StringTools.encodeMd5(password));
//			throw new BusinessException("账号或密码错误");
//		}
		// Debug log
	    if (info == null) {
	        log.debug("没有找到用户信息");
	        throw new BusinessException("账号或密码错误");
	    }

	    if (info.getPassword() == null) {
	        log.debug("数据库中的密码为空");
	        throw new BusinessException("账号或密码错误");
	    }

	    String encodedPassword = StringTools.encodeMd5(password);
	    log.debug("数据库中的密码: {}", info.getPassword());
	    log.debug("加密后的输入密码: {}", encodedPassword);

	    if (!info.getPassword().equals(encodedPassword)) {
	        log.debug("密码不匹配");
	        throw new BusinessException("账号或密码错误");
	    }
		if (UserStatusEnum.DISABLE.equals(info.getStatus())) {
			throw new BusinessException("账号已禁用");
		}		
		//TODO 查询我的群组	
		
		//查询我的联系人
		ContactQuery contactQuery=new ContactQuery();
		contactQuery.setUserId(info.getUserId());
		contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
		List<Contact> contactList =contactMapper.selectList(contactQuery);
		List<String> contactIdList = contactList.stream().map(item -> item.getContactId()).collect(Collectors.toList());
		
		redisComponent.cleanUserContact(info.getUserId());
		if (!contactList.isEmpty()) {
			redisComponent.addUserContactBatch(info.getUserId(), contactIdList);
		}
							
		TokenUserInfoDto tokenUserInfoDto=getTokenUserInfoDto(info);

		
		String lastHeartbeat= redisComponent.getUserHeartBeat(info.getUserId());
		if (lastHeartbeat!=null) {
			throw new BusinessException("此账号已在别处登录，请退出后再登录");			
		}
		
		//保存登录信息到Redis中
		String token =StringTools.encodeMd5(tokenUserInfoDto.getUserId()+StringTools.getRandomNumber(Constants.LENGTH_20));
		tokenUserInfoDto.setToken(token);
		redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
		
		return tokenUserInfoDto;
		
	}
	
	
	public TokenUserInfoDto getTokenUserInfoDto(Info info) {
		TokenUserInfoDto tokenUserInfoDto= new TokenUserInfoDto();
		
		tokenUserInfoDto.setUserId(info.getUserId());
		tokenUserInfoDto.setNickName(info.getNickName());
		//判断注册账号是否是管理员
		String adminEmails=appConfig.getAdminEmails();
		if (!StringTools.isEmpty(adminEmails)&&ArrayUtils.contains(adminEmails.split(","), info.getEmail())) {
			tokenUserInfoDto.setAdmin(true);
		}else {
			tokenUserInfoDto.setAdmin(false);
		}
		return tokenUserInfoDto;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateinfo(Info info, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException {
		if(avatarFile!=null) {
			String baseFolder= appConfig.getProjectFloder()+Constants.FILE_FOLDER_FILE;
			File tatgetFileFolser =new File(baseFolder+Constants.FILE_FOLDER_AVATAR_NAME);
			if(!tatgetFileFolser.exists()) {
				tatgetFileFolser.mkdirs();
			}
			String filePath=baseFolder+"/"+info.getUserId()+Constants.IMAGE_SUFFIX;
			avatarFile.transferTo(new File(filePath));
			avatarCover.transferTo(new File(filePath+Constants.COVER_IMAGE_SUFFIX));
		}
		
		Info dbInfo=this.infoMapper.selectByUserId(info.getUserId());		
		this.infoMapper.updateByUserId(info,info.getUserId());
		String contactNameUpdate =null;
		if(!dbInfo.getNickName().equals(info.getNickName())) {
			contactNameUpdate =info.getNickName();
		}
		//TODO 更新会话中的昵称信息
		if(contactNameUpdate==null) {
			return;
		}
		
		//更新token中的昵称
		sessionUserService.updateRedundanceInfo(contactNameUpdate, info.getUserId());
		String token =(String)redisUtils.get(Constants.REDIS_KEY_WS_TOKEN_USERID+info.getUserId());
		TokenUserInfoDto tokenUserInfoDto =redisComponent.getTokenUserInfoDto(token);
		tokenUserInfoDto.setNickName(contactNameUpdate);
		redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
		
	}

	@Override
	public void modifyPassword(String oldpassword, 
								String newPassword, 
								TokenUserInfoDto tokenUserInfoDto) {
		Info info =this.infoMapper.selectByUserId(tokenUserInfoDto.getUserId());
		
		if(StringTools.encodeMd5(oldpassword).equals(info.getPassword())) {
			throw new BusinessException("原密码与新密码不一致，请核对后再试");
		}
		info.setPassword(newPassword);
		this.infoMapper.updateByUserId(info, info.getUserId());
		
	}

	@Override
	public void updateUserStatus(Integer status, String userId) {
		UserStatusEnum userStatusEnum =UserStatusEnum.getByStatus(status);
		if(userStatusEnum==null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		Info info =new Info();
		info.setStatus(userStatusEnum.getStatus());
		this.infoMapper.updateByUserId(info, userId);
	}

	@Override
	public void forceOffLine(String userId) {
		// TODO 强制下线
		
		
	}
	
	
	
	
	


}