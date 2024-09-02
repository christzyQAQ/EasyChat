package com.easychat.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easychat.entity.po.ContactApply;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.service.ContactApplyService;

/**
 * Controller
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@RestController
@RequestMapping("/contactApply/")
public class ContactApplyController extends BaseController {

	@Resource
	private ContactApplyService contactApplyService;

	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(ContactApplyQuery query) {
		return getSuccessResponseVO(contactApplyService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(ContactApply bean) {
		contactApplyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<ContactApply> listBean) {
		contactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<ContactApply> listBean) {
		contactApplyService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId查询
	 */
	@RequestMapping("selectByApplyId")
	public ResponseVO selectByApplyId(Integer applyId) {
		return getSuccessResponseVO(contactApplyService.selectByApplyId(applyId));
	}

	/**
	 * 根据ApplyId更新
	 */
	@RequestMapping("updateByApplyId")
	public ResponseVO updateByApplyId(ContactApply bean, Integer applyId) {
		contactApplyService.updateByApplyId(bean, applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId删除
	 */
	@RequestMapping("deleteByApplyId")
	public ResponseVO deleteByApplyId(Integer applyId) {
		contactApplyService.deleteByApplyId(applyId);
		return getSuccessResponseVO(null);
	}

}