package com.easychat.controller;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 31, 2024 3:26:29 PM
* @ClassName:ChatController.java

*/

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.MessageSendDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Message;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.service.MessageService;
import com.easychat.utils.StringTools;

@RestController
@RequestMapping("/chat/")
public class ChatController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Resource
	private MessageService messageService;

	@Resource
	private AppConfig appConfig;

	@RequestMapping("sendMessage")
	@GlobalInterceptor
	public ResponseVO sendMessage(HttpServletRequest request, @NotNull String contactId,
			@NotNull @Max(500) String messageContent, @NotNull Integer messageType, Long fileSize, String fileName,
			Integer fileType) {
		// 判断消息类型
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(messageType);

		/**
		 * 保存消息
		 */
		Message message = new Message();
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);		
		message.setContactId(contactId);
		message.setSendUserId(tokenUserInfoDto.getUserId());
		message.setSendUserNickName(tokenUserInfoDto.getNickName());
		message.setMessageContent(messageContent);
		message.setMessageType(messageType);
		message.setFileSize(fileSize);
		message.setFileName(fileName);
		message.setFileType(fileType);
		messageService.saveMessage(message, tokenUserInfoDto);

		return getSuccessResponseVO(null);

	}

	@RequestMapping("uploadFile")
	@GlobalInterceptor
	public ResponseVO uploadFile(HttpServletRequest request, @NotNull Long messageId, @NotEmpty MultipartFile file,
			@NotEmpty MultipartFile coverFile) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		String sendUserId = tokenUserInfoDto.getUserId();
		messageService.saveMessageFile(sendUserId, messageId, file, coverFile);
		return getSuccessResponseVO(null);
	}

	@SuppressWarnings("null")
	@RequestMapping("downloadFile")
	@GlobalInterceptor
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, @NotEmpty String fileId,
			@NotEmpty boolean showCover) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		OutputStream out = null;
		FileInputStream in = null;
		try {
			File file;
			if (StringTools.isNumber(fileId)) {
				String avatarFolderName = Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_AVATAR_NAME;
				String avatarPath = appConfig.getProjectFloder() + avatarFolderName + fileId + Constants.IMAGE_SUFFIX;
				if (showCover) {
					avatarPath = avatarPath + Constants.COVER_IMAGE_SUFFIX;
				}
				file = new File(avatarPath);
				if (!file.exists()) {
					throw new BusinessException(ResponseCodeEnum.CODE_602);
				} else {
					messageService.downloadFile(tokenUserInfoDto, Long.parseLong(fileId), showCover);
				}
				
				// 设置正确的Content-Type，根据文件类型设置
				String fileName = file.getName();
				// 设置文件名编码，防止中文乱码
				String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
				
				// 根据文件扩展名设置不同的Content-Type
				if (fileName.toLowerCase().endsWith(".xlsx")) {
					response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				} else if (fileName.toLowerCase().endsWith(".xls")) {
					response.setContentType("application/vnd.ms-excel");
				} else if (fileName.toLowerCase().endsWith(".pdf")) {
					response.setContentType("application/pdf");
				} else if (fileName.toLowerCase().endsWith(".doc")) {
					response.setContentType("application/msword");
				} else if (fileName.toLowerCase().endsWith(".docx")) {
					response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				} else if (fileName.toLowerCase().endsWith(".txt")) {
					response.setContentType("text/plain");
				} else {
					response.setContentType("application/octet-stream");
				}
				
				// 设置Content-Disposition头，指定文件名
				response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
				response.setContentLengthLong(file.length());
				
				// 初始化输出流
				out = response.getOutputStream();
				in = new FileInputStream(file);
				
				byte[] byteData = new byte[1024];
				int len;
				while ((len = in.read(byteData)) != -1) {
					out.write(byteData, 0, len);
				}
				out.flush();
			}

		} catch (Exception e) {
			logger.error("下载文件失败", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					logger.error("IO异常", e);
				}
			}
			if (in != null) {
				try {
					in.close(); // 确保输入流也被关闭
				} catch (Exception e) {
					logger.error("IO异常", e);
				}
			}
		}
	}
	


	
	
}
