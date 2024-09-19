package com.easychat.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 21, 20246:56:07 PM
* @ClassName:GlobalIntercepter.java

*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface GlobalInterceptor {
	
	//校验登录，默认需要，不需要设置为false
	boolean checkLogin() default true;
	//校验是否为管理员，默认不是
	boolean checkadmin() default false;
	
	
}
