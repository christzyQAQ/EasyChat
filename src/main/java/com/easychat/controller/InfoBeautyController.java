package com.easychat.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easychat.entity.po.InfoBeauty;
import com.easychat.entity.query.InfoBeautyQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.InfoBeautyService;

/**
 * Controller
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
@RestController
@RequestMapping("/infoBeauty/")
public class InfoBeautyController extends BaseController {

	@Resource
	private InfoBeautyService infoBeautyService;

	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(InfoBeautyQuery query) {
		return getSuccessResponseVO(infoBeautyService.findListByPage(query));
	}

	/**z
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(InfoBeauty bean) {
		infoBeautyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<InfoBeauty> listBean) {
		infoBeautyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<InfoBeauty> listBean) {
		infoBeautyService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询
	 */
	@RequestMapping("selectById")
	public ResponseVO selectById(Integer id) {
		return getSuccessResponseVO(infoBeautyService.selectById(id));
	}

	/**
	 * 根据Id更新
	 */
	@RequestMapping("updateById")
	public ResponseVO updateById(InfoBeauty bean, Integer id) {
		infoBeautyService.updateById(bean, id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("deleteById")
	public ResponseVO deleteById(Integer id) {
		infoBeautyService.deleteById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId查询
	 */
	@RequestMapping("selectByUserId")
	public ResponseVO selectByUserId(String userId) {
		return getSuccessResponseVO(infoBeautyService.selectByUserId(userId));
	}

	/**
	 * 根据UserId更新
	 */
	@RequestMapping("updateByUserId")
	public ResponseVO updateByUserId(InfoBeauty bean, String userId) {
		infoBeautyService.updateByUserId(bean, userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId删除
	 */
	@RequestMapping("deleteByUserId")
	public ResponseVO deleteByUserId(String userId) {
		infoBeautyService.deleteByUserId(userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询
	 */
	@RequestMapping("selectByEmail")
	public ResponseVO selectByEmail(String email) {
		return getSuccessResponseVO(infoBeautyService.selectByEmail(email));
	}

	/**
	 * 根据Email更新
	 */
	@RequestMapping("updateByEmail")
	public ResponseVO updateByEmail(InfoBeauty bean, String email) {
		infoBeautyService.updateByEmail(bean, email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email删除
	 */
	@RequestMapping("deleteByEmail")
	public ResponseVO deleteByEmail(String email) {
		infoBeautyService.deleteByEmail(email);
		return getSuccessResponseVO(null);
	}

}