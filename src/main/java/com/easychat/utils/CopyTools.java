package com.easychat.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 20243:02:29 PM
* @ClassName:CopyTools.java

*/
public class CopyTools {



	/**
	 * 将集合对象List<S>的属性快速复制到集合对象List<T>
	 * 
	 * @param <T> 目标对象集合
	 * @param <S> 源对象集合
	 * @param sList
	 * @param classz
	 * @return List<T>
	 */
	public static <T,S> List<T> CopyList(List<S> sList,Class classz) {
		List<T> list =new ArrayList<T>();
		for(S s:sList) {
			T t=null; 
		
		try {
		 t = (T) classz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(s, t);
		list.add(t);
		}
		return list;
		
	}
	
	/**
	 * copy 单个对象S到单个对象T
	 * 
	 * @param <T>
	 * @param <S>
	 * @param s
	 * @param classz
	 * @return t
	 */
	public static <T,S> T copy(S s,Class<T> classz) {
		T t=null;
		
		try {
			t= classz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(s, t);
		
		return t;
		
	}
	
}
