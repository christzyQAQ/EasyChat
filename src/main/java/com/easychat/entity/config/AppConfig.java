package com.easychat.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 202410:44:40 AM
* @ClassName:AppConfig.java

*/
@Component("appConfig")
@ComponentScan(basePackages = "com.easychat")
public class AppConfig {

	@Value("${ws.port:}")
	private Integer wsport;
	
	@Value("${project.folder:}")
	private String projectFloder;
	
	@Value("${admin.emails}")
	private String adminEmails;
	


	/**
	 * @return the wsport
	 */
	public Integer getWsport() {
		return wsport;
	}

	/**
	 * @return the projectFloder
	 */
	public String getProjectFloder() {
		return projectFloder;
	}

	/**
	 * @return the adminEmails
	 */
	public String getAdminEmails() {
		return adminEmails;
	}			


	

	
}
